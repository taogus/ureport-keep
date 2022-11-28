package com.ureport.ureportkeep.core.parser.json.cell.model.value.property;

import com.ureport.ureportkeep.core.definition.Scope;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 17:50
 * @Description:
 **/
public class PropertyConfigModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    private Scope scope;

    private Object value;

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
