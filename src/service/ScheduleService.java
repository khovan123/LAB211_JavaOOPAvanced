package service;

import java.util.ArrayList;
import java.util.List;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;
import model.PracticalDay;

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
        practicalDays.removeIf(practicalDay -> practicalDay.getPracticeDayId().equals(id));
        System.out.println("Deleted successfully!");
    }

    @Override
    public void updatePracticeDay(PracticalDay practiceDay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PracticalDay searchPracticeDay(Predicate<PracticalDay> p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
