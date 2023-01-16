package com.ureport.ureportkeep.core.definition.searchform.control;

import com.ureport.ureportkeep.core.definition.searchform.Component;

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
}
