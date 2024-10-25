package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
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
    private final TreeSet<Schedule> scheduleTreeSet;

    public ScheduleService() {
        scheduleTreeSet = new TreeSet<>();
        readFromDatabase();
    }

    public ScheduleService(TreeSet<Schedule> scheduleTreeSet) {
        this.scheduleTreeSet = scheduleTreeSet;
        readFromDatabase();
    }

    public void readFromDatabase() {
        try {
            scheduleTreeSet.addAll(scheduleRepository.readFile());
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
        } else {
            throw new EventException("ID: " + schedule.getScheduleId() + " existed.");
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        scheduleList.remove(findById(id));
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
                    System.out.println(ex.getMessage());
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
        throw new NotFoundException("Can not found any schedule.");
    }

    @Override
    public Schedule findById(String id) throws NotFoundException {
        try{
            return this.search(p -> p.getScheduleId().equals(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    public boolean existID(Schedule schedule) {
        try {
            if (findById(schedule.getScheduleId()) == null) {
                return true;
            }
        } catch (NotFoundException ex) {
            System.out.println("Can not find " + schedule);
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
