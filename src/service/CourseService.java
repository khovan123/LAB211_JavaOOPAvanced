package service;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.Course;
import model.Workout;
import repository.CourseRepository;
import service.interfaces.ICourseService;
import utils.ObjectUtils;

public class CourseService implements ICourseService {

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
            throw new EmptyDataException("-> Course List Is Empty.");
        } else {
            for (Course course : courseList) {
                course.getInfo();
            }
        }
    }

    public void readFromDatabase() {
        try {
            courseList.addAll(courseRepository.readFile());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void add(Course course) throws EventException {
        if (existsID(course)) {
            throw new EventException("-> Course With ID - " + course.getCourseId() + " - Already Exist");
        }
        try {
            courseList.add(course);
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Adding Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (findById(id) == null) {
            throw new NotFoundException("-> Course With ID - " + id + " - Not Found!");
        }
        try {
            courseList.remove(findById(id));
            System.out.println("-> Course With ID - " + id + " - Removed Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error While Deleting Course With ID - " + id + " - " + e.getMessage());
        }
    }

    @Override
    public void update(Course course) throws EventException, NotFoundException {
        Course existCourse = findById(course.getCourseId());
        if (existCourse == null) {
            throw new NotFoundException("-> Course with ID - " + course.getCourseId() + " - Not Found.");
        }
        try {
            existCourse.setCourseName(course.getCourseName());
            existCourse.setGenerateDate(String.valueOf(course.getGenerateDate()));
            existCourse.setPrice(String.valueOf(course.getPrice()));
            existCourse.setComboID(course.getComboID());
            existCourse.setCoachId(course.getCoachId());
            System.out.println("-> Update Course - " + course.getCourseId() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Course With ID - " + course.getCourseId() + " - " + e.getMessage());
        }
    }

    @Override
    public Course search(Predicate<Course> p) throws NotFoundException {
        for (Course course : courseList) {
            if (p.test(course)) {
                return course;
            }
        }
        throw new NotFoundException("-> No Course found matching the criteria.");
    }

    @Override
    public Course findById(String id) throws NotFoundException {
        return search(course -> course.getCourseId().equalsIgnoreCase(id));
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
