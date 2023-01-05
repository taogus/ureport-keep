package com.ureport.ureportkeep.core.parser.json.config.rowcol.col;

import com.ureport.ureportkeep.core.definition.ColumnDefinition;
import com.ureport.ureportkeep.core.parser.json.JsonParse;
import com.ureport.ureportkeep.core.parser.json.config.rowcol.RowColConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/12/27 20:38
 * @Description:
 **/
public class ColumnModelParse implements JsonParse<List<ColumnDefinition>, RowColConfig> {
    @Override
    public List<ColumnDefinition> parse(RowColConfig parseModel) {
        List<Integer> colWidths = parseModel.getColWidths();
        List<Integer> colHidden = parseModel.getColHidden();

        List<ColumnDefinition> columnDefinitions = new ArrayList<>();
        ColumnDefinition col = null;
        for (int i = 0; i < colWidths.size(); i++) {
            col = new ColumnDefinition();
            col.setColumnNumber(i + 1);
            col.setHide(colHidden.contains(i));
            col.setWidth(colWidths.get(i));

            columnDefinitions.add(col);
        }
        return columnDefinitions;
    }
}
