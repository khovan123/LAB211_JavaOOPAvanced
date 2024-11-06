package model;

import exception.InvalidDataException;
import java.text.ParseException;

public class Schedule {

    private int scheduleId;
    private int userProgressId;

    public Schedule(String scheduleId, String userProgressId) throws InvalidDataException, ParseException {
        this.setScheduleId(userProgressId);
        this.setUserProgressId(userProgressId);
        this.runValidate();
    }

    public Schedule() {

    }

    public int getUserProgressId() {
        return userProgressId;
    }

    public void setUserProgressId(String userProgressId)throws ParseException {
        this.userProgressId = Integer.parseInt(userProgressId);
    }

    public int getScheduleId() {
        return userProgressId;
    }

    public void setScheduleId(String userProgressId) throws ParseException{
        this.userProgressId = Integer.parseInt(userProgressId);
    }

    public String getInfo() {
        return String.format("%s\t%s", scheduleId, userProgressId);
    }

    public void runValidate() throws InvalidDataException {

    }

}
