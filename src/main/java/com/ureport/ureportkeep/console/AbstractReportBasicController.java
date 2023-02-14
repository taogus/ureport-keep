package com.ureport.ureportkeep.console;

import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.build.ReportBuilder;
import com.ureport.ureportkeep.core.build.paging.Page;
import com.ureport.ureportkeep.core.cache.CacheUtils;
import com.ureport.ureportkeep.core.chart.ChartData;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.export.ExportManager;
import com.ureport.ureportkeep.core.export.PageBuilder;
import com.ureport.ureportkeep.core.export.SinglePageData;
import com.ureport.ureportkeep.core.export.html.HtmlProducer;
import com.ureport.ureportkeep.core.export.html.HtmlReport;
import com.ureport.ureportkeep.core.model.Report;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 夏天
 * @Date: 2022/1/14
 * Description:
 */
public abstract class AbstractReportBasicController {

    public final String PREVIEW_KEY="p";

    public static final String KEEP_REPORT_VIEW_FILE = "k";

    @Autowired
    private ExportManager exportManager;

    @Autowired
    private ReportBuilder reportBuilder;

    private HtmlProducer htmlProducer = new HtmlProducer();

    protected String decodeContent(String content){
        if(content==null){
            return content;
        }
        try{
            content= URLDecoder.decode(content, "utf-8");
            return content;
        }catch(Exception ex){
            return content;
        }
    }

    protected String decode(String value){
        if(value==null){
            return value;
        }
        try{
            value=URLDecoder.decode(value, "utf-8");
            value=URLDecoder.decode(value, "utf-8");
            return value;
        }catch(Exception ex){
            return value;
        }
    }

    protected Throwable buildRootException(Throwable throwable){
        if(throwable.getCause()==null){
            return throwable;
        }
        return buildRootException(throwable.getCause());
    }

    protected String buildDownloadFileName(String reportFileName,String fileName,String extName){
        if(StringUtils.isNotBlank(fileName)){
            fileName=decode(fileName);
            if(!fileName.toLowerCase().endsWith(extName)){
                fileName=fileName+extName;
            }
            return fileName;
        }else{
            int pos=reportFileName.indexOf(":");
            if(pos>0){
                reportFileName=reportFileName.substring(pos+1,reportFileName.length());
            }
            pos=reportFileName.toLowerCase().indexOf(".ureport.xml");
            if(pos>0){
                reportFileName=reportFileName.substring(0,pos);
            }
            return "ureport-"+reportFileName+extName;
        }
    }

    protected Map<String, Object> buildParameters(HttpServletRequest req) {
        Map<String,Object> parameters=new HashMap<String,Object>();
        Enumeration<?> enumeration=req.getParameterNames();
        while(enumeration.hasMoreElements()){
            Object obj=enumeration.nextElement();
            if(obj==null){
                continue;
            }
            String name=obj.toString();
            String value=req.getParameter(name);
            if(name==null || value==null || name.startsWith("_")){
                continue;
            }
            parameters.put(name, decode(value));
        }
        return parameters;
    }

    /**
     * 加载报表html内容
     *
     * @param file
     * @param pageIndex
     * @param parameters
     * @return
     */
    public HtmlReport loadReport(String file, String pageIndex, Map<String, Object> parameters) {
        HtmlReport htmlReport = null;
        if (StringUtils.isBlank(file)) {
            throw new ReportComputeException("Report file can not be null.");
        }
        if (PREVIEW_KEY.equals(file) || KEEP_REPORT_VIEW_FILE.equals(file)) {
            ReportDefinition reportDefinition = CacheUtils.getReportDefinition(file);
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
                htmlReport = exportManager.exportHtml(file, parameters, index);
            } else {
                htmlReport = exportManager.exportHtml(file, parameters);
            }
        }
        return htmlReport;
    }

}
