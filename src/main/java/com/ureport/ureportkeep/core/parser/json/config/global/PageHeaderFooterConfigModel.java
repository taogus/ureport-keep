package com.ureport.ureportkeep.core.parser.json.config.global;

import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/12/4 19:25
 * @Description: 页头页尾配置model
 **/
public class PageHeaderFooterConfigModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    /**
     * 页眉/页尾顶端距离
     */
    private int margin;

    private String leftExpress;

    private String centerExpress;

    private String rightExpress;

    // 字体样式配置... 未写

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public String getLeftExpress() {
        return leftExpress;
    }

    public void setLeftExpress(String leftExpress) {
        this.leftExpress = leftExpress;
    }

    public String getCenterExpress() {
        return centerExpress;
    }

    public void setCenterExpress(String centerExpress) {
        this.centerExpress = centerExpress;
    }

    public String getRightExpress() {
        return rightExpress;
    }

    public void setRightExpress(String rightExpress) {
        this.rightExpress = rightExpress;
    }
}
