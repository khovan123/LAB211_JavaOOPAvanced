package model;

import exception.InvalidDataException;

import java.util.Comparator;
import java.util.Date;

import utils.GlobalUtils;
import utils.ObjectUtils;

public class PracticalDay implements Comparator<PracticalDay> {

    private String practicalDayId;
    private Date practiceDate;
    private String scheduleId;

    public PracticalDay(String practicalDayId, String practiceDate, String scheduleId) throws InvalidDataException {
        this.practicalDayId = practicalDayId;
        this.setPracticeDate(practiceDate);
        this.scheduleId = scheduleId;
        this.runValidate();
    }

    public String getPracticalDayId() {
        return practicalDayId;
    }

    public void setPracticalDayId(String practicalDayId) {
        this.practicalDayId = practicalDayId;
    }

    public Date getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) throws InvalidDataException {
        this.practiceDate = GlobalUtils.getDate(practiceDate);
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public int compare(PracticalDay pd1, PracticalDay pd2) {
        if(pd1.getPracticeDate() == null || pd2.getPracticeDate() == null){
            throw new UnsupportedOperationException("Practice Date cannot be null");
        }
        return pd1.getPracticeDate().compareTo(pd2.getPracticeDate());
    }

    public String getInfo() {
        return String.format("| %-10s | %-15s | %-10s |", getPracticalDayId(), GlobalUtils.getDateString(getPracticeDate()), getScheduleId());
    }

    public void runValidate () throws InvalidDataException {
        if(!ObjectUtils.validCodePracticalDay(practicalDayId)){
            throw new InvalidDataException("Practical ID must be PDYYY with YYY are numbers");
        }
        if(!ObjectUtils.validCodeSchedule(scheduleId)){
            throw new InvalidDataException("Schedule ID must be SDYYY with YYY are numbers");
        }
    }

}
