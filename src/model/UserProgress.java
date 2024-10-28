package model;

import exception.IOException;
import exception.InvalidDataException;

import java.util.TreeSet;

import service.ScheduleService;
import utils.ObjectUtils;

public class UserProgress {

    private String userProgressId;
    private String registedCourseID;

    public UserProgress(String userProgressId, String registedCourseID) {
        this.userProgressId = userProgressId;
        this.registedCourseID = registedCourseID;
    }

    public String getUserProgressId() {
        return userProgressId;
    }

    public void setUserProgressId(String userProgressId) {
        this.userProgressId = userProgressId;
    }

    public String getRegistedCourseID() {
        return registedCourseID;
    }

    public void setRegistedCourseID(String registedCourseID) {
        this.registedCourseID = registedCourseID;
    }

    public String getInfo() {
        return String.format("%s\t%s", userProgressId, registedCourseID);
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.validCodeUserProgress(userProgressId)){
            throw new InvalidDataException("UserProgress ID must be UPYYY with YYY are numbers");
        }
        if (!ObjectUtils.validCodeRegistedCourse(registedCourseID)){
            throw new InvalidDataException("RegistedCourse ID must be RCYYY with YYY are numbers");
        }
    }
}
