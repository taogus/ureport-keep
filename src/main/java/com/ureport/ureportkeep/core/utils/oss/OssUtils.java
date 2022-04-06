package com.ureport.ureportkeep.core.utils.oss;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ureport.ureportkeep.core.utils.ReportProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/4/3 20:13
 * @Description:
 **/
@Component
public class OssUtils {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private ReportProperties reportProperties;

    private OssProperties ossProperties;

    /**
     * 上传文件
     * @param objectName 文件名称
     * @throws Exception
     */
    public PutObjectResult putObject(String objectName, String content) throws Exception {
        return amazonS3.putObject(ossProperties.getBucketName(), objectName, content);
    }

    /**
     * 获取文件
     * @param objectName 文件名称
     * @return 二进制流
     */

    public S3Object getObject(String objectName) {
        return amazonS3.getObject(ossProperties.getBucketName(), objectName);
    }

    /**
     * 获取路径下的所有文件
     *
     * @param prefix
     * @return
     */
    public List<S3ObjectSummary> getObjects(String prefix) {
        ListObjectsV2Result results = amazonS3.listObjectsV2(ossProperties.getBucketName(), prefix);
        return results.getObjectSummaries();
    }

    /**
     * 删除文件
     * @param objectName 文件名称
     * @throws Exception
     *
     */
    public void removeObject(String objectName) throws Exception {
        amazonS3.deleteObject(ossProperties.getBucketName(), objectName);
    }

    @PostConstruct
    public void init() {
        ossProperties = reportProperties.getOss();
    }

}
