package com.ureport.ureportkeep.core.parser.json.parameter.parse;

import com.ureport.ureportkeep.core.definition.searchform.Component;
import com.ureport.ureportkeep.core.definition.searchform.SearchForm;
import com.ureport.ureportkeep.core.definition.searchform.control.ControlFormGroup;
import com.ureport.ureportkeep.core.definition.searchform.control.FormControl;
import com.ureport.ureportkeep.core.parser.json.JsonParse;
import com.ureport.ureportkeep.core.parser.json.enums.ParameterControlType;
import com.ureport.ureportkeep.core.parser.json.parameter.model.ParameterFormJsonModel;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.component.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2023/1/9 20:22
 * @Description:
 **/
public class ParameterFormJsonParse implements JsonParse<SearchForm, List<ParameterFormJsonModel>> {

    private final Map<ParameterControlType, FormControlParse> componentParseMap = new HashMap<>();

    {
        componentParseMap.put(ParameterControlType.text, new TextFormControlParse());
        componentParseMap.put(ParameterControlType.selector, new SelectFormControlParse());
        componentParseMap.put(ParameterControlType.date, new DateFormControlParse());
        componentParseMap.put(ParameterControlType.radio, new RadioFormControlParse());
        componentParseMap.put(ParameterControlType.checkbox, new CheckboxFormControlParse());
    }

    @Override
    public SearchForm parse(List<ParameterFormJsonModel> parseModel) {
        ControlFormGroup searchForm = new ControlFormGroup();
        List<Component> components = new ArrayList<>();
        for (ParameterFormJsonModel parameterFormJsonModel : parseModel) {
            ParameterControlType type = parameterFormJsonModel.getType();
            if (!componentParseMap.containsKey(type)) {
                continue;
            }

            FormControl formControl = componentParseMap.get(type).parse(parameterFormJsonModel);
            formControl.setName(parameterFormJsonModel.getName());
            formControl.setLabel(parameterFormJsonModel.getLabel());
            formControl.setDefaultValue(parameterFormJsonModel.getDefaultValue());
            components.add(formControl);
        }

        searchForm.setComponents(components);
        return searchForm;
    }
}
