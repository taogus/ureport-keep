package com.ureport.ureportkeep.core.parser.json.cell.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

import java.util.Map;
import java.util.Optional;

/**
 * @Author: summer
 * @Date: 2022/11/27 12:01
 * @Description:
 **/
public class FrontStyleModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    @JsonProperty("v")
    private String value;

    @JsonProperty("bg")
    private String bgColor;

    @JsonProperty("ff")
    private int FontFormat;

    @JsonProperty("fc")
    private String foreColor;

    @JsonProperty("bl")
    private int bold;

    @JsonProperty("it")
    private int italic;

    @JsonProperty("fs")
    private int fontSize = 10;

    @JsonProperty("cl")
    private int dropLine;

    @JsonProperty("ht")
    private int horizontal = 1;

    @JsonProperty("vt")
    private int vertical;

    /**
     * 换行方式
     */
    @JsonProperty("tb")
    private int lineType;

    @JsonProperty("un")
    private int underline;

    /**
     * 合并col数量
     */
    private int colSpan;

    /**
     * 合并row数量
     */
    private int rowspan;

    /**
     * 合并单元格标识
     */
    private boolean merge;

    public boolean isMerge() {
        return merge;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getFontFormat() {
        return FontFormat;
    }

    public void setFontFormat(int fontFormat) {
        FontFormat = fontFormat;
    }

    public String getForeColor() {
        return foreColor;
    }

    public void setForeColor(String foreColor) {
        this.foreColor = foreColor;
    }

    public int getBold() {
        return bold;
    }

    public void setBold(int bold) {
        this.bold = bold;
    }

    public int getItalic() {
        return italic;
    }

    public void setItalic(int italic) {
        this.italic = italic;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getDropLine() {
        return dropLine;
    }

    public void setDropLine(int dropLine) {
        this.dropLine = dropLine;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public int getUnderline() {
        return underline;
    }

    public void setUnderline(int underline) {
        this.underline = underline;
    }

    @JsonProperty("mc")
    public void setMerge(Map<String, Object> merge) {
        colSpan = Optional.ofNullable(merge.get("cs")).map(m -> (int) m).orElse(0);
        rowspan = Optional.ofNullable(merge.get("rs")).map(m -> (int) m).orElse(0);

        this.merge = true;
    }

    public int getColSpan() {
        return colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }
}
