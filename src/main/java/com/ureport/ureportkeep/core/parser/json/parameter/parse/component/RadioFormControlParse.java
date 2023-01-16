package com.ureport.ureportkeep.core.parser.json.parameter.parse.component;

import com.ureport.ureportkeep.core.definition.searchform.Option;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.definition.searchform.control.impl.RadioFormControl;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.FormControlParse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:59
 * @Description:
 **/
public class RadioFormControlParse implements FormControlParse {
    @Override
    public FormControl parse(ParameterFormJsonModel parameterForm) {
        RadioFormControl radioFormControl = new RadioFormControl();

        List<Option> options = new ArrayList<>();
        for (Map<String, Object> value : parameterForm.getRadioValues()) {
            Object radioVal = value.get("value");
            if (radioVal == null) {
                continue;
            }
            String label = value.containsKey("label") ? value.get("label").toString() : radioVal.toString();

            options.add(new Option(label, radioVal.toString()));
        }
        radioFormControl.setOptions(options);

        return radioFormControl;
    }
}
