package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import model.Schedule;
import model.Workout;
import repository.ScheduleRepository;
import utils.FieldUtils;

public class ScheduleService implements IScheduleService {

    ScheduleRepository scheduleRepository = new ScheduleRepository();
    List<Schedule> scheduleList = new ArrayList<>();
    private final TreeSet<Schedule> scheduleTreeSet;

    public ScheduleService() {
        scheduleTreeSet = new TreeSet<>();
        scheduleList = scheduleRepository.readData();
    }

    public ScheduleService(TreeSet<Schedule> scheduleTreeSet) {
        this.scheduleTreeSet = scheduleTreeSet;
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

    @Override
    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {
        for (String fieldName : entry.keySet()) {
            Schedule schedule = findById(id);
            Field field = FieldUtils.getFieldByName(schedule.getClass(), fieldName);
            try {
                field.set(schedule, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                scheduleRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        switch (fieldName) {
            case "scheduleId":
                return ScheduleRepository.ScheduleID_Column;
            case "userProgressId":
                return ScheduleRepository.UserProgressID_Column;
            default:
                throw new NotFoundException("Not found any field");
        }
    }
}
