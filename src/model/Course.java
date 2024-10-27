package model;

import exception.InvalidDataException;
import service.WorkoutService;
import utils.GlobalUtils;
import utils.ObjectUtils;

import java.text.ParseException;
import java.util.Date;

public class Course {

    private String courseId;
    private String courseName;
    private boolean addventor;
    private Date generateDate;
    private double price;
    private String comboID;
    private String coachId;
    WorkoutService workoutService; // All workouts, to know what you do in the course

    public Course(String courseId, String courseName, boolean addventor, String generateDate, double price, String comboID, String coachId) throws InvalidDataException {
        this.courseId = courseId;
        this.courseName = courseName;
        this.addventor = addventor;
        this.setGenerateDate(generateDate);
        this.price = price;
        this.comboID = comboID;
        this.coachId = coachId;
    }

    public Course(String courseId, String courseName, boolean addventor, String generateDate, double price, String comboID, String coachId, WorkoutService workoutService) throws InvalidDataException {
        this(courseId, courseName, addventor, generateDate, price, comboID, coachId); // Call primary constructor
        this.workoutService = workoutService;
    }

    public void setGenerateDate(String generateDate) throws InvalidDataException {
        try {
            this.generateDate = GlobalUtils.getDate(generateDate);
        } catch (ParseException e) {
            throw new InvalidDataException("-> Generate Date Must Be dd/MM/yyyy");
        }
    }

    public Date getGenerateDate() {
        return this.generateDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getComboID() {
        return comboID;
    }

    public void setComboID(String comboID) {
        this.comboID = comboID;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public WorkoutService getWorkoutService() {
        return workoutService;
    }

    public void setWorkoutService(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isAddventor() {
        return addventor;
    }

    public void setAddventor(boolean addventor) {
        this.addventor = addventor;
    }

    public String getInfo() {
        return String.format("Course ID: %s, Course Name: %s, Adventor: %b, Generate Date: %s, Price: %.2f, Combo ID: %s, Coach ID: %s",
                courseId, courseName, addventor, GlobalUtils.getDateString(generateDate), price, comboID, coachId);
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.validCourseID(courseId)) {
            throw new InvalidDataException("-> Course ID Must Be CS-YYYY.");
        }
        if (!ObjectUtils.validCoursePrice(String.valueOf(price))) {
            throw new InvalidDataException("-> Price Must Be Larger Than 0.");
        }
    }
}
