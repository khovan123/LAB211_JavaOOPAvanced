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
    TreeSet<PracticalDay> readFile() throws IOException;

    @Override
    void writeFile(TreeSet<PracticalDay> praciDays) throws IOException;

    
}
