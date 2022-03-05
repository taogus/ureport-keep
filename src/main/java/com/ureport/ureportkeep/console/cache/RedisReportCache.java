package com.ureport.ureportkeep.console.cache;

import com.ureport.ureportkeep.core.cache.ReportCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: summer
 * @Date: 2022/3/5 18:42
 * @Description:
 **/
@Component
public class RedisReportCache implements ReportCache {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object getObject(String file) {
        return redisTemplate.opsForValue().get(file);
    }

    @Override
    public void storeObject(String file, Object obj) {
        redisTemplate.opsForValue().set(file, obj, CacheProperties.getCacheExpire(), TimeUnit.SECONDS);

    }

    @Override
    public boolean disabled() {
        return CacheProperties.isEnableRedis();
    }
}
