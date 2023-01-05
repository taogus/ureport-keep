package com.ureport.ureportkeep.core.parser.json.config.border;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/12/2 21:07
 * @Description:
 **/
public class BorderInfoModel implements BorderJsonModel {

    private static final long serialVersionUID = 1L;

    /**
     * 边框类型
     */
    private String borderType;

    /**
     * 边框颜色
     */
    private String color;

    /**
     * 边框范围类型
     */
    private RangeType rangeType;

    /**
     * 边框线条粗细
     */
    private Integer style;

    /**
     * 单元格范围
     */
    private List<BorderRangeModel> range;

    public String getBorderType() {
        return borderType;
    }

    public void setBorderType(String borderType) {
        this.borderType = borderType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if ("#000".equals(color)) {
            color = "#000000";
        }
        this.color = color;
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    public void setRangeType(RangeType rangeType) {
        this.rangeType = rangeType;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public List<BorderRangeModel> getRange() {
        return range;
    }

    public void setRange(List<BorderRangeModel> range) {
        this.range = range;
    }
}
