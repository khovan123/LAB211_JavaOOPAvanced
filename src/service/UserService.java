package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import service.interfaces.IUserService;

import java.util.Map;
import java.util.function.Predicate;
import model.Course;
import model.PracticalDay;
import model.User;

public class UserService implements IUserService {

    public UserService() {
    }

    @Override
    public void searchCourse(Predicate<Course> p) {

    }

    @Override
    public void buyCourse(Course course) {

    }

    @Override
    public void displaySchedule(Course course) {

    }

    @Override
    public void displayCourse(Course course) {

    }

    @Override
    public void updateSchedule(Course course, PracticalDay practiceDay) {

    }

    @Override
    public void display() throws EmptyDataException {

    }

    @Override
    public void add(User entry) throws EventException, InvalidDataException {

    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {

    }

    @Override
    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {

    }

    @Override
    public User search(Predicate<User> p) throws NotFoundException {
        return null;
    }

    @Override
    public User findById(String id) throws NotFoundException {
        return null;
    }
}
