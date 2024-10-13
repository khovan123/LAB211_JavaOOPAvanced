
package service.interfaces;

import java.util.function.Predicate;
import model.Coach;
import model.Course;
import model.PracticalDay;
import model.User;

public interface ICoachService extends  Service<Coach>{
    @Override
    void display();

    @Override
    void add(Coach coach);
    
    @Override
    void delete(String id);
    
    @Override
    void update(Coach coach);
    
    @Override
    Coach search(Predicate<Coach> p);

    @Override
    Coach filter(String entry, String regex);
    
    void addCourse(Course course);
    
    void deleteCourse(String id);
    
    void updateCourse(Course course);
    
    Course searchCourse(Predicate<Course> p);
    
    void updateSchedule(Course course, PracticalDay practiceDay);
    
    void deleteUser(String id);
    
    User searchUser(Predicate<User> user);
    
    void updateUser(User user);
    
    void addUser(User user);
        
}
