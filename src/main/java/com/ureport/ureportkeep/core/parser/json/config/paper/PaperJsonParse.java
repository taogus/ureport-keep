package com.ureport.ureportkeep.core.parser.json.config.paper;

import com.ureport.ureportkeep.core.definition.Paper;
import com.ureport.ureportkeep.core.definition.PaperSize;
import com.ureport.ureportkeep.core.definition.PaperType;
import com.ureport.ureportkeep.core.parser.json.JsonParse;
import com.ureport.ureportkeep.core.parser.json.config.global.ColumnConfigModel;
import com.ureport.ureportkeep.core.parser.json.config.global.ReportConfigModel;

/**
 * @Author: summer
 * @Date: 2022/12/28 20:07
 * @Description:
 **/
public class PaperJsonParse implements JsonParse<Paper, ReportConfigModel> {
    @Override
    public Paper parse(ReportConfigModel parseModel) {
        ColumnConfigModel columnConfigModel = parseModel.getColumnConfigModel();
        PaperType pageType = parseModel.getPageType();
        int width, height;
        if (PaperType.CUSTOM.equals(pageType)) {
            width = parseModel.getWidth();
            height = parseModel.getHeight();
        } else {
            PaperSize paperSize = pageType.getPaperSize();
            width = paperSize.getWidth();
            height = paperSize.getHeight();
        }

        Paper paper=new Paper();
        paper.setOrientation(parseModel.getOrientation());
        paper.setPaperType(pageType);
        paper.setWidth(width);
        paper.setHeight(height);
        paper.setLeftMargin(parseModel.getLeftMargin());
        paper.setRightMargin(parseModel.getRightMargin());
        paper.setTopMargin(parseModel.getTopMargin());
        paper.setBottomMargin(parseModel.getBottomMargin());
        paper.setPagingMode(parseModel.getPagingMode());
        paper.setHtmlReportAlign(parseModel.getHtmlAlign());
        paper.setHtmlIntervalRefreshValue(parseModel.getHtmlRefresh());
        paper.setBgImage(parseModel.getBgImg());

        if (columnConfigModel != null) {
            paper.setColumnEnabled(columnConfigModel.isEnable());
            if (columnConfigModel.isEnable()) {
                paper.setColumnCount(columnConfigModel.getCount());
                paper.setColumnMargin(columnConfigModel.getMargin());
            }
        }
        return paper;
    }
}
