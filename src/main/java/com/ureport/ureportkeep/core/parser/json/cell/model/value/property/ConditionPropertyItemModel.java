package com.ureport.ureportkeep.core.parser.json.cell.model.value.property;

import com.ureport.ureportkeep.core.parser.json.JsonModel;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/11/27 17:26
 * @Description: 条件属性model
 **/
public class ConditionPropertyItemModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private String itemName;

    /**
     * 条件
     */
    private List<ConditionModel> conditions;

    /**
     * 属性
     */
    private List<PropertyModel> propertys;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<ConditionModel> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionModel> conditions) {
        this.conditions = conditions;
    }

    public List<PropertyModel> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<PropertyModel> propertys) {
        this.propertys = propertys;
    }
}
