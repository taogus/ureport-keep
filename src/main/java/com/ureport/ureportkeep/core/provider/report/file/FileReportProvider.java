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
package com.ureport.ureportkeep.core.provider.report.file;

import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.provider.report.ReportFile;
import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.util.*;

/**
 * @author Jacky.gao
 * @since 2017年2月11日
 */
@Component
public class FileReportProvider implements ReportProvider, ApplicationContextAware {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    private String prefix = "file:";
    @Value("${ureport.fileStoreDir}")
    private String fileStoreDir;
    @Value("${ureport.disableFileProvider}")
    private boolean disabled;

    @Override
    public InputStream loadReport(String file) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length(), file.length());
        }
        String fullPath = fileStoreDir + "/" + file;
		logger.info(fullPath);
        try {
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public void deleteReport(String file) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length(), file.length());
        }
        String fullPath = fileStoreDir + "/" + file;
        File f = new File(fullPath);
        if (f.exists()) {
            f.delete();
        }
    }

    @Override
    public List<ReportFile> getReportFiles() {
        File file = new File(fileStoreDir);
        List<ReportFile> list = new ArrayList<ReportFile>();
        for (File f : file.listFiles()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(f.lastModified());
            list.add(new ReportFile(f.getName(), calendar.getTime()));
        }
        Collections.sort(list, new Comparator<ReportFile>() {
            @Override
            public int compare(ReportFile f1, ReportFile f2) {
                return f2.getUpdateDate().compareTo(f1.getUpdateDate());
            }
        });
        return list;
    }

    @Override
    public String getName() {
        return "服务器文件系统";
    }

    @Override
    public void saveReport(String file, String content) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length(), file.length());
        }
        String fullPath = fileStoreDir + "/" + file;
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(new File(fullPath));
            IOUtils.write(content, outStream, "utf-8");
        } catch (Exception ex) {
            throw new ReportException(ex);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean disabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setFileStoreDir(String fileStoreDir) {
        this.fileStoreDir = fileStoreDir;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        File file = new File(fileStoreDir);
        if (file.exists()) {
            return;
        }
        if (applicationContext instanceof WebApplicationContext) {
			String basePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\";
            fileStoreDir = basePath + fileStoreDir;
            file = new File(fileStoreDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
}
