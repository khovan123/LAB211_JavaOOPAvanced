package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;

public interface Service<T> {

    void display() throws EmptyDataException;

    void add(T entry) throws EventException;
    
    void delete(String id) throws EventException, NotFoundException;
    
    void update(T entry) throws EventException, NotFoundException;
    
    T search(Predicate<T> p) throws NotFoundException;

    T filter(String entry, String regex) throws InvalidDataException;
}
