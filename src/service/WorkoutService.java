package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.NotFoundException;
import service.interfaces.IWorkoutService;
import java.util.function.Predicate;

import model.Workout;
import repository.WorkoutRepository;

public class WorkoutService implements IWorkoutService {

    private static WorkoutRepository workoutRepository = new WorkoutRepository();

    public WorkoutService() {
    }

    @Override
    public void display() throws EmptyDataException {
        for (Workout workout : workoutRepository.getWorkouts()) {
            System.out.println(workout.getInfo());
        }
    }

    @Override
    public void add(Workout workout) throws EventException {
        workoutRepository.add(workout);
    }

    @Override
    public void delete(String id) throws EventException {
        workoutRepository.delete(id);
    }

    @Override
    public void update(Workout entry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Workout search(Predicate<Workout> p) throws NotFoundException {
       return workoutRepository.search(p);
    }

    @Override
    public Workout filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
