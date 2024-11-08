package repository;

import exception.InvalidDataException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import model.CourseCombo;
import repository.interfaces.ICourseComboRepository;

public class CourseComboRepository implements ICourseComboRepository {

    public static final String ComboID_Column = "ComboID";
    public static final String ComboName_Column = "ComboName";
    public static final String Sales_Column = "Sales";
    private static final List<String> COURSECOMBOMODELCOLUMN = new ArrayList<>(Arrays.asList(ComboID_Column, ComboName_Column, Sales_Column));
    private final Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<CourseCombo> readData() throws SQLException {
        List<CourseCombo> processData = new ArrayList<>();
        for (String row : getMany()) {
            String[] col = row.split(",");
            try {
                processData.add(new CourseCombo(col[0].trim(), col[1].trim(), col[2].trim()));
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);
            }
        }
        return processData;
    }

    @Override
    public void insertToDB(CourseCombo entry) throws SQLException {
        Map<String, Object> entries = new HashMap<>();
//        entries.put(ComboID_Column, entry.getComboId());
        entries.put(ComboName_Column, entry.getComboName());
        entries.put(Sales_Column, String.valueOf(entry.getSales()));
        insertOne(entries);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> entries) throws SQLException {
        updateOne(id, entries);
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
            ResultSet rs = stmt.executeQuery("SELECT cb.ComboId, cb.ComboName, cb.Sales FROM CourseComboModel cb");
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
    public void insertOne(Map<String, Object> entries) throws SQLException {
        String courseComboQuery = "INSERT INTO CourseComboModel(X) VALUES(Y)";
        StringBuilder modelColumn = new StringBuilder();
        StringBuilder modelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (COURSECOMBOMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(ComboID_Column))) {
                if (modelColumn.length() > 0) {
                    modelColumn.append(", ");
                    modelValue.append(", ");
                }
                modelColumn.append(column);
                modelValue.append("?");
            }
        }

        courseComboQuery = courseComboQuery.replace("Y", modelValue.toString()).replace("X", modelColumn.toString());

        try (PreparedStatement ps = conn.prepareStatement(courseComboQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (COURSECOMBOMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(ComboID_Column))) {
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
        String query = "UPDATE CourseComboModel SET X WHERE ComboID = ?";
        StringBuilder modelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (COURSECOMBOMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(ComboID_Column))) {
                modelColumn.append((modelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        query = query.replace("X", modelColumn.toString());

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            int i = 1;
            for (String column : entries.keySet()) {
                if (COURSECOMBOMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(ComboID_Column))) {
//                    ps.setString(i++, entries.get(column));
                    SQLServerConnection.setParamater(ps, i++, entries.get(column));
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
        String query = "DELETE FROM CourseComboModel WHERE ComboID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
