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
package com.ureport.ureportkeep.core.export;

import com.ureport.ureportkeep.core.build.ReportBuilder;
import com.ureport.ureportkeep.core.cache.CacheUtils;
import com.ureport.ureportkeep.core.definition.CellDefinition;
import com.ureport.ureportkeep.core.definition.Expand;
import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.exception.CellComputeException;
import com.ureport.ureportkeep.core.exception.ReportException;
import com.ureport.ureportkeep.core.exception.ReportParseException;
import com.ureport.ureportkeep.core.export.builder.down.DownCellbuilder;
import com.ureport.ureportkeep.core.export.builder.right.RightCellbuilder;
import com.ureport.ureportkeep.core.init.ReportProvidersInit;
import com.ureport.ureportkeep.core.model.Report;
import com.ureport.ureportkeep.core.parser.ReportParser;
import com.ureport.ureportkeep.core.provider.report.ReportProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * @author Jacky.gao
 * @since 2016年12月4日
 */
@Component
public class ReportRender {
       @Autowired
       private ReportParser reportParser;
       @Autowired
       private ReportBuilder reportBuilder;

       @Autowired
       private ReportProvidersInit reportProvidersInit;

       private Collection<ReportProvider> reportProviders;
       private DownCellbuilder downCellParentbuilder = new DownCellbuilder();
       private RightCellbuilder rightCellParentbuilder = new RightCellbuilder();

       public Report render(String file, Map<String, Object> parameters) {
              ReportDefinition reportDefinition = getReportDefinition(file);
              return reportBuilder.buildReport(reportDefinition, parameters);
       }

       public Report render(ReportDefinition reportDefinition, Map<String, Object> parameters) {
              return reportBuilder.buildReport(reportDefinition, parameters);
       }

       public ReportDefinition getReportDefinition(String file) {
              ReportDefinition reportDefinition = CacheUtils.getReportDefinition(file);
              if (reportDefinition == null) {
                     reportDefinition = parseReport(file);
                     rebuildReportDefinition(reportDefinition);
                     CacheUtils.cacheReportDefinition(file, reportDefinition);
              }
              return reportDefinition;
       }

       public void rebuildReportDefinition(ReportDefinition reportDefinition) {
              List<CellDefinition> cells = reportDefinition.getCells();
              for (CellDefinition cell : cells) {
                     addRowChildCell(cell);
                     addColumnChildCell(cell);
              }
              for (CellDefinition cell : cells) {
                     Expand expand = cell.getExpand();
                     if (expand.equals(Expand.Down)) {
                            downCellParentbuilder.buildParentCell(cell, cells);
                     } else if (expand.equals(Expand.Right)) {
                            rightCellParentbuilder.buildParentCell(cell, cells);
                     }
              }
       }

       public ReportDefinition parseReport(String file) {
              InputStream inputStream = null;
              try {
                     inputStream = buildReportFile(file);
                     ReportDefinition reportDefinition = reportParser.parse(inputStream, file);
                     return reportDefinition;
              } finally {
                     try {
                            if (inputStream != null) {
                                   inputStream.close();
                            }
                     } catch (IOException e) {
                            throw new ReportParseException(e);
                     }
              }
       }

       private InputStream buildReportFile(String file) {
              InputStream inputStream = null;
              for (ReportProvider provider : reportProvidersInit.getReportProviders()) {
                     if (file.startsWith(provider.getPrefix())) {
                            inputStream = provider.loadReport(file);
                     }
              }
              if (inputStream == null) {
                     throw new ReportException("Report [" + file + "] not support.");
              }
              return inputStream;
       }

       private void addRowChildCell(CellDefinition cell) {

              Stack<CellDefinition> cellStack = new Stack<>();
              cellStack.push(cell);

              while (!cellStack.isEmpty()) {
                     CellDefinition cellDefinition = cellStack.pop();
                     CellDefinition leftParentCell = cellDefinition.getLeftParentCell();
                     if (leftParentCell == null) {
                            continue;
                     }
                     if (leftParentCell.equals(cell)) {
                            throw new CellComputeException(leftParentCell.getName() + " and " + cellDefinition.getName() + " cannot be parent of each other");
                     }

                     leftParentCell.getRowChildrenCells().add(cell);
                     cellStack.push(leftParentCell);
              }

       }

       private void addColumnChildCell(CellDefinition cell) {

              Stack<CellDefinition> cellStack = new Stack<>();
              cellStack.push(cell);

              while (!cellStack.isEmpty()) {
                     CellDefinition cellDefinition = cellStack.pop();
                     CellDefinition topParentCell = cellDefinition.getTopParentCell();
                     if (topParentCell == null) {
                            continue;
                     }
                     if (topParentCell.equals(cell)) {
                            throw new CellComputeException(topParentCell.getName() + " and " + cellDefinition.getName() + " cannot be parent of each other");
                     }

                     topParentCell.getColumnChildrenCells().add(cell);
                     cellStack.push(topParentCell);
              }
       }

       public void setReportParser(ReportParser reportParser) {
              this.reportParser = reportParser;
       }

       public void setReportBuilder(ReportBuilder reportBuilder) {
              this.reportBuilder = reportBuilder;
       }

}
