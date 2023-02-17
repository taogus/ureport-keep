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
package com.ureport.ureportkeep.core.export.excel.high.builder;

import com.ureport.ureportkeep.core.Utils;
import com.ureport.ureportkeep.core.chart.ChartData;
import com.ureport.ureportkeep.core.definition.Paper;
import com.ureport.ureportkeep.core.exception.ReportComputeException;
import com.ureport.ureportkeep.core.export.excel.high.CellStyleContext;
import com.ureport.ureportkeep.core.model.Column;
import com.ureport.ureportkeep.core.model.Image;
import com.ureport.ureportkeep.core.model.Report;
import com.ureport.ureportkeep.core.model.Row;
import com.ureport.ureportkeep.core.utils.ImageUtils;
import com.ureport.ureportkeep.core.utils.UnitUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Jacky.gao
 * @since 2017年8月10日
 */
public class ExcelBuilderDirect extends ExcelBuilder {
    public void build(Report report, OutputStream outputStream) {
        CellStyleContext cellStyleContext = new CellStyleContext();
        XSSFWorkbook wb = new XSSFWorkbook();
        CreationHelper creationHelper = wb.getCreationHelper();
        Paper paper = report.getPaper();
        try {
            List<Column> columns = report.getColumns();
            Map<Row, Map<Column, com.ureport.ureportkeep.core.model.Cell>> cellMap = report.getRowColCellMap();
            int columnSize = columns.size();
            Sheet sheet = createSheet(wb, paper, null);
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            List<Row> rows = report.getRows();
            int rowNumber = 0;
            for (Row r : rows) {
                int realHeight = r.getRealHeight();
                if (realHeight < 1) {
                    continue;
                }
                if (r.isForPaging()) {
                    return;
                }
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowNumber);
                if (row == null) {
                    row = sheet.createRow(rowNumber);
                }
                Map<Column, com.ureport.ureportkeep.core.model.Cell> colCell = cellMap.get(r);
                int skipCol = 0;
                for (int i = 0; i < columnSize; i++) {
                    Column col = columns.get(i);
                    int w = col.getWidth();
                    if (w < 1) {
                        skipCol++;
                        continue;
                    }
                    double colWidth = UnitUtils.pointToPixel(w) * 37.5;
                    int colNum = i - skipCol;
                    sheet.setColumnWidth(colNum, (short) colWidth);
                    org.apache.poi.ss.usermodel.Cell cell = row.getCell(colNum);
                    if (cell != null) {
                        continue;
                    }
                    cell = row.createCell(colNum);
                    com.ureport.ureportkeep.core.model.Cell cellInfo = null;
                    if (colCell != null) {
                        cellInfo = colCell.get(col);
                    }
                    if (cellInfo == null) {
                        continue;
                    }
                    if (cellInfo.isForPaging()) {
                        continue;
                    }
                    XSSFCellStyle style = cellStyleContext.produceXSSFCellStyle(wb, cellInfo);
                    int colSpan = cellInfo.getColSpan();
                    int rowSpan = cellInfo.getRowSpan();
                    int rowStart = rowNumber;
                    int rowEnd = rowSpan;
                    if (rowSpan == 0) {
                        rowEnd++;
                    }
                    rowEnd += rowNumber;
                    int colStart = i;
                    int colEnd = colSpan;
                    if (colSpan == 0) {
                        colEnd++;
                    }
                    colEnd += i;
                    for (int j = rowStart; j < rowEnd; j++) {
                        org.apache.poi.ss.usermodel.Row rr = sheet.getRow(j);
                        if (rr == null) {
                            rr = sheet.createRow(j);
                        }
                        for (int c = colStart; c < colEnd; c++) {
                            Cell cc = rr.getCell(c - skipCol);
                            if (cc == null) {
                                cc = rr.createCell(c - skipCol);
                            }
                            cc.setCellStyle(style);
                        }
                    }
                    if (colSpan > 0 || rowSpan > 0) {
                        if (rowSpan > 0) {
                            rowSpan--;
                        }
                        if (colSpan > 0) {
                            colSpan--;
                        }
                        CellRangeAddress cellRegion = new CellRangeAddress(rowNumber, (rowNumber + rowSpan), i - skipCol, (i - skipCol + colSpan));
                        sheet.addMergedRegion(cellRegion);
                    }
                    Object obj = cellInfo.getFormatData();
                    if (obj != null) {
                        if (obj instanceof String) {
                            cell.setCellValue((String) obj);
                            cell.setCellType(CellType.STRING);
                        } else if (obj instanceof Number) {
                            BigDecimal bigDecimal = Utils.toBigDecimal(obj);
                            cell.setCellValue(bigDecimal.doubleValue());
                            cell.setCellType(CellType.NUMERIC);
                        } else if (obj instanceof Boolean) {
                            cell.setCellValue((Boolean) obj);
                            cell.setCellType(CellType.BOOLEAN);
                        } else if (obj instanceof Image) {
                            Image img = (Image) obj;
                            InputStream inputStream = ImageUtils.base64DataToInputStream(img.getBase64Data());
                            BufferedImage bufferedImage = ImageIO.read(inputStream);
                            int width = bufferedImage.getWidth();
                            int height = bufferedImage.getHeight();
                            IOUtils.closeQuietly(inputStream);
                            inputStream = ImageUtils.base64DataToInputStream(img.getBase64Data());

                            try {
                                ClientAnchor anchor = creationHelper.createClientAnchor();
                                byte[] bytes = IOUtils.toByteArray(inputStream);
                                int pictureFormat = buildImageFormat(img);
                                int pictureIndex = wb.addPicture(bytes, pictureFormat);
                                anchor.setCol1(i);
                                anchor.setCol2(i + colSpan);
                                anchor.setRow1(rowNumber);
                                anchor.setRow2(rowNumber + rowSpan);
                                anchor.setDx1(0);
                                anchor.setDx2(width * Units.EMU_PER_PIXEL);
                                anchor.setDy1(0);
                                anchor.setDy2(height * Units.EMU_PER_PIXEL);
                                drawing.createPicture(anchor, pictureIndex);
                            } finally {
                                IOUtils.closeQuietly(inputStream);
                            }
                        } else if (obj instanceof ChartData) {
                            ChartData chartData = (ChartData) obj;
                            String base64Data = chartData.retriveBase64Data();
                            if (base64Data != null) {
                                Image img = new Image(base64Data, chartData.getWidth(), chartData.getHeight());
                                InputStream inputStream = ImageUtils.base64DataToInputStream(img.getBase64Data());
                                BufferedImage bufferedImage = ImageIO.read(inputStream);
                                int width = bufferedImage.getWidth();
                                int height = bufferedImage.getHeight();
                                IOUtils.closeQuietly(inputStream);
                                inputStream = ImageUtils.base64DataToInputStream(img.getBase64Data());

                                try {
                                    ClientAnchor anchor = creationHelper.createClientAnchor();
                                    byte[] bytes = IOUtils.toByteArray(inputStream);
                                    int pictureFormat = buildImageFormat(img);
                                    int pictureIndex = wb.addPicture(bytes, pictureFormat);
                                    anchor.setCol1(i);
                                    anchor.setCol2(i + colSpan);
                                    anchor.setRow1(rowNumber);
                                    anchor.setRow2(rowNumber + rowSpan);
                                    anchor.setDx1(0);
                                    anchor.setDx2(width * Units.EMU_PER_PIXEL);
                                    anchor.setDy1(0);
                                    anchor.setDy2(height * Units.EMU_PER_PIXEL);
                                    drawing.createPicture(anchor, pictureIndex);
                                    drawing.createPicture(anchor, pictureIndex);
                                } finally {
                                    IOUtils.closeQuietly(inputStream);
                                }
                            }
                        } else if (obj instanceof Date) {
                            cell.setCellValue((Date) obj);
                        }
                    }
                }
                row.setHeight((short) UnitUtils.pointToTwip(r.getRealHeight()));
                rowNumber++;
            }
            sheet.setRowBreak(rowNumber - 1);
            wb.write(outputStream);
        } catch (Exception ex) {
            throw new ReportComputeException(ex);
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
