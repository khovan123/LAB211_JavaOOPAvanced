package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.PracticalDay;

public interface IPracticalDayService extends Service<PracticalDay> {

    @Override
    void display() throws EmptyDataException;

    @Override
    void add(PracticalDay practicalDay) throws EventException;

    @Override
    void delete(String id) throws EventException, NotFoundException;

    @Override
    void update(PracticalDay practicalDay) throws EventException, NotFoundException;

    @Override
    PracticalDay search(Predicate<PracticalDay> p) throws NotFoundException;

}
