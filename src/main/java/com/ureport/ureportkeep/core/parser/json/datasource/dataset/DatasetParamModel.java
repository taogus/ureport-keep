package com.ureport.ureportkeep.core.parser.json.datasource.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.definition.datasource.DataType;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/12/11 13:49
 * @Description: 数据集参数model
 **/
public class DatasetParamModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    /**
     * 数据类型
     */
    private DataType dataType;

    private String name;

    /**
     * 默认值
     */
    @JsonProperty("defaultValue")
    private Object value;

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
