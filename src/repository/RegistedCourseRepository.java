package repository;

import exception.IOException;
import exception.InvalidDataException;
import model.RegisteredCourse;
import repository.interfaces.IRegistedCourseRepository;

import java.sql.*;
import java.util.*;

public class RegistedCourseRepository implements IRegistedCourseRepository {
    public static final String RegistedCourseID_Column = "RegistedCourseID";
    public static final String RegistedDate_Column = "RegistedDate";
    public static final String FinishRegistedDate_Column = "FinishRegistedDate";
    public static final String CourseID_Column = "CourseID";
    public static final String UserID_Column = "UserID";
    private static final List<String> REGISTEDCOURSEMODELCOLUMN = Arrays.asList(
            RegistedCourseID_Column, RegistedDate_Column, FinishRegistedDate_Column, CourseID_Column, UserID_Column
    );

    private static Connection connectToSQLServer() {
        var user = "minh";
        var password = "Minh@1807";
        var url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1434;databaseName=FitnessCourse;encrypt=true";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to SQL Server successfully!");
        } catch (SQLException e) {
            System.err.println("Connection to SQL Server failed: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection conn = connectToSQLServer();
        if (conn != null) {
            System.out.println("Connection is established.");
        } else {
            System.out.println("Connection is not established.");
        }
    }

    @Override
    public List<RegisteredCourse> readData() {
        List<String> rawData;
        try {
            rawData = getMany();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<RegisteredCourse> processData = new ArrayList<>();
        for (String row : rawData) {
            String[] col = row.split(", ");
            if (col.length == 5) {
                try {
                    RegisteredCourse registeredCourse = new RegisteredCourse(
                            col[0].trim(),
                            col[1].trim(),
                            col[2].trim(),
                            col[3].trim(),
                            col[4].trim()
                    );
                    registeredCourse.runValidate();
                    processData.add(registeredCourse);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return processData;
    }

    @Override
    public void insert(RegisteredCourse entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        entries.put(RegistedCourseID_Column, entry.getRegisteredCourseID());
        entries.put(RegistedDate_Column, String.valueOf(entry.getRegisteredDate()));
        entries.put(FinishRegistedDate_Column, String.valueOf(entry.getFinishRegisteredDate()));
        entries.put(CourseID_Column, entry.getCourseID());
        entries.put(UserID_Column, entry.getUserID());
        insertOne(entries);
    }

    @Override
    public void update(RegisteredCourse entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        entries.put(RegistedDate_Column, String.valueOf(entry.getRegisteredDate()));
        entries.put(FinishRegistedDate_Column, String.valueOf(entry.getFinishRegisteredDate()));
        entries.put(CourseID_Column, entry.getCourseID());
        entries.put(UserID_Column, entry.getUserID());
        updateOne(entry.getRegisteredCourseID(), entries);
    }

    @Override
    public void delete(RegisteredCourse entry) throws SQLException {
        deleteOne(entry.getRegisteredCourseID());
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try (Statement statement = connectToSQLServer().createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT RegistedCourseID, RegistedDate, FinishRegistedDate, CourseID, UserID FROM RegistedCourseModel");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(rs.getString(i)).append(i < columnCount ? ", " : "");
                }
                list.add(row.toString());
            }
        }
        return list;
    }

    @Override
    public void insertOne(Map<String, String> entries) throws SQLException {
        String registeredCourseQuery = "INSERT INTO RegistedCourseModel(X) VALUES(Y)";
        StringBuilder rcModelColumn = new StringBuilder();
        StringBuilder rcModelValues = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEDCOURSEMODELCOLUMN.contains(column)) {
                rcModelColumn.append((rcModelColumn.length() == 0 ? "" : ", ")).append(column);
                rcModelValues.append((rcModelValues.length() == 0 ? "" : ", ")).append("?");
            }
        }

        registeredCourseQuery = registeredCourseQuery.replace("Y", rcModelValues.toString()).replace("X", rcModelColumn.toString());

        try (PreparedStatement registedCoursePS = connectToSQLServer().prepareStatement(registeredCourseQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEDCOURSEMODELCOLUMN.contains(column)) {
                    registedCoursePS.setString(i++, entries.get(column));
                }
            }
            registedCoursePS.executeUpdate();
        }
    }

    @Override
    public void updateOne(String ID, Map<String, String> entries) throws SQLException {
        String rcQuery = "UPDATE RegistedCourseModel SET X WHERE RegistedCourseID = ?";
        StringBuilder rcModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEDCOURSEMODELCOLUMN.contains(column)) {
                rcModelColumn.append((rcModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        rcQuery = rcQuery.replace("X", rcModelColumn.toString());

        try (PreparedStatement registedCoursePS = connectToSQLServer().prepareStatement(rcQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEDCOURSEMODELCOLUMN.contains(column)) {
                    registedCoursePS.setString(i++, entries.get(column));
                }
            }
            registedCoursePS.setString(i, ID);
            registedCoursePS.executeUpdate();
        }
    }

    @Override
    public void deleteOne(String ID) throws SQLException {
        String rcQuery = "DELETE FROM RegistedCourseModel WHERE RegistedCourseID = ?";
        try (PreparedStatement registedCoursePS = connectToSQLServer().prepareStatement(rcQuery)) {
            registedCoursePS.setString(1, ID);
            registedCoursePS.executeUpdate();
        }
    }
}
