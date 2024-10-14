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
        return String.format("User Progress ID: %s, Practical Day Service: %s",userProgressId, practiceDayService.toString());
    }

    public void runValidate() throws InvalidDataException {
        if (userProgressId == null || userProgressId.isEmpty()) {
            throw new InvalidDataException("User Progress ID cannot be null or empty.");
        }
    }

}
