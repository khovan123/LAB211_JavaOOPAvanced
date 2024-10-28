package repository;

import exception.IOException;
import model.RegisteredCourse;
import repository.interfaces.IRegistedCourseRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RegistedCourseRepository implements IRegistedCourseRepository {
    public static final String RegistedCourseID_Column = "RegistedCourseID";
    public static final String RegistedDate_Column = "RegistedDate";
    public static final String FinishRegistedDate_Column = "FinishRegistedDate";
    public static final String CourseID_Column = "CourseID";
    public static final String UserID_Column = "UserID";
    private static final List<String> REGISTEDCOURSEMODELCOLUMN = new ArrayList<>(Arrays.asList(RegistedCourseID_Column, RegistedDate_Column, FinishRegistedDate_Column, CourseID_Column));
    private Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<RegisteredCourse> readFile() throws IOException {
        List<RegisteredCourse> registeredCourses = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File not found at Path: " + path);
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    RegisteredCourse registeredCourse = new RegisteredCourse(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim()
                    );
                    registeredCourse.runValidate();
                    registeredCourses.add(registeredCourse);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding Registered Course - " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new IOException("-> Error While Reading File - " + e.getMessage());
        }
        return registeredCourses;
    }

    @Override
    public void writeFile(List<RegisteredCourse> entry) throws IOException {

    }

    public List<String> getData() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT rc.RegistedCourseID, rc.RegistedDate, rc.FinishRegistedDate, rc.CourseID, rc.UserID FROM RegistedCourseModel rc");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    row.append(rs.getString(i)).append(i < columnCount ? ", " : "");
                }
                list.add(row.toString());
                row = new StringBuilder();
            }
            return list;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void insert(Map<String, String> entries) throws SQLException {
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

        try (PreparedStatement registedCoursePS = conn.prepareStatement(registeredCourseQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEDCOURSEMODELCOLUMN.contains(column)) {
                    registedCoursePS.setString(i++, entries.get(column));
                }
            }
            registedCoursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("-> Error while inserting data - " + e.getMessage());
        }
    }

    public void update(String ID, Map<String, String> entries) throws SQLException {
        String rcQuery = "UPDATE RegistedCourseModel SET X WHERE RegistedCourseID = ?";
        StringBuilder rcModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEDCOURSEMODELCOLUMN.contains(column)) {
                rcModelColumn.append((rcModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        rcQuery = rcQuery.replace("X", rcModelColumn.toString());

        try (PreparedStatement registedCoursePS = conn.prepareStatement(rcQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEDCOURSEMODELCOLUMN.contains(column)) {
                    registedCoursePS.setString(i++, entries.get(column));
                }
            }
            registedCoursePS.setString(i, ID);
            registedCoursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("-> Error while updating data - " + e.getMessage());
        }
    }

    public void delete(String ID) throws SQLException {
        String rcQuery = "DELETE FROM RegistedCourseModel WHERE RegistedCourseID = ?";
        try (PreparedStatement registedCoursePS = conn.prepareStatement(rcQuery)) {
            registedCoursePS.setString(1, ID);
            registedCoursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("-> Error while deleting data - " + e.getMessage());
        }
    }
}
