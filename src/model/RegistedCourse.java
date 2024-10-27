package model;

import java.time.LocalDate;

public class RegistedCourse {
    private String registedCourseID;
    private LocalDate registedDate;
    private LocalDate  finishRegistedDate;
    private String CourseID;
    private String userID;

    public RegistedCourse(String registedCourseID, LocalDate registedDate, LocalDate finishRegistedDate, String courseID, String userID) {
        this.registedCourseID = registedCourseID;
        this.registedDate = registedDate;
        this.finishRegistedDate = finishRegistedDate;
        CourseID = courseID;
        this.userID = userID;
    }

    public String getRegistedCourseID() {
        return registedCourseID;
    }

    public void setRegistedCourseID(String registedCourseID) {
        this.registedCourseID = registedCourseID;
    }

    public LocalDate getRegistedDate() {
        return registedDate;
    }

    public void setRegistedDate(LocalDate registedDate) {
        this.registedDate = registedDate;
    }

    public LocalDate getFinishRegistedDate() {
        return finishRegistedDate;
    }

    public void setFinishRegistedDate(LocalDate finishRegistedDate) {
        this.finishRegistedDate = finishRegistedDate;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getInfo(){
        return String.format("");
    }
}
