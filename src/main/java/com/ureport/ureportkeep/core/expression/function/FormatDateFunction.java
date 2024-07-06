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
package com.ureport.ureportkeep.core.expression.function;


import com.ureport.ureportkeep.core.build.BindData;
import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.expression.model.data.BindDataListExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectListExpressionData;
import com.ureport.ureportkeep.core.model.Cell;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年5月23日
 */
@Component
public class FormatDateFunction implements Function {
    private final String defaultPattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
        Object dateValue = null;
        String pattern = defaultPattern;

		int size = dataList.size();
        if (size == 0) {
            return "";
        }
        ExpressionData<?> expressionData = dataList.get(0);
        dateValue = parseData(expressionData);
		if (size > 1) {
            pattern = (String) parseData(dataList.get(1));
            if (StringUtils.isEmpty(pattern)) {
                throw new ReportComputeException("函数 [formatdate] 第二个参数值不能为空");
            }
        }

        if (dateValue == null) {
            throw new ReportComputeException("函数 [formatdate] 参数不能为空");
        } else {
            SimpleDateFormat sd = new SimpleDateFormat(pattern);
            if (dateValue instanceof Number) {
                long unixTime = ((Number) dateValue).longValue();
                int numberLength = String.valueOf(unixTime).length();
                if (numberLength == 10) {
                    unixTime = unixTime * 1000;
                }
                dateValue = new Date(unixTime);
            }

            if (dateValue instanceof Date) {
                return sd.format(dateValue);
            } else {
                throw new ReportComputeException("函数 [formatdate] 第一个参数不是可解析类型");
            }
        }
    }

    private Object parseData(ExpressionData<?> data) {
        Object value = null;
        if (data instanceof ObjectListExpressionData) {
            ObjectListExpressionData listExpressionData = (ObjectListExpressionData) data;
            List<?> list = listExpressionData.getData();
            if (!list.isEmpty()) {
                value = list.get(0);
            }
        } else if (data instanceof ObjectExpressionData) {
            value = ((ObjectExpressionData) data).getData();
        } else if (data instanceof BindDataListExpressionData) {
            BindDataListExpressionData bindDataList = (BindDataListExpressionData) data;
            List<BindData> list = bindDataList.getData();
            if (!list.isEmpty()) {
                value = list.get(0).getValue();
            }
        }

        return value;
    }

    @Override
    public String name() {
        return "formatdate";
    }
}
