package com.ureport.ureportkeep.core.parser.json.cell.model.value.property;

import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.enums.ConditionPropertyItemType;

/**
 * @Author: summer
 * @Date: 2022/11/27 17:48
 * @Description:
 **/
public class PropertyModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private ConditionPropertyItemType name;

    private PropertyConfigModel config;

    public ConditionPropertyItemType getName() {
        return name;
    }

    public void setName(ConditionPropertyItemType name) {
        this.name = name;
    }

    public PropertyConfigModel getConfig() {
        return config;
    }

    public void setConfig(PropertyConfigModel config) {
        this.config = config;
    }
}
