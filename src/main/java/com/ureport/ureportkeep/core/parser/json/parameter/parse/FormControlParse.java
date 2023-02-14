package com.ureport.ureportkeep.core.parser.json.parameter.parse;

import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2023/1/9 20:48
 * @Description:
 **/
public interface FormControlParse {

    FormControl parse(ParameterFormJsonModel parameterForm);;

    /**
     * 构建选项
     *
     * @param values
     * @return
     */
    default List<Option> buildOptions(List<Map<String, Object>> values) {
        List<Option> options = new ArrayList<>();
        for (Map<String, Object> value : values) {
            Object selectVal = value.get("value");
            if (selectVal == null) {
                continue;
            }
            String label = value.containsKey("label") ? value.get("label").toString() : selectVal.toString();
            // 如果存在逗号则分割
            if (StringUtils.contains(label, ",")) {
                String[] selectorSplit = StringUtils.split(label, ",");
                selectVal = selectorSplit[1];
                label = selectorSplit[0];
            }
            options.add(new Option(label, selectVal.toString()));
        }

        return options;
    }
}
