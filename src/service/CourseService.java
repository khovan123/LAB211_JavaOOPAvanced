package service;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.Course;
import model.Workout;
import repository.CourseRepository;
import service.interfaces.ICourseSegmentService;
import utils.ObjectUtils;

public class CourseService implements ICourseSegmentService {

    private final CourseRepository courseRepository = new CourseRepository();
    private final List<Course> courseList;

    public CourseService() {
        courseList = new ArrayList<>();
        readFromDatabase();
    }

    public CourseService(List<Course> courseList) {
        this.courseList = courseList;
        readFromDatabase();
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseList.isEmpty()) {
            throw new EmptyDataException("-> Course Segment List Is Empty. ");
        } else {
            for (Course course : courseList) {
                course.getInfo();
            }
        }
    }

    public void readFromDatabase() {
        try {
            courseList.add((Course) courseRepository.readFile());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void add(Course course) throws EventException {
        if (ObjectUtils.validID(course.getCourseId())){
            throw new EventException("-> Invalid Course ID - " + course.getCourseId() + " - Must Be CS-yyyy");
        }
        if (existsID(course)) {
            throw new EventException("-> Course Segment With ID - " + course.getCourseId() + " - Already Exist");
        }
        try {
            courseList.add(course);
            System.out.println("-> Course Segment Add Successfully!");
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Add Course Segment - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        Course segmentToRemove = findById(id);
        if (segmentToRemove != null) {
            courseList.remove(segmentToRemove);
            System.out.println("-> Course Segment With ID - " + id + " - Remove Successfully");
        } else {
            throw new EventException("-> Course Segment with ID " + id + " not found.");
        }
    }

    @Override
    public void update(Course course) throws EventException, NotFoundException {
        Course existCourse = findById(course.getCourseId());
        if (existCourse == null) {
            throw new NotFoundException("-> Course Segment With ID - " + course.getCourseId() + " - Not Found.");
        }
        try {
            courseList.remove(existCourse);
            courseList.add(course);
            System.out.println("-> Update Course Segment - " + course.getCourseId() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Updating - " + course.getCourseId());
        }
    }

    @Override
    public Course search(Predicate<Course> p) throws NotFoundException {
        for (Course course : courseList) {
            if (p.test(course)) {
                return course;
            }
        }
        throw new NotFoundException("-> No Course Segment found matching the criteria.");
    }

    @Override
    public Course findById(String id) throws NotFoundException {
        for (Course course : courseList) {
            if (course.getCourseId().equalsIgnoreCase(id)) {
                return course;
            }
        }
        throw new NotFoundException("-> Course Segment With ID - " + id + " - Not Found.");
    }

    public boolean existsID(Course course) {
        try {
            return findById(course.getCourseId()) != null;
        } catch (NotFoundException e) {
            return false;
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
