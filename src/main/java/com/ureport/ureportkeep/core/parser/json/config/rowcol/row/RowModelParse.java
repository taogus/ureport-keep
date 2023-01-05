package com.ureport.ureportkeep.core.parser.json.config.rowcol.row;

import com.ureport.ureportkeep.core.definition.RowDefinition;
import com.ureport.ureportkeep.core.parser.json.JsonParse;
import com.ureport.ureportkeep.core.parser.json.config.rowcol.RowColConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/12/26 21:37
 * @Description: 行definition解析
 **/
public class RowModelParse implements JsonParse<List<RowDefinition>, RowColConfig> {
    @Override
    public List<RowDefinition> parse(RowColConfig parseModel) {
        List<Integer> rowHeights = parseModel.getRowHeights();
        List<Integer> rowHidden = parseModel.getRowHidden();
        List<RowDefinition> rowDefinitions = new ArrayList<>();
        RowDefinition row = null;
        for (int i = 0; i < rowHeights.size(); i++) {
            row = new RowDefinition();
            row.setRowNumber(rowHidden.contains(i) ? 0 : i + 1);
            row.setHeight(rowHeights.get(i));

            rowDefinitions.add(row);
        }

        return rowDefinitions;
    }
}
