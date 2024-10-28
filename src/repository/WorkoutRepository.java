package repository;

import exception.IOException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Workout;
import repository.interfaces.IWorkoutRepository;
import java.sql.*;
import java.util.Map;

public class WorkoutRepository implements IWorkoutRepository {

    private static List<Workout> workoutList = new ArrayList<>();
    private Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<Workout> readFile() throws IOException {
        List<Workout> workoutList = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found at path: " + path);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    Workout workout = new Workout(data[0], data[1], data[2], data[3], data[4], data[5]);
                    workoutList.add(workout);
                } catch (Exception e) {
                    throw new IOException("Add failed (" + e.getMessage() + ")");
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("Read file failed!!! (" + e.getMessage() + ")");
        }
        return workoutList;
    }

    @Override
    public void writeFile(List<Workout> workoutList) throws IOException {
        System.out.println("Not yet supported!!!");
    }

    public List<String> getData() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT WorkoutID, WorkoutName, Repetition, Sets, Duration, CourseID FROM WorkoutModel");
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
        String query = "INSERT INTO WorkoutModel (WorkoutID, WorkoutName, Repetition, Sets, Duration, CourseID) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, entries.get("WorkoutID"));
            ps.setString(2, entries.get("WorkoutName"));
            ps.setString(3, entries.get("Repetition"));
            ps.setString(4, entries.get("Sets"));
            ps.setString(5, entries.get("Duration"));
            ps.setString(6, entries.get("CourseID"));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to insert Workout: " + e.getMessage());
        }
    }

    public void update(String workoutID, Map<String, String> entries) throws SQLException {
        String query = "UPDATE WorkoutModel SET WorkoutName = ?, Repetition = ?, Sets = ?, Duration = ?, CourseID = ? WHERE WorkoutID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, entries.get("WorkoutName"));
            ps.setString(2, entries.get("Repetition"));
            ps.setString(3, entries.get("Sets"));
            ps.setString(4, entries.get("Duration"));
            ps.setString(5, entries.get("CourseID"));
            ps.setString(6, workoutID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update Workout: " + e.getMessage());
        }
    }

    public void delete(String workoutID) throws SQLException {
        String query = "DELETE FROM WorkoutModel WHERE WorkoutID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, workoutID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete Workout: " + e.getMessage());
        }
    }

}
