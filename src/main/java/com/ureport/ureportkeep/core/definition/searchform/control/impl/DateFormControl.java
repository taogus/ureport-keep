package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:40
 * @Description:
 **/
public class DateFormControl extends FormControl {

    private final String textInput  = "<input type=\"text\" class=\"layui-input\" name=\"%s\" id=\"%s\">";

    private String dateFormat;

    @Override
    public String toHtml(RenderContext context) {
        String name = super.getName();
        String id = super.builderControlId();

        return String.format(textInput, name, id);
    }

    @Override
    public String initJs(RenderContext context) {
        String id = super.builderControlId();

        StringBuffer js = new StringBuffer();
        js.append("laydate.render({\n" +
                "    elem: '#" + id + "'" +
                "    ,type: 'datetime'" +
                "    ,format: '" + dateFormat + "'" +
                "  });");

        js.append(super.initJs(context));
        return js.toString();
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
