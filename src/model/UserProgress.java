package model;

import exception.InvalidDataException;
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

    public String getInfo() {
        return String.format("User ID: %s, Schedule Service: %s", getUserId(), scheduleService.toString());
    }

    public void runValidate() throws InvalidDataException {
        if (coursePacketId == null || coursePacketId.isEmpty()) {
            throw new InvalidDataException("Course Packet ID cannot be null or empty.");
        }
    }
}
