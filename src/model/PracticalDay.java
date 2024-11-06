package model;

import exception.*;
import java.text.ParseException;
import java.util.*;
import utils.*;

public class PracticalDay implements Comparable<PracticalDay> {

    private int practicalDayId;
    private Date practiceDate;
    private boolean done;
    private int scheduleId;

    public PracticalDay(String practicalDayId, String practiceDate, String done, String scheduleId) throws InvalidDataException, ParseException {
        this.setPracticalDayId(practicalDayId);
        this.setPracticeDate(practiceDate);
        this.setDone(done);
        this.setScheduleId(scheduleId);
        this.runValidate();
    }

    public int getPracticalDayId() {
        return practicalDayId;
    }

    public void setPracticalDayId(String practicalDayId) throws ParseException{
        this.practicalDayId = Integer.parseInt(practicalDayId);
    }

    public Date getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) throws InvalidDataException {
        this.practiceDate = GlobalUtils.dateParse(practiceDate);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(String done) throws InvalidDataException {
        this.done = GlobalUtils.booleanParse(done);
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) throws ParseException {
        this.scheduleId = Integer.parseInt(scheduleId);
    }

    @Override
    public int compareTo(PracticalDay other) {
        if (this.practiceDate == null || other.practiceDate == null) {
            throw new UnsupportedOperationException("Practice Date cannot be null");
        }
        return this.practiceDate.compareTo(other.practiceDate);
    }

    public String getInfo() {
        return String.format("%s\t%s\t%s", getPracticalDayId(), GlobalUtils.dateFormat(getPracticeDate()), (done?"Done":"Not yet"));
    }

    public void runValidate() throws InvalidDataException {
    }

}
