
package repository;

import model.main.Course;
import repository.interfaces.ICourseRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements ICourseRepository{

    // May method ni phai co data moi viet duoc

    @Override
    public List<Course> readFile() {
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<Course> courses) {
    }
}
