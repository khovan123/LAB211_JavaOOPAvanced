package repository;

import exception.EventException;
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

    public static void setParamater(PreparedStatement ps, int pos, Object obj) throws SQLException {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        try {
            if (obj instanceof Integer integer) {
                ps.setInt(pos, integer);
            } else if (obj instanceof String string) {
                ps.setString(pos, string);
            } else if (obj instanceof Double aDouble) {
                ps.setDouble(pos, aDouble);
            } else if (obj instanceof Boolean aBoolean) {
                ps.setBoolean(pos, aBoolean);
            } else if (obj instanceof Date date) {
                ps.setDate(pos, date);
            }
        } catch (NumberFormatException | SQLException e) {
            throw new SQLException(e);
        }
    }
}
