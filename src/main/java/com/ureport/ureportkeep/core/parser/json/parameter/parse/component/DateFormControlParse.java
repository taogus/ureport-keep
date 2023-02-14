package com.ureport.ureportkeep.core.parser.json.parameter.parse.component;

import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.definition.searchform.control.impl.DateFormControl;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.FormControlParse;
import org.thymeleaf.util.StringUtils;

/**
 * @Author: summer
 * @Date: 2023/1/10 20:36
 * @Description:
 **/
public class DateFormControlParse implements FormControlParse {
    @Override
    public FormControl parse(ParameterFormJsonModel parameterForm) {
        String dateFormat = parameterForm.getDateFormat();

        DateFormControl dateFormControl = new DateFormControl();
        dateFormControl.setDateFormat(StringUtils.isEmpty(dateFormat) ? "yyyy-MM-dd" : dateFormat);
        return dateFormControl;
    }

}
