package com.cfets.bcs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
  public static final String url = "jdbc:mysql://localhost/hunter";
  public static final String name = "com.mysql.jdbc.Driver";
  public static final String user = "root";
  public static final String password = "root";

  public static Connection connection = null;

  public static void main(String[] args) {
    String test = "te_st";
    String[] tests = test.split("-");
    System.out.println(tests);
  }

  public static Connection getConnection() {
    try {
      Class.forName(name);// 指定连接类型
      connection = DriverManager.getConnection(url, user, password);// 获取连接
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
