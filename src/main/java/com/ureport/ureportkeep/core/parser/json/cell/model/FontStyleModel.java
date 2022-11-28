package com.ureport.ureportkeep.core.parser.json.cell.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 12:01
 * @Description:
 **/
public class FontStyleModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    @JsonProperty("v")
    private String value;

    @JsonProperty("bg")
    private String bgColor;

    @JsonProperty("ff")
    private Integer FontFormat;

    @JsonProperty("fc")
    private String foreColor;

    @JsonProperty("bl")
    private Integer bold;

    @JsonProperty("it")
    private Integer italic;

    @JsonProperty("fs")
    private Integer fontSize;

    @JsonProperty("cl")
    private Integer dropLine;

    @JsonProperty("ht")
    private Integer horizontal;

    @JsonProperty("vt")
    private Integer vertical;

    /**
     * 换行方式
     */
    @JsonProperty("tb")
    private Integer lineType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getFontFormat() {
        return FontFormat;
    }

    public void setFontFormat(Integer fontFormat) {
        FontFormat = fontFormat;
    }

    public String getForeColor() {
        return foreColor;
    }

    public void setForeColor(String foreColor) {
        this.foreColor = foreColor;
    }

    public Integer getBold() {
        return bold;
    }

    public void setBold(Integer bold) {
        this.bold = bold;
    }

    public Integer getItalic() {
        return italic;
    }

    public void setItalic(Integer italic) {
        this.italic = italic;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getDropLine() {
        return dropLine;
    }

    public void setDropLine(Integer dropLine) {
        this.dropLine = dropLine;
    }

    public Integer getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Integer horizontal) {
        this.horizontal = horizontal;
    }

    public Integer getVertical() {
        return vertical;
    }

    public void setVertical(Integer vertical) {
        this.vertical = vertical;
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }
}
