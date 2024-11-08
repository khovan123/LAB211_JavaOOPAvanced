package model;

import exception.InvalidDataException;
import java.text.ParseException;

public class UserProgress {

    private int userProgressID;
    private double completed;
    private int registedCourseID;

    public UserProgress(String userProgressID, String registedCourseID) throws InvalidDataException, ParseException {
        this.setUserProgressID(userProgressID);
        this.setRegistedCourseID(registedCourseID);
        this.completed = 0.0;
        this.runValidate();
    }

    public UserProgress() {

    }

    public int getUserProgressID() {
        return userProgressID;
    }

    public void setUserProgressID(String userProgressID)throws ParseException {
        this.userProgressID = Integer.parseInt(userProgressID);
    }

    public int getRegistedCourseID() {
        return registedCourseID;
    }

    public void setRegistedCourseID(String registedCourseID) throws ParseException{
        this.registedCourseID = Integer.parseInt(registedCourseID);
    }

    public double getCompleted() {
        return completed;
    }

    public void setCompleted(double completed) {
        this.completed = completed;
    }

    public String getInfo() {
        return String.format("%s\t%s\t%f", userProgressID, registedCourseID, completed);
    }

    public void runValidate() throws InvalidDataException {

    }
}
