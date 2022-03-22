package com.example.java_demo.middleware.mysql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 */
public class DbUtils {

    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String url, String user, String pwd) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, user, pwd);
    }
}
