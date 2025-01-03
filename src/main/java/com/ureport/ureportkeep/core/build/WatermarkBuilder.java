package com.ureport.ureportkeep.core.build;

import com.ureport.ureportkeep.core.definition.Paper;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectListExpressionData;
import com.ureport.ureportkeep.core.model.Report;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author march
 * @Date: 2024年12月30日
 * Description: 水印建造器
 */
public class WatermarkBuilder {

    /**
     * 构建水印内容
     *
     * @param report 报表
     * @return
     */
    public static String builderContent(Report report) {
        Paper paper = report.getPaper();
        String watermarkContent = paper.getWatermarkText();
        if (StringUtils.isEmpty(watermarkContent) || !paper.isWatermarkEnabled()) {
            return null;
        }

        String watermarkResult = null;
        if(watermarkContent.startsWith(ExpressionUtils.EXPR_PREFIX) && watermarkContent.endsWith(ExpressionUtils.EXPR_SUFFIX)){
            String exprString = watermarkContent.substring(2, watermarkContent.length() - 1);
            Expression expr = ExpressionUtils.parseExpression(exprString);

            Context context = report.getContext();
            watermarkResult = computeExpression(context, expr);
        } else {
            watermarkResult = watermarkContent;
        }
        return watermarkResult;
    }

    private static String computeExpression(Context context, Expression contentExpression) {
        ExpressionData<?> data = contentExpression.execute(context.getRootCell(), context.getRootCell(), context);
        Object obj = null;
        if (data instanceof ObjectExpressionData) {
            obj = ((ObjectExpressionData) data).getData();
        } else if (data instanceof ObjectListExpressionData) {
            ObjectListExpressionData listData = (ObjectListExpressionData) data;
            List<?> list = listData.getData();
            if (list != null && list.size() > 0) {
                obj = "";
                for (Object o : list) {
                    if (o == null) {
                        continue;
                    }
                    if (!obj.equals("")) {
                        obj += ",";
                    }
                    obj += o.toString();
                }
            }
        }

        return Optional.ofNullable(obj).map(Object::toString).orElse("");
    }

}
