package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.Course;
import model.CourseSegment;
import model.Workout;

public interface ICourseSegmentService extends Service<CourseSegment> {

    @Override
    void display() throws EmptyDataException;

    @Override
    void add(CourseSegment entry) throws EventException;

    @Override
    void delete(String id) throws EventException, NotFoundException;

    @Override
    void update(CourseSegment entry) throws EventException, NotFoundException;

    @Override
    CourseSegment search(Predicate<CourseSegment> p) throws NotFoundException;

    @Override
    CourseSegment filter(String entry, String regex) throws InvalidDataException;

    void addWorkout(Workout workout);

    void updateWorkout(Workout workout);

    void deleteWorkout(Workout workout);

    Workout searchWorkout(Predicate<Workout> p);

}
