
package service.interfaces;

import java.util.function.Predicate;
import model.Course;
import model.Workout;

public interface ICourseSegmentService extends Service<Course>{
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
    
    void addWorkout(Workout workout);
    
    void updateWorkout(Workout workout);
    
    void deleteWorkout(Workout workout);
    
    Workout searchWorkout(Predicate<Workout> p);

}
