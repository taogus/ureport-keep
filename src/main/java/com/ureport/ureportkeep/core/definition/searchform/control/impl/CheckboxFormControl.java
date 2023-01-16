package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2023/1/10 21:37
 * @Description:
 **/
public class CheckboxFormControl extends FormControl {

    private List<Option> options;

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
}
