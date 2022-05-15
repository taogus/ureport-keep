package com.ureport.ureportkeep.controller.designer.dto;

import com.sun.istack.internal.NotNull;

/**
 * @Author: summer
 * @Date: 2022/5/15 21:17
 * @Description:
 **/
public class ReportManageDto {

    private String reportJson;

    /**
     * 储存的系统
     */
    @NotNull
    private String reportProvider;

    public String getReportJson() {
        return reportJson;
    }

    public void setReportJson(String reportJson) {
        this.reportJson = reportJson;
    }

    public String getReportProvider() {
        return reportProvider;
    }

    public void setReportProvider(String reportProvider) {
        this.reportProvider = reportProvider;
    }
}
