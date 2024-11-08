package repository;

import exception.InvalidDataException;
import java.util.*;
import model.Workout;
import repository.interfaces.IWorkoutRepository;
import utils.GlobalUtils;
import java.sql.*;
import java.text.ParseException;

public class WorkoutRepository implements IWorkoutRepository {

    private Connection conn = SQLServerConnection.getConnection();
    public static final String WorkoutID_Column = "WorkoutID";
    public static final String WorkoutName_Column = "WorkoutName";
    public static final String Repetition_Column = "Repetition";
    public static final String Sets_Column = "Sets";
    public static final String Duration_Column = "Duration";
    public static final String CourseID_Column = "CourseID";
    public static final List<String> WORKOUTMODELCOLUMN = new ArrayList<>(Arrays.asList(WorkoutID_Column, WorkoutName_Column, Repetition_Column, Sets_Column, Duration_Column, CourseID_Column));

    @Override
    public List<Workout> readData() throws SQLException {
        List<Workout> workoutList = new ArrayList<>();
        try {
            for (String row : getMany()) {
                try {
                    String[] data = row.split(",");
                    Workout workout = new Workout(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim());
                    workoutList.add(workout);
                } catch (InvalidDataException | ParseException e) {
                    throw new SQLException(e);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return workoutList;
    }

    @Override
    public void insertToDB(Workout workout) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
//        entries.put(WorkoutID_Column, workout.getWorkoutId());
        entries.put(WorkoutName_Column, workout.getWorkoutName());
        entries.put(Repetition_Column, String.valueOf(workout.getRepetition()));
        entries.put(Sets_Column, String.valueOf(workout.getSets()));
        entries.put(Duration_Column, String.valueOf(workout.getDuration()));
        entries.put(CourseID_Column, workout.getCourseId());

        insertOne(entries);
    }

    @Override
    public void updateToDB(int workoutID, Map<String, Object> workout) throws SQLException {
        Map<String, Object> workoutMap = new HashMap<>();
        for (String column : workoutMap.keySet()) {
            workoutMap.putIfAbsent(column, (workout.get(column)));
        }
        updateOne(workoutID, workoutMap);
    }

    @Override
    public void deleteToDB(int workoutID) throws SQLException {
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

    @Override
    public void insertOne(Map<String, Object> entries) throws SQLException {
        String query = "INSERT INTO WorkoutModel (WorkoutID, WorkoutName, Repetition, Sets, Duration, CourseID) VALUES (?, ?, ?, ?, ?, ?)";

        StringBuilder modelColumn = new StringBuilder();
        StringBuilder modelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (WORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(WorkoutID_Column))) {
                modelColumn.append((modelColumn.length() == 0 ? "" : ", ")).append(column);
                modelValue.append((modelValue.length() == 0 ? "?" : ", ?"));
            }
        }

        query = "INSERT INTO WorkoutModel (" + modelColumn.toString() + ") VALUES (" + modelValue.toString() + ")";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (WORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(WorkoutID_Column))) {
                    SQLServerConnection.setParamater(ps, i++, entries.get(column));
                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    @Override
    public void updateOne(int workoutID, Map<String, Object> entries) throws SQLException {
        String query = "UPDATE WorkoutModel SET WorkoutName = ?, Repetition = ?, Sets = ?, Duration = ?, CourseID = ? WHERE WorkoutID = ?";
        StringBuilder modelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (WORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(WorkoutID_Column))) {
                modelColumn.append((modelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        query = query.replace("X", modelColumn.toString());

        PreparedStatement ps = conn.prepareStatement(query);
        int i = 1;
        for (String column : entries.keySet()) {
            if (WORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(WorkoutID_Column))) {
//                    ps.setString(i++, entries.get(column));
                SQLServerConnection.setParamater(ps, i++, entries.get(column));
            }
        }
        ps.setInt(i, workoutID);
        ps.executeUpdate();
    }

    @Override
    public void deleteOne(int workoutID) throws SQLException {
        String query = "DELETE FROM WorkoutModel WHERE WorkoutID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, workoutID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete Workout: " + e.getMessage());
        }
    }

}
