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
    }

    public PracticalDay(String practicalDayId, String practiceDate, Nutrition nutrition, String scheduleId) throws InvalidDataException {
        this.practicalDayId = practicalDayId;
        this.setPracticeDate(practiceDate);
        this.nutrition = nutrition;
        this.scheduleId = scheduleId;
    }

    public PracticalDay(String practicalDayId, String practiceDate, Nutrition nutrition, List<Workout> workoutList, String scheduleId) throws InvalidDataException {
        this.practicalDayId = practicalDayId;
        this.setPracticeDate(practiceDate);
        this.nutrition = nutrition;
        for (Workout workout : workoutList) {
            this.workoutService.add(workout);
        }
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
    public int compare(PracticalDay o1, PracticalDay o2) {
        //compare 2 date
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getInfo() {
        return String.format("%-15s | %-15s | %-15s | %-15s", practicalDayId, GlobalUtils.getDateString(practiceDate), nutrition.getCalories(), workoutService);
    }

    public void runValipraticeDate() throws InvalidDataException {

    }

}
