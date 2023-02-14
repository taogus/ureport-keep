package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;

/**
 * @Author: summer
 * @Date: 2023/1/9 21:01
 * @Description:
 **/
public class TextFormControl extends FormControl {

    private final String textInput = "<input type='text' name='%s'  id='%s' class='layui-input'>";

    @Override
    public String toHtml(RenderContext context) {
        String name = super.getName();
        String id = super.builderControlId();

        return String.format(textInput, name, id);
    }
}
