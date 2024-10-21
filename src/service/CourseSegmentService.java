package service;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.CourseSegment;
import model.Workout;
import repository.CourseSegmentRepository;
import service.interfaces.ICourseSegmentService;

public class CourseSegmentService implements ICourseSegmentService {

    private static CourseSegmentRepository courseSegmentRepository;
    private static List<CourseSegment> courseSegmentList = new ArrayList<>();

    public CourseSegmentService() throws IOException {
        courseSegmentRepository = new CourseSegmentRepository();
    }

    @Override
    public void display() {
        try {
            if (!courseSegmentList.isEmpty()) {
                for (CourseSegment courseSegment : courseSegmentList) {
                    System.out.println(courseSegment.getInfo());
                }
            } else {
                System.out.println("-> List Is Empty.");
            }
        } catch (Exception e) {
            throw new RuntimeException("-> Error Occurred While Displaying Course Segment: " + e.getMessage());
        }
    }

    public void addCourseSegmentFromRepository() throws IOException {
        try {
            for (CourseSegment courseSegment : courseSegmentRepository.readFile()) {
                add(courseSegment);
            }
        } catch (IOException e) {
            throw new IOException("-> Error While Reading File: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("-> Error While Adding Course Segment From Repository: " + e.getMessage());
        }
    }

    @Override
    public void add(CourseSegment courseSegment) throws InvalidDataException {
        for (CourseSegment courseSegment1 : courseSegmentList) {
            if (courseSegment1.getCourseId().equalsIgnoreCase(courseSegment.getCourseId())) {
                throw new InvalidDataException(courseSegment.getCourseId() + " already exists!");
            }
        }
        courseSegmentList.add(courseSegment);
    }


    @Override
    public void delete(String id) throws EventException {
        try {
            boolean removed = courseSegmentList.removeIf(courseSegment -> courseSegment.getCourseId().equalsIgnoreCase(id));
            if (!removed) {
                throw new NotFoundException("-> Course Segment with ID " + id + " not found.");
            }
            System.out.println("-> Course Segment with ID " + id + " has been removed.");
        } catch (Exception e) {
            throw new EventException("-> An error occurred while deleting the Course Segment: " + e.getMessage());
        }
    }

    @Override
    public void update(CourseSegment courseSegment) throws EventException, NotFoundException {
    }

    @Override
    public CourseSegment search(Predicate<CourseSegment> p) throws NotFoundException {
        for (CourseSegment courseSegment : courseSegmentList) {
            if (p.test(courseSegment)) {
                return courseSegment;
            }
        }
        throw new NotFoundException("-> No Course Segment found matching the criteria.");
    }

    @Override
    public CourseSegment findById(String id) throws NotFoundException {
        try {
            return search(courseSegment -> courseSegment.getCourseId().equalsIgnoreCase(id));
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void addWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Workout searchWorkout(Predicate<Workout> p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
