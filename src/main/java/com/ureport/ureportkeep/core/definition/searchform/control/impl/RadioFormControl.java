package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2023/1/10 21:00
 * @Description:
 **/
public class RadioFormControl extends FormControl {

    private final String radioInput = "<input type=\"radio\" name=\"%s\" value=\"%s\" title=\"%s\" %s>";

    private List<Option> options;

    @Override
    public String toHtml(RenderContext context) {
        String name = super.getName();

        if (CollectionUtils.isEmpty(options)) {
            return String.format(radioInput, name, "", "请选择", "checked");
        }

        StringBuffer html = new StringBuffer();
        for (Option option : options) {
            html.append(String.format(radioInput, name, option.getValue(), option.getLabel(), ""));
        }
        return html.toString();
    }

    @Override
    public String initJs(RenderContext context) {
        String name = super.getName();
        super.setSelectorId("input[name='" + name + "']:checked");
        return super.initJs(context);
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
