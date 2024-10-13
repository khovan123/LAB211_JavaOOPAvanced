package model;

import exception.InvalidDataException;
import service.PracticalDayService;

public class Schedule {

    private String userProgressId;
    private PracticalDayService practiceDayService;

    public Schedule(String userProgressId, PracticalDayService practiceDayService) {
        this.userProgressId = userProgressId;
        this.practiceDayService = practiceDayService;
    }
    public String getUserProgressId() {
        return userProgressId;
    }

    public void setUserProgressId(String userProgressId) {
        this.userProgressId = userProgressId;
    }

    public String getScheduleId() {
        return userProgressId;
    }

    public void setScheduleId(String userProgressId) {
        this.userProgressId = userProgressId;
    }

    public PracticalDayService getPracticeDayService() {
        return practiceDayService;
    }

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }

}
