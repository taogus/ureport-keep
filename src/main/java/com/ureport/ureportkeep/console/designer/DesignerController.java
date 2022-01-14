package com.ureport.ureportkeep.console.designer;

import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.console.cache.TempObjectCache;
import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.export.ReportRender;
import com.ureport.ureportkeep.core.parser.ReportParser;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

/**
 * @Author: summer
 * @Date: 2022/1/12 21:20
 * @Description:
 **/
@Controller
@RequestMapping(value = "/designer")
public class DesignerController extends AbstractReportBasicController {

    @Autowired
    private ReportRender reportRender;

    @Autowired
    private ReportParser reportParser;

    /**
     * 报表设计器首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/designer", method = RequestMethod.GET)
    public String designer(HttpServletRequest request, Model model) {
        //application
        model.addAttribute("contextPath", request.getContextPath());

        return "designer";
    }

    /**
     * 加载报表表格
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/loadReport", method = RequestMethod.POST)
    public void loadReportTable(HttpServletRequest request, HttpServletResponse response) {
        String file = request.getParameter("file");
        if (file == null) {
            throw new ReportDesignException("Report file can not be null.");
        }

        file = ReportUtils.decodeFileName(file);
        Object obj = TempObjectCache.getObject(file);
        try {
            if (obj != null && obj instanceof ReportDefinition) {
                ReportDefinition reportDef = (ReportDefinition) obj;
                TempObjectCache.removeObject(file);
                writeObjectToJson(response, new ReportDefinitionWrapper(reportDef));
            } else {
                ReportDefinition reportDef = reportRender.parseReport(file);
                writeObjectToJson(response, new ReportDefinitionWrapper(reportDef));
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载报表表格
     *
     */
    @RequestMapping(value = "/savePreviewData", method = RequestMethod.POST)
    @ResponseBody
    public R savePreviewData(HttpServletRequest req) {
        String content = req.getParameter("content");
        content = decodeContent(content);
        InputStream inputStream = null;
        try {
            inputStream = IOUtils.toInputStream(content, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReportDefinition reportDef = reportParser.parse(inputStream, "p");
        reportRender.rebuildReportDefinition(reportDef);
        IOUtils.closeQuietly(inputStream);
        TempObjectCache.putObject(PREVIEW_KEY, reportDef);

        return R.ok();
    }

}
