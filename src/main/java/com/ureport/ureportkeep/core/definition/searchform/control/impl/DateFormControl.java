package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:40
 * @Description:
 **/
public class DateFormControl extends FormControl {

    private String dateFormat;

    @Override
    public String toHtml(RenderContext context) {
        return null;
    }

    @Override
    public String initJs(RenderContext context) {
        return null;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
