package repository;

import exception.InvalidDataException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.RegistedWorkout;
import repository.interfaces.IRegistedWorkoutRepository;
import utils.GlobalUtils;

public class RegistedWorkoutRepository implements IRegistedWorkoutRepository {

    public static final String RegistedWorkoutID_Column = "RegistedWorkoutID";
    public static final String WorkoutID_Column = "WorkoutID";
    public static final String PracticalDayID_Column = "PracticalDayID";
    private static final List<String> REGISTEDWORKOUTMODELCOLUMN = new ArrayList<>(Arrays.asList(RegistedWorkoutID_Column, WorkoutID_Column, PracticalDayID_Column));
    private final Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<RegistedWorkout> readData() throws SQLException {
        List<RegistedWorkout> list = new ArrayList<>();
        for (String row : getMany()) {
            String[] data = row.split(",");
            try {
                list.add(new RegistedWorkout(data[0].trim(), data[1].trim(), data[2].trim()));
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);

            }
        }
        return list;
    }

    @Override
    public void insertToDB(RegistedWorkout entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
        entries.put(RegistedWorkoutID_Column, entry.getRegistedWorkoutID());
        entries.put(WorkoutID_Column, entry.getWorkoutID());
        entries.put(PracticalDayID_Column, entry.getPracticalDayID());
        insertOne(entries);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> entry) throws SQLException {
        Map<String, Object> coachMap = new HashMap<>();
        for (String column : entry.keySet()) {
            coachMap.putIfAbsent(column, (entry.get(column)));
        }
        updateOne(id, coachMap);
    }

    @Override
    public void deleteToDB(int ID) throws SQLException {
        deleteOne(ID);
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        StringBuilder row = new StringBuilder();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RegistedWorkoutID, WorkoutID, PracticalDayID FROM RegistedWorkoutModel");
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
    }

    @Override
    public void insertOne(Map<String, Object> entries) throws SQLException {
        String registeredWorkoutQuery = "INSERT INTO RegistedWorkoutModel(X) VALUES(Y)";
        StringBuilder modelColumn = new StringBuilder();
        StringBuilder modelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && !column.equalsIgnoreCase(RegistedWorkoutID_Column)) {
                modelColumn.append((modelColumn.length() == 0 ? "" : ", ")).append(column);
                modelValue.append((modelValue.length() == 0 ? "" : ", ")).append("?");
            }
        }

        registeredWorkoutQuery = registeredWorkoutQuery.replace("Y", modelValue.toString())
                .replace("X", modelColumn.toString());

        try (PreparedStatement ps = conn.prepareStatement(registeredWorkoutQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && !column.equalsIgnoreCase(RegistedWorkoutID_Column)) {
                    SQLServerConnection.setParamater(ps, i++, entries.get(column));

                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entries) throws SQLException {
        String query = "UPDATE RegistedWorkoutModel SET X WHERE RegistedWorkoutID = ?";
        StringBuilder modelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(RegistedWorkoutID_Column))) {
                modelColumn.append((modelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        query = query.replace("X", modelColumn.toString());

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            int i = 1;
            for (String column : entries.keySet()) {
                if (REGISTEDWORKOUTMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(RegistedWorkoutID_Column))) {
                    SQLServerConnection.setParamater(ps, i++, entries.get(column));
//                    ps.setString(i++, entries.get(column));
                }
            }
            ps.setInt(i, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteOne(int ID) throws SQLException {
        String query = "DELETE FROM RegistedWorkoutModel WHERE RegistedWorkoutID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
