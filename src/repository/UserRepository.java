package repository;

import exception.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import model.User;
import repository.interfaces.IUserRepository;

public class UserRepository implements IUserRepository {

    //data sample: US-YYYY, Jonas Leonard
    static {

    }

    @Override
    public List<User> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<User> users) throws IOException, java.io.IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(path + userPath))) {
//            for (Coach coach : coachs) {
//                output.write(coach.getCoachId() + "," + coach.getCoachName());
//                output.newLine();
//            }
        } catch (java.io.IOException e) {
            throw new java.io.IOException("Error writing to the file: " + userPath);
        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
