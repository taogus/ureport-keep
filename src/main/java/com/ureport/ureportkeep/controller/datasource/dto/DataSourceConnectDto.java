package com.ureport.ureportkeep.controller.datasource.dto;

import com.ureport.ureportkeep.controller.datasource.enums.DataBaseType;
import com.ureport.ureportkeep.controller.datasource.enums.DataSourceType;

/**
 * @Author: summer
 * @Date: 2022/7/10 9:39
 * @Description:
 **/
public class DataSourceConnectDto {

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源类型
     */
    private DataSourceType dataSourceType;

    /**
     * 数据库类型
     */
    private DataBaseType dataBaseType;

    /**
     * 驱动器
     */
    private String driver;

    /**
     * 连接url
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 初始化连接数
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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
