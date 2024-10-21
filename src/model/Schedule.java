package model;

import exception.InvalidDataException;
import service.PracticalDayService;

public class Schedule {

    private String userProgressId;
    private PracticalDayService practicalDayService;

    public Schedule(String userProgressId, PracticalDayService practicalDayService) {
        this.userProgressId = userProgressId;
        this.practicalDayService = practicalDayService;
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

    public PracticalDayService getPracticalDayService() {
        return practicalDayService;
    }

    public String getInfo() {
        return null;
    }

    public void runValidate() throws InvalidDataException {
        if (userProgressId == null || userProgressId.isEmpty()) {
            throw new InvalidDataException("User Progress ID cannot be null or empty.");
        }
    }

}
