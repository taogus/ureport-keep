package com.ureport.ureportkeep.core.init;

import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        reportProviders.addAll(applicationContext.getBeansOfType(ReportProvider.class).values());

    }

    public List<ReportProvider> getReportProviders() {
        return reportProviders;
    }

}
