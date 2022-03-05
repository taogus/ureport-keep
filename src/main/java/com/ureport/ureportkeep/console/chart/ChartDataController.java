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
package com.ureport.ureportkeep.console.chart;


import com.ureport.ureportkeep.console.AbstractReportBasicController;
import com.ureport.ureportkeep.core.cache.CacheUtils;
import com.ureport.ureportkeep.core.chart.ChartData;
import com.ureport.ureportkeep.core.utils.UnitUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: summer
 * @Date: 2022/1/12 21:20
 * @Description:
 **/
@Controller
@RequestMapping(value = "/chart")
public class ChartDataController extends AbstractReportBasicController {

    @RequestMapping(value = "/storeData", method = RequestMethod.POST)
    @ResponseBody
    public void storeData(HttpServletRequest req) throws ServletException, IOException {
        String file = req.getParameter("_u");
        file = decode(file);
        String chartId = req.getParameter("_chartId");
        ChartData chartData = CacheUtils.getChartData(chartId);
        if (chartData == null) {
            return;
        }
        String base64Data = req.getParameter("_base64Data");
        String prefix = "data:image/png;base64,";
        if (base64Data != null) {
            if (base64Data.startsWith(prefix)) {
                base64Data = base64Data.substring(prefix.length(), base64Data.length());
            }
        }
        chartData.setBase64Data(base64Data);
        String width = req.getParameter("_width");
        String height = req.getParameter("_height");
        chartData.setHeight(UnitUtils.pixelToPoint(Integer.valueOf(height)));
        chartData.setWidth(UnitUtils.pixelToPoint(Integer.valueOf(width)));
    }

}
