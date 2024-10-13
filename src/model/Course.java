package model;

import exception.InvalidDataException;

public class Course {

    private String courseId;
    private String courseName;
    private String coachId;

    public Course() {
    }

    public Course(String courseId, String courseName, String coachId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coachId = coachId;
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

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }
}
