package service;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import exception.*;
import model.PracticalDay;
import model.RegistedWorkout;
import repository.PracticalDayRepository;
import service.interfaces.IPracticalDayService;
import utils.FieldUtils;
import utils.GlobalUtils;

public class PracticalDayService implements IPracticalDayService {

    private PracticalDayRepository practicalDayRepository = new PracticalDayRepository();
    private TreeSet<PracticalDay> practicalDayTreeSet = new TreeSet<>();
    private RegistedWorkoutService registedWorkoutService;
    private NutritionService nutritionService;

    public PracticalDayService(RegistedWorkoutService registedWorkoutService, NutritionService nutritionService) {
        this.registedWorkoutService = registedWorkoutService;
        this.nutritionService = nutritionService;
        this.readFromDatabase();
    }

    private void readFromDatabase() {
        try {
            for (PracticalDay practicalDay : practicalDayRepository.readData()) {
                try {
                    if (!existed(practicalDay.getPracticalDayId())) {
                        practicalDayTreeSet.add(practicalDay);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public String practicalDayToString(int practicalDayID) throws NotFoundException, EmptyDataException {
        StringBuilder str = new StringBuilder();
        str.append(practicalDayID).append(" - ")
                .append(GlobalUtils.dateFormat(this.findById(practicalDayID).getPracticeDate())).append(": ")
                .append(this.nutritionService.search(p -> Integer.compare(p.getPracticalDayId(), practicalDayID) == 0).getInfo())
                .append(this.findById(practicalDayID).isDone()?" Done":" Not yet")
                .append("\n");
        for (RegistedWorkout registedWorkout : registedWorkoutService.searchWorkoutByPracticalDay(practicalDayID)) {
            str.append(registedWorkout.getInfo()).append("\n");
        }
        return str.toString();
    }

    @Override
    public void display() throws EmptyDataException {
        if (practicalDayTreeSet.isEmpty()) {
            throw new EmptyDataException("No practice day found!!!");
        }
        System.out.println("PracticalID\tPracticeDate\tScheduleID");
        for (PracticalDay practicalDay : practicalDayTreeSet) {
            try {
                System.out.println(practicalDayToString(practicalDay.getPracticalDayId()));
            } catch (EmptyDataException | NotFoundException e) {

            }
        }
    }

    @Override
    public void add(PracticalDay practiceDay) throws EventException {
        try {
            if (!existed(practiceDay.getPracticalDayId())) {
                practicalDayTreeSet.add(practiceDay);
                practicalDayRepository.insertToDB(practiceDay);
            } else {
                throw new EventException("PracticalDay with id: " + practiceDay.getPracticalDayId() + " already exist.");
            }
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            practicalDayTreeSet.remove(findById(id));
            practicalDayRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws NotFoundException, EventException {
        PracticalDay practicalDay = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(practicalDay.getClass(), fieldName);
            try {
                field.setAccessible(true);
                field.set(practicalDay, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                practicalDayRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    @Override
    public PracticalDay search(Predicate<PracticalDay> p) throws NotFoundException {
        for (PracticalDay practicalDay : practicalDayTreeSet) {
            if (p.test(practicalDay)) {
                return practicalDay;
            }
        }
        throw new NotFoundException("Not found any practice day!!!");
    }

    @Override
    public PracticalDay findById(int id) throws NotFoundException {
        try {
            return this.search(p -> p.getPracticalDayId() == (id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    public boolean existed(int practicalDayID) {
        try {
            return this.findById(practicalDayID) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("practiceDate")) {
            return PracticalDayRepository.PracticeDate_Column;
        } else if (fieldName.equalsIgnoreCase("scheduleId")) {
            return PracticalDayRepository.ScheduleID_Column;
        } else if (fieldName.equalsIgnoreCase("practicalDayId")) {
            return PracticalDayRepository.PracticalDayID_Column;
        } else if (fieldName.equalsIgnoreCase("done")) {
            return PracticalDayRepository.PracticalDay_Status;
        }

        throw new NotFoundException("Not found any field for: " + fieldName);
    }

    public TreeSet<PracticalDay> searchPracticalDayByScheduleID(int scheduleID) throws EmptyDataException {
        TreeSet<PracticalDay> treeSet = new TreeSet<>();
        for (PracticalDay practicalDay : this.practicalDayTreeSet) {
            if (practicalDay.getScheduleId() == (scheduleID)) {
                treeSet.add(practicalDay);
            }
        }
        if (treeSet.isEmpty()) {
            throw new EmptyDataException("Schedule with ID: " + scheduleID + " had not any practical day");
        }
        return treeSet;
    }

}
