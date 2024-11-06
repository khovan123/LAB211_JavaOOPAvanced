package repository;

import exception.InvalidDataException;
import java.sql.*;
import java.util.*;
import model.UserProgress;
import repository.interfaces.IUserProgressRepository;
import java.sql.ResultSet;
import java.text.ParseException;

public class UserProgressRepository implements IUserProgressRepository {

    public static final String UserProgressID_Column = "UserProgressID";
    public static final String RegistedCourseID_Column = "RegistedCourseID";
    private static final List<String> USERPROGRESS_COLUMNS = new ArrayList<>(Arrays.asList(UserProgressID_Column, RegistedCourseID_Column));
    private Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<UserProgress> readData() throws SQLException {
        List<UserProgress> userProgressList = new ArrayList<>();
        for (String row : getMany()) {
            String data[] = row.split(",");
            try {
                userProgressList.add(new UserProgress(data[0].trim(), data[1].trim()));
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);

            }
        }
        return userProgressList;
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT UserProgressID, RegistedCourseID FROM UserProgressModel");
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
        String queryTemplate = "INSERT INTO UserProgressModel (X) VALUES (Y)";
        StringBuilder columnBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        for (String column : entries.keySet()) {
            if (USERPROGRESS_COLUMNS.contains(column) && !column.equalsIgnoreCase(UserProgressID_Column)) {
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

        String query = queryTemplate.replace("X", columnBuilder.toString())
                .replace("Y", valueBuilder.toString());

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (USERPROGRESS_COLUMNS.contains(column) && !column.equalsIgnoreCase(UserProgressID_Column)) {
                    SQLServerConnection.setParamater(ps, i++, entries.get(column));
                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error inserting data into UserProgressModel: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entries) throws SQLException {
        String query = "UPDATE UserProgressModel SET X WHERE UserProgressID = ?";
        StringBuilder columnBuilder = new StringBuilder();

        for (String column : entries.keySet()) {
            if (USERPROGRESS_COLUMNS.contains(column) && (!column.equalsIgnoreCase(UserProgressID_Column))) {
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
                if (USERPROGRESS_COLUMNS.contains(column) && (!column.equalsIgnoreCase(UserProgressID_Column))) {
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
        String query = "DELETE FROM UserProgressModel WHERE UserProgressID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void insertToDB(UserProgress entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
//        entries.put(UserProgressID_Column, entry.getUserProgressID());
        entries.put(RegistedCourseID_Column, entry.getRegistedCourseID());
        insertOne(entries);
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
        String query = "DELETE FROM UserProgressModel WHERE UserProgressID = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting data: " + e.getMessage(), e);
        }
    }

}
