package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.User;

public interface IUserRepository extends Repository<User, List<User>> {

    final String userPath = "";

    @Override
    List<User> readFile() throws IOException;

    @Override
    void writeFile(List<User> entry) throws IOException, java.io.IOException;

   
}
