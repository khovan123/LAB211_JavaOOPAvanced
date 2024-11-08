package repository;

import exception.InvalidDataException;
import model.*;
import repository.interfaces.ICourseRepository;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class CourseRepository implements ICourseRepository {

    public static final String CourseID_Column = "CourseID";
    public static final String CourseName_Column = "CourseName";
    public static final String Addventor_Column = "Addcentor";
    public static final String GenerateDate_Column = "GenerateDate";
    public static final String Price_Column = "Price";
    public static final String ComboID_Column = "ComboID";
    public static final String CoachID_Column = "CoachID";
    private static final List<String> COURSEMODELCOLUMN = new ArrayList<>(Arrays.asList(CourseID_Column, CourseName_Column, Addventor_Column, GenerateDate_Column, ComboID_Column, CoachID_Column));

    private final Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<Course> readData() throws SQLException {
        List<Course> processData = new ArrayList<>();
        for (String row : getMany()) {
            String[] col = row.split(",");
            try {
                String addventor = col[2].trim().equalsIgnoreCase("1") ? "true" : "false";
                processData.add(new Course(
                        col[0].trim(),
                        col[1].trim(),
                        addventor,
                        col[3].trim(),
                        col[4].trim(),
                        col[5].trim(),
                        col[6].trim()));
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);

            }

        }
        return processData;
    }

    @Override
    public void insertToDB(Course entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
        try {
//            entries.put(CourseID_Column, entry.getCourseId());
            entries.put(CourseName_Column, entry.getCourseName());
            entries.put(String.valueOf(Addventor_Column), String.valueOf(entry.isAddventor()));
            entries.put(GenerateDate_Column, String.valueOf(entry.getGenerateDate()));
            entries.put(Price_Column, String.valueOf(entry.getPrice()));
            entries.put(ComboID_Column, entry.getComboID());
            entries.put(CoachID_Column, entry.getCoachId());
            insertOne(entries);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateToDB(int id, Map<String, Object> entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
        for (String column : entry.keySet()) {
            if (COURSEMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(ComboID_Column))) {
                entries.put(column, String.valueOf(entry.get(column)));
            }
        }
        updateOne(id, entries);
    }

    @Override
    public void deleteToDB(int ID) throws SQLException {
        try {
            deleteOne(ID);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT c.CourseID, c.CourseName, c.Addventor, c.GenerateDate, c.Price, c.ComboID, c.CoachID FROM CourseModel c");
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

    @Override
    public void insertOne(Map<String, Object> entries) throws SQLException {
        String courseQuery = "INSERT INTO CourseModel(X) VALUES (Y)";
        StringBuilder courseModelColumn = new StringBuilder();
        StringBuilder courseModelValues = new StringBuilder();

        // Constructing SQL queries
        for (String column : entries.keySet()) {
            if (COURSEMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(CourseID_Column))) {
                if (courseModelColumn.length() > 0) {
                    courseModelColumn.append(", ");
                    courseModelValues.append(", ");
                }
                courseModelColumn.append(column);
                courseModelValues.append("?");
            }
        }

        courseQuery = courseQuery.replace("Y", courseModelValues.toString()).replace("X", courseModelColumn.toString());

        try (PreparedStatement coursePS = conn.prepareStatement(courseQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (COURSEMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(CourseID_Column))) {
                    SQLServerConnection.setParamater(coursePS, i++, entries.get(column));
                }
            }

            coursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entries) throws SQLException {
        String courseQuery = "UPDATE CourseModel SET X WHERE CourseID = ? ";
        StringBuilder courseModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (COURSEMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(CourseID_Column))) {
                courseModelColumn.append((courseModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        courseQuery = courseQuery.replace("X", courseModelColumn.toString());

        if (!courseModelColumn.isEmpty()) {
            PreparedStatement coursePS = conn.prepareStatement(courseQuery);
            int i = 1;
            for (String column : entries.keySet()) {
                if (COURSEMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(CourseID_Column))) {
//                        coursePS.setString(i++, entries.get(column));
                    SQLServerConnection.setParamater(coursePS, i++, entries.get(column));
                }
            }
            coursePS.setInt(i, ID);
            coursePS.executeUpdate();
        }
    }

    @Override
    public void deleteOne(int ID) throws SQLException {
        String courseQuery = "DELETE FROM CourseModel WHERE CourseID = ?";
        try {
            PreparedStatement coursePS = conn.prepareStatement(courseQuery);
            coursePS.setInt(1, ID);
            coursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
