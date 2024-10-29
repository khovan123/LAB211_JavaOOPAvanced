package repository;

import exception.IOException;
import exception.InvalidDataException;
import model.Course;
import model.Workout;
import repository.interfaces.ICourseRepository;

import java.sql.*;
import java.util.*;

public class CourseRepository implements ICourseRepository {
    public static final String CourseID_Column = "CourseID";
    public static final String CourseName_Column = "CourseName";
    public static final boolean Addventor_Column = true;
    public static final String GenerateDate_Column = "GenerateDate";
    public static final String Price_Column = "Price";
    public static final String ComboID_Column = "ComboID";
    public static final String CoachID_Column = "CoachID";
    private static final List<String> COURSEMODELCOLUMN = new ArrayList<>(Arrays.asList(CourseID_Column, CourseName_Column, Boolean.toString(Addventor_Column), GenerateDate_Column, ComboID_Column, CoachID_Column));
    private static List<Course> courses = new ArrayList<>();

    private final Connection conn = SQLServerConnection.getConnection();

    public static void main(String[] args) {
        CourseRepository courseRepository = new CourseRepository();
        String courseId = "CS002";
        Map<String, Object> updatedEntries = new HashMap<>();

        updatedEntries.put(CourseRepository.CourseName_Column, "Strength and Conditioning Updated");
        updatedEntries.put(CourseRepository.Price_Column, "250.00");

        try {
            courseRepository.updateToDB(courseId, updatedEntries);
            System.out.println("Update successful!");

            String row = courseRepository.getOne(courseId);
            System.out.println("Course Details: " + row);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public List<Course> readData() throws SQLException {
        List<Course> processData = new ArrayList<>();
        try {
            for (String row : getMany()) {
                String[] col = row.split(", ");
                if (col.length == 14) {
                    List<Workout> workoutList = new ArrayList<>();
                    try {
                        Workout workout = new Workout(
                                col[8].trim(),
                                col[9].trim(),
                                col[10].trim(),
                                col[11].trim(),
                                col[12].trim(),
                                col[0].trim()
                        );
                        workout.runValidate();
                        workoutList.add(workout);
                    } catch (InvalidDataException e) {
                        System.err.println("Workout data validation failed: " + e.getMessage());
                    }
                    Course course = new Course(
                            col[0].trim(),
                            col[1].trim(),
                            col[2].trim(),
                            col[3].trim(),
                            col[4].trim(),
                            col[5].trim(),
                            col[6].trim(),
                            workoutList
                    );
                    course.runValidate();
                    processData.add(course);
                }
            }
        } catch (SQLException | InvalidDataException e) {
            throw new SQLException("Data retrieval error", e);
        }
        return processData;
    }


    @Override
    public void insertToDB(Course entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        try {
            entries.put(CourseID_Column, entry.getCourseId());
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
    public void updateToDB(String id, Map<String, Object> entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        for (String column : entry.keySet()) {
            if (COURSEMODELCOLUMN.contains(column)) {
                entries.put(column, String.valueOf(entry.get(column)));
            }
        }
        updateOne(id, entries);
    }

    @Override
    public void deleteToDB(String ID) throws SQLException {
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

    public String getOne(String courseId) throws SQLException {
        String query = "SELECT CourseID, CourseName, Addventor, GenerateDate, Price, ComboID, CoachID FROM CourseModel WHERE CourseID = ?";
        StringBuilder result = new StringBuilder();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                if (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        result.append(rs.getString(i)).append(i < columnCount ? ", " : "");
                    }
                } else {
                    throw new SQLException("No Course found with ID: " + courseId);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve Course: " + e.getMessage());
        }

        return result.toString();
    }

    @Override
    public void insertOne(Map<String, String> entries) throws SQLException {
        String courseQuery = "INSERT INTO CourseModel(X) VALUES (Y)";
        StringBuilder courseModelColumn = new StringBuilder();
        StringBuilder courseModelValues = new StringBuilder();
        for (String column : entries.keySet()) {
            if (COURSEMODELCOLUMN.contains(column)) {
                courseModelColumn.append((courseModelColumn.length() == 0 ? " " : ", ")).append(column);
                courseModelValues.append((courseModelValues.length() == 0 ? " " : ", ")).append("?");
            }
        }

        courseQuery = courseQuery.replace("Y", courseModelValues).replace("X", courseModelColumn);

        try {
            PreparedStatement coursePS = conn.prepareStatement(courseQuery);
            int i = 1;
            for (String column : entries.keySet()) {
                if (COURSEMODELCOLUMN.contains(column)) {
                    coursePS.setString(i++, entries.get(column));
                }
            }
            coursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateOne(String ID, Map<String, String> entries) throws SQLException {
        String courseQuery = "UPDATE CourseModel SET X WHERE CourseID = ? ";
        StringBuilder courseModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (COURSEMODELCOLUMN.contains(column)) {
                courseModelColumn.append((courseModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        courseQuery = courseQuery.replace("X", courseModelColumn.toString());

        try {
            if (!courseModelColumn.isEmpty()) {
                PreparedStatement coursePS = conn.prepareStatement(courseQuery);
                int i = 1;
                for (String column : entries.keySet()) {
                    if (COURSEMODELCOLUMN.contains(column)) {
                        coursePS.setString(i++, entries.get(column));
                    }
                }
                coursePS.setString(i, ID);
                coursePS.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteOne(String ID) throws SQLException {
        String courseQuery = "DELETE FROM CourseModel WHERE CourseID = ?";
        try {
            PreparedStatement coursePS = conn.prepareStatement(courseQuery);
            coursePS.setString(1, ID);
            coursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
