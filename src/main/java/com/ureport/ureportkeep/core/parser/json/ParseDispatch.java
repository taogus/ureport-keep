package com.ureport.ureportkeep.core.parser.json;

import com.ureport.ureportkeep.core.definition.ReportDefinition;
import com.ureport.ureportkeep.core.parser.json.cell.parse.CellModelParse;
import com.ureport.ureportkeep.core.parser.json.config.paper.PageFooterJsonParse;
import com.ureport.ureportkeep.core.parser.json.config.paper.PageHeaderJsonParse;
import com.ureport.ureportkeep.core.parser.json.config.paper.PaperJsonParse;
import com.ureport.ureportkeep.core.parser.json.config.rowcol.RowColConfig;
import com.ureport.ureportkeep.core.parser.json.config.rowcol.col.ColumnModelParse;
import com.ureport.ureportkeep.core.parser.json.config.rowcol.row.RowModelParse;
import com.ureport.ureportkeep.core.parser.json.datasource.parse.DatasourceModelParse;
import com.ureport.ureportkeep.core.parser.json.parameter.parse.ParameterFormJsonParse;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @Author: summer
 * @Date: 2022/11/27 10:42
 * @Description:
 **/
@Component
public class ParseDispatch {

    private CellModelParse cellModelParse = new CellModelParse();
    private RowModelParse rowModelParse = new RowModelParse();
    private ColumnModelParse columnModelParse = new ColumnModelParse();
    private DatasourceModelParse datasourceModelParse = new DatasourceModelParse();
    private PaperJsonParse paperJsonParse = new PaperJsonParse();
    private PageHeaderJsonParse pageHeaderJsonParse = new PageHeaderJsonParse();
    private PageFooterJsonParse pageFooterJsonParse = new PageFooterJsonParse();
    private ParameterFormJsonParse parameterFormJsonParse = new ParameterFormJsonParse();

    public ReportDefinition dispatch(ReportModel reportModel) {
        RowColConfig rowColConfig = reportModel.getRowColConfig();

        ReportDefinition reportDefinition = new ReportDefinition();
        reportDefinition.setReportFullName(reportModel.getReportName());
        reportDefinition.setCells(cellModelParse.parse(reportModel));
        reportDefinition.setDatasources(datasourceModelParse.parse(reportModel));
        reportDefinition.setRows(rowModelParse.parse(rowColConfig));
        reportDefinition.setColumns(columnModelParse.parse(rowColConfig));
        reportDefinition.setPaper(paperJsonParse.parse(reportModel.getReportConfig()));
        reportDefinition.setHeader(pageHeaderJsonParse.parse(reportModel.getReportConfig().getHeaderConfig()));
        reportDefinition.setFooter(pageFooterJsonParse.parse(reportModel.getReportConfig().getFooterConfig()));
        reportDefinition.setSearchForm(parameterFormJsonParse.parse(reportModel.getParameterFormModels()));

        Collections.sort(reportDefinition.getRows());
        Collections.sort(reportDefinition.getColumns());
        return reportDefinition;
    }
}
