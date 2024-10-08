
package service;

import java.util.function.Predicate;
import model.Course;

public interface ICourseService extends Service<Course>{
    @Override
    void display();

    @Override
    void add(Course entry);
    
    @Override
    void delete(String id);
    
    @Override
    void update(Course course);
    
    @Override
    Course search(Predicate<Course> p);

    @Override
    Course filter(String entry, String regex);
}
