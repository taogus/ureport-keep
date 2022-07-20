package com.ureport.ureportkeep.controller.datasource.dto;

import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/7/20 20:47
 * @Description:
 **/
public class PreviewDataDto extends DataSourceConnectDto {

    private String sql;

    private Map<String, Object> params;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
