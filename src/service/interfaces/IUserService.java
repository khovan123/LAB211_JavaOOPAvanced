package service.interfaces;

import java.util.function.Predicate;

import model.Course;
import model.PracticalDay;
import model.User;

public interface IUserService extends Service<User> {

    void searchCourse(Predicate<Course> p);

    void buyCourse(Course course);

    void displaySchedule(Course course);

    void displayCourse(Course course);

    void updateSchedule(Course course, PracticalDay practiceDay);
}
