package com.ureport.ureportkeep.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: summer
 * @Date: 2022/2/13 12:34
 * @Description:
 **/
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> type) {
        assertContextInjected();
        return applicationContext.getBean(type);
    }

    public static <T> T getBean(Class<T> type, String name) {
        assertContextInjected();
        return applicationContext.getBean(name, type);
    }

    public static void assertContextInjected() {
        if (applicationContext == null) {
            throw new RuntimeException("applicationContext未注入");
        }
    }
}
