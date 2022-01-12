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
package com.ureport.ureportkeep.core.expression.function.math;

import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.model.Cell;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年1月23日
 */
@Component
public class RandomFunction extends MathFunction {
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		int feed=0;
		if(dataList.size()>0){
			BigDecimal data=buildBigDecimal(dataList);
			feed=data.intValue();
		}
		if(feed==0){
			return Math.random();
		}
		return RandomUtils.nextInt(feed);
	}

	@Override
	public String name() {
		return "random";
	}
}
