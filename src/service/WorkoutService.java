
package service;

import exception.EmptyDataException;
import exception.NotFoundException;
import service.interfaces.IWorkoutService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.Workout;

public class WorkoutService implements IWorkoutService {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_fg_GREEN = "\u001B[32m";

    private List<Workout> workoutList;

    public WorkoutService() {
        this.workoutList = new ArrayList<>();
    }

    @Override
    public void display() throws EmptyDataException {
        if (workoutList.isEmpty())  {
            throw new EmptyDataException(ANSI_RED + "No workout found!!!" + ANSI_RESET);
        } else {
            for (Workout workout : workoutList){
                System.out.println(workout);
            }
        }
    }

    @Override
    public void add(Workout workout) {
        try {
            workoutList.add(workout);
        } catch (Exception e){
            System.out.println(ANSI_RED + e.getMessage() +ANSI_RESET);
        }
    }

    @Override
    public void delete(String id) {
        try{
            workoutList.remove(this.search(p -> p.getWorkoutId().equalsIgnoreCase(id)));
        } catch (NotFoundException e){
            System.out.println(ANSI_RED + e.getMessage() + " with id: " + id + ANSI_RESET);
        }
    }

    @Override
    public void update(Workout entry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Workout search(Predicate<Workout> p) throws NotFoundException {
        for(Workout workout : workoutList){
            if(p.test(workout)){
                return workout;
            }
        }
        throw new NotFoundException(ANSI_RED + "Not found any workout!!!" +ANSI_RESET);
    }

    @Override
    public Workout filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
