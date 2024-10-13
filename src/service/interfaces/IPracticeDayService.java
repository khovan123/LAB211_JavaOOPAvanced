
package service.interfaces;

import java.util.function.Predicate;

import exception.NotFoundException;
import model.sub.PracticeDay;

public interface IPracticeDayService extends Service<PracticeDay>{
    @Override
    void display() throws NotFoundException;

    @Override
    void add(PracticeDay entry);

    @Override
    void delete(String id);

    @Override
    void update(PracticeDay entry);

    @Override
    PracticeDay search(Predicate<PracticeDay> p) throws NotFoundException;

    @Override
    PracticeDay filter(String entry, String regex);

}
