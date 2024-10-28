package service;

import exception.*;
import model.CourseCombo;
import repository.CourseComboRepository;

import service.interfaces.ICourseComboService;
import utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CourseComboService implements ICourseComboService {

    private final CourseComboRepository courseComboRepository = new CourseComboRepository();
    private final List<CourseCombo> courseComboList;

    public CourseComboService() {
        courseComboList = new ArrayList<>();
        readFromDataBase();
    }

    public CourseComboService(List<CourseCombo> courseComboList) {
        this.courseComboList = courseComboList;
        readFromDataBase();
    }

    public void readFromDataBase() {
        try {
            courseComboList.add((CourseCombo) courseComboRepository.readFile());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseComboList.isEmpty()) {
            throw new EmptyDataException("-> No Course Combo Found");
        }
        for (CourseCombo courseCombo : courseComboList) {
            courseCombo.getInfo();
        }
    }

    @Override
    public void add(CourseCombo courseCombo) throws EventException, InvalidDataException {
        if (existID(courseCombo)) {
            throw new EventException("-> Course Combo With ID - " + courseCombo.getComboId() + " - Already Exist");
        }
        try {
            courseComboList.add(courseCombo);
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Combo - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            CourseCombo courseCombo = findById(id);
            if (courseCombo == null) {
                throw new NotFoundException("-> Course With ID - " + id + " - Not Found.");
            }
            courseComboList.remove(courseCombo);
            System.out.println("-> Course With ID - " + id + " - Removed Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error While Deleting Course With ID - " + id + " - " + e.getMessage());
        }
    }


    @Override
    public void update(CourseCombo courseCombo) throws EventException, NotFoundException {
        CourseCombo existingCombo = search(combo -> combo.getComboId().equalsIgnoreCase(courseCombo.getComboId()));
        if (existingCombo == null) {
            throw new NotFoundException("-> Course Combo with ID - " + courseCombo.getComboId() + " - Not Found.");
        }
        try {
            existingCombo.setComboName(courseCombo.getComboName());
            existingCombo.setSales(String.valueOf(courseCombo.getSales()));
            System.out.println("-> Update Course Combo - " + courseCombo.getComboId() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Course Combo With ID - " + courseCombo.getComboId() + " - " + e.getMessage());
        }
    }

    @Override
    public CourseCombo search(Predicate<CourseCombo> p) throws NotFoundException {
        for (CourseCombo courseCombo : courseComboList) {
            if (p.test(courseCombo)) {
                return courseCombo;
            }
        }
        throw new NotFoundException("-> Course Combo not found matching the given criteria.");
    }

    @Override
    public CourseCombo findById(String id) throws NotFoundException {
        return search(combo -> combo.getComboId().equalsIgnoreCase(id));
    }

    public boolean existID(CourseCombo courseCombo) {
        try {
            return findById(courseCombo.getComboId()) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
