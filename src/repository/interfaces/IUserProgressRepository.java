
package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.UserProgress;


public interface IUserProgressRepository extends Repository<UserProgress, List<UserProgress>>{

    

    @Override
    List<UserProgress> readFile() throws IOException;

    @Override
    void writeFile(List<UserProgress> entry) throws IOException;

    
}
