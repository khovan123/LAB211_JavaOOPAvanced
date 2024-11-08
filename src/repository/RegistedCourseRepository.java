package repository;

import exception.InvalidDataException;
import model.RegistedCourse;
import repository.interfaces.IRegistedCourseRepository;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import utils.GlobalUtils;

public class RegistedCourseRepository implements IRegistedCourseRepository {

    public static final String RegisteredCourseID_Column = "RegistedCourseID";
    public static final String RegisteredDate_Column = "RegistedDate";
    public static final String FinishRegisteredDate_Column = "FinishRegistedDate";
    public static final String CourseID_Column = "CourseID";
    public static final String UserID_Column = "UserID";
    private static final List<String> REGISTEREDCOURSE_COLUMNS = Arrays.asList(
            RegisteredCourseID_Column, RegisteredDate_Column, FinishRegisteredDate_Column, CourseID_Column, UserID_Column
    );

    private final Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<RegistedCourse> readData() throws SQLException {
        List<RegistedCourse> processData = new ArrayList<>();
        try {
            for (String row : getMany()) {
                String[] col = row.split(", ");
                try {
                    processData.add(new RegistedCourse(
                            col[0].trim(),
                            col[1].trim(),
                            col[2].trim(),
                            col[3].trim(),
                            col[4].trim()));
                } catch (InvalidDataException | ParseException e) {
                    throw new SQLException(e);

                }
            }
        } catch (SQLException e) {
            throw new SQLException("Data retrieval error", e);
        }
        return processData;
    }

    @Override
    public void insertToDB(RegistedCourse entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
//        entries.put(RegisteredCourseID_Column, entry.getRegisteredCourseID());
        entries.put(RegisteredDate_Column, GlobalUtils.dateFormat(entry.getRegisteredDate()));
        entries.put(FinishRegisteredDate_Column, GlobalUtils.dateFormat(entry.getFinishRegisteredDate()));
        entries.put(CourseID_Column, entry.getCourseID());
        entries.put(UserID_Column, entry.getUserID());
        insertOne(entries);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
        for (String column : entry.keySet()) {
            if (REGISTEREDCOURSE_COLUMNS.contains(column) && (!column.equalsIgnoreCase(RegisteredCourseID_Column))) {
                entries.put(column, (entry.get(column)));
            }
        }
        updateOne(id, entries);
    }

    @Override
    public void deleteToDB(int ID) throws SQLException {
        deleteOne(ID);
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
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
    public void insertOne(Map<String, Object> entries) throws SQLException {
        String registeredCourseQuery = "INSERT INTO RegistedCourseModel(X) VALUES(Y)";
        StringBuilder rcModelColumn = new StringBuilder();
        StringBuilder rcModelValues = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEREDCOURSE_COLUMNS.contains(column) && !column.equalsIgnoreCase(RegisteredCourseID_Column)) {
                rcModelColumn.append((rcModelColumn.length() == 0 ? "" : ", ")).append(column);
                rcModelValues.append((rcModelValues.length() == 0 ? "" : ", ")).append("?");
            }
        }

        registeredCourseQuery = registeredCourseQuery.replace("X", rcModelColumn.toString())
                .replace("Y", rcModelValues.toString());

        try (PreparedStatement registeredCoursePS = conn.prepareStatement(registeredCourseQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEREDCOURSE_COLUMNS.contains(column) && !column.equalsIgnoreCase(RegisteredCourseID_Column)) {
                    SQLServerConnection.setParamater(registeredCoursePS, i++, entries.get(column));

                }
            }
            registeredCoursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entries) throws SQLException {
        String rcQuery = "UPDATE RegisteredCourseModel SET X WHERE RegistedCourseModel = ?";
        StringBuilder rcModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEREDCOURSE_COLUMNS.contains(column) && (!column.equalsIgnoreCase(RegisteredCourseID_Column))) {
                rcModelColumn.append((rcModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        rcQuery = rcQuery.replace("X", rcModelColumn.toString());

        try (PreparedStatement registeredCoursePS = conn.prepareStatement(rcQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEREDCOURSE_COLUMNS.contains(column) && (!column.equalsIgnoreCase(RegisteredCourseID_Column))) {
                    SQLServerConnection.setParamater(registeredCoursePS, i++, entries.get(column));
//                    registeredCoursePS.setString(i++, entries.get(column));
                }
            }
            registeredCoursePS.setInt(i, ID);
            registeredCoursePS.executeUpdate();
        }
    }

    @Override
    public void deleteOne(int ID) throws SQLException {
        String rcQuery = "DELETE FROM RegisteredCourseModel WHERE RegistedCourseModel = ?";
        try (PreparedStatement registeredCoursePS = conn.prepareStatement(rcQuery)) {
            registeredCoursePS.setInt(1, ID);
            registeredCoursePS.executeUpdate();
        }
    }
}
