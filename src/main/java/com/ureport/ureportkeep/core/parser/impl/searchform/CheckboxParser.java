/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.ureport.ureportkeep.core.parser.impl.searchform;

import java.util.ArrayList;
import java.util.List;

import com.ureport.ureportkeep.core.definition.searchform.CheckboxInputComponent;
import com.ureport.ureportkeep.core.definition.searchform.LabelPosition;
import com.ureport.ureportkeep.core.definition.searchform.Option;
import org.dom4j.Element;
import org.springframework.stereotype.Component;


/**
 * @author Jacky.gao
 * @since 2017年10月24日
 */
@Component
public class CheckboxParser implements FormParser<CheckboxInputComponent> {
	@Override
	public CheckboxInputComponent parse(Element element) {
		CheckboxInputComponent checkbox=new CheckboxInputComponent();
		checkbox.setBindParameter(element.attributeValue("bind-parameter"));
		checkbox.setOptionsInline(Boolean.valueOf(element.attributeValue("options-inline")));
		checkbox.setLabel(element.attributeValue("label"));
		checkbox.setType(element.attributeValue("type"));
		checkbox.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		List<Option> options=new ArrayList<Option>();
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(!ele.getName().equals("option")){
				continue;
			}
			Option option=new Option();
			options.add(option);
			option.setLabel(ele.attributeValue("label"));
			option.setValue(ele.attributeValue("value"));
		}
		checkbox.setOptions(options);
		return checkbox;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-checkbox");
	}
}
