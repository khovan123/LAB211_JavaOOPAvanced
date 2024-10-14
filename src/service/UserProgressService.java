package service;

import java.util.List;
import service.interfaces.IUserProgressService;
import java.util.function.Predicate;
import model.PracticalDay;

public class UserProgressService implements IUserProgressService {

    private List<PracticalDay> practiceDays;

    @Override
    public void displaySchedule() {
        if (practiceDays.isEmpty()) {
            System.out.println("No practice days scheduled.");
        } else {
            for (PracticalDay day : practiceDays) {
                System.out.println(day);
            }
        }
    }

    @Override
    public void addPracticeDay(PracticalDay practiceDay) {
        practiceDays.add(practiceDay);
        System.out.println("Practice day added: " + practiceDay);
    }

    @Override
    public void deletePracticeDay(String id) {
        boolean rm = practiceDays.removeIf(day -> day.getPracticeDayId().equals(id));
        if (rm) {
            System.out.println("Practice day with ID " + id + " was removed.");
        } else {
            System.out.println("Practice day with ID " + id + " not found.");
        }
    }

    @Override
    public void updateSchedule(PracticalDay practiceDay) {
        for (int i = 0; i < practiceDays.size(); i++) {
            if (practiceDays.get(i).getPracticeDayId().equals(practiceDay.getPracticeDayId())) {
                practiceDays.set(i, practiceDay);
                System.out.println("Practice day with ID " + practiceDay.getPracticeDayId()+ " was updated.");
                return;
            }
        }
        System.out.println("Practice day with ID " + practiceDay.getPracticeDayId()+ " not found.");
    }

    @Override
    public PracticalDay searchPracticeDay(Predicate<PracticalDay> p) {
        return practiceDays.stream().filter(p).findFirst().orElse(null);
    }

}
