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
package com.ureport.ureportkeep.core.provider.image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.utils.SpringContextUtils;


/**
 * @author Jacky.gao
 * @since 2017年3月6日
 */
@Component
public class DefaultImageProvider implements ImageProvider  {
	private String baseWebPath = SpringContextUtils.getWebBasePath();
	@Override
	public InputStream getImage(String path) {
		try {
			if(path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX) || path.startsWith("/WEB-INF")){
				return SpringContextUtils.getResourceStream(path);				
			}else{
				path=baseWebPath+path;
				return new FileInputStream(path);
			}
		} catch (IOException e) {
			throw new ReportComputeException(e);
		}
	}

	@Override
	public boolean support(String path) {
		if(path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)){
			return true;
		}else if(baseWebPath!=null && (path.startsWith("/") || path.startsWith("/WEB-INF"))){
			return true;
		}
		return false;
	}
}
