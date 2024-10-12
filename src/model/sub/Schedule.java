package model.sub;

import java.util.Date;
import service.PracticeDayService;

public class Schedule {

    String scheduleId;
    PracticeDayService practiceDayService;
    String description;
    Date startDate;
    Date endDate;

    public Schedule(String scheduleId, PracticeDayService practiceDayService, String description, Date startDate, Date endDate) {
        this.scheduleId = scheduleId;
        this.practiceDayService = practiceDayService;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public PracticeDayService getPracticeDayService() {
        return practiceDayService;
    }

    public void setPracticeDayService(PracticeDayService practiceDayService) {
        this.practiceDayService = practiceDayService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String header = String.format("%-10s | %-20s | %-30s | %-15s | %-15s",
                "ID", "PracticeDay", "Description", "StartDate", "EndDate");
        String row = String.format("%-10s   %-20s   %-30s   %-15s   %-15s",
                scheduleId, practiceDayService, description, startDate, endDate);
        return header + "\n" + row;
    }

}
