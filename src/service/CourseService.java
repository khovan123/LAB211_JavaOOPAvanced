package service;

import exception.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import model.Course;
import model.Workout;
import repository.CourseRepository;
import service.interfaces.ICourseService;
import utils.FieldUtils;
import utils.GlobalUtils;
import utils.ObjectUtils;

public class CourseService implements ICourseService {

    private final CourseRepository courseRepository = new CourseRepository();
    private final List<Course> courseList;

    public CourseService() {
        courseList = new ArrayList<>();
        readFromDatabase();
    }

    public CourseService(List<Course> courseList) {
        this.courseList = courseList;
        readFromDatabase();
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseList.isEmpty()) {
            throw new EmptyDataException("-> Course List Is Empty.");
        } else {
            for (Course course : courseList) {
                course.getInfo();
            }
        }
    }

    public void readFromDatabase()  {
        try {
            courseList.addAll(courseRepository.readData());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void add(Course course) throws EventException {
        if (existsID(course)) {
            throw new EventException("-> Course With ID - " + course.getCourseId() + " - Already Exist");
        }
        try {
            courseList.add(course);
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Adding Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (findById(id) == null) {
            throw new NotFoundException("-> Course With ID - " + id + " - Not Found!");
        }
        try {
            courseList.remove(findById(id));
        } catch (Exception e) {
            throw new EventException("-> Error While Deleting Course With ID - " + id + " - " + e.getMessage());
        }
    }

    @Override
    public void update(Course course) throws EventException, NotFoundException {
        Course existCourse = findById(course.getCourseId());
        if (existCourse == null) {
            throw new NotFoundException("-> Course with ID - " + course.getCourseId() + " - Not Found.");
        }
        try {
            existCourse.setCourseName(course.getCourseName());
            existCourse.setGenerateDate(String.valueOf(course.getGenerateDate()));
            existCourse.setPrice(String.valueOf(course.getPrice()));
            existCourse.setComboID(course.getComboID());
            existCourse.setCoachId(course.getCoachId());
        } catch (Exception e) {
            throw new EventException("-> Error While Updating Course With ID - " + course.getCourseId() + " - " + e.getMessage());
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        return switch (fieldName.toLowerCase()) {
            case "coursename" -> CourseRepository.CourseName_Column;
            case "addventor" -> String.valueOf(CourseRepository.Addventor_Column);
            case "generatedate" -> CourseRepository.GenerateDate_Column;
            case "price" -> CourseRepository.Price_Column;
            case "coachid" -> CourseRepository.CoachID_Column;
            default -> throw new NotFoundException("Not found any field for name: " + fieldName);
        };
    }

    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {
        Course existingCourse = findById(id);
        if (existingCourse == null) {
            throw new NotFoundException("-> Course with ID - " + id + " - Not Found.");
        }

        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(existingCourse.getClass(), fieldName);
            try {
                field.set(existingCourse, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.put(fieldName, entry.get(fieldName));
                courseRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    public void updateOrDeleteCourseFromConsoleCustomize() {
        if (courseList.isEmpty()) {
            System.out.println("Please create new course ^^");
            return;
        }
        while (true) {
            try {
                String id = GlobalUtils.getValue("Enter id for update: ", "Cannot be left blank");
                Course course;
                if (!ObjectUtils.validID(id)) {
                    System.out.println("Id must be in correct form: CByyyy");
                } else if ((course = findById(id)) != null) {
                    System.out.println(course.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(course.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GlobalUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                delete(course.getCourseId());
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
                                update(id, FieldUtils.getFieldValueByName(course, editMenuOptions[selection - 1], newValue));
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
    public Course search(Predicate<Course> p) throws NotFoundException {
        for (Course course : courseList) {
            if (p.test(course)) {
                return course;
            }
        }
        throw new NotFoundException("-> No Course found matching the criteria.");
    }

    @Override
    public Course findById(String id) throws NotFoundException {
        return search(course -> course.getCourseId().equalsIgnoreCase(id));
    }

    public boolean existsID(Course course) {
        try {
            return findById(course.getCourseId()) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    @Override
    public void addWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Workout searchWorkout(Predicate<Workout> p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
