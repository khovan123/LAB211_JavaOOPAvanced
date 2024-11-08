package repository;

import exception.IOException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import model.Schedule;
import model.UserProgress;
import repository.interfaces.IUserProgressRepository;
import service.PracticalDayService;
import service.ScheduleService;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserProgressRepository implements IUserProgressRepository {

    public static final String UserProgressID_Column = "UserProgressID";
    public static final String RegistedCourseID_Column = "RegistedCourseID";
    public static final String Active_Column = "Active";
    private static final List<String> USERPROGRESS_COLUMNS = new ArrayList<>(Arrays.asList(UserProgressID_Column, RegistedCourseID_Column, Active_Column));
    private Connection conn = SQLServerConnection.getConnection();
    private static final String path = "";

    //generate with id: CP-YYYY in scheduleRepository
    static {

    }

//    public static void main(String[] args) {
//        UserProgressRepository userProgressRepository = new UserProgressRepository();
//        String userProgressID = "UP001";
//        Map<String, String> updatedEntries = new HashMap<>();
//        updatedEntries.put(UserProgressRepository.RegistedCourseID_Column, "RC001");
//        try {
//            userProgressRepository.updateOne(userProgressID, updatedEntries);
//            System.out.println("Update successful!");
//        } catch (SQLException ex) {
//            Logger.getLogger(UserProgressRepository.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public List<UserProgress> readData() {
        List<UserProgress> userProgressList = new ArrayList<>();
        String query = "SELECT * FROM UserProgress WHERE Active = 1";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UserProgress userProgress = new UserProgress();
                userProgress.setUserProgressID(rs.getString(UserProgressID_Column));
                userProgress.setRegistedCourseID(rs.getString(RegistedCourseID_Column));
                userProgressList.add(userProgress);
            }
        } catch (SQLException e) {
            try {
                throw new SQLException(e);
            } catch (SQLException ex) {
                Logger.getLogger(UserProgressRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userProgressList;
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT UserProgressID, RegisteredCourseID FROM UserProgress WHERE Active = 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = rs.getString(UserProgressID_Column) + ", " + rs.getString(RegistedCourseID_Column);
                list.add(row);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void insertOne(Map<String, String> entries) throws SQLException {
        String query = "INSERT INTO UserProgress (X) VALUES (Y)";
        StringBuilder columnBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        for (String column : entries.keySet()) {
            if (USERPROGRESS_COLUMNS.contains(column)) {
                if (columnBuilder.length() > 0) {
                    columnBuilder.append(", ");
                }
                columnBuilder.append(column);
                if (valueBuilder.length() > 0) {
                    valueBuilder.append(", ");
                }
                valueBuilder.append("?");
            }
        }

        query = query.replace("X", columnBuilder.toString()).replace("Y", valueBuilder.toString());

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (USERPROGRESS_COLUMNS.contains(column)) {
                    ps.setString(i++, entries.get(column));
                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateOne(String ID, Map<String, String> entries) throws SQLException {
        String query = "UPDATE UserProgressModel SET X WHERE UserProgressID = ?";
        StringBuilder columnBuilder = new StringBuilder();

        for (String column : entries.keySet()) {
            if (USERPROGRESS_COLUMNS.contains(column)) {
                if (columnBuilder.length() > 0) {
                    columnBuilder.append(", ");
                }
                columnBuilder.append(column).append(" = ?");
            }
        }

        query = query.replace("X", columnBuilder.toString());

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (USERPROGRESS_COLUMNS.contains(column)) {
                    ps.setString(i++, entries.get(column));
                }
            }
            ps.setString(i, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteOne(String ID) throws SQLException {
        String query = "DELETE FROM UserProgressModel WHERE UserProgressID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void insertToDB(UserProgress entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        entries.put(UserProgressID_Column, entry.getUserProgressID());
        entries.put(RegistedCourseID_Column, entry.getRegistedCourseID());
        insertOne(entries);
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
        String query = "DELETE FROM UserProgress WHERE " + UserProgressID_Column + " = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting data: " + e.getMessage(), e);
        }
    }
    

}
