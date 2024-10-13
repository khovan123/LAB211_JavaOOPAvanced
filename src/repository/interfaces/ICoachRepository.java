package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.Coach;

public interface ICoachRepository extends Repository<Coach, List<Coach>> {

    final String path = "";

    @Override
    void addFromDatabase() throws EventException;

    @Override
    List<Coach> readFile() throws IOException;

    @Override
    void writeFile(List<Coach> coachs) throws IOException;

    @Override
    void add(Coach coach) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    Coach search(Predicate<Coach> p) throws NotFoundException;

    @Override
    Coach filter(String entry, String regex) throws InvalidDataException;
}
