package repository;

import exception.IOException;
import exception.InvalidDataException;
import model.Coach;
import repository.interfaces.ICoachRepository;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CoachRepository implements ICoachRepository {


    public static final String PersonID_Column = "PersonID";
    public static final String FullName_Column = "FullName";
    public static final String DoB_Column = "DOB";
    public static final String Phone_Column = "Phone";
    public static final String Email_Column = "Email";
    public static final String Certificate_Column = "Certificate";
    private static final List<String> PERSONMODELCOLUMN = List.of(PersonID_Column, FullName_Column, DoB_Column, Phone_Column, Email_Column);
    private static final List<String> COACHMODELCOLUMN = List.of(PersonID_Column, Certificate_Column);


    private Connection conn = SQLServerConnection.getConnection();



    @Override
    public List<Coach> getCoaches() {
        List<Coach> list = new ArrayList<>();
        String query = "SELECT p.PersonID, p.FullName, p.DOB, p.Phone, p.Email, c.Certificate FROM PersonModel p JOIN CoachModel c ON p.PersonID = c.PersonID";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Coach coach = new Coach(
                        rs.getString(PersonID_Column),
                        rs.getString(FullName_Column),
                        rs.getDate(DoB_Column),
                        rs.getString(Phone_Column),
                        rs.getString(Email_Column)
                );
                list.add(coach);
            }
        } catch (SQLException | InvalidDataException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Coach> readFile() throws IOException {
        List<Coach> coachList = new ArrayList<>();
        File file = new File(path + coachPath);
        if (!file.exists()) {
            return coachList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                Coach coach = new Coach(
                        details[0], // Coach ID
                        details[1], // Name
                        new Date(Long.parseLong(details[2])), // Date of Birth
                        details[3], // Phone Number
                        details[4]  // Email
                );
                coachList.add(coach);
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error reading from file: " + coachPath);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }

        return coachList;
    }

    @Override
    public void writeFile(List<Coach> coaches) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + coachPath))) {
            for (Coach coach : coaches) {
                writer.write(coach.toCSV());
                writer.newLine();
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error writing to the file: " + coachPath);
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
                personModelColumn.append(personModelColumn.length() == 0 ? "" : ", ").append(column);
                personModelValue.append(personModelValue.length() == 0 ? "?" : ", ?");
            }
            if (COACHMODELCOLUMN.contains(column)) {
                coachModelColumn.append(coachModelColumn.length() == 0 ? "" : ", ").append(column);
                coachModelValue.append(coachModelValue.length() == 0 ? "?" : ", ?");
            }
        }

        personQuery = personQuery.replace("Y", personModelValue).replace("X", personModelColumn);
        coachQuery = coachQuery.replace("Y", coachModelValue).replace("X", coachModelColumn);

        try (PreparedStatement personPS = conn.prepareStatement(personQuery);
             PreparedStatement coachPS = conn.prepareStatement(coachQuery)) {
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
                personModelColumn.append(personModelColumn.isEmpty() ? "" : ", ").append(column).append(" = ?");
            }
            if (COACHMODELCOLUMN.contains(column)) {
                coachModelColumn.append(coachModelColumn.isEmpty() ? "" : ", ").append(column).append(" = ?");
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
        String personQuery = "DELETE FROM PersonModel WHERE PersonID = ?";
        String coachQuery = "DELETE FROM CoachModel WHERE PersonID = ?";

        try (PreparedStatement coachPS = conn.prepareStatement(coachQuery);
             PreparedStatement personPS = conn.prepareStatement(personQuery)) {
            coachPS.setString(1, ID);
            personPS.setString(1, ID);
            coachPS.executeUpdate();
            personPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
