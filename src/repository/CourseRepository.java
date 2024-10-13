
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


    @Override
    public void addFromDatabase() throws EventException {

    }

    @Override
    public List<Course> readFile() throws IOException {
        return new ArrayList<>();
    }

    @Override
    public void delete(String id) throws EventException {

    }

    @Override
    public Course filter(String entry, String regex) throws InvalidDataException {
        return null;
    }

    @Override
    public Course search(Predicate<Course> p) throws NotFoundException {
        return null;
    }

    @Override
    public void add(Course entry) throws EventException {

    }

    @Override
    public void writeFile(List<Course> entry) throws IOException {

    }
}
