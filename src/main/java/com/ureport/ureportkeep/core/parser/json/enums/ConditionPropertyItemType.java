package com.ureport.ureportkeep.core.parser.json.enums;

import com.ureport.ureportkeep.core.definition.*;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.property.PropertyConfigModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/12/25 10:43
 * @Description:
 **/
public enum ConditionPropertyItemType {

    bgColor {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setBgcolorScope(scope);
                cellStyle.setBgcolor(value.toString());
                item.setCellStyle(cellStyle);
            }
        }
    },
    frColor {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setForecolorScope(scope);
                cellStyle.setForecolor(value.toString());
                item.setCellStyle(cellStyle);
            }
        }
    },
    font {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setFontFamilyScope(scope);
                cellStyle.setFontFamily(value.toString());
                item.setCellStyle(cellStyle);
            }
        }
    },
    fontSize {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setFontSizeScope(scope);
                cellStyle.setFontSize(((Number) value).intValue());
                item.setCellStyle(cellStyle);
            }
        }
    },
    fontBold {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setBoldScope(scope);
                cellStyle.setBold((boolean) value);
                item.setCellStyle(cellStyle);
            }
        }
    },
    fontTilt {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setItalicScope(scope);
                cellStyle.setItalic((boolean) value);
                item.setCellStyle(cellStyle);
            }
        }
    },
    under {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setUnderlineScope(scope);
                cellStyle.setUnderline((boolean) value);
                item.setCellStyle(cellStyle);
            }
        }
    },
    hAlign {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setAlignScope(scope);
                cellStyle.setAlign(Alignment.valueOf(value.toString()));
                item.setCellStyle(cellStyle);
            }
        }
    },
    vAlign {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Scope scope = config.getScope();
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setValignScope(scope);
                cellStyle.setValign(Alignment.valueOf(value.toString()));
                item.setCellStyle(cellStyle);
            }
        }
    },
    border {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();

                List<Map<String, Object>> borderList = (List<Map<String, Object>>) value;

                for (Map<String, Object> borderInfo : borderList) {
                    Map<String, Object> style = (Map<String, Object>) borderInfo.get("style");
                    String borderType = (String) borderInfo.get("value");
                    if ("top".equals(borderType)) {
                        cellStyle.setTopBorder(buildBorder(style));
                    } else if ("bottom".equals(borderType)) {
                        cellStyle.setBottomBorder(buildBorder(style));
                    } else if ("left".equals(borderType)) {
                        cellStyle.setLeftBorder(buildBorder(style));
                    } else if ("right".equals(borderType)) {
                        cellStyle.setRightBorder(buildBorder(style));
                    }
                }

                item.setCellStyle(cellStyle);
            }
        }
    },
    newValue {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Object value = config.getValue();

            if (value != null) {
                item.setNewValue(value.toString());
            }
        }
    },
    format {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setFormat(value.toString());
                item.setCellStyle(cellStyle);
            }
        }
    },
    rowHeight {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Object value = config.getValue();

            if (value != null) {
                ConditionCellStyle cellStyle = new ConditionCellStyle();
                cellStyle.setLineHeight(Float.parseFloat(value.toString()));
                item.setCellStyle(cellStyle);
            }
        }
    },
    colWidth {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Object value = config.getValue();

            if (value != null) {
                item.setColWidth((Integer) value);
            }
        }
    },
    newPage {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Object value = config.getValue();

            if (value != null) {
                Integer position = (Integer) value;

                ConditionPaging conditionPaging = new ConditionPaging();
                conditionPaging.setLine(0);
                if (position == 1) {
                    conditionPaging.setPosition(PagingPosition.before);
                } else {
                    conditionPaging.setPosition(PagingPosition.after);
                }

                item.setPaging(conditionPaging);
            }
        }
    },
    link {
        @Override
        public void parseItem(ConditionPropertyItem item, PropertyConfigModel config) {
            Object value = config.getValue();

            if (value != null) {
                Map<String, Object> linkConfig = (Map<String, Object>) value;
                item.setLinkUrl((String) linkConfig.get("link"));
                item.setLinkTargetWindow(WindowOption.parse((Integer) linkConfig.get("window")).getTarget());

                List<Map<String, Object>> params = (List<Map<String, Object>>) linkConfig.get("params");
                List<LinkParameter> linkParameters = new ArrayList<>();
                item.setLinkParameters(linkParameters);
                for (Map<String, Object> param : params) {
                    LinkParameter linkParameter = new LinkParameter();
                    linkParameter.setName((String) param.get("name"));
                    linkParameter.setValue((String) param.get("expression"));
                    linkParameter.setValueExpression(ExpressionUtils.parseExpression(linkParameter.getValue()));

                    linkParameters.add(linkParameter);
                }
            }
        }
    };

    public abstract void parseItem(ConditionPropertyItem item, PropertyConfigModel config);

    protected Border buildBorder(Map<String, Object> style) {
        Border border = new Border();
        border.setWidth((Integer) style.get("width"));
        border.setColor((String) style.get("color"));
        border.setStyle(BorderStyle.valueOf((String) style.get("line")));

        return border;
    }
}
