package com.ureport.ureportkeep.core.parser.json.cell.parse.value.impl;

import com.ureport.ureportkeep.core.definition.value.SimpleValue;
import com.ureport.ureportkeep.core.definition.value.Value;
import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.ValueModel;
import com.ureport.ureportkeep.core.parser.json.cell.parse.value.ValueJsonParse;

/**
 * @Author: summer
 * @Date: 2022/12/24 15:18
 * @Description:
 **/
public class SimpleValueJsonParse implements ValueJsonParse {

    @Override
    public Value parse(CellModel parseModel) {
        ValueModel value = parseModel.getValue();
        String data = null;
        if (value != null) {
            data = parseModel.getValue().getValue();
        }
        SimpleValue simpleValue = new SimpleValue(data);
        return simpleValue;
    }

}
