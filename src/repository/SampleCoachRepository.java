package repository;

import exception.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import model.Coach;
import repository.interfaces.ICoachRepository;

public class SampleCoachRepository implements ICoachRepository {

    public static final String PersonID_Column = "PersonID";
    public static final String FullName_Column = "FullName";
    public static final String DoB_Column = "DOB";
    public static final String Phone_Column = "Phone";
    public static final boolean Active_Column = true;
    public static final String Certificate_Column = "Certificate";
    private static final List<String> PERSONMODELCOLUMN = new ArrayList<>(Arrays.asList(PersonID_Column, FullName_Column, DoB_Column, Phone_Column,Boolean.toString(Active_Column)));
    private static final List<String> COACHMODELCOLUMN = new ArrayList<>(Arrays.asList(PersonID_Column, Certificate_Column));
    private Connection conn = SQLServerConnection.getConnection();
    
    @Override
    public List<Coach> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<Coach> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<String> getData() throws SQLException {
        List<String> list = new ArrayList<>();
        try {
            StringBuilder row = new StringBuilder();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.PersonID, p.FullName, p.DoB, p.Phone, c.Certificate  FROM PersonModel p JOIN CoachModel c ON p.PersonID = c.PersonID WHERE p.Active = 1");
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
        String personQuery = "INSERT INTO PersonModel(X) VALUES(Y)";
        String coachQuery = "INSERT INTO CoachModel(X) VALUES(Y)";
        StringBuilder personModelColumn = new StringBuilder();
        StringBuilder coachModelColumn = new StringBuilder();
        StringBuilder personModelValue = new StringBuilder();
        StringBuilder coachModelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (PERSONMODELCOLUMN.contains(column)) {
                personModelColumn.append((personModelColumn.length() == 0 ? "" : ", ")).append(column);
                personModelValue.append((personModelValue.length() == 0 ? "?" : ", ?"));
            }
            if (COACHMODELCOLUMN.contains(column)) {
                coachModelColumn.append((coachModelColumn.length() == 0 ? "" : ", ")).append(column);
                coachModelValue.append((coachModelValue.length() == 0 ? "?" : ", ?"));
            }
        }

        personQuery = personQuery.replace("Y", personModelValue).replace("X", personModelColumn);
        coachQuery = coachQuery.replace("Y", coachModelValue).replace("X", coachModelColumn);

        try {
            PreparedStatement personPS = conn.prepareStatement(personQuery);
            PreparedStatement coachPS = conn.prepareStatement(coachQuery);
            int i = 1, j = 1;
            for (String column : entries.keySet()) {
                if (PERSONMODELCOLUMN.contains(column)) {
                    personPS.setString(i++, entries.get(column));
                }
                if (COACHMODELCOLUMN.contains(column)) {
                    coachPS.setString(j++, entries.get(column));
                }
            }
            personPS.executeUpdate();
            coachPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void update(String ID, Map<String, String> entries) throws SQLException {
        String coachQuery = "UPDATE CoachModel SET X WHERE PersonID = ?";
        String personQuery = "UPDATE PersonModel SET X WHERE PersonID = ?";
        StringBuilder coachModelColumn = new StringBuilder();
        StringBuilder personModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (PERSONMODELCOLUMN.contains(column)) {
                personModelColumn.append((personModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
            if (COACHMODELCOLUMN.contains(column)) {
                coachModelColumn.append((coachModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }
        coachQuery = coachQuery.replace("X", coachModelColumn.toString());
        personQuery = personQuery.replace("X", personModelColumn.toString());
        try {
            if (!coachModelColumn.isEmpty()) {
                PreparedStatement coachPS = conn.prepareStatement(coachQuery);
                int i = 1;
                for (String column : entries.keySet()) {
                    if (COACHMODELCOLUMN.contains(column)) {
                        coachPS.setString(i++, entries.get(column));
                    }
                }
                coachPS.setString(i, ID);
                coachPS.executeUpdate();
            }
            if (!personModelColumn.isEmpty()) {
                PreparedStatement personPS = conn.prepareStatement(personQuery);
                int i = 1;
                for (String column : entries.keySet()) {
                    if (PERSONMODELCOLUMN.contains(column)) {
                        personPS.setString(i++, entries.get(column));
                    }

                }
                personPS.setString(i, ID);
                personPS.executeUpdate();
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void delete(String ID) throws SQLException {
        String personQuery = "UPDATE PersonModel SET Active = 0 WHERE PersonID = ?";
        try {
            PreparedStatement personPS = conn.prepareStatement(personQuery);
            personPS.setString(1, ID);
            personPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
