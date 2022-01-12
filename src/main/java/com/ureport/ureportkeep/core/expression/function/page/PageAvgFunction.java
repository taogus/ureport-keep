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
package com.ureport.ureportkeep.core.expression.function.page;

import com.ureport.ureportkeep.core.Utils;
import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectListExpressionData;
import com.ureport.ureportkeep.core.model.Cell;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年5月5日
 */
@Component
public class PageAvgFunction extends PageFunction {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		if(dataList==null){
			return 0;
		}
		int size=0;
		BigDecimal total=new BigDecimal(0);
		for(ExpressionData<?> exprData:dataList){
			if(exprData instanceof ObjectListExpressionData){
				ObjectListExpressionData listExpr=(ObjectListExpressionData)exprData;
				List<?> list=listExpr.getData();
				for(Object obj:list){
					if(obj==null){
						continue;
					}
					BigDecimal bigData= Utils.toBigDecimal(obj);
					total=total.add(bigData);
					size++;
				}
			}
		}
		return total.divide(new BigDecimal(size), 8, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String name() {
		return "pavg";
	}
}
