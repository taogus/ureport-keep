package com.ureport.ureportkeep.core.parser.json;

import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/11/27 18:10
 * @Description:
 **/
public class ReportModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    /**
     * 单元格值
     */
    private List<CellModel> cellDatas;

    public List<CellModel> getCellDatas() {
        return cellDatas;
    }

    public void setCellDatas(List<CellModel> cellDatas) {
        this.cellDatas = cellDatas;
    }
}
