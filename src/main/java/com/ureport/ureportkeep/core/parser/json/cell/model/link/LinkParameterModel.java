package com.ureport.ureportkeep.core.parser.json.cell.model.link;

import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 16:25
 * @Description:
 **/
public class LinkParameterModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private String name;

    private String expression;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
