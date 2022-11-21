package com.shubham;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
        static final String DB_URL = "jdbc:mysql://localhost/";
        static final String USER = "admin";
        static final String PASS = "admin123";

        static String createDatabaseQuery = "CREATE DATABASE ipl3";
        static String useDatabaseQuery = "USE ipl3";
        static String createTableQuery = "CREATE TABLE demo(id INTEGER, name varchar(10))";
        static String insertValueQuery1 = "insert into demo values(11, 'Shubham')";
        static String insertValueQuery2 = "insert into demo values(12, 'Sumit')";


    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();
            ){

                createDatabase(statement, createDatabaseQuery);
                useDatabase(statement, useDatabaseQuery);
                createTable(statement, createTableQuery);
                insertValue(statement, insertValueQuery1);
                insertValue(statement, insertValueQuery2);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDatabase(Statement statement, String createDatabaseQuery){
        try {
            statement.executeUpdate(Main.createDatabaseQuery);
            System.out.println("Database created Successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void useDatabase(Statement statement, String useDatabaseQuery){
        try {
            statement.executeUpdate(useDatabaseQuery);
            System.out.println("Database Changed");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable(Statement statement, String createTableQuery){
        try {
            statement.executeUpdate(createTableQuery);
            System.out.println("Table Created Successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertValue(Statement statement, String insertValueQuery){
        try {
            statement.executeUpdate(insertValueQuery);
            System.out.println("Value Inserted Successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



//    public static void convertCsvtoSqlFile(Statement statement, String path){
//            String sql = "";
//            statement.executeUpdate();
//    }


}