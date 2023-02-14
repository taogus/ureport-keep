package com.ureport.ureportkeep.core.parser.json.parameter.parse.component;

import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.definition.searchform.control.impl.CheckboxFormControl;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.FormControlParse;

/**
 * @Author: summer
 * @Date: 2023/1/10 21:36
 * @Description:
 **/
public class CheckboxFormControlParse implements FormControlParse {
    @Override
    public FormControl parse(ParameterFormJsonModel parameterForm) {
        CheckboxFormControl checkboxFormControl = new CheckboxFormControl();
        checkboxFormControl.setOptions(buildOptions(parameterForm.getCheckboxValues()));
        return checkboxFormControl;
    }
}
