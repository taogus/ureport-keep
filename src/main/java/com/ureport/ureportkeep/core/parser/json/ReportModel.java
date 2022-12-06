package com.ureport.ureportkeep.core.parser.json;

import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.config.border.BorderInfoModel;
import com.ureport.ureportkeep.core.parser.json.config.global.ReportConfigModel;

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

    /**
     * 列宽
     */
    private List<Integer> colWidths;

    /**
     * 行高
     */
    private List<Integer> rowHeights;

    /**
     * 边框信息
     */
    private List<BorderInfoModel> borderInfo;

    /**
     * 全局配置
     */
    private ReportConfigModel reportConfig;

    public ReportConfigModel getReportConfig() {
        return reportConfig;
    }

    public void setReportConfig(ReportConfigModel reportConfig) {
        this.reportConfig = reportConfig;
    }

    public List<BorderInfoModel> getBorderInfo() {
        return borderInfo;
    }

    public void setBorderInfo(List<BorderInfoModel> borderInfo) {
        this.borderInfo = borderInfo;
    }

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

    public List<CellModel> getCellDatas() {
        return cellDatas;
    }

    public void setCellDatas(List<CellModel> cellDatas) {
        this.cellDatas = cellDatas;
    }
}
