package com.ureport.ureportkeep.core.expression.function;


import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.model.Cell;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年12月7日
 */
@Component
public class ParameterIsEmptyFunction extends ParameterFunction{
	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context,
						  Cell currentCell) {
		Object obj = super.execute(dataList, context, currentCell);
		if(obj==null || obj.toString().trim().equals("")){
			return true;
		}
		return false;
	}
	@Override
	public String name() {
		return "emptyparam";
	}
}
