package repository;

import exception.InvalidDataException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import model.User;
import repository.interfaces.IUserRepository;
import utils.GlobalUtils;

public class UserRepository implements IUserRepository {

    public static final String PersonID_Column = "UserID";
    public static final String FullName_Column = "FullName";
    public static final String DoB_Column = "DOB";
    public static final String Phone_Column = "Phone";
    public static final String Active_Column = "Active";
    public static final String Addventor_Column = "Addventor";
    private static List<String> PERSONMODELCOLUMN = new ArrayList<>(Arrays.asList(PersonID_Column, FullName_Column, DoB_Column, Phone_Column, Active_Column));
    private static List<String> USERMODELCOLUMN = new ArrayList<>(Arrays.asList(PersonID_Column, Addventor_Column));
    private Connection conn = SQLServerConnection.getConnection();

    @Override
    public List<User> readData() throws SQLException {
        List<User> coaches = new ArrayList<>();
        for (String row : getMany()) {
            String data[] = row.split(",");
            try {
                User coach = new User(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim().equalsIgnoreCase("1") ? "true" : "false", data[5].trim().equalsIgnoreCase("1") ? "true" : "false");
                coaches.add(coach);
            } catch (InvalidDataException | ParseException e) {
                throw new SQLException(e);
            }
        }
        return coaches;
    }

    @Override
    public void insertToDB(User user) throws SQLException {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(PersonID_Column, user.getPersonId());
        userMap.put(FullName_Column, user.getFullName());
        userMap.put(DoB_Column, GlobalUtils.dateFormat(user.getDoB()));
        userMap.put(Phone_Column, user.getPhone());
//        userMap.put(Active_Column, GlobalUtils.convertToString(user.isActive()));
        userMap.put(Addventor_Column, Boolean.toString(user.isAddventor()));
        insertOne(userMap);
    }

    @Override
    public void updateToDB(int id, Map<String, Object> user) throws SQLException {
        Map<String, Object> userMap = new HashMap<>();
        for (String column : user.keySet()) {
            userMap.putIfAbsent(column, (user.get(column)));
        }
        updateOne(id, userMap);
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
            ResultSet rs = stmt.executeQuery("SELECT u.UserID, p.FullName, p.DoB, p.Phone, p.Active, u.Addventor FROM PersonModel p JOIN UserModel u ON p.PersonID = u.UserID WHERE p.Active = 1");
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
        String userQuery = "INSERT INTO UserModel(X) VALUES(Y)";
        StringBuilder personModelColumn = new StringBuilder();
        StringBuilder userModelColumn = new StringBuilder();
        StringBuilder personModelValue = new StringBuilder();
        StringBuilder userModelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (PERSONMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column)) && (!column.equalsIgnoreCase(Active_Column))) {
                if (personModelColumn.length() > 0) {
                    personModelColumn.append(", ");
                    personModelValue.append(", ");
                }
                personModelColumn.append(column);
                personModelValue.append("?");
            }
            if (USERMODELCOLUMN.contains(column)) {
                if (userModelColumn.length() > 0) {
                    userModelColumn.append(", ");
                    userModelValue.append(", ");
                }
                userModelColumn.append(column);
                userModelValue.append("?");
            }
        }

        personQuery = personQuery.replace("Y", personModelValue.toString()).replace("X", personModelColumn.toString());
        userQuery = userQuery.replace("Y", userModelValue.toString()).replace("X", userModelColumn.toString());

        try (PreparedStatement personPS = conn.prepareStatement(personQuery); PreparedStatement userPS = conn.prepareStatement(userQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (PERSONMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column)) && (!column.equalsIgnoreCase(Active_Column))) {
                    SQLServerConnection.setParamater(personPS, i++, entries.get(column));
                }
            }

            int j = 1;
            for (String column : entries.keySet()) {
                if (USERMODELCOLUMN.contains(column)) {
                    SQLServerConnection.setParamater(userPS, j++, entries.get(column));
                }
            }

            personPS.executeUpdate();
            userPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    @Override
    public void updateOne(int ID, Map<String, Object> entries) throws SQLException {
        String userQuery = "UPDATE UserModel SET X WHERE UserID = ?";
        String personQuery = "UPDATE PersonModel SET X WHERE PersonID = ?";
        StringBuilder coachModelColumn = new StringBuilder();
        StringBuilder personModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (PERSONMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
                personModelColumn.append((personModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
            if (USERMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
                coachModelColumn.append((coachModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }
        userQuery = userQuery.replace("X", coachModelColumn.toString());
        personQuery = personQuery.replace("X", personModelColumn.toString());
        try {
            if (!coachModelColumn.isEmpty()) {
                PreparedStatement coachPS = conn.prepareStatement(userQuery);
                int i = 1;
                for (String column : entries.keySet()) {
                    if (USERMODELCOLUMN.contains(column) && (!column.equalsIgnoreCase(PersonID_Column))) {
                        SQLServerConnection.setParamater(coachPS, i++, entries.get(column));
//                        coachPS.setString(i++, entries.get(column));
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
                        SQLServerConnection.setParamater(personPS, i++, entries.get(column));
//                        personPS.setString(i++, entries.get(column));
                    }

                }
                personPS.setInt(i, ID);
                personPS.executeUpdate();
            }

        } catch (SQLException e) {
            throw new SQLException(e);
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
