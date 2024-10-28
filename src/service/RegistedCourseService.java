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
        if (existID(registeredCourse)) {
            throw new EventException("-> Registed Course With ID - " + registeredCourse.getRegisteredCourseID() + " - Already Exist");
        }
        try {
            registeredCourseList.add(registeredCourse);
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Registed Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (findById(id) == null) {
            throw new NotFoundException("-> Registed Course with ID - " + id + " - Not Found.");
        }
        registeredCourseList.remove(findById(id));
        System.out.println("-> Registed Course With ID - " + id + " - Removed Successfully");
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
            System.out.println("-> Updated Registed Course - " + registeredCourse.getRegisteredCourseID() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Registed Course With ID - " + registeredCourse.getRegisteredCourseID() + " - " + e.getMessage());
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
