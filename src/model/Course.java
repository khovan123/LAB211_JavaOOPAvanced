package model;

import exception.InvalidDataException;
import service.WorkoutService;

import java.time.LocalDate;

//this class store default course data, can not be change when coursepacket data change
public class Course {

    private String courseId;
    private String courseName;
    private boolean addventor;
    private LocalDate generateDate;
    private double price;
    private String comboID;
    private String coachId;
    WorkoutService workoutService; //all workout, to know what you do in course

    public Course(String courseId, String courseName, String coachId, WorkoutService workoutService) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coachId = coachId;
        this.workoutService = workoutService;
    }

    public Course(String courseId, String courseName, String coachId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coachId = coachId;
    }

    public Course(String courseId, String courseName, boolean addventor, LocalDate generateDate, double price, String comboID, String coachId, WorkoutService workoutService) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.addventor = addventor;
        this.generateDate = generateDate;
        this.price = price;
        this.comboID = comboID;
        this.coachId = coachId;
        this.workoutService = workoutService;
    }

    public Course(String courseId, String courseName, boolean addventor, LocalDate generateDate, double price, String comboID, String coachId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.addventor = addventor;
        this.generateDate = generateDate;
        this.price = price;
        this.comboID = comboID;
        this.coachId = coachId;
    }

    public Course(Course course) {

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

    public boolean getAddventor() {
        return addventor;
    }

    public void setAddventor(boolean addventor) {
        this.addventor = addventor;
    }

    public LocalDate getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(LocalDate generateDate) {
        this.generateDate = generateDate;
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

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }
}
