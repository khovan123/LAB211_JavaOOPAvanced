package repository;


import exception.IOException;
import exception.InvalidDataException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import model.User;
import repository.interfaces.IUserRepository;

public class UserRepository implements IUserRepository {

    private static List<User> users = new ArrayList<>();
    //data sample: US-YYYY, Jonas Leonard
    static {

    }

    public List<User> getUsers() {
        return users;
    }


    @Override
    public List<User> readFile() throws IOException {
        users.clear();
        File file = new File(path + userPath);
        if (!file.exists()) {
            return users;
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
                users.add(user);
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error reading from file: " + userPath);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }

        return users; }



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

}
