package model;

import service.ScheduleService;

public class UserProgress {

    private String coursePacketId;
    private ScheduleService scheduleService;

    public UserProgress() {
        this.scheduleService = new ScheduleService();
    }

    public UserProgress(String coursePacketId) {
        this.coursePacketId = coursePacketId;
        this.scheduleService = new ScheduleService();
    }

    public String getUserId() {
        return coursePacketId;
    }

    public void setUserId(String coursePacketId) {
        this.coursePacketId = coursePacketId;
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }
}
