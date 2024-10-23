package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.Coach;
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



    @Override
    public void writeFile(List<User> users) throws IOException, java.io.IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(path + userPath))) {

        } catch (java.io.IOException e) {
            throw new java.io.IOException("Error writing to the file: " + userPath);
        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
