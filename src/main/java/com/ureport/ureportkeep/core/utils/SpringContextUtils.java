package com.ureport.ureportkeep.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

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

    public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T getBean(Class<T> type) {
        assertContextInjected();
        return applicationContext.getBean(type);
    }

    public static <T> T getBean(Class<T> type, String name) {
        assertContextInjected();
        return applicationContext.getBean(name, type);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        assertContextInjected();
        return applicationContext.getBeansOfType(type);
    }

    
    public static <T> Collection<T> getAllBeansOfType(Class<T> type) {
        assertContextInjected();
        return applicationContext.getBeansOfType(type).values();
    }

	public static Resource getResource(String path) {
		assertContextInjected();
		return applicationContext.getResource(path);
	}
    
    public static InputStream getResourceStream(String path) {
    	assertContextInjected();
    	try {
			return applicationContext.getResource(path).getInputStream();
		} catch (IOException e) {
			throw new RuntimeException("load resource error.", e);
		}
    }
    
	public static String getWebBasePath() {
		if (applicationContext != null && applicationContext instanceof WebApplicationContext) {
			WebApplicationContext context = (WebApplicationContext) applicationContext;
			return context.getServletContext().getRealPath("/");
		} else {
			return null;
		}
	}
    
    public static void assertContextInjected() {
        if (applicationContext == null) {
            throw new RuntimeException("applicationContext未注入");
        }
    }
}
