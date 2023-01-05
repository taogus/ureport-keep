package com.ureport.ureportkeep.core.parser.json.config.paper;

import com.ureport.ureportkeep.core.definition.HeaderFooterDefinition;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.parser.json.JsonParse;
import com.ureport.ureportkeep.core.parser.json.config.global.PageHeaderFooterConfigModel;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: summer
 * @Date: 2022/12/28 20:25
 * @Description:
 **/
public abstract class PageHeaderFooterJsonParse implements JsonParse<HeaderFooterDefinition, PageHeaderFooterConfigModel> {

    @Override
    public HeaderFooterDefinition parse(PageHeaderFooterConfigModel parseModel) {
        String leftExpress = parseModel.getLeftExpress();
        String centerExpress = parseModel.getCenterExpress();
        String rightExpress = parseModel.getRightExpress();

        HeaderFooterDefinition hf = new HeaderFooterDefinition();
        // 缺少字体样式配置
        hf.setMargin(parseModel.getMargin());
        hf.setLeft(leftExpress);
        if (!StringUtils.isEmpty(leftExpress)) {
            Expression expr= ExpressionUtils.parseExpression(leftExpress);
            hf.setLeftExpression(expr);
        }
        hf.setCenter(centerExpress);
        if (!StringUtils.isEmpty(centerExpress)) {
            Expression expr= ExpressionUtils.parseExpression(centerExpress);
            hf.setCenterExpression(expr);
        }
        hf.setRight(rightExpress);
        if (!StringUtils.isEmpty(rightExpress)) {
            Expression expr= ExpressionUtils.parseExpression(rightExpress);
            hf.setRightExpression(expr);
        }

        return hf;
    }
}
