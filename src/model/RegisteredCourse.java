package model;

import exception.InvalidDataException;
import utils.GlobalUtils;
import utils.ObjectUtils;

import java.text.ParseException;
import java.util.Date;

public class RegisteredCourse {

    private String registeredCourseID;
    private Date registeredDate;
    private Date finishRegisteredDate;
    private String courseID;
    private String userID;

    public RegisteredCourse() {
    }

    public RegisteredCourse(String registeredCourseID, String registeredDate, String finishRegisteredDate, String courseID, String userID) throws InvalidDataException {
        this.registeredCourseID = registeredCourseID;
        this.setRegisteredDate(registeredDate);
        this.setFinishRegisteredDate(finishRegisteredDate);
        this.courseID = courseID;
        this.userID = userID;
        this.runValidate();
    }

    public String getRegisteredCourseID() {
        return registeredCourseID;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public Date getFinishRegisteredDate() {
        return finishRegisteredDate;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getUserID() {
        return userID;
    }

    public String getInfo() {
        return String.format("Registered Course ID: %s, Registered Date: %s, Finish Registered Date: %s, Course ID: %s, User ID: %s",
                registeredCourseID, GlobalUtils.getDateString(registeredDate), GlobalUtils.getDateString(finishRegisteredDate), courseID, userID);
    }

    public void setRegisteredCourseID(String registeredCourseID) {
        this.registeredCourseID = registeredCourseID;
    }

    public void setRegisteredDate(String registeredDate) throws InvalidDataException {
        this.registeredDate = GlobalUtils.getDate(registeredDate);
    }

    public void setFinishRegisteredDate(String finishRegisteredDate) throws InvalidDataException {
        this.finishRegisteredDate = GlobalUtils.getDate(finishRegisteredDate);
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void runValidate() throws InvalidDataException {
        if (registeredCourseID == null || registeredCourseID.isEmpty()) {
            throw new InvalidDataException("-> Registered Course ID must not be null or empty.");
        }
        if (!ObjectUtils.validCourseRegistedID(registeredCourseID)) {
            throw new InvalidDataException("-> Registered Course ID must be RCyyyy.");
        }

        if (!ObjectUtils.validCourseID(courseID)) {
            throw new InvalidDataException("-> Course ID must be CSyyyy.");
        }
        if (!ObjectUtils.validUserID(userID)) {
            throw new InvalidDataException("-> User ID must be Uyyyy.");
        }
        if (!GlobalUtils.validDateNow(registeredDate)) {
            throw new InvalidDataException("-> Registered Date Must Not Be In The Future.");
        }
        if (finishRegisteredDate != null && !ObjectUtils.isRegisteredDateBeforeFinishDate(registeredDate, finishRegisteredDate)) {
            throw new InvalidDataException("-> Finish Registered Date Must Be After Registered Date.");
        }
    }
}
