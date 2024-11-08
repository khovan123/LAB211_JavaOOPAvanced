package service;

import exception.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import model.RegistedWorkout;
import model.Workout;
import repository.RegistedWorkoutRepository;
import service.interfaces.IRegistedWorkoutService;
import utils.FieldUtils;

public class RegistedWorkoutService implements IRegistedWorkoutService {

    private RegistedWorkoutRepository registedWorkoutRepository = new RegistedWorkoutRepository();
    private List<RegistedWorkout> workouts = new ArrayList<>();
    private WorkoutService workoutService;

    public RegistedWorkoutService(WorkoutService workoutService) {
        this.workoutService = workoutService;
        this.readFromDatabase();
    }

    public void readFromDatabase() {
        try {
            for (RegistedWorkout registedWorkout : registedWorkoutRepository.readData()) {
                try {
                    if (!existed(registedWorkout.getRegistedWorkoutID())) {
                        registedWorkout.setWorkout(new Workout());
                        setWorkout(registedWorkout);
                        this.workouts.add(registedWorkout);
                    }
                } catch (NotFoundException e) {

                }
            }
        } catch (SQLException e) {

        }
    }

    public void setWorkout(RegistedWorkout registedWorkout) throws NotFoundException {
        registedWorkout.setWorkout(workoutService.findById(registedWorkout.getWorkoutID()));
    }

    @Override
    public void display() throws EmptyDataException {
        if (workouts.isEmpty()) {
            throw new EmptyDataException("List of registered workout is empty");
        } else {
            for (RegistedWorkout registedWorkout : workouts) {
                System.out.println(registedWorkout.getWorkout().getInfo());
            }
        }
    }

    @Override
    public void add(RegistedWorkout entry) throws EventException, InvalidDataException {
        if (!this.existed(entry.getRegistedWorkoutID())) {
            try {
                this.workouts.add(entry);
                this.setWorkout(entry);
                this.registedWorkoutRepository.insertToDB(entry);
            } catch (NotFoundException | SQLException e) {
                throw new InvalidDataException(e);
            }
        } else {
            throw new InvalidDataException("Registered workout with ID: " + entry.getRegistedWorkoutID() + " was existed");
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            workouts.remove(this.findById(id));
            registedWorkoutRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }

    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        RegistedWorkout registedWorkout = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(registedWorkout.getClass(), fieldName);
            try {
                field.setAccessible(true);
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                registedWorkoutRepository.updateToDB(id, updatedMap);
                field.set(registedWorkout, entry.get(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("registedWorkoutID")) {
            return RegistedWorkoutRepository.RegistedWorkoutID_Column;
        }
        if (fieldName.equalsIgnoreCase("workoutID")) {
            return RegistedWorkoutRepository.WorkoutID_Column;
        }
        if (fieldName.equalsIgnoreCase("practicalDayID")) {
            return RegistedWorkoutRepository.PracticalDayID_Column;
        }

        throw new NotFoundException("Not found any field");
    }

    @Override
    public RegistedWorkout search(Predicate<RegistedWorkout> p) throws NotFoundException {
        for (RegistedWorkout registedWorkout : this.workouts) {
            if (p.test(registedWorkout)) {
                return registedWorkout;
            }
        }
        throw new NotFoundException("Not found any registered workout");
    }

    @Override
    public RegistedWorkout findById(int id) throws NotFoundException {
        return this.search(p -> p.getRegistedWorkoutID() == (id));
    }

    public boolean existed(int id) {
        try {
            return this.findById(id) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public List<RegistedWorkout> searchWorkoutByPracticalDay(int practicalDayID) throws EmptyDataException {
        List<RegistedWorkout> list = new ArrayList<>();
        for (RegistedWorkout registedWorkout : this.workouts) {
            if (Integer.compare(registedWorkout.getPracticalDayID(), practicalDayID) == 0) {
                list.add(registedWorkout);
            }
        }
        if (list.isEmpty()) {
            throw new EmptyDataException("Practical Day with ID: " + practicalDayID + " had not any workout");
        }
        return list;
    }

}
