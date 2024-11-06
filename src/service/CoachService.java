package service;

import exception.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import model.*;
import repository.CoachRepository;
import service.interfaces.ICoachService;
import utils.FieldUtils;
import view.Printer;

public class CoachService implements ICoachService {

    private CoachRepository coachRepository;
    private List<Coach> coaches;
    private RegistedCourseService registedCourseService;
    private UserService userService;
    private CourseService courseService;
    private CourseComboService courseComboService;
    public CoachService(RegistedCourseService registedCourseService, UserService userService, CourseService courseService, CourseComboService courseComboService) {
        this.coachRepository = new CoachRepository();
        this.coaches = new ArrayList<>();
        this.registedCourseService = registedCourseService;
        this.userService = userService;
        this.courseService = courseService;
        this.courseComboService = courseComboService;
        this.readFromDatabase();
    }

    public void displayUserInCoursesByCoach(int coachID) throws EmptyDataException {
        if (registedCourseService.searchRegistedCourseByCoach(coachID).isEmpty()) {
            throw new EmptyDataException("Coach with id: " + coachID + " had not any course");
        }
        Map<Integer, List<User>> registedCourseMapUser = new HashMap<>();
        for (RegistedCourse registedCourse : registedCourseService.searchRegistedCourseByCoach(coachID)) {
            if (!registedCourseMapUser.containsKey(registedCourse.getCourseID())) {
                registedCourseMapUser.put(registedCourse.getCourseID(), new ArrayList<>());
            }
            try {
                User user = userService.findById(registedCourse.getUserID());
                registedCourseMapUser.get(registedCourse.getCourseID()).add(user);
            } catch (NotFoundException e) {

            }
        }
        if (registedCourseMapUser.isEmpty()) {
            throw new EmptyDataException("Coach with id: " + coachID + " had not any member");
        }
        List<String> list = new ArrayList<>();
        for (int courseID : registedCourseMapUser.keySet()) {
            try {
                for (User user : registedCourseMapUser.get(courseID)) {
                    list.add(courseService.findById(courseID).getCourseName()+": "+user.getInfo());
                }
                Printer.printTable("","USER", list);
            } catch (NotFoundException e) {

            }
        }
    }

    public void displayCoursesByCoach(int coachID) throws NotFoundException {
        List<String> list = new ArrayList<>();
        for (Course course : courseService.searchCourseByCoachId(coachID)) {
            list.add(course.getInfo()+" "+courseComboService.search(p->p.getComboId()==course.getComboID())+" "+this.findById(coachID).getFullName());
        }
        Printer.printTable("YOUR COURSE","COURSE", list);
    }

    public boolean isEmpty() {
        return this.coaches.isEmpty();
    }

    public int size() {
        return this.coaches.size();
    }

    public void readFromDatabase() {
        try {
            for (Coach coach : coachRepository.readData()) {
                try {
                    if (!existed(coach.getPersonId())) {
                        this.coaches.add(coach);
                    }
                } catch (Exception e) {

                }
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (coaches.isEmpty()) {
            throw new EmptyDataException("List of coach is empty");
        }
        List<String> list = new ArrayList<>();
        for (Coach coach : coaches) {
            list.add(coach.getInfo());
        }
        Printer.printTable("List Of Coach", "Coach", list);
    }

    @Override
    public void add(Coach coach) throws EventException, InvalidDataException {
        if (!existed(coach.getPersonId())) {
            try {
                coaches.add(coach);
                coachRepository.insertToDB(coach);
            } catch (SQLException e) {
                throw new EventException(e);
            }
        } else {
            throw new InvalidDataException("Coach with id: " + coach.getPersonId() + "was existed");
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            coachRepository.deleteToDB(id);
            coaches.remove(this.findById(id));
        } catch (NotFoundException | SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws NotFoundException, EventException {
        Coach coach = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(coach.getClass(), fieldName);
            try {
                field.setAccessible(true);
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                coachRepository.updateToDB(id, updatedMap);
                field.set(coach, entry.get(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("personId")) {
            return CoachRepository.PersonID_Column;
        }
        if (fieldName.equalsIgnoreCase("fullName")) {
            return CoachRepository.FullName_Column;
        }
        if (fieldName.equalsIgnoreCase("DoB")) {
            return CoachRepository.DoB_Column;
        }
        if (fieldName.equalsIgnoreCase("phone")) {
            return CoachRepository.Phone_Column;
        }
        if (fieldName.equalsIgnoreCase("certificate")) {
            return CoachRepository.Certificate_Column;
        }
        if (fieldName.equalsIgnoreCase("active")) {
            return CoachRepository.Active_Column;
        }
        throw new NotFoundException("Not found any field");
    }

    @Override
    public Coach search(Predicate<Coach> p) throws NotFoundException {
        for (Coach coach : coaches) {
            if (p.test(coach)) {
                return coach;
            }
        }
        throw new NotFoundException("Not found any coach");
    }

    @Override
    public Coach findById(int id) throws NotFoundException {
        return this.search(p -> p.getPersonId() == (id));
    }

    public boolean existed(int id) {
        try {
            return this.findById(id) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
