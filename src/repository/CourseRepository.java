
package repository;

import exception.EventException;
import exception.IOException;


import model.Course;
import repository.interfaces.ICourseRepository;

import java.util.ArrayList;
import java.util.List;


public class CourseRepository implements ICourseRepository {

    public List<Course> readFile() throws IOException {
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<Course> entry) throws IOException {

    }
}
