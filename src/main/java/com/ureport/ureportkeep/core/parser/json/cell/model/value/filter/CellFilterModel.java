package com.ureport.ureportkeep.core.parser.json.cell.model.value.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.expression.model.condition.Join;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 16:40
 * @Description: 单元格过滤条件model
 **/
public class CellFilterModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private String property;

    private Join join;

    private String op;

    @JsonProperty("value")
    private String express;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }
}
