package model;

import exception.InvalidDataException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import service.WorkoutService;

public class PracticalDay implements Comparator<PracticalDay> {

    private String practiceDayId;
    private Date date;
    private Nutrition nutrition;
    private WorkoutService workoutService;
    private String scheduleId;

    public PracticalDay() {
    }

    public PracticalDay(String practiceDayId, Date date, Nutrition nutrition, String scheduleId) {
        this.practiceDayId = practiceDayId;
        this.date = date;
        this.nutrition = nutrition;
        this.scheduleId = scheduleId;
    }

    public PracticalDay(String practiceDayId, Date date, Nutrition nutrition, List<Workout> workouts, String scheduleId) {
        this.practiceDayId = practiceDayId;
        this.date = date;
        this.nutrition = nutrition;
        for (Workout workout : workouts) {
            this.workoutService.add(workout);
        }
    }

    public String getPracticeDayId() {
        return practiceDayId;
    }

    public void setPracticeDayId(String practiceDayId) {
        this.practiceDayId = practiceDayId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }

}
