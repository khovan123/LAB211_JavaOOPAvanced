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
    List<Workout> readFile() throws IOException;

    @Override
    void writeFile(List<Workout> workouts) throws IOException;

}
