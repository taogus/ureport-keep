package com.ureport.ureportkeep.console.html;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.console.MobileUtils;
import com.ureport.ureportkeep.console.cache.TempObjectCache;
import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.build.ReportBuilder;
import com.ureport.ureportkeep.core.build.paging.Page;
import com.ureport.ureportkeep.core.cache.CacheUtils;
import com.ureport.ureportkeep.core.chart.ChartData;
import com.ureport.ureportkeep.core.definition.Paper;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.definition.searchform.FormPosition;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.export.*;
import com.ureport.ureportkeep.core.export.html.HtmlProducer;
import com.ureport.ureportkeep.core.export.html.HtmlReport;
import com.ureport.ureportkeep.core.export.html.SearchFormData;
import com.ureport.ureportkeep.core.model.Report;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @author summer
 * @Date: 2022/1/14
 * Description: html预览控制器
 */
@Controller
@RequestMapping(value = "/html")
public class HtmlPreviewController extends AbstractReportBasicController {

    @Autowired
    private ExportManager exportManager;

    @Autowired
    private ReportBuilder reportBuilder;

    @Autowired
    private ReportRender reportRender;

    private HtmlProducer htmlProducer = new HtmlProducer();

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public String preview(HttpServletRequest request, HttpServletResponse response, Model model) {
        HtmlReport htmlReport = null;
        String errorMsg = null;
        try {
            htmlReport = loadReport(request);
        } catch (Exception ex) {
            if (!(ex instanceof ReportDesignException)) {
                ex.printStackTrace();
            }
            errorMsg = buildExceptionMessage(ex);
        }
        model.addAttribute("title", "预览报表");
        if (htmlReport == null) {
            model.addAttribute("content", "<div style='color:red'><strong>报表计算出错，错误信息如下：</strong><br><div style=\"margin:10px\">" + errorMsg + "</div></div>");
            model.addAttribute("error", true);
            model.addAttribute("searchFormJs", "");
            model.addAttribute("downSearchFormHtml", "");
            model.addAttribute("upSearchFormHtml", "");
        } else {
            SearchFormData formData = htmlReport.getSearchFormData();
            if (formData != null) {
                model.addAttribute("searchFormJs", formData.getJs());
                if (formData.getFormPosition().equals(FormPosition.up)) {
                    model.addAttribute("upSearchFormHtml", formData.getHtml());
                    model.addAttribute("downSearchFormHtml", "");
                } else {
                    model.addAttribute("downSearchFormHtml", formData.getHtml());
                    model.addAttribute("upSearchFormHtml", "");
                }
            } else {
                model.addAttribute("searchFormJs", "");
                model.addAttribute("downSearchFormHtml", "");
                model.addAttribute("upSearchFormHtml", "");
            }
            model.addAttribute("content", htmlReport.getContent());
            model.addAttribute("style", htmlReport.getStyle());
            model.addAttribute("reportAlign", htmlReport.getReportAlign());
            model.addAttribute("totalPage", htmlReport.getTotalPage());
            model.addAttribute("totalPageWithCol", htmlReport.getTotalPageWithCol());
            model.addAttribute("pageIndex", htmlReport.getPageIndex());
            model.addAttribute("chartDatas", convertJson(htmlReport.getChartDatas()));
            model.addAttribute("error", false);
            model.addAttribute("file", request.getParameter("_u"));
            model.addAttribute("intervalRefreshValue", htmlReport.getHtmlIntervalRefreshValue());
            String customParameters = buildCustomParameters(request);
            model.addAttribute("customParameters", customParameters);
            model.addAttribute("_t", "");
            Tools tools = null;
            if (MobileUtils.isMobile(request)) {
                tools = new Tools(false);
                tools.setShow(false);
            } else {
                String toolsInfo = request.getParameter("_t");
                if (StringUtils.isNotBlank(toolsInfo)) {
                    tools = new Tools(false);
                    if (toolsInfo.equals("0")) {
                        tools.setShow(false);
                    } else {
                        String[] infos = toolsInfo.split(",");
                        for (String name : infos) {
                            tools.doInit(name);
                        }
                    }
                    model.addAttribute("_t", toolsInfo);
                    model.addAttribute("hasTools", true);
                } else {
                    tools = new Tools(true);
                }
            }
            model.addAttribute("tools", tools);
        }

        return "html-preview.html";
    }

    @RequestMapping(value = "/loadPrintPages", method = RequestMethod.POST)
    public void loadPrintPages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file=req.getParameter("_u");
        file=decode(file);
        if(StringUtils.isBlank(file)){
            throw new ReportComputeException("Report file can not be null.");
        }
        Map<String, Object> parameters = buildParameters(req);
        ReportDefinition reportDefinition=null;
        if(file.equals(PREVIEW_KEY)){
            reportDefinition=(ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
            if(reportDefinition==null){
                throw new ReportDesignException("Report data has expired,can not do export excel.");
            }
        }else{
            reportDefinition=reportRender.getReportDefinition(file);
        }
        Report report=reportBuilder.buildReport(reportDefinition, parameters);
        Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
        if(chartMap.size()>0){
            CacheUtils.storeChartDataMap(chartMap);
        }
        FullPageData pageData= PageBuilder.buildFullPageData(report);
        StringBuilder sb=new StringBuilder();
        List<List<Page>> list=pageData.getPageList();
        Context context=report.getContext();
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                List<Page> columnPages=list.get(i);
                if(i==0){
                    String html=htmlProducer.produce(context,columnPages,pageData.getColumnMargin(),false);
                    sb.append(html);
                }else{
                    String html=htmlProducer.produce(context,columnPages,pageData.getColumnMargin(),false);
                    sb.append(html);
                }
            }
        }else{
            List<Page> pages=report.getPages();
            for(int i=0;i<pages.size();i++){
                Page page=pages.get(i);
                if(i==0){
                    String html=htmlProducer.produce(context,page, false);
                    sb.append(html);
                }else{
                    String html=htmlProducer.produce(context,page, true);
                    sb.append(html);
                }
            }
        }
        Map<String,String> map=new HashMap<String,String>();
        map.put("html", sb.toString());
        writeObjectToJson(resp, map);
    }

    @RequestMapping(value = "/loadPagePaper", method = RequestMethod.GET)
    public void loadPagePaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file=req.getParameter("_u");
        file=decode(file);
        if(StringUtils.isBlank(file)){
            throw new ReportComputeException("Report file can not be null.");
        }
        ReportDefinition report=null;
        if(file.equals(PREVIEW_KEY)){
            report=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
            if(report==null){
                throw new ReportDesignException("Report data has expired.");
            }
        }else{
            report=reportRender.getReportDefinition(file);
        }
        Paper paper=report.getPaper();
        writeObjectToJson(resp, paper);
    }

    private HtmlReport loadReport(HttpServletRequest req) {
        Map<String, Object> parameters = buildParameters(req);
        HtmlReport htmlReport = null;
        String file = req.getParameter("_u");
        file = decode(file);
        String pageIndex = req.getParameter("_i");
        if (StringUtils.isBlank(file)) {
            throw new ReportComputeException("Report file can not be null.");
        }
        if (file.equals(PREVIEW_KEY)) {
            ReportDefinition reportDefinition = (ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
            if (reportDefinition == null) {
                throw new ReportDesignException("Report data has expired,can not do preview.");
            }
            Report report = reportBuilder.buildReport(reportDefinition, parameters);
            Map<String, ChartData> chartMap = report.getContext().getChartDataMap();
            if (chartMap.size() > 0) {
                CacheUtils.storeChartDataMap(chartMap);
            }
            htmlReport = new HtmlReport();
            String html = null;
            if (StringUtils.isNotBlank(pageIndex) && !pageIndex.equals("0")) {
                Context context = report.getContext();
                int index = Integer.valueOf(pageIndex);
                SinglePageData pageData = PageBuilder.buildSinglePageData(index, report);
                List<Page> pages = pageData.getPages();
                if (pages.size() == 1) {
                    Page page = pages.get(0);
                    html = htmlProducer.produce(context, page, false);
                } else {
                    html = htmlProducer.produce(context, pages, pageData.getColumnMargin(), false);
                }
                htmlReport.setTotalPage(pageData.getTotalPages());
                htmlReport.setPageIndex(index);
            } else {
                html = htmlProducer.produce(report);
            }
            if (report.getPaper().isColumnEnabled()) {
                htmlReport.setColumn(report.getPaper().getColumnCount());
            }
            htmlReport.setChartDatas(report.getContext().getChartDataMap().values());
            htmlReport.setContent(html);
            htmlReport.setTotalPage(report.getPages().size());
            htmlReport.setStyle(reportDefinition.getStyle());
            htmlReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(), parameters));
            htmlReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
            htmlReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
        } else {
            if (StringUtils.isNotBlank(pageIndex) && !pageIndex.equals("0")) {
                int index = Integer.valueOf(pageIndex);
                htmlReport = exportManager.exportHtml(file, req.getContextPath(), parameters, index);
            } else {
                htmlReport = exportManager.exportHtml(file, req.getContextPath(), parameters);
            }
        }
        return htmlReport;
    }

    protected Map<String, Object> buildParameters(HttpServletRequest req) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        Enumeration<?> enumeration = req.getParameterNames();
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (obj == null) {
                continue;
            }
            String name = obj.toString();
            String value = req.getParameter(name);
            if (name == null || value == null || name.startsWith("_")) {
                continue;
            }
            parameters.put(name, decode(value));
        }
        return parameters;
    }

    private String buildExceptionMessage(Throwable throwable) {
        Throwable root = buildRootException(throwable);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        root.printStackTrace(pw);
        String trace = sw.getBuffer().toString();
        trace = trace.replaceAll("\n", "<br>");
        pw.close();
        return trace;
    }

    private String buildCustomParameters(HttpServletRequest req){
        StringBuilder sb=new StringBuilder();
        Enumeration<?> enumeration=req.getParameterNames();
        while(enumeration.hasMoreElements()){
            Object obj=enumeration.nextElement();
            if(obj==null){
                continue;
            }
            String name=obj.toString();
            String value=req.getParameter(name);
            if(name==null || value==null || (name.startsWith("_") && !name.equals("_n"))){
                continue;
            }
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append(name);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }

    private String convertJson(Collection<ChartData> data){
        if(data==null || data.size()==0){
            return "";
        }
        ObjectMapper mapper=new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(data);
            return json;
        } catch (Exception e) {
            throw new ReportComputeException(e);
        }
    }
}
