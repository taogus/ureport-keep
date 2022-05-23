package com.ureport.ureportkeep.core.cache;

import com.ureport.ureportkeep.console.cache.CacheProperties;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: summer
 * @Date: 2022/5/23 22:00
 * @Description:
 **/
@Component
public class RedisReportDefinitionCache implements ReportDefinitionCache {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ReportDefinition getReportDefinition(String file) {
        return (ReportDefinition) redisTemplate.opsForValue().get(file);
    }

    @Override
    public void cacheReportDefinition(String file, ReportDefinition reportDefinition) {
        redisTemplate.opsForValue().set(file, reportDefinition);
    }

    @Override
    public boolean disabled() {
        return CacheProperties.isEnableRedis();
    }
}
