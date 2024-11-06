package repository;

import exception.InvalidDataException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import model.PracticalDay;
import repository.interfaces.IPracticalDayRepository;
import utils.GlobalUtils;

public class PracticalDayRepository implements IPracticalDayRepository {

    private Connection conn = SQLServerConnection.getConnection();
    public static final String PracticalDayID_Column = "PracticalDayID";
    public static final String PracticeDate_Column = "PracticalDate";
    public static final String PracticalDay_Status = "Done";
    public static final String ScheduleID_Column = "ScheduleID";
    public static final List<String> PRACTICALDAYMODELCOLUMN = new ArrayList<>(Arrays.asList(PracticalDayID_Column, PracticeDate_Column, PracticalDay_Status, ScheduleID_Column));

    @Override
    public TreeSet<PracticalDay> readData() throws SQLException {
        TreeSet<PracticalDay> practicalDaysFromFile = new TreeSet<>();
        for (String row : getMany()) {
            try {
                String[] data = row.split(",");
                PracticalDay practicalDay = new PracticalDay(data[0].trim(), data[1].trim(), data[2].trim().equalsIgnoreCase("1") ? "true" : "false", data[3].trim());
                practicalDay.runValidate();
                practicalDaysFromFile.add(practicalDay);
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);

            }
        }
        return practicalDaysFromFile;
    }

    @Override
    public void insertToDB(PracticalDay practicalDay) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
//        entries.put(PracticalDayID_Column, practicalDay.getPracticalDayId());
        entries.put(PracticeDate_Column, GlobalUtils.dateFormat(practicalDay.getPracticeDate()));
        entries.put(PracticalDay_Status, Boolean.toString(practicalDay.isDone()));
        entries.put(ScheduleID_Column, practicalDay.getScheduleId());
        insertOne(entries);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> practicalDayMap) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        for (String column : practicalDayMap.keySet()) {
            map.putIfAbsent(column, (practicalDayMap.get(column)));
        }
        updateOne(id, map);
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
            ResultSet rs = stmt.executeQuery("SELECT PracticalDayID, PracticalDate, Done, ScheduleID FROM PracticalDayModel");
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
        String query = "INSERT INTO PracticalDayModel (PracticalDayID, PracticalDate, Done, ScheduleID) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (PRACTICALDAYMODELCOLUMN.contains(column)) {
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
        String query = "UPDATE PracticalDayModel SET X WHERE PracticalDayID = ?";
        StringBuilder courseModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (PRACTICALDAYMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PracticalDayID_Column))) {
                courseModelColumn.append((courseModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }
        query = query.replace("X", courseModelColumn.toString());

        if (!courseModelColumn.isEmpty()) {
            PreparedStatement ps = conn.prepareStatement(query);
            int i = 1;
            for (String column : entries.keySet()) {
                if (PRACTICALDAYMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PracticalDayID_Column))) {
//                        coursePS.setString(i++, entries.get(column));
                    SQLServerConnection.setParamater(ps, i++, entries.get(column));
                }
            }
            ps.setInt(i, ID);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteOne(int ID) throws SQLException {
        String query = "DELETE FROM PracticalDayModel WHERE PracticalDayID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete PracticalDay: " + e.getMessage());
        }
    }

}
