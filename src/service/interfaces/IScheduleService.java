package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.Schedule;

public interface IScheduleService extends Service<Schedule> {

    @Override

    void display() throws EmptyDataException;

    @Override
    void add(Schedule schedule) throws EventException;

    @Override
    void delete(String id) throws EventException, NotFoundException;

    @Override
    void update(Schedule schedule) throws EventException, NotFoundException;

    @Override
    Schedule search(Predicate<Schedule> p) throws NotFoundException;

    @Override
    Schedule filter(String entry, String regex) throws InvalidDataException;

}
