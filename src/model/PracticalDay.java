package model;

import exception.InvalidDataException;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import service.WorkoutService;
import utils.GlobalUtils;

public class PracticalDay implements Comparator<PracticalDay> {

    private String practicalDayId;
    private Date practiceDate;
    private Nutrition nutrition;
    private WorkoutService workoutService;
    private String scheduleId;

    public PracticalDay() {
        this.workoutService = new WorkoutService();
    }

    public PracticalDay(String practicalDayId, String practiceDate, Nutrition nutrition, String scheduleId) throws InvalidDataException {
        this.practicalDayId = practicalDayId;
        this.setPracticeDate(practiceDate);
        this.nutrition = nutrition;
        this.scheduleId = scheduleId;
        this.workoutService = new WorkoutService();
    }

    public PracticalDay(String practicalDayId, String practiceDate, Nutrition nutrition, List<Workout> workoutList, String scheduleId) throws InvalidDataException {
        this.practicalDayId = practicalDayId;
        this.setPracticeDate(practiceDate);
        this.nutrition = nutrition;
        this.workoutService = new WorkoutService(workoutList);
        this.scheduleId = scheduleId;
    }

    public String getPracticeDayId() {
        return practicalDayId;
    }

    public void setPracticeDayId(String practicalDayId) {
        this.practicalDayId = practicalDayId;
    }

    public Date getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) throws InvalidDataException {
        this.practiceDate = GlobalUtils.getDate(practiceDate);
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public WorkoutService getWorkoutService() {
        return workoutService;
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
        return String.format("");
    }

    public void runValipraticeDate() throws InvalidDataException {

    }

}
