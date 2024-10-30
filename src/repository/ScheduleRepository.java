package repository;

import exception.IOException;
import exception.NotFoundException;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.PracticalDay;
import model.Schedule;
import repository.interfaces.IScheduleRepository;
import service.PracticalDayService;

public class ScheduleRepository implements IScheduleRepository {

    public static final String ScheduleID_Column = "ScheduleID";
    public static final String UserProgressID_Column = "UserProgressID";
    private static final List<String> SCHEDULE_COLUMNS = Arrays.asList(ScheduleID_Column, UserProgressID_Column);
    private Connection conn = SQLServerConnection.getConnection();
    //no path, just handle practicalRepository

    //generate Schdule with id of CP-YYYY in practicalDayRepository
    static {

    }

    public static void main(String[] args) {
        ScheduleRepository scheduleRepository = new ScheduleRepository();
        String scheduleID = "SD001";
        Map<String, String> updatedEntries = new HashMap<>();
        updatedEntries.put(ScheduleRepository.UserProgressID_Column, "UP001");
        try {
            scheduleRepository.updateOne(scheduleID, updatedEntries);
            System.out.println("Update successful!");
        } catch (SQLException ex) {
            Logger.getLogger(UserProgressRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Schedule> readData() {
        List<Schedule> schedules = new ArrayList<>();
        try {
            String query = "SELECT * FROM ScheduleModel";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getString(ScheduleID_Column));
                schedule.setUserProgressId(rs.getString(UserProgressID_Column));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            try {
                throw new SQLException(e);
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return schedules;
    }

    @Override
    public void insertToDB(Schedule entry) throws SQLException {
        Map<String, String> entryMap = new HashMap<>();
        entryMap.put(ScheduleID_Column, entry.getScheduleId());
        entryMap.put(UserProgressID_Column, entry.getUserProgressId());
        insertOne(entryMap);
    }

    @Override
    public void updateToDB(String id, Map<String, Object> entry) throws SQLException {
        Map<String, String> stringEntry = new HashMap<>();
        for (Map.Entry<String, Object> e : entry.entrySet()) {
            stringEntry.put(e.getKey(), e.getValue() == null ? "" : e.getValue().toString());
        }
        updateOne(id, stringEntry);
    }

    @Override
    public void deleteToDB(String ID) throws SQLException {
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
    public void insertOne(Map<String, String> entry) throws SQLException {
        String scheduleQuery = "INSERT INTO ScheduleModel(X) VALUES(Y)";
        StringBuilder scheduleColumns = new StringBuilder();
        StringBuilder scheduleValues = new StringBuilder();

        for (String column : entry.keySet()) {
            if (SCHEDULE_COLUMNS.contains(column)) {
                scheduleColumns.append((scheduleColumns.length() == 0 ? "" : ", ")).append(column);
                scheduleValues.append((scheduleValues.length() == 0 ? "?" : ", ?"));
            }
        }

        scheduleQuery = scheduleQuery.replace("Y", scheduleValues).replace("X", scheduleColumns);

        try {
            PreparedStatement schedulePS = conn.prepareStatement(scheduleQuery);
            int i = 1;
            for (String column : entry.keySet()) {
                if (SCHEDULE_COLUMNS.contains(column)) {
                    schedulePS.setString(i++, entry.get(column));
                }
            }
            schedulePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void updateOne(String ID, Map<String, String> entry) throws SQLException {
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
                    if (SCHEDULE_COLUMNS.contains(column)) {
                        schedulePS.setString(i++, entry.get(column));
                    }
                }
                schedulePS.setString(i, ID);
                schedulePS.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteOne(String ID) throws SQLException {
        String scheduleQuery = "DELETE FROM ScheduleModel WHERE ScheduleID = ?";
        try {
            PreparedStatement schedulePS = conn.prepareStatement(scheduleQuery);
            schedulePS.setString(1, ID);
            schedulePS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
