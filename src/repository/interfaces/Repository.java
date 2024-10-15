package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;

public interface Repository<T, C> {

    final String path = "";

    void addFromDatabase() throws EventException;

    C readFile() throws IOException, java.io.IOException;

    void writeFile(C entry) throws IOException, java.io.IOException;

    void add(T entry) throws EventException, InvalidDataException;

    void delete(String id) throws EventException;

    T search(Predicate<T> p) throws NotFoundException;

    T filter(String entry, String regex) throws InvalidDataException;

}
