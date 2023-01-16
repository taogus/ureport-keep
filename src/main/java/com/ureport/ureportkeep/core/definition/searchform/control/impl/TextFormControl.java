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
        String label = super.getLabel();
        String name = super.getName();
        String id = super.builderControlId();

        StringBuffer html = new StringBuffer();
        html.append("<label class=\"layui-form-label\">").append(label).append("</label>");
        html.append("<div class=\"layui-input-block\">");
        html.append(String.format(textInput, name, id));
        html.append("</div>");
        return html.toString();
    }

    @Override
    public String initJs(RenderContext context) {
        String name = super.getName();
        String id = super.builderControlId();

        StringBuffer js = new StringBuffer();
        js.append("formControlFuns.push(");
        js.append(" function(){");
        js.append("     if(''==='"+ super.getName() +"'){");
        js.append("         layer.open({");
        js.append("             title: '错误提示', ");
        js.append("             content: '配置各种参数，试试效果' ");
        js.append("         });");
        js.append("     } else { ");
        js.append("         return {");
        js.append("             "+ name +": ");
        js.append("             $('#").append(id).append("'.val()");
        js.append("         }");
        js.append("     }");
        js.append(" }");
        js.append("}");
        return js.toString();
    }
}
