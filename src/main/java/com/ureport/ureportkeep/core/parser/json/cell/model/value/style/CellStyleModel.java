package com.ureport.ureportkeep.core.parser.json.cell.model.value.style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 17:01
 * @Description: 单元格样式配置model
 **/
public class CellStyleModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    @JsonProperty("fillBlankLine")
    private boolean blankLine;

    /**
     * 补充空白行倍数
     */
    private int multiple;

    /**
     * 自动换行
     */
    private boolean wrapCompute;

    /**
     * 格式化
     */
    private String format;

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public boolean isBlankLine() {
        return blankLine;
    }

    public void setBlankLine(boolean blankLine) {
        this.blankLine = blankLine;
    }

    public boolean isWrapCompute() {
        return wrapCompute;
    }

    public void setWrapCompute(boolean wrapCompute) {
        this.wrapCompute = wrapCompute;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
