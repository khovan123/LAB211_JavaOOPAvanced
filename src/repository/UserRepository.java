package repository;

import exception.IOException;
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
    public void writeFile(List<User> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
