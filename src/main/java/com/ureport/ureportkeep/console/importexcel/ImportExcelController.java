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
package com.ureport.ureportkeep.console.importexcel;

import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.console.cache.TempObjectCache;
import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Jacky.gao
 * @since 2017年5月25日
 */
@Controller
@RequestMapping(value = "/import")
public class ImportExcelController extends AbstractReportBasicController {
    private List<ExcelParser> excelParsers = new ArrayList<ExcelParser>();

    public ImportExcelController() {
        excelParsers.add(new HSSFExcelParser());
        excelParsers.add(new XSSFExcelParser());
    }

     /**
     * 导入excel
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public R execute(MultipartHttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, MultipartFile> fileMap = req.getFileMap();
        ReportDefinition report = null;
        String errorInfo = null;
        try {
			for (MultipartFile file : fileMap.values()) {
				if (file.isEmpty()) {
					continue;
				}

				InputStream inputStream = null;
				try {
					String name = file.getOriginalFilename().toLowerCase();
					inputStream = file.getInputStream();
					for (ExcelParser parser : excelParsers) {
						if (parser.support(name)) {
							report = parser.parse(inputStream);
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(inputStream != null) {
						inputStream.close();
					}
					break;
				}
			}
			if (report == null)
            	errorInfo = "请选择一个合法的Excel导入";

        } catch (Exception e) {
            e.printStackTrace();
            errorInfo = e.getMessage();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (report != null) {
            result.put("result", true);
            TempObjectCache.putObject("classpath:static/template/template.ureport.xml", report);
        } else {
            result.put("result", false);
            if (errorInfo != null) {
                result.put("errorInfo", errorInfo);
            }
        }

        return R.ok().success(result);
    }

}
