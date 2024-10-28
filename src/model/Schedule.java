package model;

import exception.InvalidDataException;

import java.util.TreeSet;

import service.PracticalDayService;
import utils.ObjectUtils;

import java.util.TreeSet;

public class Schedule {
    private String scheduleId;
    private String userProgressId;

    public Schedule(String scheduleId, String userProgressId) {
        this.scheduleId = scheduleId;
        this.userProgressId = userProgressId;
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


    public String getInfo() {
        return String.format("%s\t%s\t%s", scheduleId, userProgressId);
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.validCodeSchedule(scheduleId)){
            throw new InvalidDataException("Schedule ID must be RCYYY with YYY are numbers");
        }
        if (!ObjectUtils.validCodeUserProgress(userProgressId)){
            throw new InvalidDataException("UserProgress ID must be UPYYY with YYY are numbers");
        }
    }

}
