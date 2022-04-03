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
package com.ureport.ureportkeep.core.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import com.ureport.ureportkeep.core.build.assertor.Assertor;
import com.ureport.ureportkeep.core.build.assertor.EqualsAssertor;
import com.ureport.ureportkeep.core.build.assertor.EqualsGreatThenAssertor;
import com.ureport.ureportkeep.core.build.assertor.EqualsLessThenAssertor;
import com.ureport.ureportkeep.core.build.assertor.GreatThenAssertor;
import com.ureport.ureportkeep.core.build.assertor.InAssertor;
import com.ureport.ureportkeep.core.build.assertor.LessThenAssertor;
import com.ureport.ureportkeep.core.build.assertor.LikeAssertor;
import com.ureport.ureportkeep.core.build.assertor.NotEqualsAssertor;
import com.ureport.ureportkeep.core.build.assertor.NotInAssertor;
import com.ureport.ureportkeep.core.dsl.ReportParserLexer;
import com.ureport.ureportkeep.core.dsl.ReportParserParser;
import com.ureport.ureportkeep.core.exception.ReportParseException;
import com.ureport.ureportkeep.core.expression.function.Function;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.expression.model.Op;
import com.ureport.ureportkeep.core.expression.parse.ExpressionErrorListener;
import com.ureport.ureportkeep.core.expression.parse.ExpressionVisitor;
import com.ureport.ureportkeep.core.expression.parse.builder.BooleanExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.CellObjectExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.CellPositionExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.CurrentCellDataExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.CurrentCellValueExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.DatasetExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.ExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.FunctionExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.IntegerExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.NullExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.NumberExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.RelativeCellExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.SetExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.StringExpressionBuilder;
import com.ureport.ureportkeep.core.expression.parse.builder.VariableExpressionBuilder;
import com.ureport.ureportkeep.core.utils.SpringContextUtils;


/**
 * @author Jacky.gao
 * @since 2016年12月24日
 */
public class ExpressionUtils {
	public static final String EXPR_PREFIX="${";
	public static final String EXPR_SUFFIX="}";
	private static ExpressionVisitor exprVisitor;
	private static Map<String, Function> functions=new HashMap<String,Function>();
	private static Map<Op, Assertor> assertorsMap=new HashMap<Op,Assertor>();
	private static List<ExpressionBuilder> expressionBuilders=new ArrayList<ExpressionBuilder>();
	private static List<String> cellNameList=new ArrayList<String>();
	private static String[] LETTERS={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	static{
		Collection<Function> coll = SpringContextUtils.getAllBeansOfType(Function.class);
		for (Function fun : coll) {
			functions.put(fun.name(), fun);
		}
		
		expressionBuilders.add(new StringExpressionBuilder());
		expressionBuilders.add(new VariableExpressionBuilder());
		expressionBuilders.add(new BooleanExpressionBuilder());
		expressionBuilders.add(new IntegerExpressionBuilder());
		expressionBuilders.add(new DatasetExpressionBuilder());
		expressionBuilders.add(new FunctionExpressionBuilder());
		expressionBuilders.add(new NumberExpressionBuilder());
		expressionBuilders.add(new CellPositionExpressionBuilder());
		expressionBuilders.add(new RelativeCellExpressionBuilder());
		expressionBuilders.add(new SetExpressionBuilder());
		expressionBuilders.add(new CellObjectExpressionBuilder());
		expressionBuilders.add(new NullExpressionBuilder());
		expressionBuilders.add(new CurrentCellValueExpressionBuilder());
		expressionBuilders.add(new CurrentCellDataExpressionBuilder());
		
		assertorsMap.put(Op.Equals, new EqualsAssertor());
		assertorsMap.put(Op.EqualsGreatThen, new EqualsGreatThenAssertor());
		assertorsMap.put(Op.EqualsLessThen, new EqualsLessThenAssertor());
		assertorsMap.put(Op.GreatThen, new GreatThenAssertor());
		assertorsMap.put(Op.LessThen, new LessThenAssertor());
		assertorsMap.put(Op.NotEquals, new NotEqualsAssertor());
		assertorsMap.put(Op.In, new InAssertor());
		assertorsMap.put(Op.NotIn, new NotInAssertor());
		assertorsMap.put(Op.Like, new LikeAssertor());
		
		for(int i=0;i<LETTERS.length;i++){
			cellNameList.add(LETTERS[i]);
		}
		
		for(int i=0;i<LETTERS.length;i++){
			String name=LETTERS[i];
			for(int j=0;j<LETTERS.length;j++){
				cellNameList.add(name+LETTERS[j]);
			}
		}
	}
	
	public static List<String> getCellNameList() {
		return cellNameList;
	}
	
	public static Map<String, Function> getFunctions() {
		return functions;
	}
	
	public static Map<Op, Assertor> getAssertorsMap() {
		return assertorsMap;
	}
	
	public static boolean conditionEval(Op op,Object left,Object right){
		Assertor assertor=assertorsMap.get(op);
		boolean result=assertor.eval(left, right);
		return result;
	}
	
	public static Expression parseExpression(String text){
		ANTLRInputStream antlrInputStream=new ANTLRInputStream(text);
		ReportParserLexer lexer=new ReportParserLexer(antlrInputStream);
		CommonTokenStream tokenStream=new CommonTokenStream(lexer);
		ReportParserParser parser=new ReportParserParser(tokenStream);
		ExpressionErrorListener errorListener=new ExpressionErrorListener();
		parser.addErrorListener(errorListener);
		exprVisitor=new ExpressionVisitor(expressionBuilders);
		Expression expression=exprVisitor.visitEntry(parser.entry());
		String error=errorListener.getErrorMessage();
		if(error!=null){
			throw new ReportParseException("Expression parse error:"+error);
		}
		return expression;
	}
	
	public static ExpressionVisitor getExprVisitor() {
		return exprVisitor;
	}
}
