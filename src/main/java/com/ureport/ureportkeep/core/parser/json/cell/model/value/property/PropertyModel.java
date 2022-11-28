package com.ureport.ureportkeep.core.parser.json.cell.model.value.property;

import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 17:48
 * @Description:
 **/
public class PropertyModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private String name;

    private PropertyConfigModel config;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyConfigModel getConfig() {
        return config;
    }

    public void setConfig(PropertyConfigModel config) {
        this.config = config;
    }
}
