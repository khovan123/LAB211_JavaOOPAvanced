package service.interfaces;

import java.util.function.Predicate;
import model.sub.PracticeDay;

public interface IScheduleService {

    void displaySchedule();

    void addPracticeDay(PracticeDay entry);

    void deletePracticeDay(String id);

    void updatePracticeDay(PracticeDay practiceDay);

    PracticeDay searchPracticeDay(Predicate<PracticeDay> p);

}
