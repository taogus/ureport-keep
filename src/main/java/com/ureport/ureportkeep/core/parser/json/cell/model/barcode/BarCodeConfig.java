package com.ureport.ureportkeep.core.parser.json.cell.model.barcode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.definition.Expand;
import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.enums.BarCodeFormat;
import com.ureport.ureportkeep.core.parser.json.enums.BarCodeSource;

/**
 * @Author: summer
 * @Date: 2022/11/27 18:02
 * @Description:
 **/
public class BarCodeConfig implements JsonModel {
    private static final long serialVersionUID = 1L;

    private int width;

    private int height;

    private BarCodeSource source;

    /**
     * 文本内容/表达式
     */
    private String value;

    /**
     * 条形码类型
     */
    private BarCodeFormat format;

    @JsonProperty("expandDirection")
    private Expand expand;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BarCodeSource getSource() {
        return source;
    }

    public void setSource(BarCodeSource source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BarCodeFormat getFormat() {
        return format;
    }

    public void setFormat(BarCodeFormat format) {
        this.format = format;
    }

    public Expand getExpand() {
        return expand;
    }

    public void setExpand(Expand expand) {
        this.expand = expand;
    }
}
