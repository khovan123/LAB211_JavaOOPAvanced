package service;

import java.util.ArrayList;
import java.util.List;
import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;

public class ScheduleService implements IScheduleService {

    List<PracticalDay> practicalDays = new ArrayList<>();

    @Override
    public void displaySchedule() {
        if (practicalDays.isEmpty()) {
            return;
        } else {
            for (PracticalDay practicalDay : practicalDays) {
                System.out.println(practicalDay);
            }
        }
    }

    @Override
    public void addPracticeDay(PracticalDay entry) {
        practicalDays.add(entry);
        System.out.println("Added successful!");
    }

    @Override
    public void deletePracticeDay(String id) {
        boolean removed = practicalDays.removeIf(practicalDay -> practicalDay.getPracticeDayId().equals(id));
        if (removed) {
            System.out.println("Deleted successfully!");
        } else {
            System.out.println("Practice day not found.");
        }
    }

    @Override
    public void updatePracticeDay(PracticalDay practiceDay) {
        for (int i = 0; i < practicalDays.size(); i++) {
            if (practicalDays.get(i).getPracticeDayId().equals(practiceDay.getPracticeDayId())) {
                practicalDays.set(i, practiceDay);
                System.out.println("Updated successfully!");
                return;
            }
        }
        System.out.println("Practice day not found.");
    }

    @Override
    public PracticalDay searchPracticeDay(Predicate<PracticalDay> p) {
        for (PracticalDay practicalDay : practicalDays) {
            if (p.test(practicalDay)) {
                return practicalDay;
            }
        }
        System.out.println("No matching practical day found.");
        return null;
    }
}
