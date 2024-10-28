package repository;

import exception.IOException;
import exception.InvalidDataException;
import model.User;
import repository.interfaces.IUserRepository;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserRepository implements IUserRepository {

    public static final String UserID_Column = "UserID";
    public static final String FullName_Column = "FullName";
    public static final String DoB_Column = "DOB";
    public static final String Phone_Column = "Phone";
    public static final String Email_Column = "Email";
    public static final String Gender_Column = "Gender";
    private static final List<String> USERMODELCOLUMN = List.of(UserID_Column, FullName_Column, DoB_Column, Phone_Column, Email_Column, Gender_Column);

    private Connection conn = SQLServerConnection.getConnection();



    @Override
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT UserID, FullName, DOB, Gender, Phone, Email FROM UserModel";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(
                        rs.getString(UserID_Column),
                        rs.getString(FullName_Column),
                        rs.getBoolean(Gender_Column),
                        rs.getDate(DoB_Column),
                        rs.getString(Phone_Column),
                        rs.getString(Email_Column)
                );
                list.add(user);
            }
        } catch (SQLException | InvalidDataException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<User> readFile() throws IOException {
        List<User> userList = new ArrayList<>();
        File file = new File(path + userPath);
        if (!file.exists()) {
            return userList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                User user = new User(
                        details[0], // User ID
                        details[1], // Name
                        Boolean.parseBoolean(details[2]), // Gender
                        new Date(Long.parseLong(details[3])), // Date of Birth
                        details[4], // Phone Number
                        details[5]  // Email
                );
                userList.add(user);
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error reading from file: " + userPath);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    @Override
    public void writeFile(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + userPath))) {
            for (User user : users) {
                writer.write(user.toCSV());
                writer.newLine();
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error writing to the file: " + userPath);
        }
    }

    public void insert(Map<String, String> entries) throws SQLException {
        String userQuery = "INSERT INTO UserModel(X) VALUES(Y)";
        StringBuilder userModelColumn = new StringBuilder();
        StringBuilder userModelValue = new StringBuilder();

        for (String column : entries.keySet()) {
            if (USERMODELCOLUMN.contains(column)) {
                userModelColumn.append((userModelColumn.length() == 0 ? "" : ", ")).append(column);
                userModelValue.append((userModelValue.length() == 0 ? "?" : ", ?"));
            }
        }

        userQuery = userQuery.replace("Y", userModelValue).replace("X", userModelColumn);

        try (PreparedStatement userPS = conn.prepareStatement(userQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (USERMODELCOLUMN.contains(column)) {
                    userPS.setString(i++, entries.get(column));
                }
            }
            userPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void update(String ID, Map<String, String> entries) throws SQLException {
        String userQuery = "UPDATE UserModel SET X WHERE UserID = ?";
        StringBuilder userModelColumn = new StringBuilder();

        for (String column : entries.keySet()) {
            if (USERMODELCOLUMN.contains(column)) {
                userModelColumn.append((userModelColumn.isEmpty() ? "" : ", ")).append(column).append(" = ?");
            }
        }
        userQuery = userQuery.replace("X", userModelColumn.toString());

        try (PreparedStatement userPS = conn.prepareStatement(userQuery)) {
            int i = 1;
            for (String column : entries.keySet()) {
                if (USERMODELCOLUMN.contains(column)) {
                    userPS.setString(i++, entries.get(column));
                }
            }
            userPS.setString(i, ID);
            userPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void delete(String ID) throws SQLException {
        String userQuery = "DELETE FROM UserModel WHERE UserID = ?";

        try (PreparedStatement userPS = conn.prepareStatement(userQuery)) {
            userPS.setString(1, ID);
            userPS.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
