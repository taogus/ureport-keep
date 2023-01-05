package com.ureport.ureportkeep.core.parser.json.datasource.model;

import com.ureport.ureportkeep.controller.datasource.enums.DataBaseType;
import com.ureport.ureportkeep.controller.datasource.enums.DataSourceType;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/12/11 11:46
 * @Description: 数据源model
 **/
public class DataSourceModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    /**
     * 数据库类型
     */
    private DataBaseType dataBaseType;

    /**
     * 数据源类型
     */
    private DataSourceType dataSourceType;

    private String driver;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源URL
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 初始化连接
     */
    private Integer initSize;

    /**
     * 最大活动连接数
     */
    private Integer maxActiveSize;

    /**
     * 最大空闲连接数
     */
    private Integer maxIdleSize;

    /**
     * 最小空闲连接数
     */
    private Integer minIdleSize;

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getInitSize() {
        return initSize;
    }

    public void setInitSize(Integer initSize) {
        this.initSize = initSize;
    }

    public Integer getMaxActiveSize() {
        return maxActiveSize;
    }

    public void setMaxActiveSize(Integer maxActiveSize) {
        this.maxActiveSize = maxActiveSize;
    }

    public Integer getMaxIdleSize() {
        return maxIdleSize;
    }

    public void setMaxIdleSize(Integer maxIdleSize) {
        this.maxIdleSize = maxIdleSize;
    }

    public Integer getMinIdleSize() {
        return minIdleSize;
    }

    public void setMinIdleSize(Integer minIdleSize) {
        this.minIdleSize = minIdleSize;
    }
}
