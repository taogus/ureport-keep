package com.ureport.ureportkeep.controller.datasource.dto;

import com.ureport.ureportkeep.core.definition.datasource.DataType;

/**
 * @Author: summer
 * @Date: 2022/7/31 9:56
 * @Description:
 **/
public class DatasetParamDto {

    private String name;

    private DataType type;

    private String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
