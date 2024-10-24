package model;

import exception.IOException;
import exception.InvalidDataException;
import java.util.TreeSet;
import service.ScheduleService;

public class UserProgress {

    private String coursePacketId;
    private ScheduleService scheduleService;

    public UserProgress(){
        this.scheduleService = new ScheduleService();
    }

    public UserProgress(String coursePacketId,TreeSet<Schedule> scheduleTreeSet){
        this.coursePacketId = coursePacketId;
        this.scheduleService = new ScheduleService(scheduleTreeSet);
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
        return String.format("%s", coursePacketId);
    }

    public void runValidate() throws InvalidDataException {
        if (coursePacketId == null || coursePacketId.isEmpty()) {
            throw new InvalidDataException("Course Packet ID cannot be null or empty.");
        }
    }
}
