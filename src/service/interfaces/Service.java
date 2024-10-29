package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.Map;
import java.util.function.Predicate;

public interface Service<T> {

    void display() throws EmptyDataException;

    void add(T entry) throws EventException, InvalidDataException;
    
    void delete(String id) throws EventException, NotFoundException;
    
    void update(String id, Map<String, Object> entry) throws EventException, NotFoundException;
    
    T search(Predicate<T> p) throws NotFoundException;
    
    T findById(String id) throws NotFoundException;

}
