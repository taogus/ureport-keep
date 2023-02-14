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
import com.ureport.ureportkeep.core.export.pdf.PdfProducer;
import com.ureport.ureportkeep.core.model.Report;
import org.apache.commons.lang3.StringUtils;
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
 * @Date: 2023/1/20 22:24
 * @Description: pdf导出控制器
 **/
@Controller
@RequestMapping("/export/pdf")
public class PDFController {

    private final String defaultFile = AbstractReportBasicController.KEEP_REPORT_VIEW_FILE;

    @Autowired
    private ReportBuilder reportBuilder;

    @Autowired
    private ExportManager exportManager;

    private PdfProducer pdfProducer = new PdfProducer();

    @GetMapping("/show/{file}")
    public void show(HttpServletResponse response, @PathVariable("file") String file, @RequestParam Map<String, Object> params) {
        if (StringUtils.isBlank(file)) {
            throw new ReportComputeException("Report file can not be null.");
        }

        OutputStream outputStream = null;
        try {
            String fileName = Optional.ofNullable(params.get("fileName")).map(f -> f.toString()).orElse(file);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");

            outputStream = response.getOutputStream();
            if (file.equals(defaultFile)) {
                ReportDefinition reportDefinition = CacheUtils.getReportDefinition(defaultFile);
                if (reportDefinition == null) {
                    throw new ReportDesignException("Report data has expired,can not do export pdf.");
                }
                Report report = reportBuilder.buildReport(reportDefinition, params);
                pdfProducer.produce(report, outputStream);
            } else {
                ExportConfigure configure = new ExportConfigureImpl(file, params, outputStream);
                exportManager.exportPdf(configure);
            }
        } catch (Exception e) {
            throw new ReportException(e);
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
