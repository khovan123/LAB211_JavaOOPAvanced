package service.interfaces;

import java.util.function.Predicate;
import model.main.Course;
import model.sub.PracticeDay;
import model.main.User;

public interface IUserService extends Service<User> {

    @Override
    void display();

    @Override
    void add(User user);

    @Override
    void delete(String id);

    @Override
    void update(User user);

    @Override
    User search(Predicate<User> p);

    @Override
    User filter(String entry, String regex);
    
    void searchCourse(Predicate<Course> p);

    void buyCourse(Course course);
    
    void displaySchedule(Course course);
    
    void displayCourse(Course course);
    
    void updateSchedule(Course course, PracticeDay practiceDay);
}