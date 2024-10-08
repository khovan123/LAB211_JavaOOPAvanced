
package service;

import java.util.function.Predicate;
import model.Course;

public interface ICourseService extends Service<Course>{
    void display();

    void add(Course entry);
    
    void delete(String id);
    
    Course search(Predicate<Course> p);

    Course filter(String entry, String regex);
}
