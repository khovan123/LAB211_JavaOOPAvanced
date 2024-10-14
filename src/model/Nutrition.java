package model;

import exception.InvalidDataException;

public class Nutrition {

    private String practiceDayId;
    private double calories;

    public Nutrition() {
    }

    public Nutrition(String practiceDayId, double calories) {
        this.practiceDayId = practiceDayId;
        this.calories = calories;
    }

    public String getNutritionId() {
        return practiceDayId;
    }

    public void setNutritionId(String practiceDayId) {
        this.practiceDayId = practiceDayId;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }
}
