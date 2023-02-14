package com.ureport.ureportkeep.core.parser.json.parameter.parse.component;

import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.definition.searchform.control.impl.SelectFormControl;
import com.ureport.ureportkeep.core.parser.json.enums.SelectorFormValueType;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import com.ureport.ureportkeep.core.parser.json.parameter.model.SelectorFormModel;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.FormControlParse;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:19
 * @Description:
 **/
public class SelectFormControlParse implements FormControlParse {
    @Override
    public FormControl parse(ParameterFormJsonModel parameterForm) {
        SelectorFormModel selectorFormModel = parameterForm.getSelectorFormModel();
        SelectorFormValueType valueType = selectorFormModel.getValueType();

        SelectFormControl selectFormControl = new SelectFormControl();
        selectFormControl.setValueType(valueType);
        if (SelectorFormValueType.fix.equals(valueType)) {
            selectFormControl.setOptions(buildOptions(selectorFormModel.getValues()));
        } else {
            selectFormControl.setDatasetName(selectorFormModel.getDatasetName());
            selectFormControl.setFieldName(selectorFormModel.getFieldName());
            selectFormControl.setShowFieldName(selectorFormModel.getShowFieldName());
        }

        return selectFormControl;
    }
}
