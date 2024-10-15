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
    void addFromDatabase() throws EventException;

    @Override
    List<User> readFile() throws IOException;

    @Override
    void writeFile(List<User> entry) throws IOException, java.io.IOException;

    @Override
    void add(User entry) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    User search(Predicate<User> p) throws NotFoundException;

    @Override
    User filter(String entry, String regex) throws InvalidDataException;
}
