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
package com.ureport.ureportkeep.core.parser.impl;

import com.ureport.ureportkeep.core.definition.*;
import com.ureport.ureportkeep.core.parser.Parser;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Component;


/**
 * @author Jacky.gao
 * @since 2017年1月19日
 */
@Component
public class PaperParser implements Parser<Paper> {
	@Override
	public Paper parse(Element element) {
		Paper paper=new Paper();
		String orientation=element.attributeValue("orientation");
		paper.setOrientation(Orientation.valueOf(orientation));
		paper.setPaperType(PaperType.valueOf(element.attributeValue("type")));
		if(paper.getPaperType().equals(PaperType.CUSTOM)){
			paper.setWidth(Integer.valueOf(element.attributeValue("width")));
			paper.setHeight(Integer.valueOf(element.attributeValue("height")));
		}else{
			PaperSize size=paper.getPaperType().getPaperSize();
			paper.setWidth(size.getWidth());
			paper.setHeight(size.getHeight());
		}
		String leftMargin=element.attributeValue("left-margin");
		if(StringUtils.isNotBlank(leftMargin)){
			paper.setLeftMargin(Integer.valueOf(leftMargin));
		}
		String rightMargin=element.attributeValue("right-margin");
		if(StringUtils.isNotBlank(rightMargin)){
			paper.setRightMargin(Integer.valueOf(rightMargin));
		}
		String topMargin=element.attributeValue("top-margin");
		if(StringUtils.isNotBlank(topMargin)){
			paper.setTopMargin(Integer.valueOf(topMargin));
		}
		String bottomMargin=element.attributeValue("bottom-margin");
		if(StringUtils.isNotBlank(bottomMargin)){
			paper.setBottomMargin(Integer.valueOf(bottomMargin));
		}
		paper.setPagingMode(PagingMode.valueOf(element.attributeValue("paging-mode")));
		if(paper.getPagingMode().equals(PagingMode.fixrows)){
			paper.setFixRows(Integer.valueOf(element.attributeValue("fixrows")));
		}
		String columnEnabled=element.attributeValue("column-enabled");
		if(StringUtils.isNotBlank(columnEnabled)){
			paper.setColumnEnabled(Boolean.valueOf(columnEnabled));
		}
		if(paper.isColumnEnabled()){
			paper.setColumnCount(Integer.valueOf(element.attributeValue("column-count")));
			paper.setColumnMargin(Integer.valueOf(element.attributeValue("column-margin")));
		}
		String htmlReportAlign=element.attributeValue("html-report-align");
		if(StringUtils.isNotBlank(htmlReportAlign)){
			paper.setHtmlReportAlign(HtmlReportAlign.valueOf(htmlReportAlign));
		}
		String htmlIntervalRefreshValue=element.attributeValue("html-interval-refresh-value");
		if(StringUtils.isNotBlank(htmlIntervalRefreshValue)){
			paper.setHtmlIntervalRefreshValue(Integer.valueOf(htmlIntervalRefreshValue));
		}
		paper.setBgImage(element.attributeValue("bg-image"));
		watermarkParse(element, paper);
		return paper;
	}

	/**
	 * 水印内容解析
	 *
	 * @param element
	 * @param paper
	 */
	private void watermarkParse(Element element, Paper paper) {
		String watermarkEnabled = element.attributeValue("watermark-enabled");
		if (!StringUtils.isEmpty(watermarkEnabled)) {
			String watermarkText = element.attributeValue("watermark-text");
			String watermarkColor = element.attributeValue("watermark-color");
			if (!StringUtils.isEmpty(watermarkText)) {
				String color = watermarkColor;
				if (StringUtils.isEmpty(color)) {
					color = "0,0,0";
				}
				String[] colorNum = color.split(",");
				if (colorNum.length < 3) {
					colorNum = new String[]{"0", "0", "0"};
				}
				int[] rgbColor = new int[]{
						Integer.parseInt(colorNum[0].trim()),
						Integer.parseInt(colorNum[1].trim()),
						Integer.parseInt(colorNum[2].trim())
				};

				paper.setWatermarkEnabled(Boolean.parseBoolean(watermarkEnabled));
				paper.setWatermarkText(watermarkText);
				paper.setWatermarkColor(rgbColor);
			}
		}
	}

	@Override
	public String getName() {
		return "paper";
	}
}
