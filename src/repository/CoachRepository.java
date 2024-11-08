package repository;

import exception.InvalidDataException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import model.Coach;
import repository.interfaces.ICoachRepository;
import utils.GlobalUtils;

public class CoachRepository implements ICoachRepository {

    public static final String PersonID_Column = "CoachID";
    public static final String FullName_Column = "FullName";
    public static final String DoB_Column = "DOB";
    public static final String Phone_Column = "Phone";
    public static final String Active_Column = "Active";
    public static final String Certificate_Column = "Certificate";
    private static List<String> PERSONMODELCOLUMN = new ArrayList<>(Arrays.asList(PersonID_Column, FullName_Column, DoB_Column, Phone_Column, Active_Column));
    private static List<String> COACHMODELCOLUMN = new ArrayList<>(Arrays.asList(PersonID_Column, Certificate_Column));
    private Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<Coach> readData() throws SQLException {
        List<Coach> coaches = new ArrayList<>();
        for (String row : getMany()) {
            String data[] = row.split(",");
            try {
                Coach coach = new Coach(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim());
                coaches.add(coach);
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);
            }
        }
        return coaches;
    }

    @Override
    public void insertToDB(Coach coach) throws SQLException {
        Map<String, Object> coachMap = new HashMap<>();
        coachMap.put(PersonID_Column, coach.getPersonId());
        coachMap.put(FullName_Column, coach.getFullName());
        coachMap.put(DoB_Column, GlobalUtils.dateFormat(coach.getDoB()));
        coachMap.put(Phone_Column, coach.getPhone());
//        coachMap.put(Active_Column, GlobalUtils.convertToString(coach.isActive()));
        coachMap.put(Certificate_Column, coach.getCertificate());
        insertOne(coachMap);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> coachMap) throws SQLException {
        updateOne(id, coachMap);
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
            ResultSet rs = stmt.executeQuery("SELECT c.CoachID, p.FullName, p.DoB, p.Phone, p.Active, c.Certificate FROM PersonModel p JOIN CoachModel c ON p.PersonID = c.CoachID WHERE p.Active = 1");
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
        String personQuery = "INSERT INTO PersonModel(X) VALUES(Y)";
        String coachQuery = "INSERT INTO CoachModel(X) VALUES(Y)";
        StringBuilder personModelColumn = new StringBuilder();
        StringBuilder coachModelColumn = new StringBuilder();
        StringBuilder personModelValue = new StringBuilder();
        StringBuilder coachModelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (PERSONMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
                if (personModelColumn.length() > 0) {
                    personModelColumn.append(", ");
                    personModelValue.append(", ");
                }
                personModelColumn.append(column);
                personModelValue.append("?");
            }
            if (COACHMODELCOLUMN.contains(column)) {
                if (coachModelColumn.length() > 0) {
                    coachModelColumn.append(", ");
                    coachModelValue.append(", ");
                }
                coachModelColumn.append(column);
                coachModelValue.append("?");
            }
        }

        personQuery = personQuery.replace("Y", personModelValue.toString()).replace("X", personModelColumn.toString());
        coachQuery = coachQuery.replace("Y", coachModelValue.toString()).replace("X", coachModelColumn.toString());

        try (PreparedStatement personPS = conn.prepareStatement(personQuery); PreparedStatement coachPS = conn.prepareStatement(coachQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (PERSONMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
                    SQLServerConnection.setParamater(personPS, i++, entries.get(column));
                }
            }

            int j = 1;
            for (String column : entries.keySet()) {
                if (COACHMODELCOLUMN.contains(column)) {
                    SQLServerConnection.setParamater(coachPS, j++, entries.get(column));
                }
            }

            personPS.executeUpdate();
            coachPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entries) throws SQLException {
        String coachQuery = "UPDATE CoachModel SET X WHERE CoachID = ?";
        String personQuery = "UPDATE PersonModel SET X WHERE PersonID = ?";
        StringBuilder coachModelColumn = new StringBuilder();
        StringBuilder personModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (PERSONMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
                personModelColumn.append((personModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
            if (COACHMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
                coachModelColumn.append((coachModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }
        coachQuery = coachQuery.replace("X", coachModelColumn.toString());
        personQuery = personQuery.replace("X", personModelColumn.toString());
        if (!coachModelColumn.isEmpty()) {
            PreparedStatement coachPS = conn.prepareStatement(coachQuery);
            int i = 1;
            for (String column : entries.keySet()) {
                if (COACHMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
//                        coachPS.setString(i++, entries.get(column));
                    SQLServerConnection.setParamater(coachPS, i++, entries.get(column));
                }
            }
            coachPS.setInt(i, ID);
            coachPS.executeUpdate();
        }
        if (!personModelColumn.isEmpty()) {
            PreparedStatement personPS = conn.prepareStatement(personQuery);
            int i = 1;
            for (String column : entries.keySet()) {
                if (PERSONMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
//                            personPS.setString(i++, entries.get(column));
                    SQLServerConnection.setParamater(personPS, i++, entries.get(column));
                }

            }
            personPS.setInt(i, ID);
            personPS.executeUpdate();
        }
    }

    @Override
    public void deleteOne(int ID) throws SQLException {
        String personQuery = "UPDATE PersonModel SET Active = 0 WHERE PersonID = ?";
        try {
            PreparedStatement personPS = conn.prepareStatement(personQuery);
            personPS.setInt(1, ID);
            personPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
