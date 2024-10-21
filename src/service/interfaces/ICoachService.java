package service.interfaces;

import exception.EventException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.Coach;
import model.Course;
import model.PracticalDay;
import model.User;

public interface ICoachService extends Service<Coach> {

    void addCourse(Course course) throws EventException;

    void deleteCourse(String id) throws EventException;

    void updateCourse(Course course);

    Course searchCourse(Predicate<Course> p) throws NotFoundException;

    void updateSchedule(Course course, PracticalDay practiceDay);

    void deleteUser(String id) throws EventException;

    User searchUser(Predicate<User> user) throws NotFoundException;

    void updateUser(User user);

    void addUser(User user) throws EventException;

}
