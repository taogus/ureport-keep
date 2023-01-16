package com.ureport.ureportkeep.controller.preview;

import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.export.html.HtmlReport;
import com.ureport.ureportkeep.core.export.html.SearchFormData;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: summer
 * @Date: 2022/12/29 20:30
 * @Description:
 **/
@Controller
@RequestMapping("/view/html")
public class HtmlController extends AbstractReportBasicController {

    /**
     * 输出HTML格式
     *
     * @param model
     * @param file
     * @param parameters
     * @return
     */
    @GetMapping("/{file}")
    public String html(Model model, @PathVariable("file") String file, @RequestParam Map<String, Object> parameters) {
        Map<String, Object> result = htmlBuild(file, parameters);
        model.addAllAttributes(result);
        return "html-view";
    }

    /**
     * 加载报表
     *
     * @param file
     * @param parameters
     * @return
     */
    @PostMapping("/load/{file}")
    public R load(@PathVariable("file") String file, @RequestParam Map<String, Object> parameters) {
        Map<String, Object> result = htmlBuild(file, parameters);
        return R.ok().success(result);
    }

    /**
     * html结构构建
     *
     * @param file
     * @param parameters
     * @return
     */
    private Map<String, Object> htmlBuild(@PathVariable("file") String file, @RequestParam Map<String, Object> parameters) {
        if (StringUtils.isBlank(file)) {
            throw new ReportComputeException("报表文件为空");
        }

        Map<String, Object> htmlResult = new HashMap<>();
        HtmlReport htmlReport = null;
        try {
            htmlResult.put("title", "报表");
            htmlReport = super.loadReport(file, "0", parameters);
        } catch (Exception e) {
            e.printStackTrace();

            htmlResult.put("content",
                    "<fieldset class=\"layui-elem-field\">\n" +
                            "  <legend>错误信息如下</legend>\n" +
                            "  <div class=\"layui-field-box\">\n" +
                            "    " + e.getLocalizedMessage() + "\n" +
                            "  </div>\n" +
                            "</fieldset>");
            htmlResult.put("error", true);
            htmlResult.put("formJs", "");
            htmlResult.put("formHtml", "");

            return htmlResult;
        }
        SearchFormData formData = htmlReport.getSearchFormData();
        htmlResult.put("formHtml", Optional.ofNullable(formData).map(SearchFormData::getHtml).orElse(""));
        htmlResult.put("formJs", Optional.ofNullable(formData).map(SearchFormData::getJs).orElse(""));

        htmlResult.put("content", htmlReport.getContent());
        htmlResult.put("style", htmlReport.getStyle());
        htmlResult.put("reportAlign", htmlReport.getReportAlign());
        htmlResult.put("totalPage", htmlReport.getTotalPage());
        htmlResult.put("totalPageWithCol", htmlReport.getTotalPageWithCol());
        htmlResult.put("pageIndex", htmlReport.getPageIndex());
        htmlResult.put("error", false);
        htmlResult.put("file", file);
        htmlResult.put("intervalRefreshValue", htmlReport.getHtmlIntervalRefreshValue());

        return htmlResult;
    }

}
