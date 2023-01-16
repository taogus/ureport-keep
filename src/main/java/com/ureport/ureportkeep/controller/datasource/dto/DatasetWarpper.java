package com.ureport.ureportkeep.controller.datasource.dto;

import com.ureport.ureportkeep.core.parser.json.utils.DatasetJsonParseUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/7/20 20:47
 * @Description:
 **/
public class DatasetWarpper extends DataSourceConnectDto {

    private String sql;

    private String parseSql;

    private Map<String, Object> params;

    private List<DatasetParamDto> paramsValues;

    public List<DatasetParamDto> getParamsValues() {
        return paramsValues;
    }

    public void setParamsValues(List<DatasetParamDto> paramsValues) {
        this.paramsValues = paramsValues;
    }

    public String getSql() {
        if (!StringUtils.isEmpty(parseSql)) {
            return this.parseSql;
        }

        this.parseSql = DatasetJsonParseUtils.convertSQL(this.sql);
        return this.parseSql;
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
