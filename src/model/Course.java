package model;

import exception.InvalidDataException;
import service.WorkoutService;

//this class store default course data, can not be change when coursepacket data change
public class Course {

    private String courseId;
    private String courseName;
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

    public Course(Course course) {

    }

    public WorkoutService getWorkoutService() {
        return workoutService;
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

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
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
