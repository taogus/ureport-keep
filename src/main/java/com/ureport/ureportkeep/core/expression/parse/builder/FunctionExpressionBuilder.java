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
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.expr.BaseExpression;
import com.ureport.ureportkeep.core.expression.model.expr.FunctionExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2016年12月26日
 */
public class FunctionExpressionBuilder extends BaseExpressionBuilder {
	@Override
	public BaseExpression build(ReportParserParser.UnitContext unitContext) {
		ReportParserParser.FunctionContext ctx=unitContext.function();
		FunctionExpression expr=new FunctionExpression();
		expr.setExpr(ctx.getText());
		expr.setName(ctx.Identifier().getText());
		ReportParserParser.FunctionParameterContext functionParameterContext=ctx.functionParameter();
		if(functionParameterContext!=null){
			List<BaseExpression> exprList=new ArrayList<BaseExpression>();
			List<ReportParserParser.ItemContext> itemContexts=functionParameterContext.item();
			if(itemContexts!=null){
				for(int i=0;i<itemContexts.size();i++){
					ReportParserParser.ItemContext itemContext=itemContexts.get(i);
					BaseExpression baseExpr= ExpressionUtils.getExprVisitor().parseItemContext(itemContext);
					exprList.add(baseExpr);
				}
			}
			expr.setExpressions(exprList);
		}
		return expr;
	}

	@Override
	public boolean support(ReportParserParser.UnitContext unitContext) {
		return unitContext.function()!=null;
	}
}
