package com.ureport.ureportkeep.core.parser.json.datasource.parse;

import com.ureport.ureportkeep.core.definition.dataset.DatasetDefinition;
import com.ureport.ureportkeep.core.definition.dataset.Field;
import com.ureport.ureportkeep.core.definition.dataset.Parameter;
import com.ureport.ureportkeep.core.definition.dataset.SqlDatasetDefinition;
import com.ureport.ureportkeep.core.definition.datasource.DatasourceDefinition;
import com.ureport.ureportkeep.core.definition.datasource.JdbcDatasourceDefinition;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.parser.json.JsonParse;
import com.ureport.ureportkeep.core.parser.json.ReportModel;
import com.ureport.ureportkeep.core.parser.json.datasource.dataset.DatasetInfoModel;
import com.ureport.ureportkeep.core.parser.json.datasource.dataset.DatasetModel;
import com.ureport.ureportkeep.core.parser.json.datasource.dataset.DatasetParamModel;
import com.ureport.ureportkeep.core.parser.json.datasource.model.DataSourceModel;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: summer
 * @Date: 2022/12/27 21:13
 * @Description: 数据源解析
 **/
public class DatasourceModelParse implements JsonParse<List<DatasourceDefinition>, ReportModel> {
    @Override
    public List<DatasourceDefinition> parse(ReportModel parseModel) {
        List<DataSourceModel> dataSources = parseModel.getDataSources();
        List<DatasetModel> datasetDatas = parseModel.getDatasetDatas();

        List<DatasourceDefinition> datasourceDefinitions = new ArrayList<>();
        // jdbc数据源
        if (!CollectionUtils.isEmpty(dataSources)) {
            JdbcDatasourceDefinition ds = null;
            for (DataSourceModel dataSource : dataSources) {
                String name = dataSource.getName();
                List<DatasetModel> datasetModels = datasetDatas.stream().filter(d -> name.equals(d.getName())).collect(Collectors.toList());

                ds = new JdbcDatasourceDefinition();
                ds.setName(name);
                ds.setDriver(dataSource.getDriver());
                ds.setUrl(dataSource.getUrl());
                ds.setUsername(dataSource.getUserName());
                ds.setPassword(dataSource.getPassword());
                ds.setDatasets(parseDatasets(datasetModels));

                datasourceDefinitions.add(ds);
            }

        }
        return datasourceDefinitions;
    }

    private List<DatasetDefinition> parseDatasets(List<DatasetModel> datasetDatas) {
        if (CollectionUtils.isEmpty(datasetDatas)) {
            return new ArrayList<>();
        }
        DatasetModel datasetModel = datasetDatas.get(0);
        List<DatasetInfoModel> datasetInfos = datasetModel.getDatasetInfos();
        if (CollectionUtils.isEmpty(datasetInfos)) {
            return new ArrayList<>();
        }

        List<DatasetDefinition> list = new ArrayList<>();
        SqlDatasetDefinition dataset = null;
        for (DatasetInfoModel datasetInfo : datasetInfos) {
            dataset = new SqlDatasetDefinition();

            dataset.setName(datasetInfo.getName());
            dataset.setSql(parseSql(datasetInfo.getSql(), dataset));
            dataset.setFields(parseFields(datasetInfo.getProperty()));
            dataset.setParameters(parseParameters(datasetInfo.getParams()));

            list.add(dataset);
        }

        return list;
    }

    private List<Parameter> parseParameters(List<DatasetParamModel> params) {
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = null;
        for (DatasetParamModel param : params) {
            parameter = new Parameter();
            parameter.setName(param.getName());
            parameter.setDefaultValue(param.getValue().toString());
            parameter.setType(param.getDataType());

            parameters.add(parameter);
        }

        return parameters;
    }

    /**
     * 解析数据集字段
     *
     * @param property
     * @return
     */
    private List<Field> parseFields(List<String> property) {
        List<Field> fields = new ArrayList<>();
        for (String field : property) {
            fields.add(new Field(field));
        }

        return fields;
    }

    /**
     * 解析sql
     *
     * @param sql
     * @param dataset
     * @return
     */
    private String parseSql(String sql, SqlDatasetDefinition dataset) {
        String parseSql = sql.trim();

        if (parseSql.startsWith(ExpressionUtils.EXPR_PREFIX) && parseSql.endsWith(ExpressionUtils.EXPR_SUFFIX)) {
            parseSql = parseSql.substring(2, parseSql.length() - 1);
            Expression expr = ExpressionUtils.parseExpression(parseSql);
            dataset.setSqlExpression(expr);
        }

        return parseSql;
    }
}
