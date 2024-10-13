package service.interfaces;

import java.util.function.Predicate;
import model.Workout;

public interface IWorkoutService extends Service<Workout> {

    @Override
    void display();

    @Override
    void add(Workout entry);

    @Override
    void delete(String id);

    @Override
    void update(Workout entry);

    @Override
    Workout search(Predicate<Workout> p);

    @Override
    Workout filter(String entry, String regex);
    
    
}
