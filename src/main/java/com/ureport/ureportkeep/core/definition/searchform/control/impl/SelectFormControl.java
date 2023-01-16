package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.parser.json.enums.SelectorFormValueType;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:18
 * @Description:
 **/
public class SelectFormControl extends FormControl {

    private List<Option> options;

    private SelectorFormValueType valueType;

    private String datasetName;

    private String fieldName;

    private String showFieldName;

    @Override
    public String toHtml(RenderContext context) {
        return null;
    }

    @Override
    public String initJs(RenderContext context) {
        return null;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
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
