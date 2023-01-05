package com.ureport.ureportkeep.core.parser.json.datasource.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/12/11 13:37
 * @Description: 数据集属性model
 **/
public class DatasetInfoModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    private String name;

    private String sql;

    @JsonProperty("children")
    private List<String> property;

    /**
     * 数据集参数
     */
    private List<DatasetParamModel> params;

    public List<DatasetParamModel> getParams() {
        return params;
    }

    public void setParams(List<DatasetParamModel> params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getProperty() {
        return property;
    }

    public void setProperty(List<Map<String, Object>> property) {
        List<String> propertys = new ArrayList<>();
        for (Map<String, Object> map : property) {
            Object name = map.get("name");
            if (name == null) {
                continue;
            }

            propertys.add(name.toString());
        }
        this.property = propertys;
    }


}
