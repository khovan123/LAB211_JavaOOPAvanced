package repository;

import exception.IOException;
import exception.InvalidDataException;
import model.RegisteredCourse;
import repository.interfaces.IRegistedCourseRepository;

import java.sql.*;
import java.util.*;

public class RegistedCourseRepository implements IRegistedCourseRepository {
    public static final String RegisteredCourseID_Column = "RegisteredCourseID";
    public static final String RegisteredDate_Column = "RegisteredDate";
    public static final String FinishRegisteredDate_Column = "FinishRegisteredDate";
    public static final String CourseID_Column = "CourseID";
    public static final String UserID_Column = "UserID";

    private static final List<String> REGISTEREDCOURSE_COLUMNS = Arrays.asList(
            RegisteredCourseID_Column, RegisteredDate_Column, FinishRegisteredDate_Column, CourseID_Column, UserID_Column
    );

    private final Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<RegisteredCourse> readData() {
        List<String> rawData;
        try {
            rawData = getMany();
        } catch (SQLException e) {
            throw new RuntimeException("Data fetch error", e);
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
                    throw new RuntimeException("Data validation failed", e);
                }
            }
        }
        return processData;
    }

    @Override
    public void insertToDB(RegisteredCourse entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        entries.put(RegisteredCourseID_Column, entry.getRegisteredCourseID());
        entries.put(RegisteredDate_Column, String.valueOf(entry.getRegisteredDate()));
        entries.put(FinishRegisteredDate_Column, String.valueOf(entry.getFinishRegisteredDate()));
        entries.put(CourseID_Column, entry.getCourseID());
        entries.put(UserID_Column, entry.getUserID());
        insertOne(entries);
    }

    @Override
    public void updateToDB(String id, Map<String, Object> entry) throws SQLException {
        Map<String, Object> entryMap = new HashMap<>();
        entryMap.put(RegisteredCourseID_Column, entry.getRegisteredCourseID());
        entryMap.put(RegisteredDate_Column, entry.getRegisteredDate());
        entryMap.put(FinishRegisteredDate_Column, entry.getFinishRegisteredDate());
        entryMap.put(CourseID_Column, entry.getCourseID());
        entryMap.put(UserID_Column, entry.getUserID());
        updateToDB(entry.getRegisteredCourseID(), entryMap);
    }

    @Override
    public void deleteToDB(String ID) throws SQLException {
        deleteOne(ID);
    }

    @Override
    public void insert(RegisteredCourse entry) throws SQLException {
        insertToDB(entry);
    }

    @Override
    public void update(RegisteredCourse entry) throws SQLException {

    }

    @Override
    public void delete(RegisteredCourse entry) throws SQLException {
        deleteToDB(entry.getRegisteredCourseID());
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT RegisteredCourseID, RegisteredDate, FinishRegisteredDate, CourseID, UserID FROM RegisteredCourseModel");
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
        String registeredCourseQuery = "INSERT INTO RegisteredCourseModel(X) VALUES(Y)";
        StringBuilder rcModelColumn = new StringBuilder();
        StringBuilder rcModelValues = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEREDCOURSE_COLUMNS.contains(column)) {
                rcModelColumn.append((rcModelColumn.length() == 0 ? "" : ", ")).append(column);
                rcModelValues.append((rcModelValues.length() == 0 ? "" : ", ")).append("?");
            }
        }

        registeredCourseQuery = registeredCourseQuery.replace("Y", rcModelValues.toString()).replace("X", rcModelColumn.toString());

        try (PreparedStatement registeredCoursePS = conn.prepareStatement(registeredCourseQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEREDCOURSE_COLUMNS.contains(column)) {
                    registeredCoursePS.setString(i++, entries.get(column));
                }
            }
            registeredCoursePS.executeUpdate();
        }
    }

    @Override
    public void updateOne(String ID, Map<String, String> entries) throws SQLException {
        String rcQuery = "UPDATE RegisteredCourseModel SET X WHERE RegisteredCourseID = ?";
        StringBuilder rcModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEREDCOURSE_COLUMNS.contains(column)) {
                rcModelColumn.append((rcModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        rcQuery = rcQuery.replace("X", rcModelColumn.toString());

        try (PreparedStatement registeredCoursePS = conn.prepareStatement(rcQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEREDCOURSE_COLUMNS.contains(column)) {
                    registeredCoursePS.setString(i++, entries.get(column));
                }
            }
            registeredCoursePS.setString(i, ID);
            registeredCoursePS.executeUpdate();
        }
    }

    @Override
    public void deleteOne(String ID) throws SQLException {
        String rcQuery = "DELETE FROM RegisteredCourseModel WHERE RegisteredCourseID = ?";
        try (PreparedStatement registeredCoursePS = conn.prepareStatement(rcQuery)) {
            registeredCoursePS.setString(1, ID);
            registeredCoursePS.executeUpdate();
        }
    }
}
