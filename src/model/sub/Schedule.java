package model.sub;

import service.PracticeDayService;

public class Schedule {
    private String coursePacketId;
    private PracticeDayService practiceDayService;

    public String getScheduleId() {
        return coursePacketId;
    }

    public void setScheduleId(String coursePacketId) {
        this.coursePacketId = coursePacketId;
    }

    public PracticeDayService getPracticeDayService() {
        return practiceDayService;
    }

}
