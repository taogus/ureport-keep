package com.ureport.ureportkeep.core.parser.json.cell.parse.value.impl;

import com.ureport.ureportkeep.core.definition.Order;
import com.ureport.ureportkeep.core.definition.value.DatasetValue;
import com.ureport.ureportkeep.core.definition.value.Value;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Condition;
import com.ureport.ureportkeep.core.expression.model.Op;
import com.ureport.ureportkeep.core.expression.model.condition.PropertyExpressionCondition;
import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.ValueModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.filter.CellFilterModel;
import com.ureport.ureportkeep.core.parser.json.cell.parse.value.ValueJsonParse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/12/24 15:23
 * @Description:
 **/
public class DatasetValueJsonParse implements ValueJsonParse {
    @Override
    public Value parse(CellModel parseModel) {
        ValueModel valueModel = parseModel.getValue();

        DatasetValue value=new DatasetValue();
        value.setAggregate(valueModel.getAggregate());
        value.setDatasetName(valueModel.getDatasetName());
        value.setProperty(valueModel.getProperty());
        // 缺少排序配置
        value.setOrder(Order.none);

        List<Condition> conditions=new ArrayList<Condition>();
        PropertyExpressionCondition topCondition=null;
        PropertyExpressionCondition prevCondition=null;

        value.setConditions(conditions);
        List<CellFilterModel> filters = valueModel.getFilters();
        for (CellFilterModel filter : filters) {
            String op = filter.getOp();
            String express = filter.getExpress();

            PropertyExpressionCondition condition = new PropertyExpressionCondition();
            condition.setLeftProperty(filter.getProperty());
            condition.setLeft(filter.getProperty());
            condition.setOperation(op);
            condition.setOp(Op.parse(op));
            condition.setRight(express);
            condition.setRightExpression(ExpressionUtils.parseExpression(express));
            condition.setJoin(filter.getJoin());
            conditions.add(condition);

            if (topCondition == null) {
                topCondition = condition;
                prevCondition = topCondition;
            } else {
                prevCondition.setNextCondition(condition);
                prevCondition.setJoin(condition.getJoin());
                prevCondition = condition;
            }
        }

        if (topCondition != null) {
            value.setCondition(topCondition);
        }
        return value;
    }

}
