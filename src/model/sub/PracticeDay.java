
package model.sub;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import service.WorkoutService;
import utils.GlobalUtils;

public class PracticeDay {
    private String practiceDayId;
    private Date praticeDate;
    private Nutrition nutrition;
    private WorkoutService workoutService;

    public PracticeDay() {
    }

    public PracticeDay(String practiceDayId, String practiceDate, Nutrition nutrition, List<Workout> workoutList) throws ParseException {
        this.practiceDayId = practiceDayId;
        this.setPracticeDate(practiceDate);
        this.nutrition = nutrition;
        for(Workout workout: workoutList){
            this.workoutService.add(workout);
        }
    }

    public String getPracticeDayId() {
        return practiceDayId;
    }

    public void setPracticeDayId(String practiceDayId) {
        this.practiceDayId = practiceDayId;
    }

    public Date getPracticeDate() {
        return praticeDate;
    }

    public void setPracticeDate(String practiceDate) throws ParseException {
        this.praticeDate = GlobalUtils.getDate(practiceDate);
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

    @Override
    public String toString(){
        return String.format("%-15s | %-15s | %-15s | %-15s", practiceDayId, GlobalUtils.getDateString(praticeDate), nutrition.getCalories(), workoutService);
    }

}
