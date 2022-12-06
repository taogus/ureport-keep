package com.ureport.ureportkeep.core.parser.json.config.border;

/**
 * @Author: summer
 * @Date: 2022/12/2 21:15
 * @Description:
 **/
public class BorderRangeModel implements BorderJsonModel {
    private static final long serialVersionUID = 1L;

    /**
     * 列范围
     */
    private Integer[] column;

    /**
     * 行范围
     */
    private Integer[] row;

    public Integer[] getColumn() {
        return column;
    }

    public void setColumn(Integer[] column) {
        this.column = column;
    }

    public Integer[] getRow() {
        return row;
    }

    public void setRow(Integer[] row) {
        this.row = row;
    }
}
