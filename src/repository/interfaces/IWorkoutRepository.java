package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.Workout;

public interface IWorkoutRepository extends Repository<Workout, List<Workout>> {

    final String path = "";

    @Override
    void addFromDatabase() throws EventException;

    @Override
    List<Workout> readFile() throws IOException;

    @Override
    void writeFile(List<Workout> workouts) throws IOException;

    @Override
    void add(Workout workout) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    Workout search(Predicate<Workout> p) throws NotFoundException;

    @Override
    Workout filter(String entry, String regex) throws InvalidDataException;
}
