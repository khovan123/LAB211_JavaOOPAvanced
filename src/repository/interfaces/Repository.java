package repository.interfaces;

import java.util.function.Predicate;

public interface Repository<T, C> {

    final String path = "";

    C readFile();

    void writeFile(C entry);
    
    void add(T entry);
    
    void delete(String id);
    
    T search(Predicate<T> p);

    T filter(String entry, String regex);

}
