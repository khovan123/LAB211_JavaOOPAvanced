package service;

import exception.*;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import model.User;
import repository.UserRepository;
import service.interfaces.IUserService;

public class UserService implements IUserService {

    private UserRepository userRepository = new UserRepository();
    private List<User> users;

    public UserService() {
        this.userRepository = new UserRepository();
        this.users = new ArrayList<>();
    }

    public void readFromDatabase() {
        try {
            for (User user : userRepository.readData()) {
                try {
                    this.add(user);
                } catch (EventException | InvalidDataException e) {

                }
            }
        } catch (SQLException e) {

        }

    }

    @Override
    public void display() throws EmptyDataException {
        if (users.isEmpty()) {
            throw new EmptyDataException("List of user is empty");
        }
        System.out.println("User ID\tFull Name\tDoB\tPhone\tTarget");
        for (User user : users) {
            System.out.println(user.getInfo());
        }
    }

    @Override
    public void add(User user) throws EventException, InvalidDataException {
        if (!existed(user.getPersonId())) {
            users.add(user);
        } else {
            throw new InvalidDataException("User with id: " + user.getPersonId() + " was existed");
        }
    }

    @Override
    public void add(User entry) throws EventException, InvalidDataException {
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        this.users.remove(this.findById(id));
    }

    @Override
    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User search(Predicate<User> p) throws NotFoundException {
        for (User user : users) {
            if (p.test(user)) {
                return user;
            }
        }
        throw new NotFoundException("Not found any user");
    }

    @Override
    public User findById(String id) throws NotFoundException {
        return this.search(p -> p.getPersonId().equalsIgnoreCase(id));
    }

    public boolean existed(String id) {
        try {
            return this.findById(id) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
