package repository;

import exception.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    private Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<CourseCombo> readFile() throws IOException {
        List<CourseCombo> courseCombos = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File not found at Path: " + path);
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    CourseCombo courseCombo = new CourseCombo(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim()
                    );
                    courseCombos.add(courseCombo);
                } catch (InvalidDataException e) {
                    throw new IOException("-> Error While Adding Course Combo");
                }
            }
        } catch (Exception e) {
            throw new IOException("-> Error While Reading File");
        }
        return courseCombos;
    }

    @Override
    public void writeFile(List<CourseCombo> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getData() throws SQLException {
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

    public void insert(Map<String, String> entries) throws SQLException {
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
            throw new SQLException("-> Error while inserting data - " + e.getMessage());
        }
    }

    public void update(String ID, Map<String, String> entries) throws SQLException {
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
            throw new SQLException("-> Error while updating data - " + e.getMessage());
        }
    }

    public void delete(String ID) throws SQLException {
        String query = "DELETE FROM CourseComboModel WHERE ComboID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("-> Error while deleting data - " + e.getMessage());
        }
    }
}
