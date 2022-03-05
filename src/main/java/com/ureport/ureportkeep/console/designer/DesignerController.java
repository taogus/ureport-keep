package com.ureport.ureportkeep.console.designer;

import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.console.cache.TempObjectCache;
import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.core.cache.CacheUtils;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.dsl.ReportParserLexer;
import com.ureport.ureportkeep.core.dsl.ReportParserParser;
import com.ureport.ureportkeep.core.export.ReportRender;
import com.ureport.ureportkeep.core.expression.ErrorInfo;
import com.ureport.ureportkeep.core.expression.ScriptErrorListener;
import com.ureport.ureportkeep.core.init.ReportProvidersInit;
import com.ureport.ureportkeep.core.parser.ReportParser;
import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: summer
 * @Date: 2022/1/12 21:20
 * @Description: 设计器控制器
 **/
@Controller
@RequestMapping(value = "/designer")
public class DesignerController extends AbstractReportBasicController {

    @Autowired
    private ReportRender reportRender;

    @Autowired
    private ReportParser reportParser;

    @Autowired
    private ReportProvidersInit reportProvidersInit;

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载报表表格
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

        try {
            TempObjectCache.putObject(PREVIEW_KEY, reportDef);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok();
    }

    /**
     * 加载报表
     *
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/loadReportProviders", method = RequestMethod.GET)
    public void loadReportProviders(HttpServletResponse resp) throws ServletException, IOException {
        writeObjectToJson(resp, reportProvidersInit.getReportProviders().stream().filter(r -> r.getName() != null).collect(Collectors.toList()));
    }

    /**
     * 保存报表文件
     *
     * @param req
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/saveReportFile", method = RequestMethod.POST)
    @ResponseBody
    public R saveReportFile(HttpServletRequest req) throws ServletException, IOException {
        String file = req.getParameter("file");
        file = ReportUtils.decodeFileName(file);
        String content = req.getParameter("content");
        content = decodeContent(content);
        ReportProvider targetReportProvider = null;
        for (ReportProvider provider : reportProvidersInit.getReportProviders()) {
            if (file.startsWith(provider.getPrefix())) {
                targetReportProvider = provider;
                break;
            }
        }
        if (targetReportProvider == null) {
            throw new ReportDesignException("File [" + file + "] not found available report provider.");
        }
        targetReportProvider.saveReport(file, content);
        InputStream inputStream = IOUtils.toInputStream(content, "utf-8");
        ReportDefinition reportDef = reportParser.parse(inputStream, file);
        reportRender.rebuildReportDefinition(reportDef);
        CacheUtils.cacheReportDefinition(file, reportDef);
        IOUtils.closeQuietly(inputStream);

        return R.ok();
    }

    /**
     * 表达式合法校验
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/scriptValidation", method = RequestMethod.POST)
    public void scriptValidation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(content);
        ReportParserLexer lexer = new ReportParserLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ReportParserParser parser = new ReportParserParser(tokenStream);
        ScriptErrorListener errorListener = new ScriptErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        parser.expression();
        List<ErrorInfo> infos = errorListener.getInfos();
        writeObjectToJson(resp, infos);
    }

    /**
     * 过滤条件表达式合法校验
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/conditionScriptValidation", method = RequestMethod.POST)
    public void conditionScriptValidation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(content);
        ReportParserLexer lexer = new ReportParserLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ReportParserParser parser = new ReportParserParser(tokenStream);
        ScriptErrorListener errorListener = new ScriptErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        parser.expr();
        List<ErrorInfo> infos = errorListener.getInfos();
        writeObjectToJson(resp, infos);
    }

    @RequestMapping(value = "/parseDatasetName", method = RequestMethod.POST)
    public void parseDatasetName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String expr = req.getParameter("expr");
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(expr);
        ReportParserLexer lexer = new ReportParserLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ReportParserParser parser = new ReportParserParser(tokenStream);
        parser.removeErrorListeners();
        ReportParserParser.DatasetContext ctx = parser.dataset();
        String datasetName = ctx.Identifier().getText();
        Map<String, String> result = new HashMap<String, String>();
        result.put("datasetName", datasetName);
        writeObjectToJson(resp, result);
    }

    @RequestMapping(value = "/deleteReportFile", method = RequestMethod.POST)
    public void deleteReportFile(HttpServletRequest req) throws ServletException, IOException {
        String file = req.getParameter("file");
        if (file == null) {
            throw new ReportDesignException("Report file can not be null.");
        }
        ReportProvider targetReportProvider = null;
        for (ReportProvider provider : reportProvidersInit.getReportProviders()) {
            if (file.startsWith(provider.getPrefix())) {
                targetReportProvider = provider;
                break;
            }
        }
        if (targetReportProvider == null) {
            throw new ReportDesignException("File [" + file + "] not found available report provider.");
        }
        targetReportProvider.deleteReport(file);
    }

}
