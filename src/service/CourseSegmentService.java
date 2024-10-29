package service;

import exception.*;

import java.util.Map;
import java.util.function.Predicate;

import model.CourseSegment;
import model.Workout;
import repository.CourseSegmentRepository;
import service.interfaces.ICourseSegmentService;

public class CourseSegmentService implements ICourseSegmentService {

    private static CourseSegmentRepository courseSegmentRepository;


    @Override
    public void addWorkout(Workout workout) {

    }

    @Override
    public void updateWorkout(Workout workout) {

    }

    @Override
    public void deleteWorkout(Workout workout) {

    }

    @Override
    public Workout searchWorkout(Predicate<Workout> p) {
        return null;
    }

    @Override
    public void display() throws EmptyDataException {

    }

    @Override
    public void add(CourseSegment entry) throws EventException, InvalidDataException {

    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {

    }

    @Override
    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {

    }

    @Override
    public CourseSegment search(Predicate<CourseSegment> p) throws NotFoundException {
        return null;
    }

    @Override
    public CourseSegment findById(String id) throws NotFoundException {
        return null;
    }
}
