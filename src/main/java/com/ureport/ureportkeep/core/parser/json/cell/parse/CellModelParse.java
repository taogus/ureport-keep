package com.ureport.ureportkeep.core.parser.json.cell.parse;

import com.ureport.ureportkeep.core.definition.*;
import com.ureport.ureportkeep.core.definition.value.Value;
import com.ureport.ureportkeep.core.export.pdf.font.FontBuilder;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.parser.json.JsonParse;
import com.ureport.ureportkeep.core.parser.json.ReportModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.CellModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.FrontStyleModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.PositionModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.link.LinkConfigModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.link.LinkParameterModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.property.ConditionPropertyItemModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.property.PropertyModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.style.CellStyleModel;
import com.ureport.ureportkeep.core.parser.json.cell.parse.value.ValueJsonParse;
import com.ureport.ureportkeep.core.parser.json.cell.parse.value.impl.*;
import com.ureport.ureportkeep.core.parser.json.config.border.BorderInfoModel;
import com.ureport.ureportkeep.core.parser.json.config.border.BorderJsonModel;
import com.ureport.ureportkeep.core.parser.json.config.border.BorderRangeModel;
import com.ureport.ureportkeep.core.parser.json.enums.CellType;
import com.ureport.ureportkeep.core.parser.json.enums.ConditionPropertyItemType;
import com.ureport.ureportkeep.core.parser.json.enums.WindowOption;
import com.ureport.ureportkeep.core.parser.json.utils.CellJsonParseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/12/11 14:00
 * @Description:
 **/
public class CellModelParse implements JsonParse<List<CellDefinition>, ReportModel> {

    private final Map<CellType, ValueJsonParse> cellJsonParseMap = new HashMap<>();

    {
        cellJsonParseMap.put(CellType.SIMPLE, new SimpleValueJsonParse());
        cellJsonParseMap.put(CellType.DATASET, new DatasetValueJsonParse());
        cellJsonParseMap.put(CellType.EXPRESS, new ExpressionValueJsonParser());
        cellJsonParseMap.put(CellType.IMAGE, new ImageValueJsonParse());
        cellJsonParseMap.put(CellType.BARCODE, new BarCodeValueJsonParse());
    }

    @Override
    public List<CellDefinition> parse(ReportModel parseModel) {
        List<CellModel> cellDatas = parseModel.getCellDatas();

        List<CellDefinition> cellDefs = new ArrayList<>();
        CellDefinition cell = null;
        for (CellModel cellModel : cellDatas) {
            String cellName = cellModel.getCellName();
            PositionModel position = cellModel.getPosition();
            FrontStyleModel fontStyle = cellModel.getFrontStyle();
            CellStyleModel style = cellModel.getCellStyle();

            cell = new CellDefinition();
            cell.setName(cellName);
            cell.setColumnNumber(position.getCol() + 1);
            cell.setRowNumber(position.getRow() + 1);
            cell.setLeftParentCellName(cellModel.getLeftCellName());
            cell.setTopParentCellName(cellModel.getTopCellName());
            cell.setRowSpan(fontStyle.getRowspan());
            cell.setColSpan(fontStyle.getColSpan());
            cell.setValue(parseValue(cellModel));
            cell.setExpand(cellModel.getExpand());
            cell.setCellStyle(parseStyle(cellModel, parseModel.getBorderInfo()));
            boolean blankLine = style.isBlankLine();
            if (blankLine) {
                cell.setFillBlankRows(true);
                cell.setMultiple(style.getMultiple());
            }
            // 链接配置
            parseLinkConfig(cellModel, cell);
            // 条件属性
            parseConditionProperty(cellModel, cell);

            cellDefs.add(cell);
        }

        return cellDefs;
    }

    /**
     * 解析条件属性配置
     *
     * @param cellModel
     * @param cell
     */
    private void parseConditionProperty(CellModel cellModel, CellDefinition cell) {
        List<ConditionPropertyItem> conditionItems = new ArrayList<>();
        cell.setConditionPropertyItems(conditionItems);

        List<ConditionPropertyItemModel> conditionPropertyItems = cellModel.getConditionPropertyItems();
        if (CollectionUtils.isEmpty(conditionPropertyItems)) {
            return;
        }
        for (ConditionPropertyItemModel conditionPropertyItem : conditionPropertyItems) {
            ConditionPropertyItem item = new ConditionPropertyItem();

            List<PropertyModel> propertys = conditionPropertyItem.getPropertys();
            for (PropertyModel property : propertys) {
                ConditionPropertyItemType itemType = property.getName();
                itemType.parseItem(item, property.getConfig());
            }

            conditionItems.add(item);
        }
    }

    /**
     * 解析超级链接配置
     *
     * @param cellModel
     * @param cell
     */
    private void parseLinkConfig(CellModel cellModel, CellDefinition cell) {
        LinkConfigModel linkConfig = cellModel.getLinkConfig();
        if (linkConfig != null) {
            String url = linkConfig.getUrl();

            cell.setLinkTargetWindow(WindowOption.parse(linkConfig.getTargetWindow()).getTarget());
            cell.setLinkUrl(url);
            if (StringUtils.isNotBlank(url)) {
                if (url.startsWith(ExpressionUtils.EXPR_PREFIX) && url.endsWith(ExpressionUtils.EXPR_SUFFIX)) {
                    String expr = url.substring(2, url.length() - 1);
                    Expression urlExpression = ExpressionUtils.parseExpression(expr);
                    cell.setLinkUrlExpression(urlExpression);
                }
            }

            List<LinkParameterModel> parameters = linkConfig.getParameters();
            if (!CollectionUtils.isEmpty(parameters)) {
                List<LinkParameter> linkParameters = new ArrayList<>();
                cell.setLinkParameters(linkParameters);
                for (LinkParameterModel parameter : parameters) {
                    LinkParameter param = new LinkParameter();
                    param.setName(parameter.getName());
                    param.setValue(parameter.getExpression());
                    Expression expr= ExpressionUtils.parseExpression(parameter.getExpression());
                    param.setValueExpression(expr);;

                    linkParameters.add(param);
                }
            }

        }
    }

    /**
     * 单元格样式解析
     *
     * @param cellModel
     * @param borderInfos
     * @return
     */
    private CellStyle parseStyle(CellModel cellModel, List<BorderInfoModel> borderInfos) {
        int rowIdx = cellModel.getPosition().getRow();
        int colIdx = cellModel.getPosition().getCol();

        CellStyleModel cellStyleModel = cellModel.getCellStyle();
        FrontStyleModel frontStyle = cellModel.getFrontStyle();

        CellStyle cellStyle = new CellStyle();
        cellStyle.setFormat(cellStyleModel.getFormat());
        cellStyle.setWrapCompute(cellStyleModel.isWrapCompute());
        cellStyle.setBgcolor(CellJsonParseUtils.hex2RGB(frontStyle.getBgColor()));
        cellStyle.setForecolor(CellJsonParseUtils.hex2RGB(frontStyle.getForeColor()));
        cellStyle.setFontSize(frontStyle.getFontSize());
        cellStyle.setFontFamily(FontBuilder.fontIndexMap.get(frontStyle.getFontFormat()));
        cellStyle.setAlign(Alignment.hParse(frontStyle.getHorizontal()));
        cellStyle.setValign(Alignment.vParse(frontStyle.getVertical()));
        cellStyle.setBold(frontStyle.getBold() == 1);
        cellStyle.setItalic(frontStyle.getItalic() == 1);
        cellStyle.setUnderline(frontStyle.getUnderline() == 1);
        cellStyle.setDropLine(frontStyle.getDropLine() == 1);

        if (!CollectionUtils.isEmpty(borderInfos)) {
            for (BorderInfoModel borderInfo : borderInfos) {
                String borderTypeStr = borderInfo.getBorderType();
                BorderJsonModel.BorderType borderType = BorderJsonModel.BorderType.parse(borderTypeStr);
                BorderJsonModel.RangeType rangeType = borderInfo.getRangeType();
                if (BorderJsonModel.RangeType.cell.equals(rangeType)) {
                    // 单元格范围暂不支持
                    continue;
                }
                List<BorderRangeModel> range = borderInfo.getRange();
                BorderRangeModel borderRangeModel = range.get(0);
                Integer[] row = borderRangeModel.getRow();
                Integer[] column = borderRangeModel.getColumn();

                if (!(rowIdx >= row[0] && rowIdx <= row[1]) || !(colIdx >= column[0] && colIdx <= column[1])) {
                    continue;
                }

                borderType.builderBorder(rowIdx, colIdx, cellStyle, borderInfo);
            }
        }
        return cellStyle;
    }

    /**
     * 解析value
     *
     * @param cellModel
     * @return
     */
    private Value parseValue(CellModel cellModel) {
        CellType cellType = cellModel.getCellType();
        return cellJsonParseMap.get(cellType).parse(cellModel);
    }

}
