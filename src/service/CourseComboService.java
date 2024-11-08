package service;

import exception.*;
import model.CourseCombo;
import repository.CourseComboRepository;
import service.interfaces.ICourseComboService;
import utils.FieldUtils;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import view.Printer;

public class CourseComboService implements ICourseComboService {

    private final CourseComboRepository courseComboRepository = new CourseComboRepository();
    private final List<CourseCombo> courseComboList = new ArrayList<>();

    public CourseComboService() {
        this.readFromDataBase();
    }

    public void readFromDataBase() {
        try {
            for (CourseCombo courseCombo : courseComboRepository.readData()) {
                try {
                    if (!existID(courseCombo.getComboId())) {
                        this.courseComboList.add(courseCombo);
                    }
                } catch (Exception e) {

                }
            }
        } catch (SQLException e) {
            // Handle exception if necessary
        }
    }

    public boolean isEmpty() {
        return this.courseComboList.isEmpty();
    }
    
    public int size(){
        return this.courseComboList.size();
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseComboList.isEmpty()) {
            throw new EmptyDataException("-> No Course Combo Found");
        }
        List<String> list = new ArrayList<>();
        for (CourseCombo courseCombo : courseComboList) {
            list.add(courseCombo.getInfo());
        }
        Printer.printTable("List Of Course Combo", "Combo", list);
    }

    @Override
    public void add(CourseCombo courseCombo) throws EventException, InvalidDataException {
        if (existID(courseCombo.getComboId())) {
            throw new InvalidDataException("Course Combo With ID: " + courseCombo.getComboId() + " already existed");
        }
        try {
            courseComboList.add(courseCombo);
            courseComboRepository.insertToDB(courseCombo);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            courseComboList.remove(findById(id));
            courseComboRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        return switch (fieldName.toLowerCase()) {
            case "comboName" ->
                CourseComboRepository.ComboName_Column;
            case "sales" ->
                CourseComboRepository.Sales_Column;
            case "comboId" ->
                CourseComboRepository.ComboID_Column;
            default ->
                throw new NotFoundException("Not found any field");
        };
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        CourseCombo courseCombo = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(courseCombo.getClass(), fieldName);
            try {
                field.setAccessible(true);
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                courseComboRepository.updateToDB(id, updatedMap);
                field.set(courseCombo, entry.get(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
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
    public CourseCombo findById(int id) throws NotFoundException {
        return search(combo -> combo.getComboId() == (id));
    }

    public boolean existID(int ID) {
        try {
            return findById(ID) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
