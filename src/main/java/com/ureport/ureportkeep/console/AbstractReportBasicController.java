package com.ureport.ureportkeep.console;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 夏天
 * @Date: 2022/1/14
 * Description:
 */
public abstract class AbstractReportBasicController {

    public final String PREVIEW_KEY="p";

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

}
