package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.Course;
import model.Workout;

public interface ICourseSegmentService extends Service<Course> {

    @Override
    void display() throws EmptyDataException;

    @Override
    void add(Course course) throws EventException;

    @Override
    void delete(String id) throws EventException, NotFoundException;

    @Override
    void update(Course course) throws EventException, NotFoundException;

    @Override
    Course search(Predicate<Course> p) throws NotFoundException;

    @Override
    Course filter(String entry, String regex) throws InvalidDataException;

    void addWorkout(Workout workout);

    void updateWorkout(Workout workout);

    void deleteWorkout(Workout workout);

    Workout searchWorkout(Predicate<Workout> p);

}
