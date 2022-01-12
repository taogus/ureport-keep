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

import com.ureport.ureportkeep.core.Utils;
import com.ureport.ureportkeep.core.dsl.ReportParserParser;
import com.ureport.ureportkeep.core.exception.ReportParseException;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.expression.model.Op;
import com.ureport.ureportkeep.core.expression.model.condition.*;
import com.ureport.ureportkeep.core.expression.model.expr.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2016年12月26日
 */
public abstract class BaseExpressionBuilder implements ExpressionBuilder{
	protected BaseExpression parseSimpleValueContext(ReportParserParser.SimpleValueContext valueContext) {
		if(valueContext.BOOLEAN()!=null){
			return new BooleanExpression(Boolean.valueOf(valueContext.getText()));
		}else if(valueContext.INTEGER()!=null){
			return new IntegerExpression(Integer.valueOf(valueContext.INTEGER().getText()));
		}else if(valueContext.STRING()!=null){
			String text=valueContext.STRING().getText();
			text=text.substring(1,text.length()-1);
			return new StringExpression(text);
		}else if(valueContext.NUMBER()!=null){
			return new NumberExpression(Utils.toBigDecimal(valueContext.NUMBER().getText()));
		}else if(valueContext.NULL()!=null){
			return new NullExpression();
		}
		throw new ReportParseException("Unknow simple value context "+valueContext);
	}

	
	protected BaseCondition buildConditions(ReportParserParser.ConditionsContext conditionsContext) {
		List<ReportParserParser.ConditionContext> conditionContextList=conditionsContext.condition();
		List<ReportParserParser.JoinContext> joins=conditionsContext.join();
		BaseCondition condition=null;
		BaseCondition topCondition=null;
		int opIndex=0;
		for(ReportParserParser.ConditionContext conditionCtx:conditionContextList){
			if(condition==null){
				condition=parseCondition(conditionCtx);
				topCondition=condition;
			}else{
				BaseCondition nextCondition=parseCondition(conditionCtx);
				condition.setNextCondition(nextCondition);
				condition.setJoin(Join.parse(joins.get(opIndex).getText()));
				opIndex++;
				condition=nextCondition;
			}
		}
		return topCondition;
	}
	private BaseCondition parseCondition(ReportParserParser.ConditionContext context){
		if(context instanceof ReportParserParser.ExprConditionContext){
			ReportParserParser.ExprConditionContext ctx=(ReportParserParser.ExprConditionContext)context;
			BothExpressionCondition condition=new BothExpressionCondition();
			List<ReportParserParser.ExprContext> exprContexts=ctx.expr();
			String left=exprContexts.get(0).getText();
			condition.setLeft(left);
			Expression leftExpr= ExpressionUtils.parseExpression(left);
			condition.setLeftExpression(leftExpr);
			String rightExpr=exprContexts.get(1).getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			condition.setOperation(ctx.OP().getText());
			return condition;
		}else if(context instanceof ReportParserParser.CurrentValueConditionContext){
			ReportParserParser.CurrentValueConditionContext ctx=(ReportParserParser.CurrentValueConditionContext)context;
			CurrentValueExpressionCondition condition=new CurrentValueExpressionCondition();
			String rightExpr=ctx.expr().getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			return condition;
		}else if(context instanceof ReportParserParser.PropertyConditionContext){
			ReportParserParser.PropertyConditionContext ctx=(ReportParserParser.PropertyConditionContext)context;
			PropertyExpressionCondition condition=new PropertyExpressionCondition();
			String left=ctx.property().getText();
			condition.setLeft(left);
			condition.setLeftProperty(left);
			String rightExpr=ctx.expr().getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			return condition;
		}else if(context instanceof ReportParserParser.CellNameExprConditionContext){
			ReportParserParser.CellNameExprConditionContext ctx=(ReportParserParser.CellNameExprConditionContext)context;
			CellExpressionCondition condition=new CellExpressionCondition();
			String left=ctx.Cell().getText();
			condition.setLeft(left);
			condition.setCellName(left);
			String rightExpr=ctx.expr().getText();
			condition.setRight(rightExpr);
			condition.setRightExpression(ExpressionUtils.parseExpression(rightExpr));
			condition.setOp(parseOp(ctx.OP()));
			return condition;
		}
		throw new ReportParseException("Unknow condition context : "+context);
	}
	
	private Op parseOp(TerminalNode opNode){
		if(opNode.getText().equals(">")){
			return Op.GreatThen;
		}
		if(opNode.getText().equals("<")){
			return Op.LessThen;
		}
		if(opNode.getText().equals(">=")){
			return Op.EqualsGreatThen;
		}
		if(opNode.getText().equals("<=")){
			return Op.EqualsLessThen;
		}
		if(opNode.getText().equals("==")){
			return Op.Equals;
		}
		if(opNode.getText().equals("!=")){
			return Op.NotEquals;
		}
		throw new ReportParseException("Unknow operator :" +opNode);
	}
}
