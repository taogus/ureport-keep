package com.ureport.ureportkeep.core.parser.json;

import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.config.border.BorderInfoModel;
import com.ureport.ureportkeep.core.parser.json.config.global.ReportConfigModel;
import com.ureport.ureportkeep.core.parser.json.config.rowcol.RowColConfig;
import com.ureport.ureportkeep.core.parser.json.datasource.dataset.DatasetModel;
import com.ureport.ureportkeep.core.parser.json.datasource.model.DataSourceModel;
import com.ureport.ureportkeep.core.parser.json.utils.CellJsonParseUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: summer
 * @Date: 2022/11/27 18:10
 * @Description:
 **/
public class ReportModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private String reportName;

    /**
     * 单元格值
     */
    private List<CellModel> cellDatas;

    /**
     * 边框信息
     */
    private List<BorderInfoModel> borderInfo;

    /**
     * 行列配置
     */
    private RowColConfig rowColConfig;

    /**
     * 全局配置
     */
    private ReportConfigModel reportConfig;

    /**
     * 数据源
     */
    private List<DataSourceModel> dataSources;

    /**
     * 数据集
     */
    private List<DatasetModel> datasetDatas;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<DataSourceModel> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<DataSourceModel> dataSources) {
        this.dataSources = dataSources;
    }

    public List<DatasetModel> getDatasetDatas() {
        return datasetDatas;
    }

    public void setDatasetDatas(List<DatasetModel> datasetDatas) {
        this.datasetDatas = datasetDatas;
    }

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

    public RowColConfig getRowColConfig() {
        return rowColConfig;
    }

    public void setRowColConfig(RowColConfig rowColConfig) {
        this.rowColConfig = rowColConfig;
        this.rowColConfig.buildRowHeight(borderInfo, cellDatas);
        this.rowColConfig.buildColWidth(borderInfo, cellDatas);
        completeCellData();
    }

    public List<CellModel> getCellDatas() {
        return cellDatas;
    }

    public void setCellDatas(List<CellModel> cellDatas) {

        this.cellDatas = cellDatas;
    }

    /**
     * 补全单元格
     */
    private void completeCellData() {
        int maxRow = this.rowColConfig.getRowHeights().size();
        int maxCol = this.rowColConfig.getColWidths().size();

        Map<String, CellModel> mergeRemoveCells = cellDatas.stream().filter(c ->
                c.getFrontStyle() != null
                        && c.getFrontStyle().isMerge() && (c.getFrontStyle().getColSpan() <= 0 || c.getFrontStyle().getRowspan() <= 0)
        ).collect(Collectors.toMap(CellModel::getCellName, cell -> cell));
        cellDatas.removeAll(mergeRemoveCells.values());

        Set<String> cellSet = cellDatas.stream().map(c -> c.getCellName()).collect(Collectors.toSet());
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                String cellName = CellJsonParseUtils.convertCellName(row + 1, col);
                if (cellSet.contains(cellName) || mergeRemoveCells.containsKey(cellName)) {
                    continue;
                }

                cellDatas.add(new CellModel(row, col));
            }
        }
    }
}
