package com.ureport.ureportkeep.core.parser.json.datasource.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/12/11 13:34
 * @Description: 数据集model
 **/
public class DatasetModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据集
     */
    @JsonProperty("children")
    private List<DatasetInfoModel> datasetInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DatasetInfoModel> getDatasetInfos() {
        return datasetInfos;
    }

    public void setDatasetInfos(List<DatasetInfoModel> datasetInfos) {
        this.datasetInfos = datasetInfos;
    }
}
