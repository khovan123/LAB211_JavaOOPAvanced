package service;

import exception.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import service.interfaces.IWorkoutService;
import java.util.function.Predicate;
import model.Workout;
import repository.WorkoutRepository;
import utils.FieldUtils;

public class WorkoutService implements IWorkoutService {

    private final WorkoutRepository workoutRepository = new WorkoutRepository();
    private List<Workout> workoutList = new ArrayList<>();

    public WorkoutService() {
        this.readFromDatabase();
    }

    public WorkoutService(List<Workout> workouts) {
        this.workoutList = workouts;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void readFromDatabase() {
        try {
            List<Workout> workoutFormDB = workoutRepository.readData();
            for (Workout workout : workoutFormDB) {
                if (!existID(workout.getWorkoutId())) {
                    try {
                        this.workoutList.add(workout);
                    } catch (Exception e) {
                    }
                }
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (workoutList.isEmpty()) {
            throw new EmptyDataException("No workout found!!!");
        }
        System.out.println("WorkoutID\tWorkoutName\tRepetition\tSets\tDuration\tCourseId");
        for (Workout workout : workoutList) {
            System.out.println(workout.getInfo());
        }
    }

    @Override
    public void add(Workout workout) throws EventException {
        try {
            if (!existID(workout.getWorkoutId())) {
                workoutList.add(workout);
                workoutRepository.insertToDB(workout);
            } else {
                throw new EventException("Workout with id: " + workout.getWorkoutId() + " already exist.");
            }
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            workoutList.remove(findById(id));
            workoutRepository.deleteToDB(id);
        } catch (NotFoundException | SQLException e) {
            throw new EventException("An error occurred while deleting Workout with ID: " + id + ". ");
        }
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws NotFoundException, EventException {
        Workout workout = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(workout.getClass(), fieldName);
            try {
                field.setAccessible(true);
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
    public Workout findById(int id) throws NotFoundException {
        try {
            return this.search(p -> p.getWorkoutId() == (id));
        } catch (NotFoundException e) {
            throw new NotFoundException("Workout with ID: " + id + " not found.");
        }
    }

    public boolean existID(int workoutID) {
        try {
            return findById(workoutID) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        switch (fieldName) {
            case "workoutId" -> {
                return "WorkoutID";
            }
            case "workoutName" -> {
                return "WorkoutName";
            }
            case "repetition" -> {
                return "Repetition";
            }
            case "sets" -> {
                return "Sets";
            }
            case "duration" -> {
                return "Duration";
            }
            case "courseId" -> {
                return "CourseID";
            }
            default ->
                throw new NotFoundException("Not found any field for: " + fieldName);
        }
    }

    public List<Workout> searchWorkoutByCourse(int courseID) throws EmptyDataException {
        List<Workout> list = new ArrayList<>();
        for (Workout workout : this.workoutList) {
            if (workout.getCourseId() == (courseID)) {
                list.add(workout);
            }
        }
        if (list.isEmpty()) {
            throw new EmptyDataException("Course with ID: " + courseID + " had not any workout");
        }
        return list;
    }

}
