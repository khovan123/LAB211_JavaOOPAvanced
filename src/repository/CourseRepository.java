
package repository;

import exception.EventException;
import exception.NotFoundException;
import model.main.Course;
import repository.interfaces.ICourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class CourseRepository implements ICourseRepository{

    @Override
    public void addFromDatabase() throws EventException {

    }

    @Override
    public List<Course> readFile() {
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<Course> courses) {

    }

    @Override
    public void add(Course entry) throws EventException {

    }

    @Override
    public void delete(String id) throws EventException {

    }

    @Override
    public Course search(Predicate<Course> p) throws NotFoundException {
        return null;
    }

    @Override
    public Course filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
