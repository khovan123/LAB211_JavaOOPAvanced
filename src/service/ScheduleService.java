package service;

import exception.*;
import java.lang.reflect.Field;
import java.util.*;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;
import java.sql.SQLException;
import model.PracticalDay;
import model.Schedule;
import repository.ScheduleRepository;
import utils.FieldUtils;

public class ScheduleService implements IScheduleService {

    private ScheduleRepository scheduleRepository = new ScheduleRepository();
    private List<Schedule> scheduleList = new ArrayList<>();
    private PracticalDayService practicalDayService;

    public ScheduleService(PracticalDayService practicalDayService) {
        this.practicalDayService = practicalDayService;
        this.readFromDatabase();
    }

    private void readFromDatabase() {
        try {
            for (Schedule schedule : scheduleRepository.readData()) {
                try {
                    if (!existID(schedule.getScheduleId())) {
                        this.scheduleList.add(schedule);
                    }
                } catch (Exception e) {

                }
            }
        } catch (SQLException e) {

        }
    }

    public String scheduleToString(int scheduleID) throws EmptyDataException {
        StringBuilder str = new StringBuilder();
        for (PracticalDay practicalDay : practicalDayService.searchPracticalDayByScheduleID(scheduleID)) {
            try {
                str.append(practicalDayService.practicalDayToString(practicalDay.getPracticalDayId())).append("\n");
            } catch (EmptyDataException | NotFoundException e) {

            }
        }
        return str.toString();
    }

    public double getCompletedUserProgress(int scheduleID) throws EmptyDataException {
        TreeSet<PracticalDay> practicalDays = practicalDayService.searchPracticalDayByScheduleID(scheduleID);
        int total = practicalDays.size();
        int count = 0;
        for (PracticalDay practicalDay : practicalDays) {
            if (practicalDay.isDone()) {
                count++;
            }
        }
        return (double) count / (double) total;

    }

    @Override
    public void display() throws EmptyDataException {
        if (scheduleList.isEmpty()) {
            throw new EmptyDataException("Schedule is empty.");
        } else {
            for (Schedule s : scheduleList) {
                System.out.println("haha");
                System.out.print(scheduleToString(s.getScheduleId()));
            }
        }
    }

    @Override
    public void add(Schedule schedule) throws EventException {
        if (!existID(schedule.getScheduleId())) {
            try {
                scheduleList.add(schedule);
                scheduleRepository.insertToDB(schedule);
            } catch (SQLException e) {
                throw new EventException(e);
            }
        } else {
            throw new EventException("ID: " + schedule.getScheduleId() + " existed.");
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            scheduleList.remove(findById(id));
            scheduleRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
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
    public Schedule findById(int id) throws NotFoundException {
        try {
            return this.search(p -> p.getScheduleId() == (id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    public boolean existID(int scheduleID) {
        try {
            return findById(scheduleID) != null;
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        Schedule schedule = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(schedule.getClass(), fieldName);
            try {
                field.setAccessible(true);
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
            case "scheduleId" -> {
                return ScheduleRepository.ScheduleID_Column;
            }
            case "userProgressId" -> {
                return ScheduleRepository.UserProgressID_Column;
            }
            default ->
                throw new NotFoundException("Not found any field");
        }
    }

}
