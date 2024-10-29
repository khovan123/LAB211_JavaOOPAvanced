package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.PracticalDay;
import repository.PracticalDayRepository;
import service.interfaces.IWorkoutService;

import java.util.Map;
import java.util.function.Predicate;

import model.Workout;
import repository.WorkoutRepository;
import utils.FieldUtils;
import utils.GlobalUtils;
import utils.ObjectUtils;

public class WorkoutService implements IWorkoutService {

    private final WorkoutRepository workoutRepository = new WorkoutRepository();
    private final List<Workout> workoutList;

    public WorkoutService() {
        workoutList = new ArrayList<>();
        readFromDatabase();
    }

    public WorkoutService(List<Workout> workouts) {
        this.workoutList = workouts;
        readFromDatabase();
    }

    public List<Workout> getWorkoutList(){
        return workoutList;
    }

    public void readFromDatabase() {
        try {
            workoutList.addAll(workoutRepository.readData());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (workoutList.isEmpty()) {
            throw new EmptyDataException("No workout found!!!");
        } else {
            for (Workout workout : workoutList) {
                System.out.println(workout.getInfo());
            }
        }
    }

    @Override
    public void add(Workout workout) throws EventException {
        try {
            if(!existID(workout)){

            workoutList.add(workout);
            workoutRepository.insertToDB(workout);
            } else {
                throw new EventException(workout.getWorkoutId() + " already exist.");
            }
        } catch (Exception e) {
            throw new EventException("Failed to add Workout: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            Workout workout = this.findById(id);
            workoutList.remove(workout);
            workoutRepository.deleteToDB(id);
        } catch (Exception e) {
            throw new EventException("An error occurred while deleting Workout with ID: " + id + ". " + e.getMessage());
        }
    }

    public void update(String id, Map<String, Object> entry) throws NotFoundException, EventException {
        Workout workout = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(workout.getClass(), fieldName);
            try {
                field.set(workout, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                workoutRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    @Override
    public Workout search(Predicate<Workout> p) throws NotFoundException {
        for (Workout workout : workoutList) {
            if (p.test(workout)) {
                return workout;
            }
        }
        throw new NotFoundException("Not found any workout!!!");
    }

    @Override
    public Workout findById(String id) throws NotFoundException {
        try {
            return this.search(p -> p.getWorkoutId().equalsIgnoreCase(id));
        } catch (NotFoundException e) {
            throw new NotFoundException("Workout with ID: " + id + " not found.");
        }
    }

    public boolean existID(Workout workout) {
        try {
            findById(workout.getWorkoutId());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        switch (fieldName) {
            case "workoutId":
                return "WorkoutID";
            case "workoutName":
                return "WorkoutName";
            case "repetition":
                return "Repetition";
            case "sets":
                return "Sets";
            case "duration":
                return "Duration";
            case "courseId":
                return "CourseID";
            default:
                throw new NotFoundException("Not found any field for: " + fieldName);
        }
    }
}
