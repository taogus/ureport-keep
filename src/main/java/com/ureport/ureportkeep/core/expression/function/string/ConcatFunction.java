package com.ureport.ureportkeep.core.expression.function.string;

import com.ureport.ureportkeep.core.build.BindData;
import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.expression.function.Function;
import com.ureport.ureportkeep.core.expression.model.data.BindDataListExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectListExpressionData;
import com.ureport.ureportkeep.core.model.Cell;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/3/26 10:09
 * @Description:
 **/
@Component
public class ConcatFunction implements Function {
    @Override
    public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {

        StringBuffer result = new StringBuffer();
        for (ExpressionData<?> expressionData : dataList) {

            if(expressionData instanceof ObjectListExpressionData){
                ObjectListExpressionData listData=(ObjectListExpressionData)expressionData;
                List<?> list=listData.getData();
                if(list==null){
                    continue;
                }

                for (Object obj : list) {
                    if (obj == null) {
                        continue;
                    }

                    result.append(obj.toString());
                }
            }else if(expressionData instanceof ObjectExpressionData){
                ObjectExpressionData objData=(ObjectExpressionData)expressionData;
                Object obj=objData.getData();
                if(obj==null){
                    continue;
                }
                result.append(obj.toString());
            } else if(expressionData instanceof BindDataListExpressionData) {
                BindDataListExpressionData objData = (BindDataListExpressionData) expressionData;
                List<BindData> datas = objData.getData();
                if (datas == null) {
                    continue;
                }

                for (BindData data : datas) {
                    if (data.getValue() == null) {
                        continue;
                    }

                    result.append(data.getValue());
                }

            } else {
                throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
            }
        }

        return result.toString();
    }

    @Override
    public String name() {
        return "concat";
    }
}
