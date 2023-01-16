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
package com.ureport.ureportkeep.core.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureport.ureportkeep.core.definition.*;
import com.ureport.ureportkeep.core.definition.datasource.DatasourceDefinition;
import com.ureport.ureportkeep.core.definition.searchform.SearchForm;
import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.exception.ReportParseException;
import com.ureport.ureportkeep.core.exception.ReportStartupException;
import com.ureport.ureportkeep.core.parser.json.ParseDispatch;
import com.ureport.ureportkeep.core.parser.json.ReportModel;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;


/**
 * @author Jacky.gao
 * @since 2016年12月2日
 */
@Component
public class ReportParser extends ReportParseFactory implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ParseDispatch parseDispatch;

    /**
     * 解析json报表文件
     *
     * @param json
     * @param file
     * @return
     */
    public ReportDefinition parse(String json, String file) {
        if (StringUtils.isEmpty(json)) {
            throw new ReportParseException("找不到报表文件");
        }

        ReportModel reportModel = null;
        try {
            reportModel = objectMapper.readValue(json, ReportModel.class);
            reportModel.setReportName(file);
        } catch (JsonProcessingException e) {
            throw new ReportParseException(e);
        }

        ReportDefinition report = parseDispatch.dispatch(reportModel);
        rebuild(report);
        return report;
    }

    /**
     * 解析xml报表文件
     *
     * @param inputStream
     * @param file
     * @return
     */
    public ReportDefinition parse(InputStream inputStream, String file) {
        ReportDefinition report = new ReportDefinition();
        report.setReportFullName(file);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            Element element = document.getRootElement();
            if (!element.getName().equals("ureport")) {
                throw new ReportParseException("Unknow report file.");
            }
            List<RowDefinition> rows = new ArrayList<RowDefinition>();
            List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
            List<CellDefinition> cells = new ArrayList<CellDefinition>();
            List<DatasourceDefinition> datasources = new ArrayList<DatasourceDefinition>();
            report.setRows(rows);
            report.setColumns(columns);
            report.setCells(cells);
            report.setDatasources(datasources);
            for (Object obj : element.elements()) {
                if (obj == null || !(obj instanceof Element)) {
                    continue;
                }
                Element ele = (Element) obj;
                Parser<?> parser = getParse(ele.getName());
                if (parser != null) {
                    Object target = parser.parse(ele);
                    if (target instanceof RowDefinition) {
                        rows.add((RowDefinition) target);
                    } else if (target instanceof ColumnDefinition) {
                        columns.add((ColumnDefinition) target);
                    } else if (target instanceof CellDefinition) {
                        cells.add((CellDefinition) target);
                    } else if (target instanceof DatasourceDefinition) {
                        datasources.add((DatasourceDefinition) target);
                    } else if (target instanceof Paper) {
                        Paper paper = (Paper) target;
                        report.setPaper(paper);
                    } else if (target instanceof HeaderFooterDefinition) {
                        HeaderFooterDefinition hf = (HeaderFooterDefinition) target;
                        if (ele.getName().equals("header")) {
                            report.setHeader(hf);
                        } else {
                            report.setFooter(hf);
                        }
                    } else if (target instanceof SearchForm) {
                        SearchForm form = (SearchForm) target;
                        report.setSearchForm(form);
                        report.setSearchFormXml(ele.asXML());
                    }
                } else {
                    throw new ReportParseException("Unknow element :" + ele.getName());
                }
            }
            Collections.sort(rows);
            Collections.sort(columns);
        } catch (Exception ex) {
            throw new ReportParseException(ex);
        }
        rebuild(report);
        return report;
    }

    private void rebuild(ReportDefinition report) {
        List<CellDefinition> cells = report.getCells();
        Map<String, CellDefinition> cellsMap = new HashMap<String, CellDefinition>();
        Map<String, CellDefinition> cellsRowColMap = new HashMap<String, CellDefinition>();
        for (CellDefinition cell : cells) {
            cellsMap.put(cell.getName(), cell);
            int rowNum = cell.getRowNumber(), colNum = cell.getColumnNumber(), rowSpan = cell.getRowSpan(), colSpan = cell.getColSpan();
            rowSpan = rowSpan > 0 ? rowSpan-- : 1;
            colSpan = colSpan > 0 ? colSpan-- : 1;
            int rowStart = rowNum, rowEnd = rowNum + rowSpan, colStart = colNum, colEnd = colNum + colSpan;
            for (int i = rowStart; i < rowEnd; i++) {
                cellsRowColMap.put(i + "," + colNum, cell);
            }
            for (int i = colStart; i < colEnd; i++) {
                cellsRowColMap.put(rowNum + "," + i, cell);
            }
        }
        for (CellDefinition cell : cells) {
            int rowNumber = cell.getRowNumber();
            int colNumber = cell.getColumnNumber();
            String leftParentCellName = cell.getLeftParentCellName();
            if (StringUtils.isNotBlank(leftParentCellName)) {
                if (!leftParentCellName.equals("root")) {
                    CellDefinition targetCell = cellsMap.get(leftParentCellName);
                    if (targetCell == null) {
                        throw new ReportException("Cell [" + cell.getName() + "] 's left parent cell [" + leftParentCellName + "] not exist.");
                    }
                    cell.setLeftParentCell(targetCell);
                }
            } else {
                if (colNumber > 1) {
                    CellDefinition targetCell = cellsRowColMap.get(rowNumber + "," + (colNumber - 1));
                    cell.setLeftParentCell(targetCell);
                }
            }
            String topParentCellName = cell.getTopParentCellName();
            if (StringUtils.isNotBlank(topParentCellName)) {
                if (!topParentCellName.equals("root")) {
                    CellDefinition targetCell = cellsMap.get(topParentCellName);
                    if (targetCell == null) {
                        throw new ReportException("Cell [" + cell.getName() + "] 's top parent cell [" + topParentCellName + "] not exist.");
                    }
                    cell.setTopParentCell(targetCell);
                }
            } else {
                if (rowNumber > 1) {
                    CellDefinition targetCell = cellsRowColMap.get((rowNumber - 1) + "," + colNumber);
                    cell.setTopParentCell(targetCell);
                }
            }
        }
    }

    /**
     * 初始化解析器
     *
     * @throws Exception
     */
    private void initParse(ApplicationContext applicationContext) {
        Collection<Parser> parsers = applicationContext.getBeansOfType(Parser.class).values();
        for (Parser parser : parsers) {
            if (StringUtils.isEmpty(parser.getName())) {
                throw new ReportStartupException("[" + parser.getClass().getSimpleName() + "]解析类没有声明name");
            }
            super.addParse(parser);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initParse(applicationContext);
    }
}
