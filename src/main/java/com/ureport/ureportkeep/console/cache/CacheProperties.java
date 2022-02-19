package com.ureport.ureportkeep.console.cache;

import com.ureport.ureportkeep.core.utils.ReportProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: summer
 * @Date: 2022/2/13 12:16
 * @Description:
 **/
@Component
public class CacheProperties {

    @Autowired
    private ReportProperties reportProperties;

    private static boolean enableRedis;

    private static int cacheExpire;

    public static boolean isEnableRedis() {
        return enableRedis;
    }

    public static void setEnableRedis(boolean enableRedis) {
        CacheProperties.enableRedis = enableRedis;
    }

    public static int getCacheExpire() {
        return cacheExpire;
    }

    public static void setCacheExpire(int cacheExpire) {
        CacheProperties.cacheExpire = cacheExpire;
    }

    @PostConstruct
    public void init() {
        CacheProperties.enableRedis = reportProperties.isEnableRedis();
        CacheProperties.cacheExpire = reportProperties.getCacheExpire();
    }

}
