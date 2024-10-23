package model;

import exception.InvalidDataException;
import java.util.TreeSet;
import service.PracticalDayService;

import java.util.TreeSet;

public class Schedule {

    private String userProgressId;
    private final PracticalDayService practicalDayService;

    public Schedule(String userProgressId, TreeSet<PracticalDay>  practicalDayTreeSet) {
        this.userProgressId = userProgressId;
        this.practicalDayService = new PracticalDayService(practicalDayTreeSet);
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
        return String.format("%s\t%s", userProgressId, practicalDayService);
    }

    public void runValidate() throws InvalidDataException {
        if (userProgressId == null || userProgressId.isEmpty()) {
            throw new InvalidDataException("User Progress ID cannot be null or empty.");
        }
    }

}
