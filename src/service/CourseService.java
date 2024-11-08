package service;

import exception.*;
import model.Course;
import repository.CourseRepository;
import service.interfaces.ICourseService;
import utils.FieldUtils;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class CourseService implements ICourseService {

    private final CourseRepository courseRepository = new CourseRepository();
    private final List<Course> courseList = new ArrayList<>();
    private WorkoutService workoutService;

    public CourseService(WorkoutService workoutService) {
        this.workoutService = workoutService;
        readFromDatabase();
    }
    
    public int size(){
        return this.courseList.size();
    }

    public boolean isEmpty() {
        return this.courseList.isEmpty();
    }

    public List<Course> searchCourseByCoachId(int coachID) throws NotFoundException {
        List<Course> list = new ArrayList<>();
        for (Course course : this.courseList) {
            if (Integer.compare(course.getCoachId(), coachID) == 0) {
                list.add(course);
            }
        }
        if (list.isEmpty()) {
            throw new NotFoundException("Not found any course witch coach ID: " + coachID);
        }
        return list;
    }

    @Override
    public void display() throws EmptyDataException {
        if (courseList.isEmpty()) {
            throw new EmptyDataException("-> Course List Is Empty.");
        }
        System.out.println("CourseID\tCourseName\tAddventor\tGenerateDate\tPrice\tComboID\tCoachID\tWorkoutService");
        for (Course course : courseList) {
            System.out.println(course.getInfo());
        }

    }

    public void readFromDatabase() {
        try {
            for (Course course : courseRepository.readData()) {
                try {
                    if (!existsID(course.getCourseId())) {
                        course.setWorkoutService(workoutService.searchWorkoutByCourse(course.getCourseId()));
                        this.courseList.add(course);
                    }
                } catch (EmptyDataException e) {
                }
            }
        } catch (SQLException e) {
            // Handle exception if necessary
        }
    }

    @Override
    public void add(Course course) throws EventException {
        if (existsID(course.getCourseId())) {
            throw new EventException("Course With ID: " + course.getCourseId() + " already exsited");
        }
        try {
            course.setWorkoutService(workoutService.searchWorkoutByCourse(course.getCourseId()));
            courseList.add(course);
            courseRepository.insertToDB(course);
        } catch (SQLException | EmptyDataException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            courseList.remove(findById(id));
            courseRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        return switch (fieldName.toLowerCase()) {
            case "courseName" ->
                CourseRepository.CourseName_Column;
            case "addventor" ->
                String.valueOf(CourseRepository.Addventor_Column);
            case "generateDate" ->
                CourseRepository.GenerateDate_Column;
            case "price" ->
                CourseRepository.Price_Column;
            case "coachId" ->
                CourseRepository.CoachID_Column;
            case "courseId" ->
                CourseRepository.CourseID_Column;
            case "comboID" ->
                CourseRepository.ComboID_Column;
            default ->
                throw new NotFoundException("Not found any field for name: " + fieldName);
        };
    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        Course course = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(course.getClass(), fieldName);
            try {
                field.setAccessible(true);
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                courseRepository.updateToDB(id, updatedMap);
                field.set(course, entry.get(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
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
        throw new NotFoundException("Not found any course");
    }

    @Override
    public Course findById(int id) throws NotFoundException {
        return search(course -> course.getCourseId() == (id));
    }

    public boolean existsID(int courseID) {
        try {
            return findById(courseID) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
    
}
