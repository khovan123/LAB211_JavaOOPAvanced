package model;

import exception.InvalidDataException;

import java.util.TreeSet;

import service.PracticalDayService;

import java.util.TreeSet;

public class Schedule {
    private String scheduleId;
    private String userProgressId;

    public Schedule(String scheduleId, String userProgressId) {
        this.scheduleId = scheduleId;
        this.userProgressId = userProgressId;
    }

//    public Schedule(String scheduleId, String userProgressId, TreeSet<PracticalDay> practicalDayTreeSet) {
//        this.scheduleId = scheduleId;
//        this.userProgressId = userProgressId;
//        this.practicalDayService = new PracticalDayService(practicalDayTreeSet);
//        }

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


    public String getInfo() {
        return String.format("%s\t%s\t%s", scheduleId, userProgressId);
    }

    public void runValidate() throws InvalidDataException {
        if (userProgressId == null || userProgressId.isEmpty()) {
            throw new InvalidDataException("User Progress ID cannot be null or empty.");
        }
    }

}
