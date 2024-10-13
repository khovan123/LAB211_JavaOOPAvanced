
package repository;

import model.main.Course;
import repository.interfaces.ICourseRepository;

import java.util.ArrayList;
import java.util.List;


public class CourseRepository implements ICourseRepository{

    @Override
    public List<Course> readFile() {
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<Course> courses) {

    }

    @Override
    public Course filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
