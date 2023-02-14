package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.Utils;
import com.ureport.ureportkeep.core.build.Dataset;
import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.exception.DatasetUndefinitionException;
import com.ureport.ureportkeep.core.parser.json.enums.SelectorFormValueType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:18
 * @Description:
 **/
public class SelectFormControl extends FormControl {

    private final String defaultOption = "<option value='' selected>请选择</option>";

    private List<Option> options;

    private SelectorFormValueType valueType;

    private String datasetName;

    private String fieldName;

    private String showFieldName;

    @Override
    public String toHtml(RenderContext context) {
        String name = super.getName();
        String id = super.builderControlId();
        String paramValue = Optional.ofNullable(context.getParameter(name)).map(p -> p.toString()).orElse("");

        StringBuffer html = new StringBuffer();
        html.append("<select id=\"" + id + "\" name=\"" + name + "\" lay-verify=\"\">");
        if (SelectorFormValueType.dataset.equals(valueType) && !StringUtils.isEmpty(datasetName)) {
            Dataset ds = context.getDataset(datasetName);
            if (ds == null) {
                throw new DatasetUndefinitionException(datasetName);
            }

            for (Object obj : ds.getData()) {
                Object datasetLabel = Utils.getProperty(obj, showFieldName);
                Object datasetValue = Utils.getProperty(obj, fieldName);
                String selected = datasetValue.equals(paramValue) ? "selected" : "";
                html.append("<option value='" + datasetValue + "' " + selected + ">" + datasetLabel + "</option>");
            }

            if (CollectionUtils.isEmpty(ds.getData())) {
                html.append(defaultOption);
            }
        } else {
            for (Option option : options) {
                String value = option.getValue();
                String optionLabel = option.getLabel();

                if (StringUtils.isEmpty(optionLabel)) {
                    continue;
                }

                String selected = value.equals(paramValue) ? "selected" : "";
                html.append("<option value='" + value + "' " + selected + ">" + option.getLabel() + "</option>");
            }

            if (CollectionUtils.isEmpty(options)) {
                html.append(defaultOption);
            }
        }
        html.append("</select>");
        return html.toString();
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
