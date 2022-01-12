/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.ureport.ureportkeep.console.word;

import com.ureport.ureportkeep.console.BaseServletAction;
import com.ureport.ureportkeep.console.cache.TempObjectCache;
import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.core.build.ReportBuilder;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.export.ExportConfigure;
import com.ureport.ureportkeep.core.export.ExportConfigureImpl;
import com.ureport.ureportkeep.core.export.ExportManager;
import com.ureport.ureportkeep.core.export.word.high.WordProducer;
import com.ureport.ureportkeep.core.model.Report;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2017年4月17日
 */
public class ExportWordServletAction extends BaseServletAction {
	private ReportBuilder reportBuilder;
	private ExportManager exportManager;
	private WordProducer wordProducer=new WordProducer();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{			
			buildWord(req, resp);
		}
	}
	
	public void buildWord(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		OutputStream outputStream=resp.getOutputStream();
		try {
			String fileName=req.getParameter("_n");
			fileName=buildDownloadFileName(file, fileName, ".docx");
			fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
			resp.setContentType("application/octet-stream;charset=ISO8859-1");
			resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
			Map<String, Object> parameters = buildParameters(req);
			if(file.equals(PREVIEW_KEY)){
				ReportDefinition reportDefinition=(ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export word.");
				}
				Report report=reportBuilder.buildReport(reportDefinition, parameters);
				wordProducer.produce(report, outputStream);
			}else{
				ExportConfigure configure=new ExportConfigureImpl(file,parameters,outputStream);
				exportManager.exportWord(configure);
			}			
		}catch(Exception ex) {
			throw new ReportException(ex);
		}finally {			
			outputStream.flush();
			outputStream.close();
		}
	}
	
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}
	public void setExportManager(ExportManager exportManager) {
		this.exportManager = exportManager;
	}

	@Override
	public String url() {
		return "/word";
	}
}
