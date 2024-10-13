package service.interfaces;

import exception.NotFoundException;

import java.util.function.Predicate;

public interface Service<T> {

    void display() throws NotFoundException;

    void add(T entry);
    
    void delete(String id);
    
    void update(T entry);
    
    T search(Predicate<T> p) throws NotFoundException;

    T filter(String entry, String regex);
}
