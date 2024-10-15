package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;

import java.util.List;
import java.util.function.Predicate;

import model.Coach;

public interface ICoachRepository extends Repository<Coach, List<Coach>> {

    final String coachPath = "/data/coach.csv"; // Define coach file path

    @Override
    List<Coach> readFile() throws IOException, java.io.IOException;

    @Override
    void writeFile(List<Coach> coachs) throws IOException, java.io.IOException;

    @Override
    void add(Coach coach) throws EventException, InvalidDataException;

    @Override
    void delete(String id) throws EventException;

    @Override
    Coach search(Predicate<Coach> p) throws NotFoundException;

    @Override
    Coach filter(String entry, String regex) throws InvalidDataException;

    @Override
    void addFromDatabase() throws EventException;
}
