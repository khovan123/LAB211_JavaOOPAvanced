package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.Course;
import model.RegisteredCourse;
import repository.CourseRepository;
import repository.RegistedCourseRepository;
import service.interfaces.IRegistedCourseService;
import utils.FieldUtils;
import utils.GlobalUtils;
import utils.ObjectUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class RegistedCourseService implements IRegistedCourseService {
    private final RegistedCourseRepository registedCourseRepository = new RegistedCourseRepository();
    private final List<RegisteredCourse> registeredCourseList;

    public RegistedCourseService() {
        registeredCourseList = new ArrayList<>();
        readFromDataBase();
    }

    public void readFromDataBase() {
        try {
            registeredCourseList.addAll(registedCourseRepository.readData());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (registeredCourseList.isEmpty()) {
            throw new EmptyDataException("-> No Registed Course Found");
        }
        for (RegisteredCourse registeredCourse : registeredCourseList) {
            registeredCourse.getInfo();
        }
    }

    @Override
    public void add(RegisteredCourse registeredCourse) throws EventException, InvalidDataException {
        if (existID(registeredCourse)) {
            throw new EventException("-> Registed Course With ID - " + registeredCourse.getRegisteredCourseID() + " - Already Exist");
        }
        try {
            registedCourseRepository.insertToDB(registeredCourse);
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Registed Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (findById(id) == null) {
            throw new NotFoundException("-> Registed Course with ID - " + id + " - Not Found.");
        }
        try {
            registedCourseRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(RegisteredCourse registeredCourse) throws EventException, NotFoundException {
        RegisteredCourse existRegisteredCourse = findById(registeredCourse.getRegisteredCourseID());
        if (existRegisteredCourse == null) {
            throw new NotFoundException("-> Registed Course with ID - " + registeredCourse.getRegisteredCourseID() + " - Not Found.");
        }
        try {
            existRegisteredCourse.setRegisteredDate(String.valueOf(registeredCourse.getRegisteredDate()));
            existRegisteredCourse.setRegisteredCourseID(String.valueOf(registeredCourse.getFinishRegisteredDate()));
            existRegisteredCourse.setCourseID(registeredCourse.getCourseID());
            existRegisteredCourse.setUserID(registeredCourse.getUserID());
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Registed Course With ID - " + registeredCourse.getRegisteredCourseID() + " - " + e.getMessage());
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        return switch (fieldName.toLowerCase()) {
            case "registereddate" -> RegistedCourseRepository.RegisteredDate_Column;
            case "finishregistereddate" -> RegistedCourseRepository.FinishRegisteredDate_Column;
            case "courseid" -> RegistedCourseRepository.CourseID_Column;
            case "userid" -> RegistedCourseRepository.UserID_Column;
            default -> throw new NotFoundException("Not found any field for name: " + fieldName);
        };
    }

    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {
        RegisteredCourse existingCourse = findById(id);
        if (existingCourse == null) {
            throw new NotFoundException("-> Course with ID - " + id + " - Not Found.");
        }

        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(existingCourse.getClass(), fieldName);
            try {
                field.set(existingCourse, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.put(fieldName, entry.get(fieldName));
                registedCourseRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    public void updateOrDeleteCourseFromConsoleCustomize() {
        if (registeredCourseList.isEmpty()) {
            System.out.println("Please create new course ^^");
            return;
        }
        while (true) {
            try {
                String id = GlobalUtils.getValue("Enter id for update: ", "Cannot be left blank");
                RegisteredCourse registeredCourse;
                if (!ObjectUtils.validID(id)) {
                    System.out.println("Id must be in correct form: CByyyy");
                } else if ((registeredCourse = findById(id)) != null) {
                    System.out.println(registeredCourse.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(registeredCourse.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GlobalUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                delete(registeredCourse.getRegisteredCourseID());
                            } catch (EventException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Delete successfully");
                            return;
                        } else if (selection == editMenuOptions.length) {
                            return;
                        }
                        while (true) {
                            try {
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Cannot be blank");
                                update(id, FieldUtils.getFieldValueByName(registeredCourse, editMenuOptions[selection - 1], newValue));
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public RegisteredCourse search(Predicate<RegisteredCourse> p) throws NotFoundException {
        for (RegisteredCourse registeredCourse : registeredCourseList) {
            if (p.test(registeredCourse)) {
                return registeredCourse;
            }
        }
        throw new NotFoundException("-> Registed Course not found matching the given criteria.");
    }

    @Override
    public RegisteredCourse findById(String id) throws NotFoundException {
        return search(course -> course.getRegisteredCourseID().equalsIgnoreCase(id));
    }

    public boolean existID(RegisteredCourse registeredCourse) {
        try {
            return findById(registeredCourse.getRegisteredCourseID()) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
