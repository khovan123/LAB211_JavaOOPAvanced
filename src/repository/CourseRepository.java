
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
    public void writeFile(List<Course> entry) throws IOException {

    }
}
