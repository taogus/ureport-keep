package com.ureport.ureportkeep.controller.preview;

import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.export.html.HtmlReport;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/12/29 20:30
 * @Description:
 **/
@Controller
@RequestMapping("/view/html")
public class HtmlController extends AbstractReportBasicController {

    @GetMapping("/{file}")
    public String html(Model model, @PathVariable("file") String file, @RequestParam Map<String, Object> parameters) {
        if (StringUtils.isBlank(file)) {
            throw new ReportComputeException("报表文件为空");
        }

        HtmlReport htmlReport = null;
        try {
            model.addAttribute("title", "报表");
            htmlReport = super.loadReport(file, "0", parameters);
        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("content", "<div style='color:red'><strong>报表计算出错，错误信息如下：</strong><br><div style=\"margin:10px\">" + e.getLocalizedMessage() + "</div></div>");
            model.addAttribute("error", true);
            model.addAttribute("searchFormJs", "");
            model.addAttribute("downSearchFormHtml", "");
            model.addAttribute("upSearchFormHtml", "");

            return "html-view";
        }

        model.addAttribute("content", htmlReport.getContent());
        model.addAttribute("style", htmlReport.getStyle());
        model.addAttribute("reportAlign", htmlReport.getReportAlign());
        model.addAttribute("totalPage", htmlReport.getTotalPage());
        model.addAttribute("totalPageWithCol", htmlReport.getTotalPageWithCol());
        model.addAttribute("pageIndex", htmlReport.getPageIndex());
        model.addAttribute("error", false);
        model.addAttribute("file", file);
        model.addAttribute("intervalRefreshValue", htmlReport.getHtmlIntervalRefreshValue());
        return "html-view";
    }

}
