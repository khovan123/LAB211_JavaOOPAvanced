package repository;

import exception.IOException;
import model.Course;
import model.Workout;
import repository.interfaces.ICourseRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CourseRepository implements ICourseRepository {
    public static final String CourseID_Column = "CourseID";
    public static final String CourseName_Column = "CourseName";
    public static final boolean Addventor_Column = true;
    public static final String GenerateDate_Column = "GenerateDate";
    public static final String ComboID_Column = "ComboID";
    public static final String CoachID_Column = "CoachID";
    private static final List<String> COURSEMODELCOLUMN = new ArrayList<>(Arrays.asList(CourseID_Column, CourseName_Column, Boolean.toString(Addventor_Column), GenerateDate_Column, ComboID_Column, CoachID_Column));
    private Connection conn = SQLServerConnection.getConnection();
    private static List<Course> courses = new ArrayList<>();
    //data sample: CS-YYYY, 30 days full body master, CA-YYYY

    public List<Course> readFile() throws IOException {
        List<Course> courseList = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File Not Found At Path - " + path);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                List<Workout> workouts = new ArrayList<>();
                for (int i = 8; i < data.length; i += 7) {
                    if (i + 6 < data.length) {
                        Workout workout = new Workout(
                                data[i].trim(),
                                data[i + 1].trim(),
                                data[i + 2].trim(),
                                data[i + 3].trim(),
                                data[i + 4].trim(),
                                data[i + 5].trim(),
                                data[i + 6].trim(),
                                data[0].trim()
                        );
                        workouts.add(workout);
                    }
                }
                Course course = new Course(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim(),
                        workouts
                );
                course.runValidate();
                courseList.add(course);
            }
        } catch (Exception e) {
            throw new IOException("-> Error While Reading File or Adding Course - " + e.getMessage(), e);
        }
        return courseList;
    }


    @Override
    public void writeFile(List<Course> courses) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getData() throws SQLException {
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

    public void insert(Map<String, String> entries) throws SQLException {
        String courseQuery = "INSERT INTO CourseModel(X) VALUES (Y)";
        StringBuilder courseModelColumn = new StringBuilder();
        StringBuilder courseModelValues = new StringBuilder();
        for (String column : entries.keySet()){
            if (COURSEMODELCOLUMN.contains(column)){
                courseModelColumn.append((courseModelColumn.length() == 0 ? " " : ", ")).append(column);
                courseModelValues.append((courseModelValues.length() == 0 ? " " : ", ")).append("?");
            }
        }

        courseQuery = courseQuery.replace("Y", courseModelValues).replace("X", courseModelColumn);

        try {
            PreparedStatement coursePS = conn.prepareStatement(courseQuery);
            int i = 1;
            for (String column : entries.keySet()){
                if (COURSEMODELCOLUMN.contains(column)){
                    coursePS.setString(i++, entries.get(column));
                }
            }
            coursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("-> Error while inserting data - " + e.getMessage());
        }
    }

    public void update(String ID, Map<String, String> entries) throws SQLException {
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
            throw new SQLException("-> Error while updating data - " + e.getMessage());
        }
    }

    public void delete(String ID) throws SQLException {
        String courseQuery = "DELETE FROM CourseModel WHERE CourseID = ?";
        try {
            PreparedStatement coursePS = conn.prepareStatement(courseQuery);
            coursePS.setString(1, ID);
            coursePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("-> Error while deleting data - " + e.getMessage());
        }
    }

}
