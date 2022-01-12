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
import com.ureport.ureportkeep.core.expression.model.expr.cell.CellObjectExpression;

/**
 * @author Jacky.gao
 * @since 2017年1月20日
 */
public class CellObjectExpressionBuilder implements ExpressionBuilder {

	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		ReportParserParser.CellContext ctx=unitContext.cell();
		String property=null;
		ReportParserParser.PropertyContext propCtx=ctx.property();
		if(propCtx!=null){
			property=propCtx.getText();
		}
		CellObjectExpression expr=new CellObjectExpression(property);
		expr.setExpr(ctx.getText());
		return expr;
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.cell()!=null;
	}
}
