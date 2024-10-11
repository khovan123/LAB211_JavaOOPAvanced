
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
}
