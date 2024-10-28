package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import model.PracticalDay;
import service.interfaces.IWorkoutService;

import java.util.function.Predicate;

import model.Workout;
import repository.WorkoutRepository;

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

    public void readFromDatabase() {
        try {
            workoutList.addAll(workoutRepository.readFile());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (workoutList.isEmpty()) {
            throw new EmptyDataException("No workout found!!!");
        } else {
            for (Workout workout : workoutList) {
                System.out.println(workout);
            }
        }
    }

    @Override
    public void add(Workout workout) throws EventException {
        try {
            workoutList.add(workout);
        } catch (Exception e) {
            throw new EventException("Failed to add Workout: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            Workout workout = this.findById(id);
            workoutList.remove(workout);
        } catch (Exception e) {
            throw new EventException("An error occurred while deleting Workout with ID: " + id + ". " + e.getMessage());
        }
    }

    @Override
    public void update(Workout workout) throws EventException, NotFoundException {
        try {
            Workout existingWorkout = this.findById(workout.getWorkoutId());
            if (workout.getWorkoutName() != null) {
                existingWorkout.setWorkoutName(workout.getWorkoutName());
            }
            if (workout.getRepetition() > 0) {
                existingWorkout.setRepetition(String.valueOf(workout.getRepetition()));
            }
            if (workout.getSets() > 0) {
                existingWorkout.setSets(String.valueOf(workout.getSets()));
            }
            if (workout.getDuration() > 0) {
                existingWorkout.setDuration(String.valueOf(workout.getDuration()));
            }
            if (workout.getCourseId() != null) {
                existingWorkout.setCourseId(workout.getCourseId());
            }
        } catch (Exception e) {
            throw new EventException("An error occurred while updating Workout with ID: " + workout.getWorkoutId() + e.getMessage());
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


}
