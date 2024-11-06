package service;

import exception.*;
import model.RegistedCourse;
import repository.RegistedCourseRepository;
import service.interfaces.IRegistedCourseService;
import utils.FieldUtils;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;
import model.Course;
import utils.GlobalUtils;

public class RegistedCourseService implements IRegistedCourseService {

    private final RegistedCourseRepository registedCourseRepository = new RegistedCourseRepository();
    private final List<RegistedCourse> registeredCourseList = new ArrayList<>();
    private CourseService courseService;

    public RegistedCourseService(CourseService courseService) {
        this.courseService = courseService;
        readFromDataBase();
    }
    
    public int size(){
        return this.registeredCourseList.size();
    }

    public void readFromDataBase() {
        try {
            for (RegistedCourse registedCourse : registedCourseRepository.readData()) {
                try {
                    if (!existID(registedCourse.getRegisteredCourseID())) {
                        Course course = new Course();
                        registedCourse.setRegistedCourse(course);
                        this.refeshRegistedCourse(registedCourse.getRegistedCourse(), courseService.findById(registedCourse.getCourseID()));
                        this.registeredCourseList.add(registedCourse);
                    }

                } catch (InvalidDataException | NotFoundException | ParseException e) {
                }
            }
        } catch (SQLException e) {
            // Handle exception if necessary
        }
    }

    private void refeshRegistedCourse(Course registedC, Course course) throws InvalidDataException, ParseException {
        registedC.setAddventor(Boolean.toString(course.isAddventor()));
        registedC.setCoachId(String.valueOf(course.getCoachId()));
        registedC.setComboID(String.valueOf(course.getComboID()));
        registedC.setCourseId(String.valueOf(course.getCourseId()));
        registedC.setCourseName(course.getCourseName());
        registedC.setPrice(String.valueOf(course.getPrice()));
        registedC.setGenerateDate(GlobalUtils.dateFormat(course.getGenerateDate()));
        registedC.setWorkoutService(course.getWorkoutService().getWorkoutList());
    }

    @Override
    public void display() throws EmptyDataException {
        if (registeredCourseList.isEmpty()) {
            throw new EmptyDataException("-> No Registered Course Found");
        }
        System.out.println("registeredCourseID\tRegistedDate\tFinishRegistedDate\tCourseID\tUserID");
        for (RegistedCourse registeredCourse : registeredCourseList) {
            registeredCourse.getInfo();
        }
    }

    @Override
    public void add(RegistedCourse registeredCourse) throws EventException, InvalidDataException {
        if (existID(registeredCourse.getRegisteredCourseID())) {
            throw new EventException("Registered Course With ID :" + registeredCourse.getRegisteredCourseID() + " already existed");
        }
        try {
            Course course = new Course();
            registeredCourse.setRegistedCourse(course);
            this.refeshRegistedCourse(registeredCourse.getRegistedCourse(), courseService.findById(registeredCourse.getCourseID()));
            this.registeredCourseList.add(registeredCourse);
            this.registedCourseRepository.insertToDB(registeredCourse);
        } catch (SQLException | NotFoundException | ParseException e) {
            throw new EventException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            this.registeredCourseList.remove(this.findById(id));
            registedCourseRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        return switch (fieldName.toLowerCase()) {
            case "registeredDate" ->
                RegistedCourseRepository.RegisteredDate_Column;
            case "finishRegisteredDate" ->
                RegistedCourseRepository.FinishRegisteredDate_Column;
            case "courseID" ->
                RegistedCourseRepository.CourseID_Column;
            case "userID" ->
                RegistedCourseRepository.UserID_Column;
            case "registeredCourseID" ->
                RegistedCourseRepository.RegisteredCourseID_Column;
            default ->
                throw new NotFoundException("Not found any field for name: " + fieldName);
        };
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        RegistedCourse existingCourse = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(existingCourse.getClass(), fieldName);
            try {
                field.setAccessible(true);
                field.set(existingCourse, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.put(getColumnByFieldName(fieldName), entry.get(fieldName));
                registedCourseRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException("-> Error While Updating Registered Course");
            }
        }
    }

    @Override
    public RegistedCourse search(Predicate<RegistedCourse> p) throws NotFoundException {
        for (RegistedCourse registeredCourse : registeredCourseList) {
            if (p.test(registeredCourse)) {
                return registeredCourse;
            }
        }
        throw new NotFoundException("-> Registered Course not found matching the given criteria.");
    }

    @Override
    public RegistedCourse findById(int id) throws NotFoundException {
        return search(course -> course.getRegisteredCourseID() == (id));
    }

    public boolean existID(int registeredCourseID) {
        try {
            return findById(registeredCourseID) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public List<RegistedCourse> searchRegistedCourseByCoach(int coachID) throws EmptyDataException {
        List<RegistedCourse> coachMapRegistedCourse = new ArrayList<>();
        for (RegistedCourse registedCourse : this.registeredCourseList) {

            if (Integer.compare(registedCourse.getRegistedCourse().getCoachId(), coachID) == 0) {
                System.out.println(coachID);
                coachMapRegistedCourse.add(registedCourse);
            }
        }
        if (coachMapRegistedCourse.isEmpty()) {
            throw new EmptyDataException("Coach with ID " + coachID + " had not any course");
        }
        return coachMapRegistedCourse;
    }

    public List<RegistedCourse> searchRegistedCourseByUser(int userID) throws EmptyDataException {
        List<RegistedCourse> userMapRegistedCourse = new ArrayList<>();
        for (RegistedCourse registedCourse : this.registeredCourseList) {
            if (Integer.compare(registedCourse.getUserID(), userID) == 0) {
                userMapRegistedCourse.add(registedCourse);
            }
        }
        if (userMapRegistedCourse.isEmpty()) {
            throw new EmptyDataException("User with ID: " + userID + " did not join any course");
        }
        return userMapRegistedCourse;
    }
}
