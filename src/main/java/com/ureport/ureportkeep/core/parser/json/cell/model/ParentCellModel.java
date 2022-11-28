package com.ureport.ureportkeep.core.parser.json.cell.model;

import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 16:09
 * @Description:
 **/
public class ParentCellModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private int col;

    private int row;

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
