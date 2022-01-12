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
package com.ureport.ureportkeep.core.expression.parse.builder;


import com.ureport.ureportkeep.core.dsl.ReportParserParser;
import com.ureport.ureportkeep.core.expression.model.expr.BaseExpression;
import com.ureport.ureportkeep.core.expression.model.expr.NullExpression;

/**
 * @author Jacky.gao
 * @since 2016年12月25日
 */
public class NullExpressionBuilder implements ExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		return new NullExpression();
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.NULL()!=null;
	}
}
