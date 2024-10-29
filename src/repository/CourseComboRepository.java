package repository;

import java.sql.*;
import java.util.*;

import exception.InvalidDataException;
import model.CourseCombo;
import repository.interfaces.ICourseComboRepository;

public class CourseComboRepository implements ICourseComboRepository {
    public static final String ComboID_Column = "ComboID";
    public static final String ComboName_Column = "ComboName";
    public static final String Sales_Column = "Sales";
    private static final List<String> COURSECOMBOMODELCOLUMN = new ArrayList<>(Arrays.asList(ComboID_Column, ComboName_Column, Sales_Column));

    private final Connection conn = SQLServerConnection.getConnection();

    public static void main(String[] args) {
        CourseComboRepository courseComboRepository = new CourseComboRepository();
        try {
            for (String row : courseComboRepository.getMany()){
                System.out.println(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CourseCombo> readData() {
        List<String> rawData = null;
        try {
            rawData = getMany();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<CourseCombo> processData = new ArrayList<>();
        for (String row : rawData) {
            String[] col = row.split(", ");
            if (col.length == 3) {
                try {
                    CourseCombo courseCombo = new CourseCombo(
                            col[0].trim(),
                            col[1].trim(),
                            col[2].trim()
                    );
                    courseCombo.runValidate();
                    processData.add(courseCombo);
                } catch (InvalidDataException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return processData;
    }

    @Override
    public void insertToDB(CourseCombo entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        try {
            entries.put(ComboID_Column, entry.getComboId());
            entries.put(ComboName_Column, entry.getComboName());
            entries.put(Sales_Column, String.valueOf(entry.getSales()));
            insertOne(entries);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void updateToDB(String id, Map<String, Object> entry) throws SQLException {
        Map<String, String> entries = new HashMap<>();
        for (String column : entry.keySet()) {
            if (COURSECOMBOMODELCOLUMN.contains(column)) {
                entries.put(column, String.valueOf(entry.get(column)));
            }
        }
        updateOne(id, entries);
    }


    @Override
    public void deleteToDB(String ID) throws SQLException {
        try {
            deleteOne(ID);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<String> getMany() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT cb.ComboId, cb.ComboName, cb.Sale FROM CourseComboModel cb");
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
    public void insertOne(Map<String, String> entries) throws SQLException {
        String courseComboQuery = "INSERT INTO CourseComboModel(X) VALUES(Y)";
        StringBuilder modelColumn = new StringBuilder();
        StringBuilder modelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (COURSECOMBOMODELCOLUMN.contains(column)) {
                modelColumn.append((modelColumn.length() == 0 ? "" : ", ")).append(column);
                modelValue.append((modelValue.length() == 0 ? "?" : ", ?"));
            }
        }

        courseComboQuery = courseComboQuery.replace("Y", modelValue).replace("X", modelColumn);

        try {
            PreparedStatement ps = conn.prepareStatement(courseComboQuery);
            int i = 1;
            for (String column : entries.keySet()) {
                if (COURSECOMBOMODELCOLUMN.contains(column)) {
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
        String query = "UPDATE CourseComboModel SET X WHERE ComboID = ?";
        StringBuilder modelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (COURSECOMBOMODELCOLUMN.contains(column)) {
                modelColumn.append((modelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }

        query = query.replace("X", modelColumn.toString());

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            int i = 1;
            for (String column : entries.keySet()) {
                if (COURSECOMBOMODELCOLUMN.contains(column)) {
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
        String query = "DELETE FROM CourseComboModel WHERE ComboID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
