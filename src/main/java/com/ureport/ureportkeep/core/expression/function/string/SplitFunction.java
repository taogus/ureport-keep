package com.ureport.ureportkeep.core.expression.function.string;

import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.model.Cell;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/10/23 10:48
 * @Description:
 **/
@Component
public class SplitFunction extends StringFunction{
    @Override
    public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
        if (dataList.size() < 1) {
            throw new ReportComputeException("[" + name() + "]函数需要两个参数值");
        }
        String str = buildString(dataList.get(0));
        String separatorChars = buildString(dataList.get(1));
        return StringUtils.split(str, separatorChars);
    }

    @Override
    public String name() {
        return "split";
    }
}
