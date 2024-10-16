package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.Coach;
import model.Course;
import model.PracticalDay;
import model.User;

public interface ICoachService extends Service<Coach> {

    @Override
    void display() throws EmptyDataException;

    @Override
    void add(Coach entry) throws EventException;

    @Override
    void delete(String id) throws EventException, NotFoundException;

    @Override
    void update(Coach entry) throws EventException, NotFoundException;

    @Override
    Coach search(Predicate<Coach> p) throws NotFoundException;

   

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
