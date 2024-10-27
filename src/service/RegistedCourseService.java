package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.RegisteredCourse;
import repository.RegistedCourseRepository;
import service.interfaces.IRegistedCourseService;
import utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RegistedCourseService implements IRegistedCourseService {
    private final RegistedCourseRepository registedCourseRepository = new RegistedCourseRepository();
    private final List<RegisteredCourse> registeredCourseList;

    public RegistedCourseService() {
        registeredCourseList = new ArrayList<>();
        readFromDataBase();
    }

    public RegistedCourseService(List<RegisteredCourse> registeredCourseList) {
        this.registeredCourseList = registeredCourseList;
        readFromDataBase();
    }

    public void readFromDataBase() {
        try {
            registeredCourseList.addAll(registedCourseRepository.readFile());
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
        if (!ObjectUtils.validID(registeredCourse.getRegistedCourseID())) {
            throw new EventException("-> Invalid Registed Course ID - " + registeredCourse.getRegistedCourseID() + " - Must Be RC-yyyy");
        }
        if (!ObjectUtils.validID(registeredCourse.getRegistedCourseID())) {
            throw new EventException("-> Invalid Course ID - " + registeredCourse.getCourseID() + " - Must Be C-yyyy");
        }
        if (!ObjectUtils.validID(registeredCourse.getRegistedCourseID())) {
            throw new EventException("-> Invalid User ID - " + registeredCourse.getUserID() + " - Must Be U-yyyy");
        }
        if (existID(registeredCourse)) {
            throw new EventException("-> Registed Course With ID - " + registeredCourse.getRegistedCourseID() + " - Already Exist");
        }
        try {
            registeredCourseList.add(registeredCourse);
            System.out.println("-> Registed Course Added Successfully!");
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Registed Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (!registeredCourseList.remove(search(course -> course.getRegistedCourseID().equalsIgnoreCase(id)))) {
            throw new NotFoundException("-> Registed Course with ID - " + id + " - Not Found.");
        }
        System.out.println("-> Registed Course With ID - " + id + " - Removed Successfully");
    }

    @Override
    public void update(RegisteredCourse registeredCourse) throws EventException, NotFoundException {
        if (!registeredCourseList.remove(search(course -> course.getRegistedCourseID().equalsIgnoreCase(registeredCourse.getRegistedCourseID())))) {
            throw new NotFoundException("-> Registed Course with ID - " + registeredCourse.getRegistedCourseID() + " - Not Found.");
        }
        try {
            registeredCourseList.add(registeredCourse);
            System.out.println("-> Updated Registed Course - " + registeredCourse.getRegistedCourseID() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Registed Course With ID - " + registeredCourse.getRegistedCourseID() + " - " + e.getMessage());
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
        return search(course -> course.getRegistedCourseID().equalsIgnoreCase(id));
    }

    public boolean existID(RegisteredCourse registeredCourse) {
        try {
            return findById(registeredCourse.getRegistedCourseID()) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}