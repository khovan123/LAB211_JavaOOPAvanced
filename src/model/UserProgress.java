package model;

import exception.IOException;
import exception.InvalidDataException;
import java.util.TreeSet;
import service.ScheduleService;

public class UserProgress {

    private String coursePacketId;
    private final Schedule schedule;

    public UserProgress(String coursePacketId, Schedule schedule){
        this.coursePacketId = coursePacketId;
        this.schedule = new Schedule(coursePacketId);
    }

    public String getUserId() {
        return coursePacketId;
    }

    public void setUserId(String coursePacketId) {
        this.coursePacketId = coursePacketId;
    }

    public Schedule getSchedule() {
        return schedule;
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
