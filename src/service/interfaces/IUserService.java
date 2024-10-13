package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.Course;
import model.PracticalDay;
import model.User;

public interface IUserService extends Service<User> {

    @Override
    void display() throws EmptyDataException;

    @Override
    void add(User entry) throws EventException;

    @Override
    void delete(String id) throws EventException, NotFoundException;

    @Override
    void update(User entry) throws EventException, NotFoundException;

    @Override
    User search(Predicate<User> p) throws NotFoundException;

    @Override
    User filter(String entry, String regex) throws InvalidDataException;

    void searchCourse(Predicate<Course> p);

    void buyCourse(Course course);

    void displaySchedule(Course course);

    void displayCourse(Course course);

    void updateSchedule(Course course, PracticalDay practiceDay);
}
