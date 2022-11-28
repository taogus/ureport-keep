package com.ureport.ureportkeep.core.parser.json.cell.model.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.definition.Expand;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/11/27 17:57
 * @Description:
 **/
public class ImageConfigModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private ImageSource source;

    private String path;

    @JsonProperty("expandDirection")
    private Expand expand;

    private int width;

    private int height;

    private String expression;

    public ImageSource getSource() {
        return source;
    }

    public void setSource(ImageSource source) {
        this.source = source;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Expand getExpand() {
        return expand;
    }

    public void setExpand(Expand expand) {
        this.expand = expand;
    }

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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
