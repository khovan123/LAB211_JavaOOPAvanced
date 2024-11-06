package service;

import exception.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import model.RegistedCourse;
import model.User;
import repository.UserRepository;
import service.interfaces.IUserService;
import utils.FieldUtils;
import utils.GlobalUtils;
import view.Printer;

public class UserService implements IUserService {

    private UserRepository userRepository = new UserRepository();
    private List<User> users = new ArrayList<>();
    private RegistedCourseService registedCourseService;
    private UserProgressService userProgressService;

    public UserService(RegistedCourseService registedCourseService, UserProgressService userProgressService) {
        this.registedCourseService = registedCourseService;
        this.userProgressService = userProgressService;
        this.readFromDatabase();
    }

    public void readFromDatabase() {
        try {
            for (User user : userRepository.readData()) {
                try {
                    if (!this.existed(user.getPersonId())) {
                        this.users.add(user);
                    }
                } catch (Exception e) {

                }
            }
        } catch (SQLException e) {

        }

    }

    public int size() {
        return this.users.size();
    }

    public boolean isEmpty() {
        return this.users.isEmpty();
    }

    @Override
    public void display() throws EmptyDataException {
        if (users.isEmpty()) {
            throw new EmptyDataException("List of user is empty");
        }
        List<String> list = new ArrayList<>();
        for (User user : users) {
            list.add(user.getInfo());
        }
        Printer.printTable("List Of User", "User", list);
    }

    @Override
    public void add(User user) throws EventException, InvalidDataException {
        if (!existed(user.getPersonId())) {
            try {
                users.add(user);
                this.userRepository.insertToDB(user);
            } catch (SQLException e) {
                throw new EventException(e);
            }
        } else {
            throw new InvalidDataException("User with id: " + user.getPersonId() + " was existed");
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            this.users.remove(this.findById(id));
            this.userRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        for (String fieldName : entry.keySet()) {
            User user = findById(id);
            Field field = FieldUtils.getFieldByName(user.getClass(), fieldName);
            try {
                field.setAccessible(true);
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                userRepository.updateToDB(id, updatedMap);
                field.set(user, entry.get(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("personId")) {
            return UserRepository.PersonID_Column;
        }
        if (fieldName.equalsIgnoreCase("fullName")) {
            return UserRepository.FullName_Column;
        }
        if (fieldName.equalsIgnoreCase("DoB")) {
            return UserRepository.DoB_Column;
        }
        if (fieldName.equalsIgnoreCase("phone")) {
            return UserRepository.Phone_Column;
        }
        if (fieldName.equalsIgnoreCase("addventor")) {
            return UserRepository.Addventor_Column;
        }
        if (fieldName.equalsIgnoreCase("active")) {
            return UserRepository.Active_Column;
        }
        throw new NotFoundException("Not found any field");
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
    public User findById(int id) throws NotFoundException {
        return this.search(p -> p.getPersonId() == (id));
    }

    public boolean existed(int id) {
        try {
            return this.findById(id) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public void displayJoinedCourse(int userID) throws EmptyDataException {
        if (registedCourseService.searchRegistedCourseByUser(userID).isEmpty()) {
            throw new EmptyDataException("User with id: " + userID + " did not join any course");
        }
        List<String> str = new ArrayList<>();
        for (RegistedCourse registedCourse : registedCourseService.searchRegistedCourseByUser(userID)) {
            userProgressService.setCompletedUserProgress(registedCourse.getRegisteredCourseID());
            try {
                str.add(registedCourse.getInfo() + " " + GlobalUtils.decimalFormat(userProgressService.search(p -> p.getRegistedCourseID() == registedCourse.getRegisteredCourseID()).getCompleted() * 100) + "%");
            } catch (NotFoundException e) {

            }
        }
        Printer.printTable("Your Course", "Course", str);
    }

}
