package com.ureport.ureportkeep.controller.datasource;

import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.console.exception.ReportDesignException;
import com.ureport.ureportkeep.controller.datasource.dto.DataSourceConnectDto;
import com.ureport.ureportkeep.controller.datasource.enums.DataSourceType;
import com.ureport.ureportkeep.core.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/7/10 9:32
 * @Description: 数据源控制器
 **/
@RestController
@RequestMapping(value = "/api/dataSource")
public class DataSourceController {

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

    @PostMapping(value = "/parseSqlParams")
    public R parseSqlParams(@RequestBody Map<String, String> sqlInfo) {
        String sql = sqlInfo.get("sql");
        if (StringUtils.isEmpty(sql)) {
            return R.ok().success();
        }

        String[] paramNames = StringUtils.substringsBetween(sql, "${", "}");
        return R.ok().success(paramNames);
    }

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
