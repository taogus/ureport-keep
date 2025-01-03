package com.ureport.ureportkeep.core.export.html;

import java.io.Serializable;

/**
 * @author march
 * @Date: 2024年12月30日
 * Description:
 */
public class WatermarkHtmlData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content;

    private int[] color;

    private Float alpha;

    private Integer size;

    private Integer tiltAngle;

    private Integer hSpace;

    private Integer vSpace;

    public static WatermarkHtmlData builder(String content, int[] color) {
        WatermarkHtmlData watermarkHtmlData = new WatermarkHtmlData();
        watermarkHtmlData.setContent(content);
        watermarkHtmlData.setColor(color);
        watermarkHtmlData.setAlpha(0.4F);
        watermarkHtmlData.setSize(12);
        watermarkHtmlData.setTiltAngle(30);
        watermarkHtmlData.sethSpace(80);
        watermarkHtmlData.setvSpace(50);
        return watermarkHtmlData;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public Float getAlpha() {
        return alpha;
    }

    public void setAlpha(Float alpha) {
        this.alpha = alpha;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTiltAngle() {
        return tiltAngle;
    }

    public void setTiltAngle(Integer tiltAngle) {
        this.tiltAngle = tiltAngle;
    }

    public Integer gethSpace() {
        return hSpace;
    }

    public void sethSpace(Integer hSpace) {
        this.hSpace = hSpace;
    }

    public Integer getvSpace() {
        return vSpace;
    }

    public void setvSpace(Integer vSpace) {
        this.vSpace = vSpace;
    }
}
