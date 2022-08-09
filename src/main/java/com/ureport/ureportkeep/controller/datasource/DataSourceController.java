package com.ureport.ureportkeep.controller.datasource;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.console.designer.DataResult;
import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.controller.datasource.dto.DataSourceConnectDto;
import com.ureport.ureportkeep.controller.datasource.dto.DatasetParamDto;
import com.ureport.ureportkeep.controller.datasource.dto.DatasetWarpper;
import com.ureport.ureportkeep.controller.datasource.enums.DataSourceType;
import com.ureport.ureportkeep.core.Utils;
import com.ureport.ureportkeep.core.build.Context;
import com.ureport.ureportkeep.core.definition.dataset.Field;
import com.ureport.ureportkeep.core.definition.datasource.DataType;
import com.ureport.ureportkeep.core.expression.ExpressionUtils;
import com.ureport.ureportkeep.core.expression.model.Expression;
import com.ureport.ureportkeep.core.expression.model.data.ExpressionData;
import com.ureport.ureportkeep.core.expression.model.data.ObjectExpressionData;
import com.ureport.ureportkeep.core.utils.ProcedureUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: summer
 * @Date: 2022/7/10 9:32
 * @Description: 数据源控制器
 **/
@RestController
@RequestMapping(value = "/api/dataSource")
public class DataSourceController {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 数据源测试连接
     *
     * @param dataSourceConnectDto
     * @return
     */
    @RequestMapping(value = "/testConnection", method = RequestMethod.POST)
    public R testConnection(@RequestBody DataSourceConnectDto dataSourceConnectDto) {
        String userName = dataSourceConnectDto.getUserName();
        String password = dataSourceConnectDto.getPassword();
        String driver = dataSourceConnectDto.getDriver();
        String url = dataSourceConnectDto.getUrl();

        Connection conn = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            map.put("result", true);
        } catch (Exception ex) {
            map.put("error", ex.toString());
            map.put("result", false);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return R.ok().success(map);
    }

    /**
     * 获取数据库表
     *
     * @param dataSourceConnectDto
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/buildDatabaseTables", method = RequestMethod.POST)
    public R buildDatabaseTables(@RequestBody DataSourceConnectDto dataSourceConnectDto) throws ServletException, IOException {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = buildConnection(dataSourceConnectDto);
            DatabaseMetaData metaData = conn.getMetaData();
            String url = metaData.getURL();
            String schema = null;
            if (url.toLowerCase().contains("oracle")) {
                schema = metaData.getUserName();
            }
            List<Map<String, String>> tables = new ArrayList<Map<String, String>>();
            rs = metaData.getTables(conn.getCatalog(), schema, null, new String[]{"TABLE", "VIEW"});
            while (rs.next()) {
                Map<String, String> table = new HashMap<String, String>();
                table.put("name", rs.getString("TABLE_NAME"));
                table.put("type", rs.getString("TABLE_TYPE"));
                tables.add(table);
            }

            return R.ok().success(tables);
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(conn);
        }
    }

    /**
     * 解析sql参数
     *
     * @param sqlInfo
     * @return
     */
    @PostMapping(value = "/parseSqlParams")
    public R parseSqlParams(@RequestBody Map<String, String> sqlInfo) {
        String sql = sqlInfo.get("sql");
        if (StringUtils.isEmpty(sql)) {
            return R.ok().success();
        }

        String[] paramNames = StringUtils.substringsBetween(sql, "${", "}");
        if (ArrayUtils.isEmpty(paramNames)) {
            return R.ok().success(new String[0]);
        }
        long distinctSize = Arrays.stream(paramNames).distinct().count();
        if (distinctSize != paramNames.length) {
            throw new ReportDesignException("SQL参数存在重复变量名");
        }
        return R.ok().success(paramNames);
    }

    /**
     * 预览sql数据
     *
     * @param datasetWarpper
     * @return
     */
    @PostMapping(value = "/previewData")
    public R previewData(@RequestBody DatasetWarpper datasetWarpper) {
        String sql = datasetWarpper.getSql();
        Map<String, Object> params = datasetWarpper.getParams();
        sql = parseSql(sql, params);
        Connection connection = null;
        try {
            connection = buildConnection(datasetWarpper);
            List<Map<String, Object>> list = null;
            if (ProcedureUtils.isProcedure(sql)) {
                list = ProcedureUtils.procedureQuery(sql, params, connection);
            } else {
                DataSource dataSource = new SingleConnectionDataSource(connection, false);
                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
                list = jdbc.queryForList(sql, params);
            }
            int size = list.size();
            int currentTotal = size;
            if (currentTotal > 500) {
                currentTotal = 500;
            }
            List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < currentTotal; i++) {
                ls.add(list.get(i));
            }
            DataResult result = new DataResult();
            List<String> fields = new ArrayList<String>();
            if (size > 0) {
                Map<String, Object> item = list.get(0);
                for (String name : item.keySet()) {
                    fields.add(name);
                }
            }
            result.setFields(fields);
            result.setCurrentTotal(currentTotal);
            result.setData(ls);
            result.setTotal(size);

            return R.ok().success(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return R.ok().success();
    }

    /**
     * 构建数据集字段
     *
     * @param datasetWarpper
     * @return
     */
    @RequestMapping(value = "/buildFields", method = RequestMethod.POST)
    public R buildFields(@RequestBody DatasetWarpper datasetWarpper) {
        String sql = datasetWarpper.getSql();
        List<DatasetParamDto> paramsValues = datasetWarpper.getParamsValues();

        Connection connection = null;
        final List<Field> fields = new ArrayList<>();
        try {
            connection = buildConnection(datasetWarpper);
            Map<String, Object> map = buildParameters(paramsValues);
            sql = parseSql(sql, map);
            if (ProcedureUtils.isProcedure(sql)) {
                List<Field> fieldsList = ProcedureUtils.procedureColumnsQuery(sql, map, connection);
                fields.addAll(fieldsList);
            } else {
                DataSource dataSource = new SingleConnectionDataSource(connection, false);
                NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
                PreparedStatementCreator statementCreator = getPreparedStatementCreator(sql, new MapSqlParameterSource(map));
                jdbc.getJdbcOperations().execute(statementCreator, new PreparedStatementCallback<Object>() {
                    @Override
                    public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        ResultSet rs = null;
                        try {
                            rs = ps.executeQuery();
                            ResultSetMetaData metadata = rs.getMetaData();
                            int columnCount = metadata.getColumnCount();
                            for (int i = 0; i < columnCount; i++) {
                                String columnName = metadata.getColumnLabel(i + 1);
                                fields.add(new Field(columnName));
                            }
                            return null;
                        } finally {
                            JdbcUtils.closeResultSet(rs);
                        }
                    }
                });
            }

            return R.ok().success(fields);
        } catch (BadSqlGrammarException badSqlGrammarException) {
            throw new ReportDesignException("SQL语法错误，请检查！");
        } catch (Exception e) {
            throw new ReportDesignException(e);
        }finally {
            JdbcUtils.closeConnection(connection);
        }

    }

    protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource) {
        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
        List<SqlParameter> declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
        return pscf.newPreparedStatementCreator(params);
    }

    /**
     * 解析参数
     *
     * @param paramsValues
     * @return
     * @throws IOException
     * @throws JsonParseException
     * @throws JsonMappingException
     */
    private Map<String, Object> buildParameters(List<DatasetParamDto> paramsValues) throws IOException, JsonParseException, JsonMappingException {
        if (CollectionUtils.isEmpty(paramsValues)) {
            return new HashMap<>();
        }
        HashMap<String, Object> paramValueMap = new HashMap<>();
        for (DatasetParamDto param : paramsValues) {
            String name = param.getName();
            DataType type = param.getType();
            String defaultValue = param.getDefaultValue();
            if (defaultValue == null || defaultValue.equals("")) {
                switch (type) {
                    case Boolean:
                        paramValueMap.put(name, false);
                    case Date:
                        paramValueMap.put(name, new Date());
                    case Float:
                        paramValueMap.put(name, new Float(0));
                    case Integer:
                        paramValueMap.put(name, 0);
                    case String:
                        if (defaultValue != null && defaultValue.equals("")) {
                            paramValueMap.put(name, "");
                        } else {
                            paramValueMap.put(name, "null");
                        }
                        break;
                    case List:
                        paramValueMap.put(name, new ArrayList<Object>());
                }
            } else {
                paramValueMap.put(name, type.parse(defaultValue));
            }
        }
        return paramValueMap;
    }

    /**
     * 解析sql
     *
     * @param sql
     * @param parameters
     * @return
     */
    private String parseSql(String sql, Map<String, Object> parameters) {
        sql = sql.trim();
        Context context = new Context(applicationContext, parameters);
        if (sql.startsWith(ExpressionUtils.EXPR_PREFIX) && sql.endsWith(ExpressionUtils.EXPR_SUFFIX)) {
            sql = sql.substring(2, sql.length() - 1);
            Expression expr = ExpressionUtils.parseExpression(sql);
            sql = executeSqlExpr(expr, context);
            return sql;
        } else {
            String sqlForUse = sql;
            Pattern pattern = Pattern.compile("\\$\\{.*?\\}");
            Matcher matcher = pattern.matcher(sqlForUse);
            while (matcher.find()) {
                String substr = matcher.group();
                String sqlExpr = substr.substring(2, substr.length() - 1);
                Expression expr = ExpressionUtils.parseExpression(sqlExpr);
                String result = executeSqlExpr(expr, context);
                sqlForUse = sqlForUse.replace(substr, result);
            }
            Utils.logToConsole("DESIGN SQL:" + sqlForUse);
            return sqlForUse;
        }
    }

    private String executeSqlExpr(Expression sqlExpr, Context context) {
        String sqlForUse = null;
        ExpressionData<?> exprData = sqlExpr.execute(null, null, context);
        if (exprData instanceof ObjectExpressionData) {
            ObjectExpressionData data = (ObjectExpressionData) exprData;
            Object obj = data.getData();
            if (obj != null) {
                String s = obj.toString();
                s = s.replaceAll("\\\\", "");
                sqlForUse = s;
            }
        }
        return sqlForUse;
    }

    /**
     * 构建连接
     *
     * @param dataSourceConnectDto
     * @return
     * @throws Exception
     */
    private Connection buildConnection(DataSourceConnectDto dataSourceConnectDto) throws Exception {
        DataSourceType dataSourceType = dataSourceConnectDto.getDataSourceType();
        if (DataSourceType.JDBC.equals(dataSourceType)) {
            String username = dataSourceConnectDto.getUserName();
            String password = dataSourceConnectDto.getPassword();
            String driver = dataSourceConnectDto.getDriver();
            String url = dataSourceConnectDto.getUrl();

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } else {
            String name = dataSourceConnectDto.getName();
            Connection conn = Utils.getBuildinConnection(name);
            if (conn == null) {
                throw new ReportDesignException("Buildin datasource [" + name + "] not exist.");
            }
            return conn;
        }

    }



}
