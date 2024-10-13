package service.interfaces;

import java.util.function.Predicate;

import exception.NotFoundException;
import model.sub.Workout;

public interface IWorkoutService extends Service<Workout> {

    @Override
    void display() throws NotFoundException;

    @Override
    void add(Workout entry);

    @Override
    void delete(String id);

    @Override
    void update(Workout entry);

    @Override
    Workout search(Predicate<Workout> p) throws NotFoundException;

    @Override
    Workout filter(String entry, String regex);
    
    
}
