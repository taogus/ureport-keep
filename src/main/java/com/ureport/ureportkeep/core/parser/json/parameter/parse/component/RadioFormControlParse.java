package com.ureport.ureportkeep.core.parser.json.parameter.parse.component;

import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.definition.searchform.control.impl.RadioFormControl;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.FormControlParse;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:59
 * @Description:
 **/
public class RadioFormControlParse implements FormControlParse {
    @Override
    public FormControl parse(ParameterFormJsonModel parameterForm) {
        RadioFormControl radioFormControl = new RadioFormControl();

        radioFormControl.setOptions(buildOptions(parameterForm.getRadioValues()));

        return radioFormControl;
    }
}
