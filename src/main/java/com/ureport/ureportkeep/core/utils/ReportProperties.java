package com.ureport.ureportkeep.core.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: summer
 * @Date: 2022/2/13 12:02
 * @Description:
 **/
@ConfigurationProperties(prefix = "ureport")
@Component
public class ReportProperties {

    private boolean disableHttpSessionReportCache;

    private boolean disableFileProvider;

    private String fileStoreDir;

    private boolean debug;

    /**
     * 开启redis缓存
     */
    private boolean enableRedis;

    /**
     * redis缓存过期时间 (单位：分钟)
     */
    private int cacheExpire;

    public boolean isDisableHttpSessionReportCache() {
        return disableHttpSessionReportCache;
    }

    public void setDisableHttpSessionReportCache(boolean disableHttpSessionReportCache) {
        this.disableHttpSessionReportCache = disableHttpSessionReportCache;
    }

    public boolean isDisableFileProvider() {
        return disableFileProvider;
    }

    public void setDisableFileProvider(boolean disableFileProvider) {
        this.disableFileProvider = disableFileProvider;
    }

    public String getFileStoreDir() {
        return fileStoreDir;
    }

    public void setFileStoreDir(String fileStoreDir) {
        this.fileStoreDir = fileStoreDir;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isEnableRedis() {
        return enableRedis;
    }

    public void setEnableRedis(boolean enableRedis) {
        this.enableRedis = enableRedis;
    }

    public int getCacheExpire() {
        return cacheExpire;
    }

    public void setCacheExpire(int cacheExpire) {
        this.cacheExpire = cacheExpire;
    }
}