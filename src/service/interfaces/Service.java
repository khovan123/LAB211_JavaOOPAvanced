package service.interfaces;

import java.util.function.Predicate;

public interface Service<T> {

    void display();

    void add(T entry);
    
    void delete(String id);
    
    void update(T entry);
    
    T search(Predicate<T> p);

    T filter(String entry, String regex);
}
