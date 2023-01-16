package com.ureport.ureportkeep.core.parser.json.utils;

import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.util.ArrayUtils;

/**
 * @Author: summer
 * @Date: 2023/1/5 20:46
 * @Description: 数据集json解析工具类
 **/
public class DatasetJsonParseUtils {

    /**
     * 转换SQL参数表达式
     *
     * @param SQL
     * @return
     */
    public static String convertSQL(String SQL) {
        boolean expressSQL = false;
        String convertSQL = SQL;
        convertSQL = convertSQL.trim();
        if (expressSQL = (convertSQL.startsWith(ExpressionUtils.EXPR_PREFIX) && convertSQL.endsWith(ExpressionUtils.EXPR_SUFFIX))) {
            convertSQL = StringUtils.removeEnd(StringUtils.removeStart(convertSQL, ExpressionUtils.EXPR_PREFIX), ExpressionUtils.EXPR_SUFFIX);
        }

        String[] paramNames = StringUtils.substringsBetween(convertSQL, "${", "}");
        if (!ArrayUtils.isEmpty(paramNames)) {
            for (String paramName : paramNames) {
                convertSQL = StringUtils.replace(convertSQL, "${" + paramName + "}", ":" + paramName);
            }
        }
        return expressSQL ? ExpressionUtils.EXPR_PREFIX + convertSQL + ExpressionUtils.EXPR_SUFFIX : convertSQL;
    }

}
