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
package com.ureport.ureportkeep.core.parser.impl;

import com.ureport.ureportkeep.core.definition.LinkParameter;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.parser.Parser;
import org.dom4j.Element;


/**
 * @author Jacky.gao
 * @since 2017年3月31日
 */
public class LinkParameterParser implements Parser<LinkParameter> {
	@Override
	public LinkParameter parse(Element element) {
		LinkParameter param=new LinkParameter();
		param.setName(element.attributeValue("name"));
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(ele.getName().equals("value")){
				param.setValue(ele.getText());
				Expression expr= ExpressionUtils.parseExpression(ele.getText());
				param.setValueExpression(expr);;
				break;
			}
		}
		return param;
	}
}
