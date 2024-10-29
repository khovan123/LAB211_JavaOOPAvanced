package service;

import exception.*;
import model.CourseCombo;
import repository.CourseComboRepository;

import service.interfaces.ICourseComboService;
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
            courseComboList.add((CourseCombo) courseComboRepository.readData());
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
            courseComboRepository.insertToDB(courseCombo);
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Combo - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            if (findById(id) == null) {
                throw new NotFoundException("-> Course With ID - " + id + " - Not Found.");
            }
            courseComboRepository.deleteToDB(id);
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
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Course Combo With ID - " + courseCombo.getComboId() + " - " + e.getMessage());
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        return switch (fieldName.toLowerCase()) {
            case "combname" -> CourseComboRepository.ComboName_Column;
            case "sales" -> CourseComboRepository.Sales_Column;
            default -> throw new NotFoundException("Not found any field");
        };
    }

    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {
        CourseCombo existingCombo = findById(id);
        if (existingCombo == null) {
            throw new NotFoundException("-> Course Combo with ID - " + id + " - Not Found.");
        }

        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(existingCombo.getClass(), fieldName);
            try {
                field.set(existingCombo, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                courseComboRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    public void updateOrDeleteCourseComboFromConsoleCustomize(Object obj) {
        if (courseComboList.isEmpty()) {
            System.out.println("Please create new course combo ^^");
            return;
        }
        while (true) {
            try {
                String id = GlobalUtils.getValue("Enter id for update: ", "Cannot be left blank");
                CourseCombo courseCombo;
                if (!ObjectUtils.validID(id)) {
                    System.out.println("Id must be correct form: CByyyy");
                } else if ((courseCombo = findById(id)) != null) {
                    System.out.println(courseCombo.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(courseCombo.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GlobalUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                delete(courseCombo.getComboId());
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
                                update(id, FieldUtils.getFieldValueByName(courseCombo, editMenuOptions[selection - 1], newValue));
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
