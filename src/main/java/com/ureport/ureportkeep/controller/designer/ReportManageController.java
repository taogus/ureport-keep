package com.ureport.ureportkeep.controller.designer;

import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.controller.designer.dto.ReportManageDto;
import com.ureport.ureportkeep.core.init.ReportProvidersInit;
import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: summer
 * @Date: 2022/3/5 18:13
 * @Description: 报表管理控制器
 **/
@RestController
@RequestMapping(value = "/api/keepReport")
public class ReportManageController {

    @Autowired
    private ReportProvidersInit reportProvidersInit;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    @RequestMapping(value = "/loadReport", method = RequestMethod.POST)
    @CrossOrigin
    public String loadReport(@RequestParam String provider, @RequestParam String file) {
        String sheetsStr = "";
        List<Map> results = new ArrayList<>();

        ReportProvider reportProvider = reportProvidersInit.getProvider(provider);
        InputStream inputStream = null;
        try {
            inputStream = reportProvider.loadReport(file);
            byte[] bytes = new byte[10240];
            IOUtils.read(inputStream, bytes);

            results.add(objectMapper.readValue(bytes, Map.class));
            sheetsStr = objectMapper.writeValueAsString(results);
            return sheetsStr;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sheetsStr;
    }

    /**
     * 保存报表文件
     *
     * @param reportManageDto
     * @return
     */
    @PostMapping("/save")
    public R saveReport(@RequestBody ReportManageDto reportManageDto) {
        String reportProvider = reportManageDto.getReportProvider();
        ReportProvider provider = reportProvidersInit.getProvider(reportProvider);
        provider.saveReport(StringUtils.randomAlphanumeric(8) + ".json", reportManageDto.getReportJson());

        return R.ok();
    }

}
