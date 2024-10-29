package repository;

import exception.IOException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Workout;
import repository.interfaces.IWorkoutRepository;
import utils.GlobalUtils;

import java.sql.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkoutRepository implements IWorkoutRepository {

    private static List<Workout> workoutList = new ArrayList<>();
    private Connection conn = SQLServerConnection.getConnection();
    public static final String WorkoutID_Column = "WorkoutID";
    public static final String WorkoutName_Column = "WorkoutName";
    public static final String Repetition_Column = "Repetition";
    public static final String Sets_Column = "Sets";
    public static final String Duration_Column = "Duration";
    public static final String CourseID_Column = "CourseID";

    public static void main(String[] args) {
        WorkoutRepository workoutRepository = new WorkoutRepository();
        String workoutID = "WK001"; // ID cần kiểm tra

        // Cập nhật các giá trị cần thiết
        Map<String, String> updatedEntries = new HashMap<>();
        updatedEntries.put(WorkoutRepository.WorkoutName_Column, "Leg Day Updated");
        updatedEntries.put(WorkoutRepository.Repetition_Column, "15");
        updatedEntries.put(WorkoutRepository.Sets_Column, "4");
        updatedEntries.put(WorkoutRepository.Duration_Column, "60");
        updatedEntries.put(WorkoutRepository.CourseID_Column, "CS002");

        try {
            // Thực hiện cập nhật
            workoutRepository.updateOne(workoutID, updatedEntries);
            System.out.println("Update successful!");

            // Lấy lại thông tin của bản ghi vừa được cập nhật
            String row = workoutRepository.getOne(workoutID);
            System.out.println("Workout Details: " + row);
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Workout> readData() throws SQLException {
        List<Workout> workoutList = new ArrayList<>();
        try {
            for (String row : getMany()) {
                try {
                    String[] data = row.split(",");
                    Workout workout = new Workout(data[0], data[1], data[2], data[3], data[4], data[5]);
                    workoutList.add(workout);
                } catch (Exception e) {
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return workoutList;
    }

    @Override
    public void insertToDB(Workout workout) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        entries.put(WorkoutID_Column, workout.getWorkoutId());
        entries.put(WorkoutName_Column, workout.getWorkoutName());
        entries.put(Repetition_Column, String.valueOf(workout.getRepetition()));
        entries.put(Sets_Column, String.valueOf(workout.getSets()));
        entries.put(Duration_Column, String.valueOf(workout.getDuration()));
        entries.put(CourseID_Column, workout.getCourseId());

        insertOne(entries);
    }

    @Override
    public void updateToDB(String workoutID, Map<String, Object> workout) throws SQLException {
        Map<String, String> workoutMap = new HashMap<>();
        for(String column: workoutMap.keySet()){
            workoutMap.putIfAbsent(column, GlobalUtils.convertToString(workout.get(column)));
        }
        updateOne(workoutID, workoutMap);
    }

    @Override
    public void deleteToDB(String workoutID) throws SQLException {
        deleteOne(workoutID);
    }

    @Override
    public List<String> getMany() throws SQLException {
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

    public String getOne(String workoutID) throws SQLException {
        String query = "SELECT WorkoutID, WorkoutName, Repetition, Sets, Duration, CourseID FROM WorkoutModel WHERE WorkoutID = ?";
        StringBuilder result = new StringBuilder();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, workoutID);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Nếu tìm thấy bản ghi
                if (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        result.append(rs.getString(i)).append(i < columnCount ? ", " : "");
                    }
                } else {
                    throw new SQLException("No Workout found with ID: " + workoutID);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve Workout: " + e.getMessage());
        }

        return result.toString();
    }

    @Override
    public void insertOne(Map<String, String> entries) throws SQLException {
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

    @Override
    public void updateOne(String workoutID, Map<String, String> entries) throws SQLException {
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

    @Override
    public void deleteOne(String workoutID) throws SQLException {
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
