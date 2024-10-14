package model;

import exception.InvalidDataException;
import service.PracticalDayService;

public class Schedule {

    private String userProgressId;
    private PracticalDayService practicalDayService;

    public String getScheduleId() {
        return userProgressId;
    }

    public void setScheduleId(String userProgressId) {
        this.userProgressId = userProgressId;
    }

    public PracticalDayService getPracticeDayService() {
        return practicalDayService;
    }

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }

}
