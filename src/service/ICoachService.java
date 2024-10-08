
package service;

import java.util.function.Predicate;
import model.Coach;

public interface ICoachService extends  Service<Coach>{
    void display();

    void add(Coach entry);
    
    void delete(String id);
    
    Coach search(Predicate<Coach> p);

    Coach filter(String entry, String regex);
}
