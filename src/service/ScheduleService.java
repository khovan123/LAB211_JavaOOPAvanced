package service;

import java.util.ArrayList;
import java.util.List;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;
import model.sub.PracticeDay;

public class ScheduleService implements IScheduleService {

    List<PracticeDay> practiceDays = new ArrayList<>();

    @Override
    public void displaySchedule() {
        if (practiceDays.isEmpty()) {
            return;
        } else {
            for (PracticeDay practiceDay : practiceDays) {
                System.out.println(practiceDay);
            }
        }
    }

    @Override
    public void addPracticeDay(PracticeDay entry) {
        practiceDays.add(entry);
        System.out.println("Added successful!");
    }

    @Override
    public void deletePracticeDay(String id) {
        
    }

    @Override
    public void updatePracticeDay(PracticeDay practiceDay) {

    }

    @Override
    public PracticeDay searchPracticeDay(Predicate<PracticeDay> p) {
        for(PracticeDay practiceDay : practiceDays){
            if (p.test(practiceDay)){
                return practiceDay;
            }
        }
        return null;
    }

}
