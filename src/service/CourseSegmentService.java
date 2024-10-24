package service;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.CourseSegment;
import model.Workout;
import repository.CourseSegmentRepository;
import service.interfaces.ICourseSegmentService;

public class CourseSegmentService implements ICourseSegmentService {

    private final CourseSegmentRepository courseSegmentRepository = new CourseSegmentRepository();
    private final List<CourseSegment> courseSegmentList;

    public CourseSegmentService() throws IOException {
        courseSegmentList = new ArrayList<>();
        readFromDatabase();
    }

    public CourseSegmentService(List<CourseSegment> courseSegmentList) throws IOException {
        this.courseSegmentList = courseSegmentList;
        readFromDatabase();
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseSegmentList.isEmpty()) {
            throw new EmptyDataException("-> Course Segment List Is Empty. ");
        } else {
            for (CourseSegment courseSegment : courseSegmentList) {
                System.out.println(courseSegment);
            }
        }
    }

    public void readFromDatabase() throws IOException {
        try {
            for (CourseSegment courseSegment : courseSegmentRepository.readFile()) {
                courseSegmentList.add(courseSegment);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void add(CourseSegment courseSegment) throws EventException {
        try {
            courseSegmentList.add(courseSegment);
            System.out.println("-> Course Segment Add Successfully!");
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Add Course Segment - " + e.getMessage());
        }
    }


    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            courseSegmentList.remove(this.search(courseSegment -> courseSegment.getCourseId().equalsIgnoreCase(id)));
            System.out.println("-> Course Segment With ID - " + id + " - Remove Successfully");
        } catch (Exception e) {
            throw new EventException("-> Course Segment with ID " + id + " not found.");
        }
    }

    @Override
    public void update(CourseSegment courseSegment) throws EventException, NotFoundException {
        try {
            courseSegmentList.remove(this.search(courseSegment1 -> courseSegment1.getCourseId().equalsIgnoreCase(courseSegment.getCourseId())));
            courseSegmentList.add(courseSegment);
            System.out.println("-> Update Course Segment - " + courseSegment.getCourseId() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Updating - " + courseSegment.getCourseId() + " - " + e.getMessage());
        }
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
        } catch (NotFoundException e) {
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
