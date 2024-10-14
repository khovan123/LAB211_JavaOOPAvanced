
package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.Course;
import repository.interfaces.ICourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class CourseRepository implements ICourseRepository{
    private static List<Course> courses =  new ArrayList<>();

    public CourseRepository() {}

    public List<Course> getCourses() {
        return courses;
    }

    public static void setCourses(List<Course> courses) {
        CourseRepository.courses = courses;
    }

    @Override
    public void addFromDatabase() throws EventException {

    }

    public List<Course> readFile() throws IOException {
        return new ArrayList<>();
    }

    @Override
    public void delete(String id) throws EventException {
        try {
            boolean removed = courses.removeIf(course -> course.getCourseId().equals(id));
            if (removed) {
                System.out.println("-> Course with ID " + id + " has been removed.");
            } else {
                System.out.println("-> Course with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println("-> Error while deleting course ID - " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Course filter(String entry, String regex) throws InvalidDataException {
        for (Course course : courses) {
            if (course.getCourseId().matches(regex)) {
                return course;
            }
        }
        throw new InvalidDataException("-> No course found matching the criteria.");
    }

    @Override
    public Course search(Predicate<Course> p) throws NotFoundException {
        try {
            for (Course course : courses) {
                if (p.test(course)) {
                    return course;
                }
            }
            throw new NotFoundException("-> Course not found.");
        } catch (Exception e) {
            System.out.println("-> Error while searching for course: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void add(Course entry) throws EventException {
        try {
            courses.add(entry);
            System.out.println("-> Course added successfully.");
        } catch (Exception e) {
            System.out.println("-> Error while adding course: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(List<Course> entry) throws IOException {

    }
}
