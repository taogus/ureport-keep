package com.ureport.ureportkeep.core.parser.json.cell.parse.value.impl;

import com.ureport.ureportkeep.core.definition.value.ImageValue;
import com.ureport.ureportkeep.core.definition.value.Value;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.image.ImageConfigModel;
import com.ureport.ureportkeep.core.parser.json.cell.parse.value.ValueJsonParse;

/**
 * @Author: summer
 * @Date: 2022/12/24 16:36
 * @Description:
 **/
public class ImageValueJsonParse implements ValueJsonParse {
    @Override
    public Value parse(CellModel parseModel) {
        ImageConfigModel imageConfig = parseModel.getImageConfig();
        JsonModel.ImageSource source = imageConfig.getSource();

        ImageValue value=new ImageValue();
        value.setWidth(imageConfig.getWidth());
        value.setHeight(imageConfig.getHeight());
        value.setImageSource(source);

        if (JsonModel.ImageSource.path.equals(source)
                || JsonModel.ImageSource.upload.equals(source)) {
            value.setPath(imageConfig.getPath());
        } else {
            value.setExpr(imageConfig.getExpression());
            value.setExpression(ExpressionUtils.parseExpression(value.getExpr()));
        }

        return value;
    }

}
