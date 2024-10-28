package repository;

import com.sun.jdi.connect.spi.Connection;
import exception.IOException;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

public class UserProgressRepository implements IUserProgressRepository {

    public static final String UserProgressID_Column = "UserProgressID";
    public static final String RegistedCourseID_Column = "RegistedCourseID";
    private static final String path = "path_to_user_progress_file.txt";
    private static final List<String> USERPROGRESS_COLUMNS = new ArrayList<>(Arrays.asList(UserProgressID_Column, RegistedCourseID_Column));
    private Connection conn = (Connection) SQLServerConnection.getConnection();

    //generate with id: CP-YYYY in scheduleRepository
    static {

    }

    @Override
    public List<UserProgress> readFile() throws IOException {
        List<UserProgress> userProgresses = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found at " + path);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String userProgressID = data[0].trim();
                    String registedCourseID = data[1].trim();
                    UserProgress userProgress = new UserProgress(userProgressID, registedCourseID);
                    userProgress.runValidate();
                    userProgresses.add(userProgress);
                }
            }
        } catch (Exception e) {
            throw new IOException(e);
        }

        return userProgresses;
    }

    @Override
    public void writeFile(List<UserProgress> entries) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (UserProgress progress : entries) {
                bw.write(String.format("%s,%s%n", progress.getUserProgressId(), progress.getRegistedCourseID()));
            }
        } catch (Exception e) {
            throw new IOException("Error write file: " + e.getMessage(), e);
        }
    }

    public List<String> getData() throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT UserProgressID, RegisteredCourseID FROM UserProgress WHERE Active = 1";
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
            throw new SQLException("Error fetching data: " + e.getMessage(), e);
        }
        return list;
    }

    public void insert(Map<String, String> entries) throws SQLException {
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

        query = query.replace("X", columnBuilder).replace("Y", valueBuilder);

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (USERPROGRESS_COLUMNS.contains(column)) {
                    ps.setString(i++, entries.get(column));
                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error inserting data: " + e.getMessage(), e);
        }
    }

    public void update(String ID, Map<String, String> entries) throws SQLException {
        String query = "UPDATE UserProgress SET X WHERE UserProgressID = ?";
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
            throw new SQLException("Error updating data: " + e.getMessage(), e);
        }
    }

    public void delete(String ID) throws SQLException {
        String query = "DELETE FROM UserProgress WHERE UserProgressID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
