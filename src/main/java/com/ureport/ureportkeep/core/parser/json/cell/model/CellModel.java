package com.ureport.ureportkeep.core.parser.json.cell.model;

import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.barcode.BarCodeConfig;
import com.ureport.ureportkeep.core.parser.json.cell.model.image.ImageConfigModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.link.LinkConfigModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.ValueModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.property.ConditionPropertyItemModel;
import com.ureport.ureportkeep.core.parser.json.enums.CellType;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/11/27 11:52
 * @Description: 单元格json解析模型
 **/
public class CellModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    private String cellName;

    private CellType cellType;

    private FontStyleModel fontStyle;

    private ParentCellModel leftCell;

    private ParentCellModel topCell;

    private PositionModel position;

    /**
     * 超级链接配置
     */
    private LinkConfigModel linkConfig;

    /**
     * 图片单元格配置
     */
    private ImageConfigModel imageConfig;

    /**
     * 条形码配置
     */
    private BarCodeConfig barCodeConfig;

    /**
     * 单元格值
     */
    private ValueModel value;

    /**
     * 条件属性
     */
    private List<ConditionPropertyItemModel> conditionPropertyItems;

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public FontStyleModel getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(FontStyleModel fontStyle) {
        this.fontStyle = fontStyle;
    }

    public ParentCellModel getLeftCell() {
        return leftCell;
    }

    public void setLeftCell(ParentCellModel leftCell) {
        this.leftCell = leftCell;
    }

    public ParentCellModel getTopCell() {
        return topCell;
    }

    public void setTopCell(ParentCellModel topCell) {
        this.topCell = topCell;
    }

    public PositionModel getPosition() {
        return position;
    }

    public void setPosition(PositionModel position) {
        this.position = position;
    }

    public LinkConfigModel getLinkConfig() {
        return linkConfig;
    }

    public void setLinkConfig(LinkConfigModel linkConfig) {
        this.linkConfig = linkConfig;
    }

    public ImageConfigModel getImageConfig() {
        return imageConfig;
    }

    public void setImageConfig(ImageConfigModel imageConfig) {
        this.imageConfig = imageConfig;
    }

    public BarCodeConfig getBarCodeConfig() {
        return barCodeConfig;
    }

    public void setBarCodeConfig(BarCodeConfig barCodeConfig) {
        this.barCodeConfig = barCodeConfig;
    }

    public ValueModel getValue() {
        return value;
    }

    public void setValue(ValueModel value) {
        this.value = value;
    }

    public List<ConditionPropertyItemModel> getConditionPropertyItems() {
        return conditionPropertyItems;
    }

    public void setConditionPropertyItems(List<ConditionPropertyItemModel> conditionPropertyItems) {
        this.conditionPropertyItems = conditionPropertyItems;
    }
}
