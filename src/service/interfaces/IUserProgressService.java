
package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.UserProgress;

public interface IUserProgressService extends Service<UserProgress>{
    
    @Override
    void display() throws EmptyDataException;

    @Override
    void add(UserProgress userProgress) throws EventException;
    
    @Override
    void delete(String id) throws EventException, NotFoundException;
    
    @Override
    void update(UserProgress userProgress) throws EventException, NotFoundException;
    
    @Override
    UserProgress search(Predicate<UserProgress> p) throws NotFoundException;

    @Override
    UserProgress filter(String entry, String regex) throws InvalidDataException;
    
}
