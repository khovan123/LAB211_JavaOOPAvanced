package repository.interfaces;

import exception.IOException;

public interface Repository<T, C> {

    final String path = "";
  
    C readFile() throws IOException;

    void writeFile(C entry) throws IOException;

    void add(T entry) throws EventException, InvalidDataException;

    void delete(String id) throws EventException;

    T search(Predicate<T> p) throws NotFoundException;

    T filter(String entry, String regex) throws InvalidDataException;

}
