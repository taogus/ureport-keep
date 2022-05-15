package com.ureport.ureportkeep.core.init;

import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/3/5 18:22
 * @Description:
 **/
@Component
public class ReportProvidersInit implements ApplicationContextAware {

    private List<ReportProvider> reportProviders = new ArrayList<>();

    /**
     * 根据名称获取储存系统
     *
     * @param name
     * @return
     */
    public ReportProvider getProvider(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new ReportException("储存系统名称不能为空");
        }
        ReportProvider reportProvider = reportProviders.stream().filter(r -> name.equals(r.getName())).findFirst()
                .orElseThrow(() -> new ReportException("找不到[" + name + "]储存系统"));

        return reportProvider;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        reportProviders.addAll(applicationContext.getBeansOfType(ReportProvider.class).values());
    }

    public List<ReportProvider> getReportProviders() {
        return reportProviders;
    }

}
