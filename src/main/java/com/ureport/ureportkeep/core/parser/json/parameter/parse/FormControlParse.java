package com.ureport.ureportkeep.core.parser.json.parameter.parse;

import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;

/**
 * @Author: summer
 * @Date: 2023/1/9 20:48
 * @Description:
 **/
public interface FormControlParse {

    FormControl parse(ParameterFormJsonModel parameterForm);;

}
