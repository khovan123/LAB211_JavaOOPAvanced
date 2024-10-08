
package service;

import java.util.function.Predicate;
import model.Coach;

public interface ICoachService extends  Service<Coach>{
    @Override
    void display();

    @Override
    void add(Coach entry);
    
    @Override
    void delete(String id);
    
    @Override
    void update(Coach coach);
    
    @Override
    Coach search(Predicate<Coach> p);

    @Override
    Coach filter(String entry, String regex);
        
}
