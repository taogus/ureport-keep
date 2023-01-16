package com.ureport.ureportkeep.core.parser.json.cell.model.value.property;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.enums.ConditionLeftValueType;

/**
 * @Author: summer
 * @Date: 2022/11/27 17:31
 * @Description:
 **/
public class ConditionModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private String field;

    @JsonProperty("option")
    private String op;

    private String relation;

    @JsonProperty("type")
    private ConditionLeftValueType leftValueType;

    private String valExpress;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public ConditionLeftValueType getLeftValueType() {
        return leftValueType;
    }

    public void setLeftValueType(ConditionLeftValueType leftValueType) {
        this.leftValueType = leftValueType;
    }

    public String getValExpress() {
        return valExpress;
    }

    public void setValExpress(String valExpress) {
        this.valExpress = valExpress;
    }
}
