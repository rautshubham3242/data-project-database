package com.shubham;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
        static final String DB_URL = "jdbc:mysql://localhost/";
        static final String USER = "admin";
        static final String PASS = "admin123";
        public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ){
                String sql = "CREATE DATABASE IPL";
                stmt.executeUpdate(sql);

                csvFilePath = "."
                convertCSVtoSQLFile(String path);
                createTable(String sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String createTable(String sql, ){
    }

    public static String createTable(String sql, ){
    }


}