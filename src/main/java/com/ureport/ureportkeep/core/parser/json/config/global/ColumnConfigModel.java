package com.ureport.ureportkeep.core.parser.json.config.global;

import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/12/4 19:30
 * @Description: 分栏配置
 **/
public class ColumnConfigModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    private boolean enable;

    private int count;

    private int margin;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
}
