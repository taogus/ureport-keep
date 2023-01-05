package com.ureport.ureportkeep.core.parser.json.config.rowcol;

import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.config.border.BorderInfoModel;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: summer
 * @Date: 2022/12/27 20:49
 * @Description: 行列配置
 **/
public class RowColConfig {

    /**
     * 列宽
     */
    private List<Integer> colWidths;

    /**
     * 行高
     */
    private List<Integer> rowHeights;

    /**
     * 隐藏列
     */
    private List<Integer> colHidden;

    /**
     * 隐藏行
     */
    private List<Integer> rowHidden;

    public List<Integer> getColWidths() {
        return colWidths;
    }

    public void setColWidths(List<Integer> colWidths) {
        this.colWidths = colWidths;
    }

    public List<Integer> getRowHeights() {
        return rowHeights;
    }

    public void setRowHeights(List<Integer> rowHeights) {
        this.rowHeights = rowHeights;
    }

    public List<Integer> getColHidden() {
        if (colHidden == null) {
            return new ArrayList<>();
        }
        return colHidden;
    }

    public void setColHidden(List<Integer> colHidden) {
        this.colHidden = colHidden;
    }

    public List<Integer> getRowHidden() {
        if (rowHidden == null) {
            return new ArrayList<>();
        }
        return rowHidden;
    }

    public void setRowHidden(List<Integer> rowHidden) {
        this.rowHidden = rowHidden;
    }

    public void buildRowHeight(List<BorderInfoModel> borderInfo, List<CellModel> cellDatas) {
        int maxRow = 0;
        if (!CollectionUtils.isEmpty(borderInfo)) {
            Integer maxBorderRow = borderInfo.stream().max(Comparator.comparing(b -> b.getRange().get(0).getRow()[1]))
                    .map(b -> b.getRange().get(0).getRow()[1]).orElse(0);

            if (maxBorderRow > maxRow) {
                maxRow = maxBorderRow;
            }
        }

        if (!CollectionUtils.isEmpty(cellDatas)) {
            Integer maxCellRow = cellDatas.stream().max(Comparator.comparing(c -> c.getPosition().getRow()))
                    .map(c -> c.getPosition().getRow()).orElse(0);

            if (maxCellRow > maxRow) {
                maxRow = maxCellRow;
            }
        }

        rowHeights = rowHeights.stream().skip(0).limit(maxRow + 1).collect(Collectors.toList());
    }

    public void buildColWidth(List<BorderInfoModel> borderInfo, List<CellModel> cellDatas) {
        int maxCol = 0;
        if (!CollectionUtils.isEmpty(borderInfo)) {
            int maxBorderCol = borderInfo.stream().max(Comparator.comparing(b -> b.getRange().get(0).getColumn()[1]))
                    .map(b -> b.getRange().get(0).getColumn()[1]).orElse(0);

            if (maxBorderCol > maxCol) {
                maxCol = maxBorderCol;
            }
        }

        if (!CollectionUtils.isEmpty(cellDatas)) {
            int maxCellCol = cellDatas.stream().max(Comparator.comparing(c -> c.getPosition().getCol()))
                    .map(c -> c.getPosition().getCol()).orElse(0);

            if (maxCellCol > maxCol) {
                maxCol = maxCellCol;
            }
        }
        colWidths = colWidths.stream().skip(0).limit(maxCol + 1).collect(Collectors.toList());
    }
}
