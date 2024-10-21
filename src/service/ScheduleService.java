package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Schedule;
import repository.ScheduleRepository;

public class ScheduleService implements IScheduleService {

    ScheduleRepository scheduleRepository = new ScheduleRepository();
    List<Schedule> scheduleList = new ArrayList<>();

    @Override
    public void display() throws EmptyDataException {
        if (scheduleList.isEmpty()) {
            throw new EmptyDataException("Schedule is empty.");
        } else {
            for (Schedule s : scheduleList) {
                System.out.println(s);
            }
        }
    }

    @Override
    public void add(Schedule schedule) throws EventException {
        if (schedule == null) {
            throw new EventException("Error schedule.");
        }
        scheduleList.add(schedule);
        System.out.println("Added successfully.");
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        Schedule deleteSchedule = findById(id);
        if(deleteSchedule==null){
            throw new NotFoundException("Can not found schedule.");
        }
        scheduleList.remove(deleteSchedule);
        System.out.println("Schedule deleted successfully.");
    }

    @Override
    public void update(Schedule schedule) throws EventException, NotFoundException {
        boolean found = false;

        for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleList.get(i).getScheduleId().equals(schedule.getScheduleId())) {
                scheduleList.set(i, schedule);
                try {
                    scheduleRepository.writeFile(scheduleList);
                } catch (IOException ex) {
                    Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Customer updated successfully!!!");
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NotFoundException("Customer not found with ID: " + schedule.getScheduleId());
        }
    }

    @Override
    public Schedule search(Predicate<Schedule> p) throws NotFoundException {
        for (Schedule schedule : scheduleList) {
            if (p.test(schedule)) {
                return schedule;
            }
        }
        throw new NotFoundException("Can not found schedule.");
    }

    @Override
    public Schedule findById(String id) throws NotFoundException {
        for (Schedule s : scheduleList) {
            if (s.getScheduleId().equals(id)) {
                return s;
            }
        }
        return null;
    }

}
