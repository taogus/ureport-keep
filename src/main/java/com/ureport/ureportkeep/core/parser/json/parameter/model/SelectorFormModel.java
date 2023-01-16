package com.ureport.ureportkeep.core.parser.json.parameter.model;

import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.enums.SelectorFormValueType;

import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2023/1/8 21:22
 * @Description:
 **/
public class SelectorFormModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    private List<Map<String, Object>> values;

    private SelectorFormValueType valueType;

    private String datasetName;

    private String fieldName;

    private String showFieldName;

    public List<Map<String, Object>> getValues() {
        return values;
    }

    public void setValues(List<Map<String, Object>> values) {
        this.values = values;
    }

    public SelectorFormValueType getValueType() {
        return valueType;
    }

    public void setValueType(SelectorFormValueType valueType) {
        this.valueType = valueType;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getShowFieldName() {
        return showFieldName;
    }

    public void setShowFieldName(String showFieldName) {
        this.showFieldName = showFieldName;
    }
}
