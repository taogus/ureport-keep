package com.ureport.ureportkeep.core.provider.report.oss;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.exception.ReportManageException;
import com.ureport.ureportkeep.core.provider.report.ReportFile;
import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import com.ureport.ureportkeep.core.utils.ReportProperties;
import com.ureport.ureportkeep.core.utils.oss.OssUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;

/**
 * @Author: summer
 * @Date: 2022/4/3 20:09
 * @Description:
 **/
@Component
public class OssReportProvider implements ReportProvider {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportProperties reportProperties;

    @Autowired
    private OssUtils ossUtils;

    @Override
    public InputStream loadReport(String file) {
        S3Object object = ossUtils.getObject(getPrefix() + file);
        return object.getObjectContent();
    }

    @Override
    public void deleteReport(String file) {
        try {
            ossUtils.removeObject(getPrefix() + file);
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    @Override
    public List<ReportFile> getReportFiles() {
        List<S3ObjectSummary> objects = new ArrayList<>();
        try {
            objects = ossUtils.getObjects(getPrefix());
        } catch (Exception e) {
            logger.error("云储存获取资源失败请检查配置: {}", e);
        }

        List<ReportFile> files = new ArrayList<>(objects.size());
        for (S3ObjectSummary object : objects) {
            files.add(new ReportFile(StringUtils.substringAfterLast(object.getKey(), "/"), object.getLastModified()));
        }

        Collections.sort(files, new Comparator<ReportFile>() {
            @Override
            public int compare(ReportFile f1, ReportFile f2) {
                return f2.getUpdateDate().compareTo(f1.getUpdateDate());
            }
        });
        return files;
    }

    @Override
    public void saveReport(String file, String content) {
        try {
            ossUtils.putObject(getPrefix() + file, content);
        } catch (Exception e) {
            logger.error("云储存保存报表文件错误，请检查配置: {}", e);
            throw new ReportManageException("云储存保存报表文件错误，请检查配置");
        }
    }

    @Override
    public String getName() {
        return "云储存系统";
    }

    @Override
    public boolean enabled() {
        return reportProperties.isOssEnable();
    }

    @Override
    public String getPrefix() {
        return Optional.ofNullable(reportProperties.getOss()).map(o -> o.getPrefix()).orElse("");
    }
}
