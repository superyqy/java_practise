package cn.yqy.apitest.utility;


import java.sql.*;


public class DatabaseConnection{

    public static Object connectDB() throws SQLException{
        String databaseURI = "jdbc:sqlserver://127.0.0.2:1433;databaseName=TestDB";
        String loginName = "admin";
        String password = "123456";
        Connection conn;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            conn = DriverManager.getConnection(databaseURI, loginName, password);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("connect database failed!");
            throw e;
        }

        return conn;
    }

}
