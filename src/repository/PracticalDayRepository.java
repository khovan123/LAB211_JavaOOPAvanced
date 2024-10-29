package repository;

import exception.IOException;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import exception.InvalidDataException;
import model.PracticalDay;
import repository.interfaces.IPracticalDayRepository;
import utils.GlobalUtils;

public class PracticalDayRepository implements IPracticalDayRepository {

    private static TreeSet<PracticalDay> practicalDays = new TreeSet<>();
    private Connection conn = SQLServerConnection.getConnection();
    public static final String PracticalDayID_Column = "PracticalDayID";
    public static final String PracticeDate_Column = "PracticalDate";
    public static final String ScheduleID_Column = "ScheduleID";

    public TreeSet<PracticalDay> getPracticalDays() {
        return practicalDays;
    }

    @Override
    public TreeSet<PracticalDay> readData() throws SQLException {
        TreeSet<PracticalDay> practicalDaysFromFile = new TreeSet<>();
            try {
                for (String row : getMany()){
                    try {
                        String[] data = row.split(",");

                        String practicalDayID = data[0];
                        String practiceDate = data[1];

                        PracticalDay practicalDay = new PracticalDay(practicalDayID, practiceDate, data[2]);
                        practicalDay.runValidate();
                        practicalDaysFromFile.add(practicalDay);
                    } catch (Exception e) {
                    }
                }
            } catch (SQLException e){
                throw new SQLException(e);
            }

        return practicalDaysFromFile;
    }

    @Override
    public void insertToDB(PracticalDay practicalDay) throws SQLException{
        Map<String, String> entries = new HashMap<>();
        entries.put(PracticalDayID_Column, practicalDay.getPracticalDayId());
        entries.put(PracticeDate_Column, GlobalUtils.getDateString(practicalDay.getPracticeDate()));
        entries.put(ScheduleID_Column, practicalDay.getScheduleId());
        insertOne(entries);
    }

    @Override
    public void updateToDB(String id, Map<String, Object> entry) throws SQLException{
        Map<String, String> entries = new HashMap<>();
        if (entry.containsKey(PracticeDate_Column)) {
            entries.put(PracticeDate_Column, GlobalUtils.getDateString((Date) entry.get(PracticeDate_Column)));
        }
        if (entry.containsKey(ScheduleID_Column)) {
            entries.put(ScheduleID_Column, (String) entry.get(ScheduleID_Column));
        }
        updateOne(id, entries);
    }

    @Override
    public void deleteToDB(String ID) throws SQLException{
        deleteOne(ID);
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT PracticalDayID, PracticalDate, ScheduleID FROM PracticalDayModel");
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
    public void insertOne(Map<String, String> entries) throws SQLException {
        String query = "INSERT INTO PracticalDayModel (PracticalDayID, PracticalDate, ScheduleID) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, entries.get(PracticalDayID_Column));
            ps.setString(2, entries.get(PracticeDate_Column));
            ps.setString(3, entries.get(ScheduleID_Column));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to insert PracticalDay: " + e.getMessage());
        }
    }

    @Override
    public void updateOne(String ID, Map<String, String> entries) throws SQLException {
        String query = "UPDATE PracticalDayModel SET PracticalDate = ?, ScheduleID = ? WHERE PracticalDayID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, entries.get(PracticeDate_Column));
            ps.setString(2, entries.get(ScheduleID_Column));
            ps.setString(3, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update PracticalDay: " + e.getMessage());
        }
    }

    @Override
    public void deleteOne(String ID) throws SQLException {
        String query = "DELETE FROM PracticalDayModel WHERE PracticalDayID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete PracticalDay: " + e.getMessage());
        }
    }

}
