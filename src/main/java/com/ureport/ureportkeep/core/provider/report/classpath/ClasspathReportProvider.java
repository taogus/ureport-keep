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
package com.ureport.ureportkeep.core.provider.report.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.provider.report.ReportFile;
import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import com.ureport.ureportkeep.core.utils.SpringContextUtils;

/**
 * @author Jacky.gao
 * @since 2016年12月4日
 */
@Component
public class ClasspathReportProvider implements ReportProvider  {

    @Override
    public InputStream loadReport(String file) {
        Resource resource = SpringContextUtils.getResource(file);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            String newFileName = null;
            if (file.startsWith("classpath:")) {
                newFileName = "classpath*:" + file.substring(10, file.length());
            } else if (file.startsWith("classpath*:")) {
                newFileName = "classpath:" + file.substring(11, file.length());
            }
            if (newFileName != null) {
                return SpringContextUtils.getResourceStream(file);
            }
            throw new ReportException(e);
        }
    }

    @Override
    public String getPrefix() {
        return "classpath";
    }

    @Override
    public void deleteReport(String file) {
    }

    @Override
    public void saveReport(String file, String content) {
    }

    @Override
    public List<ReportFile> getReportFiles() {
        return null;
    }

    @Override
    public boolean disabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
