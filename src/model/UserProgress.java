package model;

import exception.IOException;
import exception.InvalidDataException;

import java.util.TreeSet;

import service.ScheduleService;
import utils.ObjectUtils;

public class UserProgress {

    private String userProgressID;
    private String registedCourseID;

    public UserProgress(String userProgressID, String registedCourseID) throws InvalidDataException {
        this.userProgressID = userProgressID;
        this.registedCourseID = registedCourseID;
        this.runValidate();
    }

    public UserProgress() {
        
    }

    public String getUserProgressID() {
        return userProgressID;
    }

    public void setUserProgressID(String userProgressID) {
        this.userProgressID = userProgressID;
    }

    public String getRegistedCourseID() {
        return registedCourseID;
    }

    public void setRegistedCourseID(String registedCourseID) {
        this.registedCourseID = registedCourseID;
    }

    public String getInfo() {
        return String.format("%s\t%s", userProgressID, registedCourseID);
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.validCodeUserProgress(userProgressID)){
            throw new InvalidDataException("UserProgress ID must be UPYYY with YYY are numbers");
        }
        if (!ObjectUtils.validCodeRegistedCourse(registedCourseID)){
            throw new InvalidDataException("RegistedCourse ID must be RCYYY with YYY are numbers");
        }
    }
}
