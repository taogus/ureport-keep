/*******************************************************************************
 * Copyright 2017 Bstek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.ureport.ureportkeep.console.cache;

import com.ureport.ureportkeep.console.RequestHolder;
import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author Jacky.gao
 * @since 2017年9月6日
 */
public class TempObjectCache {

    private static Logger logger = LoggerFactory.getLogger(TempObjectCache.class);

    private static TempObjectCache tempObjectCache = new TempObjectCache();
    private Map<String, ObjectMap> sessionMap = new HashMap<String, ObjectMap>();

    private static boolean enableRedis = CacheProperties.isEnableRedis();

    private static int cacheExpire = CacheProperties.getCacheExpire();

    private RedisTemplate redisTemplate = SpringContextUtils.getBean(RedisTemplate.class, "redisTemplate");

    public static Object getObject(String key) {
        if (enableRedis) {
            try {
                return tempObjectCache.getRedisData(key);
            } catch (Exception e) {
                throw new ReportException("redis错误，请检查：" + e.getMessage());
            }
        }
        return tempObjectCache.get(key);
    }

    public static void putObject(String key, Object obj) {
        if (enableRedis) {
            try {
                tempObjectCache.storeRedis(key, obj);
            } catch (Exception e) {
                throw new ReportException("redis错误，请检查：" + e.getMessage());
            }

            return;
        }
        tempObjectCache.store(key, obj);
    }

    public static void removeObject(String key) {
        tempObjectCache.remove(key);
    }

    public void remove(String key) {
        HttpServletRequest req = RequestHolder.getRequest();
        if (req == null) {
            return;
        }
        ObjectMap mapObject = getReportMap(req);
        if (mapObject != null) {
            mapObject.remove(key);
        }
    }

    public Object get(String key) {
        HttpServletRequest req = RequestHolder.getRequest();
        if (req == null) {
            return null;
        }
        ObjectMap mapObject = getReportMap(req);
        return mapObject.get(key);
    }

    public void store(String key, Object obj) {
        HttpServletRequest req = RequestHolder.getRequest();
        if (req == null) {
            return;
        }
        ObjectMap mapObject = getReportMap(req);
        mapObject.put(key, obj);
    }

    private ObjectMap getReportMap(HttpServletRequest req) {
        List<String> expiredList = new ArrayList<String>();
        logger.info(sessionMap.keySet().toString());
        for (String key : sessionMap.keySet()) {
            ObjectMap reportObj = sessionMap.get(key);
            if (reportObj.isExpired()) {
                expiredList.add(key);
            }
        }
        for (String key : expiredList) {
            sessionMap.remove(key);
        }
        String sessionId = req.getSession().getId();
        ObjectMap obj = sessionMap.get(sessionId);
        if (obj != null) {
            return obj;
        } else {
            ObjectMap mapObject = new ObjectMap();
            sessionMap.put(sessionId, mapObject);
            return mapObject;
        }
    }

    private void storeRedis(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj, cacheExpire, TimeUnit.SECONDS);
    }

    private Object getRedisData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
