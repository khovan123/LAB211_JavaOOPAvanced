package repository;

import exception.InvalidDataException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import model.Schedule;
import repository.interfaces.IScheduleRepository;

public class ScheduleRepository implements IScheduleRepository {

    public static final String ScheduleID_Column = "ScheduleID";
    public static final String UserProgressID_Column = "UserProgressID";
    private static final List<String> SCHEDULE_COLUMNS = Arrays.asList(ScheduleID_Column, UserProgressID_Column);
    private Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<Schedule> readData() throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        for (String row : getMany()) {
            String data[] = row.split(",");
            try {
                schedules.add(new Schedule(data[0].trim(), data[1].trim()));
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);

            }
        }
        return schedules;
    }

    @Override
    public void insertToDB(Schedule entry) throws SQLException {
        Map<String, Object> entryMap = new HashMap<>();
//        entryMap.put(ScheduleID_Column, entry.getScheduleId());
        entryMap.put(UserProgressID_Column, entry.getUserProgressId());
        insertOne(entryMap);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> entry) throws SQLException {
        Map<String, Object> stringEntry = new HashMap<>();
        for (Map.Entry<String, Object> e : entry.entrySet()) {
            stringEntry.put(e.getKey(), e.getValue());
        }
        updateOne(id, stringEntry);
    }

    @Override
    public void deleteToDB(int ID) throws SQLException {
        deleteOne(ID);
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ScheduleModel");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    row.append(rs.getString(i)).append(i < columnCount ? ", " : "");
                }
                list.add(row.toString());
                row = new StringBuilder();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void insertOne(Map<String, Object> entry) throws SQLException {
        String scheduleQuery = "INSERT INTO ScheduleModel(X) VALUES(Y)";
        StringBuilder scheduleColumns = new StringBuilder();
        StringBuilder scheduleValues = new StringBuilder();

        for (String column : entry.keySet()) {
            if (SCHEDULE_COLUMNS.contains(column) && !ScheduleID_Column.equalsIgnoreCase(column)) {
                if (scheduleColumns.length() > 0) {
                    scheduleColumns.append(", ");
                    scheduleValues.append(", ");
                }
                scheduleColumns.append(column);
                scheduleValues.append("?");
            }
        }

        scheduleQuery = scheduleQuery.replace("X", scheduleColumns.toString())
                .replace("Y", scheduleValues.toString());

        try (PreparedStatement schedulePS = conn.prepareStatement(scheduleQuery)) {
            int i = 1;
            for (String column : entry.keySet()) {
                if (SCHEDULE_COLUMNS.contains(column) && !ScheduleID_Column.equalsIgnoreCase(column)) {
                    SQLServerConnection.setParamater(schedulePS, i++, entry.get(column));
                }
            }
            schedulePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error inserting data into ScheduleModel: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entry) throws SQLException {
        String scheduleQuery = "UPDATE ScheduleModel SET X WHERE ScheduleID = ?";
        StringBuilder scheduleColumns = new StringBuilder();

        for (String column : entry.keySet()) {
            if (SCHEDULE_COLUMNS.contains(column)) {
                scheduleColumns.append((scheduleColumns.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }
        scheduleQuery = scheduleQuery.replace("X", scheduleColumns.toString());

        try {
            if (!scheduleColumns.isEmpty()) {
                PreparedStatement schedulePS = conn.prepareStatement(scheduleQuery);
                int i = 1;
                for (String column : entry.keySet()) {
                    if (SCHEDULE_COLUMNS.contains(column) && (!ScheduleID_Column.equalsIgnoreCase(column))) {
                        SQLServerConnection.setParamater(schedulePS, i++, entry.get(column));
//                        schedulePS.setString(i++, entry.get(column));
                    }
                }
                schedulePS.setInt(i, ID);
                schedulePS.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteOne(int ID) throws SQLException {
        String scheduleQuery = "DELETE FROM ScheduleModel WHERE ScheduleID = ?";
        try {
            PreparedStatement schedulePS = conn.prepareStatement(scheduleQuery);
            schedulePS.setInt(1, ID);
            schedulePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
