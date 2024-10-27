package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.RegistedCourse;
import repository.RegistedCourseRepository;
import service.interfaces.IRegistedCourseService;
import utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RegistedCourseService implements IRegistedCourseService {
    private final RegistedCourseRepository registedCourseRepository = new RegistedCourseRepository();
    private final List<RegistedCourse> registedCourseList;

    public RegistedCourseService() {
        registedCourseList = new ArrayList<>();
        readFromDataBase();
    }

    public RegistedCourseService(List<RegistedCourse> registedCourseList) {
        this.registedCourseList = registedCourseList;
        readFromDataBase();
    }

    public void readFromDataBase() {
        try {
            registedCourseList.addAll(registedCourseRepository.readFile());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (registedCourseList.isEmpty()) {
            throw new EmptyDataException("-> No Registed Course Found");
        }
        for (RegistedCourse registedCourse : registedCourseList) {
            registedCourse.getInfo();
        }
    }

    @Override
    public void add(RegistedCourse registedCourse) throws EventException, InvalidDataException {
        if (!ObjectUtils.validID(registedCourse.getRegistedCourseID())) {
            throw new EventException("-> Invalid Registed Course ID - " + registedCourse.getRegistedCourseID() + " - Must Be RC-yyyy");
        }
        if (!ObjectUtils.validID(registedCourse.getRegistedCourseID())) {
            throw new EventException("-> Invalid Course ID - " + registedCourse.getCourseID() + " - Must Be C-yyyy");
        }
        if (!ObjectUtils.validID(registedCourse.getRegistedCourseID())) {
            throw new EventException("-> Invalid User ID - " + registedCourse.getUserID() + " - Must Be U-yyyy");
        }
        if (existID(registedCourse)) {
            throw new EventException("-> Registed Course With ID - " + registedCourse.getRegistedCourseID() + " - Already Exist");
        }
        try {
            registedCourseList.add(registedCourse);
            System.out.println("-> Registed Course Added Successfully!");
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Registed Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (!registedCourseList.remove(search(course -> course.getRegistedCourseID().equalsIgnoreCase(id)))) {
            throw new NotFoundException("-> Registed Course with ID - " + id + " - Not Found.");
        }
        System.out.println("-> Registed Course With ID - " + id + " - Removed Successfully");
    }

    @Override
    public void update(RegistedCourse registedCourse) throws EventException, NotFoundException {
        if (!registedCourseList.remove(search(course -> course.getRegistedCourseID().equalsIgnoreCase(registedCourse.getRegistedCourseID())))) {
            throw new NotFoundException("-> Registed Course with ID - " + registedCourse.getRegistedCourseID() + " - Not Found.");
        }
        try {
            registedCourseList.add(registedCourse);
            System.out.println("-> Updated Registed Course - " + registedCourse.getRegistedCourseID() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Registed Course With ID - " + registedCourse.getRegistedCourseID() + " - " + e.getMessage());
        }
    }

    @Override
    public RegistedCourse search(Predicate<RegistedCourse> p) throws NotFoundException {
        for (RegistedCourse registedCourse : registedCourseList) {
            if (p.test(registedCourse)) {
                return registedCourse;
            }
        }
        throw new NotFoundException("-> Registed Course not found matching the given criteria.");
    }

    @Override
    public RegistedCourse findById(String id) throws NotFoundException {
        return search(course -> course.getRegistedCourseID().equalsIgnoreCase(id));
    }

    public boolean existID(RegistedCourse registedCourse) {
        try {
            return findById(registedCourse.getRegistedCourseID()) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
