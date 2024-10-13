
package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.UserProgress;


public interface IUserProgressRepository extends Repository<UserProgress, List<UserProgress>>{

    @Override
    void addFromDatabase() throws EventException;

    @Override
    List<UserProgress> readFile() throws IOException;

    @Override
    void writeFile(List<UserProgress> entry) throws IOException;

    @Override
    void add(UserProgress userProgress) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    UserProgress search(Predicate<UserProgress> p) throws NotFoundException;

    @Override
    UserProgress filter(String entry, String regex) throws InvalidDataException;
}
