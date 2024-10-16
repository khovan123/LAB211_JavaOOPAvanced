package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.Schedule;

public interface IScheduleRepository extends Repository<Schedule, List<Schedule>> {

    
    @Override
    List<Schedule> readFile() throws IOException;

    @Override
    void writeFile(List<Schedule> entry) throws IOException;

    
}
