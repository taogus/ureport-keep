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
package com.ureport.ureportkeep.core.image;


import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Logger;

import com.ureport.ureportkeep.core.Utils;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.provider.image.ImageProvider;
import com.ureport.ureportkeep.core.utils.SpringContextUtils;

/**
 * @author Jacky.gao
 * @since 2017年3月20日
 */
public class StaticImageProcessor implements ImageProcessor<String> {
	private Logger log= Logger.getGlobal();
	@Override
	public InputStream getImage(String path) {
		Collection<ImageProvider> imageProviders= Utils.getImageProviders();
		ImageProvider targetImageProvider=null;
		for(ImageProvider provider:imageProviders){
			if(provider.support(path)){
				targetImageProvider=provider;
				break;
			}
		}
		if(targetImageProvider==null){
			throw new ReportComputeException("Unsupport image path :"+path);
		}
		try{
			InputStream inputStream=targetImageProvider.getImage(path);
			return inputStream;			
		}catch(Exception ex){
			log.warning("Image ["+path+"] not exist,use default picture.");
			String imageNotExistPath="classpath:com/bstek/ureport/image/image-not-exist.jpg";
			return SpringContextUtils.getResourceStream(imageNotExistPath);
		}
	}
}
