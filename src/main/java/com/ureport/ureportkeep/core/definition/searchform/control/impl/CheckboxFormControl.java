package com.ureport.ureportkeep.core.definition.searchform.control.impl;

import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2023/1/10 21:37
 * @Description:
 **/
public class CheckboxFormControl extends FormControl {

    private final String checkInput = "<input type=\"checkbox\" name=\"%s\" title=\"%s\" value=\"%s\" lay-skin=\"primary\" %s>";

    private List<Option> options;

    @Override
    public String toHtml(RenderContext context) {
        String name = super.getName();

        if (CollectionUtils.isEmpty(options)) {
            return String.format(checkInput, name, "请选择", "", "checked");
        }

        StringBuffer html = new StringBuffer();
        for (Option option : options) {
            html.append(String.format(checkInput, name, option.getLabel(), option.getValue(), ""));
        }
        return html.toString();
    }

    @Override
    public String initJs(RenderContext context) {
        String name = super.getName();
        super.setSelectorId("input[name='" + name + "']:checked");

        StringBuffer js = new StringBuffer();
        js.append("formControlFuns.push(");
        js.append(" function(){");
        js.append("     if(''==='"+ getName() +"'){");
        js.append("         layer.open({");
        js.append("             title: '错误提示', ");
        js.append("             content: '文本框未绑定查询参数名，不能进行查询操作!' ");
        js.append("         });");
        js.append("     } else { ");
        js.append("         let values = '';");
        js.append("         $(\"input[name='" + name + "']:checked\").each(function(index, item) {");
        js.append("             if(values===''){");
        js.append("                 values+=$(item).val();");
        js.append("             } else {");
        js.append("                 values+=','+$(item).val();");
        js.append("             }");
        js.append("         });");
        js.append("         return {");
        js.append("             "+ getName() +": values");
        js.append("         }");
        js.append("     }");
        js.append(" }");
        js.append(")");

        return js.toString();
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
