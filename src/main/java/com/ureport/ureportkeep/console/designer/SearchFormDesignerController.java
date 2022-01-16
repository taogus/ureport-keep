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
package com.ureport.ureportkeep.console.designer;

import com.ureport.ureportkeep.console.AbstractReportBasicController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author summer
 * @Date: 2022/1/14
 * Description: 查询表单控制器
 */
@Controller
@RequestMapping(value = "/searchFormDesigner")
public class SearchFormDesignerController extends AbstractReportBasicController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String searchForm() {

		return "searchform.html";
	}

}
