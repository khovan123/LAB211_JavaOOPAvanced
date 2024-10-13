package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.Workout;

public interface IWorkoutService extends Service<Workout> {

   @Override
   void display() throws EmptyDataException;

   @Override
    void add(Workout entry) throws EventException;
    
   @Override
    void delete(String id) throws EventException, NotFoundException;
    
   @Override
    void update(Workout entry) throws EventException, NotFoundException;
    
   @Override
    Workout search(Predicate<Workout> p) throws NotFoundException;

   @Override
    Workout filter(String entry, String regex) throws InvalidDataException;
    
}
