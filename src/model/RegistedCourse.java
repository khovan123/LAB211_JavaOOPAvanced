package model;

import exception.InvalidDataException;
import java.text.ParseException;
import utils.GlobalUtils;
import utils.ObjectUtils;
import java.util.Date;

public class RegistedCourse {

    private int registeredCourseID;
    private Date registeredDate;
    private Date finishRegisteredDate;
    private int courseID;
    private int userID;
    private Course registedCourse;

    public RegistedCourse() {
    }

    public RegistedCourse(String registeredCourseID, String registeredDate, String finishRegisteredDate, String courseID, String userID) throws InvalidDataException, ParseException {
        this.setRegisteredCourseID(registeredCourseID);
        this.setRegisteredDate(registeredDate);
        this.setFinishRegisteredDate(finishRegisteredDate);
        this.setCourseID(courseID);
        this.setUserID(userID);
        this.runValidate();
    }

    public int getRegisteredCourseID() {
        return registeredCourseID;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public Date getFinishRegisteredDate() {
        return finishRegisteredDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getUserID() {
        return userID;
    }

    public String getInfo() {
        return String.format("%s\t%s\t%s\t%s\t",
                registeredCourseID, registedCourse.getRegistedInfo(), GlobalUtils.dateFormat(registeredDate), GlobalUtils.dateFormat(finishRegisteredDate)        );
    }

    public void setRegisteredCourseID(String registeredCourseID)throws ParseException {
        this.registeredCourseID = Integer.parseInt(registeredCourseID);
    }

    public void setRegisteredDate(String registeredDate) throws InvalidDataException {
        this.registeredDate = GlobalUtils.dateParse(registeredDate);
    }

    public void setFinishRegisteredDate(String finishRegisteredDate) throws InvalidDataException {
        this.finishRegisteredDate = GlobalUtils.dateParse(finishRegisteredDate);
    }

    public void setCourseID(String courseID) throws ParseException{
        this.courseID = Integer.parseInt(courseID);
    }

    public void setUserID(String userID) throws ParseException{
        this.userID = Integer.parseInt(userID);
    }

    public Course getRegistedCourse() {
        return registedCourse;
    }

    public void setRegistedCourse(Course registedCourse) {
        this.registedCourse = registedCourse;
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.isRegisteredDateBeforeFinishDate(registeredDate, finishRegisteredDate)) {
            throw new InvalidDataException("Finish Registered Date Must Be After Registered Date.");
        }
    }
}
