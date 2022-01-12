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

import com.ureport.ureportkeep.core.definition.ConditionPaging;
import com.ureport.ureportkeep.core.definition.PagingPosition;
import com.ureport.ureportkeep.core.parser.Parser;
import org.dom4j.Element;

/**
 * @author Jacky.gao
 * @since 2017年6月21日
 */
public class ConditionPagingParser implements Parser<ConditionPaging> {
	@Override
	public ConditionPaging parse(Element element) {
		ConditionPaging paging=new ConditionPaging();
		String position=element.attributeValue("position");
		paging.setPosition(PagingPosition.valueOf(position));
		String line=element.attributeValue("line");
		paging.setLine(Integer.valueOf(line));
		return paging;
	}
}
