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
    void addFromDatabase() throws EventException;

    @Override
    List<Schedule> readFile() throws IOException;

    @Override
    void writeFile(List<Schedule> entry) throws IOException;

    @Override
    void add(Schedule schedule) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    Schedule search(Predicate<Schedule> p) throws NotFoundException;

    @Override
    Schedule filter(String entry, String regex) throws InvalidDataException;
}
