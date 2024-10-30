package service;

import exception.*;
import model.CourseCombo;
import repository.CourseComboRepository;
import service.interfaces.ICourseComboService;
import utils.FieldUtils;
import utils.GettingUtils;
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

    public List<CourseCombo> getCourseComboList(){
        return courseComboList;
    }

    public CourseComboService(List<CourseCombo> courseComboList) {
        this.courseComboList = courseComboList;
        readFromDataBase();
    }

    public void readFromDataBase() {
        try {
            courseComboList.add((CourseCombo) courseComboRepository.readData());
        } catch (Exception e) {
            // Handle exception if necessary
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseComboList.isEmpty()) {
            throw new EmptyDataException("-> No Course Combo Found");
        }
        System.out.println("ComboID\tComboName\tSale");
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
            courseComboRepository.insertToDB(courseCombo);
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Combo");
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            if (findById(id) == null) {
                throw new NotFoundException("-> Course With ID - " + id + " - Not Found.");
            }
            courseComboList.remove(findById(id));
            courseComboRepository.deleteToDB(id);
        } catch (Exception e) {
            throw new EventException("-> Error While Deleting Course With ID - " + id);
        }
    }

    public void update(CourseCombo courseCombo) throws EventException, NotFoundException {
        CourseCombo existingCombo = search(combo -> combo.getComboId().equalsIgnoreCase(courseCombo.getComboId()));
        if (existingCombo == null) {
            throw new NotFoundException("-> Course Combo with ID - " + courseCombo.getComboId() + " - Not Found.");
        }
        try {
            existingCombo.setComboName(courseCombo.getComboName());
            existingCombo.setSales(String.valueOf(courseCombo.getSales()));
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Course Combo With ID - " + courseCombo.getComboId());
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
                throw new EventException("-> Error While Updating Course Combo");
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
