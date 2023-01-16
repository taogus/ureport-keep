package com.ureport.ureportkeep.core.parser.json.cell.parse.value.impl;

import com.ureport.ureportkeep.core.definition.value.Value;
import com.ureport.ureportkeep.core.definition.value.ZxingValue;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.barcode.BarCodeConfig;
import com.ureport.ureportkeep.core.parser.json.cell.parse.value.ValueJsonParse;
import com.ureport.ureportkeep.core.parser.json.enums.BarCodeFormat;
import com.ureport.ureportkeep.core.parser.json.enums.BarCodeSource;

/**
 * @Author: summer
 * @Date: 2022/12/24 16:47
 * @Description:
 **/
public class BarCodeValueJsonParse implements ValueJsonParse {
    @Override
    public Value parse(CellModel parseModel) {
        BarCodeConfig barCodeConfig = parseModel.getBarCodeConfig();
        BarCodeSource source = barCodeConfig.getSource();
        BarCodeFormat format = barCodeConfig.getFormat();

        ZxingValue value=new ZxingValue();
        value.setBarCodeSource(source);
        value.setWidth(barCodeConfig.getWidth());
        value.setHeight(barCodeConfig.getHeight());
        value.setBarCodeFormat(format);
        if (BarCodeSource.TEXT.equals(source)) {
            value.setText(barCodeConfig.getValue());
        } else {
            value.setExpr(barCodeConfig.getValue());
            Expression expression= ExpressionUtils.parseExpression(value.getExpr());
            value.setExpression(expression);
        }

        parseModel.setExpand(barCodeConfig.getExpand());
        return value;
    }
}
