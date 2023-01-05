package com.ureport.ureportkeep.core.parser.json.config.border;

import com.ureport.ureportkeep.core.definition.Border;
import com.ureport.ureportkeep.core.definition.CellStyle;
import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.utils.CellJsonParseUtils;

/**
 * @Author: summer
 * @Date: 2022/12/2 21:11
 * @Description:
 **/
public interface BorderJsonModel extends JsonModel {

    /**
     * 边框范围类型
     */
    enum RangeType {
        range, cell;
    }

    enum BorderStyle {
        Thin(1),
        Hair(2),
        Dotted(3),
        Dashed(4),
        DashDot(5),
        DashDotDot(6),
        Double(7),
        Medium(8),
        MediumDashed(9),
        MediumDashDot(10),
        MediumDashDotDot(11),
        SlantedDashDot(12),
        Thick(13)
        ;

        private int style;

        BorderStyle(int style) {
            this.style = style;
        }

        public static BorderStyle parse(int type) {
            BorderStyle[] values = BorderStyle.values();
            for (BorderStyle value : values) {
                if (type == value.style) {
                    return value;
                }
            }

            return Thin;
        }
    }

    enum BorderType {
        borderLeft("border-left") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderCol = borderRangeModel.getColumn()[0];
                if (colIdx != borderCol) {
                    return;
                }

                cellStyle.setLeftBorder(createBorder(borderInfo));
            }
        },
        borderRight("border-right") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderCol = borderRangeModel.getColumn()[1];
                if (colIdx != borderCol) {
                    return;
                }

                cellStyle.setRightBorder(createBorder(borderInfo));
            }
        },
        borderTop("border-top") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderRow = borderRangeModel.getRow()[0];
                if (rowIdx != borderRow) {
                    return;
                }

                cellStyle.setTopBorder(createBorder(borderInfo));
            }
        },
        borderBottom("border-bottom") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderRow = borderRangeModel.getRow()[1];
                if (rowIdx != borderRow) {
                    return;
                }

                cellStyle.setBottomBorder(createBorder(borderInfo));
            }
        },
        borderAll("border-all") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                cellStyle.setLeftBorder(createBorder(borderInfo));
                cellStyle.setRightBorder(createBorder(borderInfo));
                cellStyle.setTopBorder(createBorder(borderInfo));
                cellStyle.setBottomBorder(createBorder(borderInfo));
            }
        },
        borderOutside("border-outside") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderRow = borderRangeModel.getRow()[0];
                Integer borderColumn = borderRangeModel.getColumn()[0];
                Integer borderEndRow = borderRangeModel.getRow()[1];
                Integer borderEndCol = borderRangeModel.getColumn()[1];

                if (rowIdx == borderRow) {
                    cellStyle.setTopBorder(createBorder(borderInfo));
                }
                if (rowIdx == borderEndRow) {
                    cellStyle.setBottomBorder(createBorder(borderInfo));
                }
                if (colIdx == borderColumn) {
                    cellStyle.setLeftBorder(createBorder(borderInfo));
                }
                if (colIdx == borderEndCol) {
                    cellStyle.setRightBorder(createBorder(borderInfo));
                }
            }
        },
        borderInside("border-inside") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderRow = borderRangeModel.getRow()[0];
                Integer borderColumn = borderRangeModel.getColumn()[0];
                Integer borderEndRow = borderRangeModel.getRow()[1];
                Integer borderEndCol = borderRangeModel.getColumn()[1];

                if (borderRow == borderEndRow || rowIdx < borderEndRow) {
                    cellStyle.setBottomBorder(createBorder(borderInfo));
                }
                if (borderColumn == borderEndCol || colIdx < borderEndCol) {
                    cellStyle.setRightBorder(createBorder(borderInfo));
                }
            }
        },
        borderHorizontal("border-horizontal") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderRow = borderRangeModel.getRow()[0];
                Integer borderEndRow = borderRangeModel.getRow()[1];

                if (borderRow == borderEndRow || rowIdx < borderEndRow) {
                    cellStyle.setBottomBorder(createBorder(borderInfo));
                }
            }
        },
        borderVertical("border-vertical") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                BorderRangeModel borderRangeModel = borderInfo.getRange().get(0);
                Integer borderColumn = borderRangeModel.getColumn()[0];
                Integer borderEndCol = borderRangeModel.getColumn()[1];

                if (borderColumn == borderEndCol || colIdx < borderEndCol) {
                    cellStyle.setRightBorder(createBorder(borderInfo));
                }
            }
        },
        borderNone("border-none") {
            @Override
            public void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo) {
                cellStyle.setLeftBorder(null);
                cellStyle.setRightBorder(null);
                cellStyle.setTopBorder(null);
                cellStyle.setBottomBorder(null);
            }
        };

        private String type;

        /**
         * 构建单元格边框
         *
         * @param rowIdx 单元格行
         * @param colIdx 单元格列
         * @param cellStyle 单元格样式
         * @param borderInfo 边框样式
         */
        public abstract void builderBorder(int rowIdx, int colIdx, CellStyle cellStyle, BorderInfoModel borderInfo);

        BorderType(String type) {
            this.type = type;
        }

        public static BorderType parse(String type) {
            BorderType[] values = BorderType.values();
            for (BorderType value : values) {
                if (type.equals(value.type)) {
                    return value;
                }
            }

            return borderNone;
        }

        /**
         * 创建边框
         *
         * @param borderInfo
         * @return
         */
        protected Border createBorder(BorderInfoModel borderInfo) {
            Border border = new Border();
            border.setWidth(1);
            border.setBorderStyle(BorderStyle.parse(borderInfo.getStyle()));
            border.setColor(CellJsonParseUtils.hex2RGB(borderInfo.getColor()));
            border.setStyle(com.ureport.ureportkeep.core.definition.BorderStyle .solid);

            return border;
        }

    }
}
