package com.ureport.ureportkeep.core.parser.json.cell.parse.value.impl;

import com.ureport.ureportkeep.core.definition.value.ExpressionValue;
import com.ureport.ureportkeep.core.definition.value.Value;
import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.cell.parse.value.ValueJsonParse;

/**
 * @Author: summer
 * @Date: 2022/12/24 16:33
 * @Description:
 **/
public class ExpressionValueJsonParser implements ValueJsonParse {
    @Override
    public Value parse(CellModel parseModel) {
        ExpressionValue value=new ExpressionValue(parseModel.getValue().getValue());
        return value;
    }

}
