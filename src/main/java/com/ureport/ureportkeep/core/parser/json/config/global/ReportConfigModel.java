package com.ureport.ureportkeep.core.parser.json.config.global;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ureport.ureportkeep.core.definition.HtmlReportAlign;
import com.ureport.ureportkeep.core.definition.Orientation;
import com.ureport.ureportkeep.core.definition.PagingMode;
import com.ureport.ureportkeep.core.definition.PaperType;
import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/12/2 21:21
 * @Description: 报表全局配置model
 **/
public class ReportConfigModel implements JsonModel {

    private static final long serialVersionUID = 1L;

    /**
     * 套打背景图
     */
    private String bgImg;

    /**
     * 纸张大小
     */
    private PaperType pageType;

    private int width;

    private int height;

    private int topMargin = 30;

    private int bottomMargin = 30;

    private int leftMargin = 30;

    private int rightMargin = 30;

    /**
     * 打印方向
     */
    private Orientation orientation;

    private PageHeaderFooterConfigModel headerConfig;

    private PageHeaderFooterConfigModel footerConfig;

    private ColumnConfigModel columnConfigModel;

    /**
     * 分页模式
     */
    private PagingMode pagingMode;

    /**
     * 固定行数
     */
    private int fixRows;

    private HtmlReportAlign htmlAlign;

    /**
     * html刷新秒数
     */
    @JsonProperty("htmlIntervalRefreshValue")
    private int htmlRefresh;

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public PaperType getPageType() {
        return pageType;
    }

    public void setPageType(PaperType pageType) {
        this.pageType = pageType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    public int getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public PageHeaderFooterConfigModel getHeaderConfig() {
        return headerConfig;
    }

    public void setHeaderConfig(PageHeaderFooterConfigModel headerConfig) {
        this.headerConfig = headerConfig;
    }

    public PageHeaderFooterConfigModel getFooterConfig() {
        return footerConfig;
    }

    public void setFooterConfig(PageHeaderFooterConfigModel footerConfig) {
        this.footerConfig = footerConfig;
    }

    public ColumnConfigModel getColumnConfigModel() {
        return columnConfigModel;
    }

    public void setColumnConfigModel(ColumnConfigModel columnConfigModel) {
        this.columnConfigModel = columnConfigModel;
    }

    public PagingMode getPagingMode() {
        return pagingMode;
    }

    public void setPagingMode(PagingMode pagingMode) {
        this.pagingMode = pagingMode;
    }

    public int getFixRows() {
        return fixRows;
    }

    public void setFixRows(int fixRows) {
        this.fixRows = fixRows;
    }

    public HtmlReportAlign getHtmlAlign() {
        return htmlAlign;
    }

    public void setHtmlAlign(HtmlReportAlign htmlAlign) {
        this.htmlAlign = htmlAlign;
    }

    public int getHtmlRefresh() {
        return htmlRefresh;
    }

    public void setHtmlRefresh(int htmlRefresh) {
        this.htmlRefresh = htmlRefresh;
    }
}
