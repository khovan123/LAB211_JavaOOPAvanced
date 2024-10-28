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
    private Connection conn = SQLServerConnection.getConnection();
    private static List<Course> courses = new ArrayList<>();
    //data sample: CS-YYYY, 30 days full body master, CA-YYYY

    public List<Course> readFile() throws IOException {
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<Course> courses) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Course> getProcessData() throws SQLException {
        List<String> rawData = getData();
        List<Course> processData = new ArrayList<>();
        for (String row : rawData) {
            String[] col = row.split(", ");
            if (col.length == 15) {
                List<Workout> workoutList = new ArrayList<>();
                try {
                    Workout workout = new Workout(
                            col[8].trim(),
                            col[9].trim(),
                            col[10].trim(),
                            col[11].trim(),
                            col[12].trim(),
                            col[13].trim(),
                            col[14].trim(),
                            col[0].trim()
                    );
                    workout.runValidate();
                    workoutList.add(workout);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
                try {
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
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return processData;
    }

    public void insertCourse(Course course) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        try {
            entries.put(CourseID_Column, course.getCourseId());
            entries.put(CourseName_Column, course.getCourseName());
            entries.put(String.valueOf(Addventor_Column), String.valueOf(course.isAddventor())); // Use "true"/"false"
            entries.put(GenerateDate_Column, String.valueOf(course.getGenerateDate()));
            entries.put(Price_Column, String.valueOf(course.getPrice()));
            entries.put(ComboID_Column, course.getComboID());
            entries.put(CoachID_Column, course.getCoachId());
            insert(entries);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void updateCourse(String ID, Course course) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        try {
            entries.put(CourseName_Column, course.getCourseName());
            entries.put(String.valueOf(Addventor_Column), String.valueOf(course.isAddventor())); // Use "true"/"false"
            entries.put(GenerateDate_Column, String.valueOf(course.getGenerateDate()));
            entries.put(Price_Column, String.valueOf(course.getPrice()));
            entries.put(ComboID_Column, course.getComboID());
            entries.put(CoachID_Column, course.getCoachId());
            update(ID, entries);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void deleteCourse(String ID) throws SQLException {
        try {
            delete(ID);
        } catch (SQLException e ){
            throw new SQLException(e);
        }
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
            throw new SQLException(e);
        }
    }

    public void delete(String ID) throws SQLException {
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
