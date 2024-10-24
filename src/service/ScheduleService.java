package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Schedule;
import model.Workout;
import repository.ScheduleRepository;

public class ScheduleService implements IScheduleService {

    ScheduleRepository scheduleRepository = new ScheduleRepository();
    List<Schedule> scheduleList = new ArrayList<>();

    public ScheduleService() {
        try {
            scheduleList = scheduleRepository.readFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

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
        if (!existID(schedule)) {
            scheduleList.add(schedule);
        }else{
            throw new EventException("ID: "+schedule.getScheduleId() + " existed.");
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        Schedule deleteSchedule = findById(id);
        if (deleteSchedule == null) {
            throw new NotFoundException("Can not found schedule.");
        }
        scheduleList.remove(deleteSchedule);
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
        throw new NotFoundException("Can not found user progress.");
    }

    public boolean existID(Schedule schedule){
        try {
            if (findById(schedule.getScheduleId()) == null) {
                return true;
            }
        } catch (NotFoundException ex) {
            Logger.getLogger(ScheduleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void addWorkoutToSchedule(Date date, Workout workout) {

    }

    public void removeWorkoutFromSchedule(Date date, Workout workout) {

    }

    public void viewSchedule() {

    }
}
