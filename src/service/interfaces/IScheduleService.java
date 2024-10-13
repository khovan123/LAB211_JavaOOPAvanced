package service.interfaces;

import java.util.function.Predicate;
import model.PracticalDay;

public interface IScheduleService {

    void displaySchedule();

    void addPracticeDay(PracticalDay entry);

    void deletePracticeDay(String id);

    void updatePracticeDay(PracticalDay practiceDay);

    PracticalDay searchPracticeDay(Predicate<PracticalDay> p);

}
