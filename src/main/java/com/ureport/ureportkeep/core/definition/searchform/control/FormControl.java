package com.ureport.ureportkeep.core.definition.searchform.control;

import com.ureport.ureportkeep.core.definition.searchform.Component;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: summer
 * @Date: 2023/1/9 21:08
 * @Description:
 **/
public abstract class FormControl implements Component {

    /**
     * 默认值
     */
    private Object defaultValue;

    /**
     * 显示标题
     */
    private String label;

    /**
     * 绑定参数
     */
    private String name;

    /**
     * 选择id
     */
    private String selectorId;

    public void setSelectorId(String selectorId) {
        this.selectorId = selectorId;
    }

    public String builderControlId() {
        return "cd_" + name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public String initJs(RenderContext context) {
        return basicJs();
    }

    /**
     * 基础js
     *
     * @return
     */
    protected String basicJs() {
        if (StringUtils.isEmpty(selectorId)) {
            selectorId = "#" + builderControlId();
        }

        StringBuffer js = new StringBuffer();
        js.append("formControlFuns.push(");
        js.append(" function(){");
        js.append("     if(''==='"+ getName() +"'){");
        js.append("         layer.open({");
        js.append("             title: '错误提示', ");
        js.append("             content: '文本框未绑定查询参数名，不能进行查询操作!' ");
        js.append("         });");
        js.append("     } else { ");
        js.append("         return {");
        js.append("             "+ getName() +": ");
        js.append("             $(\"").append(selectorId).append("\").val()");
        js.append("         }");
        js.append("     }");
        js.append(" }");
        js.append(")");

        return js.toString();
    }
}
