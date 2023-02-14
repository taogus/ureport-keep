package com.ureport.ureportkeep.controller.export;

import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.core.build.ReportBuilder;
import com.ureport.ureportkeep.core.cache.CacheUtils;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.export.ExportConfigure;
import com.ureport.ureportkeep.core.export.ExportConfigureImpl;
import com.ureport.ureportkeep.core.export.ExportManager;
import com.ureport.ureportkeep.core.export.word.high.WordProducer;
import com.ureport.ureportkeep.core.model.Report;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: summer
 * @Date: 2023/1/21 16:17
 * @Description:
 **/
@Controller
@RequestMapping("/export/word")
public class WordController {

    private final String defaultFile = AbstractReportBasicController.KEEP_REPORT_VIEW_FILE;

    @Autowired
    private ReportBuilder reportBuilder;
    @Autowired
    private ExportManager exportManager;

    private WordProducer wordProducer = new WordProducer();

    @GetMapping("/{file}")
    public void download(HttpServletResponse response, @PathVariable("file") String file, @RequestParam Map<String, Object> params) {
        if (StringUtils.isBlank(file)) {
            throw new ReportComputeException("Report file can not be null.");
        }
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();

            String fileName = Optional.ofNullable(params.get("fileName")).map(f -> f.toString()).orElse(file) + ".docx";
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            if (file.equals(defaultFile)) {
                ReportDefinition reportDefinition = CacheUtils.getReportDefinition(defaultFile);
                if (reportDefinition == null) {
                    throw new ReportDesignException("Report data has expired,can not do export word.");
                }
                Report report = reportBuilder.buildReport(reportDefinition, params);
                wordProducer.produce(report, outputStream);
            } else {
                ExportConfigure configure = new ExportConfigureImpl(file, params, outputStream);
                exportManager.exportWord(configure);
            }
        } catch (Exception ex) {
            throw new ReportException(ex);
        } finally {
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
