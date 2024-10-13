
package service.interfaces;

import java.util.function.Predicate;
import model.PracticalDay;

public interface IPracticalDayService extends Service<PracticalDay>{
    @Override
    void display();

    @Override
    void add(PracticalDay entry);

    @Override
    void delete(String id);

    @Override
    void update(PracticalDay entry);

    @Override
    PracticalDay search(Predicate<PracticalDay> p);

    @Override
    PracticalDay filter(String entry, String regex);

}
