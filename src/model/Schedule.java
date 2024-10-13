package model;

import service.PracticalDayService;

public class Schedule {
    private String userProgressId;
    private PracticalDayService practiceDayService;

    public String getScheduleId() {
        return userProgressId;
    }

    public void setScheduleId(String userProgressId) {
        this.userProgressId = userProgressId;
    }

    public PracticalDayService getPracticeDayService() {
        return practiceDayService;
    }

}
