package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.Course;
import model.PracticalDay;
import repository.UserRepository;
import service.interfaces.IUserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.function.Predicate;
import model.User;

public class UserService implements IUserService {
    private final UserRepository userRepository = new UserRepository();
    private final TreeSet<User> userTreeSet;

    public UserService() {
        userTreeSet = new TreeSet<>();
        readFromDatabase();
    }

    private void readFromDatabase() {
        try {
            userTreeSet.addAll(userRepository.readFile());
        } catch (exception.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (userTreeSet.isEmpty()) {
            throw new EmptyDataException("No users found!!!");
        } else {
            for (User user : userTreeSet) {
                System.out.println(user.getInfo());
            }
        }
    }

    @Override
    public void add(User user) throws EventException {
        try {
            userTreeSet.add(user);
            userRepository.writeFile(new ArrayList<>(userTreeSet));
            System.out.println("User added successfully!");
        } catch (Exception e) {
            throw new EventException("Failed to add User: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        User user = this.findById(id);
        if (userTreeSet.remove(user)) {
            try {
                userRepository.writeFile(new ArrayList<>(userTreeSet));
                System.out.println("Deleted User with ID: " + id + " successfully!");
            } catch (exception.IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NotFoundException("User ID " + id + " does not exist.");
        }
    }

    @Override
    public User search(Predicate<User> predicate) throws NotFoundException {
        return userTreeSet.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found!!!"));
    }

    @Override
    public User findById(String id) throws NotFoundException {
        return this.search(u -> u.getUserId().equalsIgnoreCase(id));
    }

    @Override
    public void update(User user) throws EventException, NotFoundException {
        delete(user.getUserId());
        add(user);
        System.out.println("User updated successfully!");
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
}
