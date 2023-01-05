package com.ureport.ureportkeep.controller.preview;

import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.controller.preview.dto.PreviewData;
import com.ureport.ureportkeep.core.cache.CacheUtils;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.export.ReportRender;
import com.ureport.ureportkeep.core.parser.ReportParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: summer
 * @Date: 2022/11/26 12:01
 * @Description: 报表预览控制器
 **/
@Controller
@RequestMapping("/api/preview")
public class PreviewController {

    @Autowired
    private ReportParser reportParser;

    @Autowired
    private ReportRender reportRender;

    /**
     * 解析报表文件
     *
     * @param previewData 数据
     * @return
     */
    @PostMapping("/parseReportFile")
    @ResponseBody
    public R parseReportFile(@RequestBody PreviewData previewData) {
        String file = previewData.getFile();
        String data = previewData.getData();
        if (StringUtils.isEmpty(data)) {
            return R.error("报表数据不存在");
        }

        ReportDefinition reportDefinition = reportParser.parse(data, file);
        reportRender.rebuildReportDefinition(reportDefinition);

        try {
            CacheUtils.cacheReportDefinition(file, reportDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok();
    }

}
