package com.ureport.ureportkeep.core.parser.json.parameter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.enums.ParameterControlType;
import com.ureport.ureportkeep.core.parser.json.enums.SelectorFormValueType;

import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2023/1/8 21:03
 * @Description:
 **/
public class ParameterFormJsonModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    private ParameterControlType type;

    /**
     * 参数绑定名
     */
    private String name;

    /**
     * 显示参数标题
     */
    private String label;

    @JsonProperty("value")
    private Object defaultValue;

    /**
     * 日期控件的显示格式
     */
    private String dateFormat;

    /**
     * 下拉框
     */
    private SelectorFormModel selectorFormModel;

    /**
     * 单选框值
     */
    private List<Map<String, Object>> radioValues;

    /**
     * 复选框值
     */
    private List<Map<String, Object>> checkboxValues;

    @JsonProperty("fixedValue")
    public void setValues(List<Map<String, Object>> values) {
        if (ParameterControlType.radio.equals(type)) {
            radioValues = values;
        } else if (ParameterControlType.selector.equals(type)) {
            if (selectorFormModel == null) {
                selectorFormModel = new SelectorFormModel();
            }

            selectorFormModel.setValues(values);
        } else if (ParameterControlType.checkbox.equals(type)) {
            checkboxValues = values;
        }
    }

    @JsonProperty("selectorValType")
    public void setValueType(SelectorFormValueType valueType) {
        if (selectorFormModel == null) {
            selectorFormModel = new SelectorFormModel();
        }

        selectorFormModel.setValueType(valueType);
    }

    @JsonProperty("datasetValue")
    public void setDatasetValue(Map<String, Object> datasetValue) {
        if (selectorFormModel == null) {
            selectorFormModel = new SelectorFormModel();
        }

        if (!SelectorFormValueType.dataset.equals(selectorFormModel.getValueType())) {
            return;
        }

        selectorFormModel.setDatasetName(datasetValue.get("datasetName").toString());
        selectorFormModel.setFieldName(datasetValue.get("fieldName").toString());
        selectorFormModel.setShowFieldName(datasetValue.get("showFieldName").toString());
    }

    public ParameterControlType getType() {
        return type;
    }

    public void setType(ParameterControlType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public SelectorFormModel getSelectorFormModel() {
        return selectorFormModel;
    }

    public void setSelectorFormModel(SelectorFormModel selectorFormModel) {
        this.selectorFormModel = selectorFormModel;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public List<Map<String, Object>> getRadioValues() {
        return radioValues;
    }

    public List<Map<String, Object>> getCheckboxValues() {
        return checkboxValues;
    }

}
