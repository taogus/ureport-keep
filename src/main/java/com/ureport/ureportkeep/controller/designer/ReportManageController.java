package com.ureport.ureportkeep.controller.designer;

import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.core.init.ReportProvidersInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @Author: summer
 * @Date: 2022/3/5 18:13
 * @Description:
 **/
@RestController
@RequestMapping(value = "/api/keepReport")
public class ReportManageController {

    @Autowired
    private ReportProvidersInit reportProvidersInit;

    /**
     * 加载报表列表
     *
     * @return
     */
    @RequestMapping(value = "/loadReportList", method = RequestMethod.GET)
    public R loadReportProviders() {
        return R.ok().success(
                reportProvidersInit.getReportProviders()
                        .stream()
                        .filter(r -> r.getName() != null && r.disabled())
                        .collect(Collectors.toList())
        );
    }

    /**
     * 加载报表
     *
     * @param file 报表文件名
     * @return
     */
    @RequestMapping(value = "/loadReport", method = RequestMethod.GET)
    public R loadReport(@RequestParam String file) {


        return R.ok();
    }

}
