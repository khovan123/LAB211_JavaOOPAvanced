
package service.interfaces;

import java.util.function.Predicate;
import model.PracticalDay;

public interface IUserProgressService{
    
    void displaySchedule();

    void addPracticeDay(PracticalDay practiceDay);
    
    void deletePracticeDay(String id);
    
    void updateSchedule(PracticalDay practiceDay);
    
    PracticalDay searchPracticeDay(Predicate<PracticalDay> p);
    
}
