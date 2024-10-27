package repository;

import java.sql.*;

public class SQLServerConnection {

    private static Connection connection = connectToSQLServer();

    private static Connection connectToSQLServer() {
        var user = "minh";
        var password = "Minh@1807";
        var url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=FitnessCourse;encrypt=true;trustServerCertificate=true;zeroDateTimeBehavior=CONVERT_TO_NULL";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connect to SQL Server Successfully!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println("Connect to SQL Server Failed!");
        }
        return conn;
    }

    public static Connection getConnection() {
        return connection;
    }
}
