package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.TreeSet;
import java.util.function.Predicate;
import model.PracticalDay;

public interface IPracticalDayRepository extends Repository<PracticalDay, TreeSet<PracticalDay>> {

    final String path = "";

    @Override
    void addFromDatabase() throws EventException;

    @Override
    TreeSet<PracticalDay> readFile() throws IOException;

    @Override
    void writeFile(TreeSet<PracticalDay> praciDays) throws IOException;

    @Override
    void add(PracticalDay practicalDay) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    PracticalDay search(Predicate<PracticalDay> p) throws NotFoundException;

    @Override
    PracticalDay filter(String entry, String regex) throws InvalidDataException;
}
