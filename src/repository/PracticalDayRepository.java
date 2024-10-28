package repository;

import exception.IOException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import exception.InvalidDataException;
import model.PracticalDay;
import repository.interfaces.IPracticalDayRepository;

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
    public TreeSet<PracticalDay> readFile() throws IOException {
        TreeSet<PracticalDay> practicalDaysFromFile = new TreeSet<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found at path: " + path);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");

                    String practicalDayID = data[0];
                    String practiceDate = data[1];

                    PracticalDay practicalDay = new PracticalDay(practicalDayID, practiceDate, data[2]);
                    practicalDay.runValidate();
                    practicalDaysFromFile.add(practicalDay);
                } catch (Exception e) {
                    throw new IOException("Add failed (" + e.getMessage() + ")");
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("Read file failed!!! (" + e.getMessage() + ")");
        }
        return practicalDaysFromFile;
    }

    @Override
    public void writeFile(TreeSet<PracticalDay> practicalDays) throws IOException {
        System.out.println("Not yet supported!!!");
    }

    public List<String> getData() throws SQLException {
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

    public void insert(Map<String, String> entries) throws SQLException {
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

    public void update(String ID, Map<String, String> entries) throws SQLException {
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

    public void delete(String ID) throws SQLException {
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
