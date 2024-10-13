
package model.sub;

import java.util.Date;
import java.util.List;
import service.WorkoutService;

public class PracticeDay {
    private String practiceDayId;
    private Date date;
    private Nutrition nutrition;
    private WorkoutService workoutService;

    public PracticeDay() {
    }

    public PracticeDay(String practiceDayId, Date date, Nutrition nutrition, List<Workout> workouts) {
        this.practiceDayId = practiceDayId;
        this.date = date;
        this.nutrition = nutrition;
        for(Workout workout: workouts){
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

}
