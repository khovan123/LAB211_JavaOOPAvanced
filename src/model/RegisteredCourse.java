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
    }

    public String getRegisteredCourseID() {
        return registeredCourseID;
    }

    public void setRegisteredCourseID(String registeredCourseID) {
        this.registeredCourseID = registeredCourseID;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) throws InvalidDataException {
        try {
            this.registeredDate = GlobalUtils.getDate(registeredDate);
        } catch (ParseException e) {
            throw new InvalidDataException("-> Registered Date must be in dd/MM/yyyy format.");
        }
    }

    public Date getFinishRegisteredDate() {
        return finishRegisteredDate;
    }

    public void setFinishRegisteredDate(String finishRegisteredDate) throws InvalidDataException {
        try {
            this.finishRegisteredDate = GlobalUtils.getDate(finishRegisteredDate);
        } catch (ParseException e) {
            throw new InvalidDataException("-> Finish Registered Date must be in dd/MM/yyyy format.");
        }
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getInfo() {
        return String.format("Registered Course ID: %s, Registered Date: %s, Finish Registered Date: %s, Course ID: %s, User ID: %s",
                registeredCourseID, GlobalUtils.getDateString(registeredDate), GlobalUtils.getDateString(finishRegisteredDate), courseID, userID);
    }

    public void validate() throws InvalidDataException {
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
            throw new InvalidDataException("-> Registered Date must not be in the future.");
        }
        if (finishRegisteredDate != null && registeredDate.after(finishRegisteredDate)) {
            throw new InvalidDataException("-> Finish Registered Date must be after Registered Date.");
        }
    }
}
