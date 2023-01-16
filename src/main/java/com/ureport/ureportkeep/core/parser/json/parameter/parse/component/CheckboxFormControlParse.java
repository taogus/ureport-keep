package com.ureport.ureportkeep.core.parser.json.parameter.parse.component;

import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.definition.searchform.control.impl.CheckboxFormControl;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.FormControlParse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2023/1/10 21:36
 * @Description:
 **/
public class CheckboxFormControlParse implements FormControlParse {
    @Override
    public FormControl parse(ParameterFormJsonModel parameterForm) {
        CheckboxFormControl checkboxFormControl = new CheckboxFormControl();

        List<Option> options = new ArrayList<>();
        for (Map<String, Object> value : parameterForm.getRadioValues()) {
            Object checkboxVal = value.get("value");
            if (checkboxVal == null) {
                continue;
            }
            String label = value.containsKey("label") ? value.get("label").toString() : checkboxVal.toString();

            options.add(new Option(label, checkboxVal.toString()));
        }
        checkboxFormControl.setOptions(options);
        return checkboxFormControl;
    }
}
