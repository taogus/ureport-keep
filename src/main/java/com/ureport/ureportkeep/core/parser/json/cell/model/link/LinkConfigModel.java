package com.ureport.ureportkeep.core.parser.json.cell.model.link;

import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.enums.WindowOption;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/11/27 16:14
 * @Description:
 **/
public class LinkConfigModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    private String url;

    /**
     * 打开窗口类型
     */
    private int targetWindow = WindowOption.CURRENT_WINDOW.getType();

    /**
     * 参数配置
     */
    private List<LinkParameterModel> parameters;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTargetWindow() {
        return targetWindow;
    }

    public void setTargetWindow(int targetWindow) {
        this.targetWindow = targetWindow;
    }

    public List<LinkParameterModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<LinkParameterModel> parameters) {
        this.parameters = parameters;
    }
}
