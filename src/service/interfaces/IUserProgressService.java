
package service.interfaces;

import java.util.function.Predicate;
import model.sub.PracticeDay;

public interface IUserProgressService{
    
    void displaySchedule();

    void addPracticeDay(PracticeDay practiceDay);
    
    void deletePracticeDay(String id);
    
    void updateSchedule(PracticeDay practiceDay);
    
    PracticeDay searchPracticeDay(Predicate<PracticeDay> p);
    
}
