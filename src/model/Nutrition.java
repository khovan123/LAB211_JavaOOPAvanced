package model;

import exception.InvalidDataException;

import utils.GlobalUtils;

import java.text.ParseException;
import java.util.Date;

public class Nutrition {

    private String practiceDayId;
    private double calories;

    public Nutrition() {
    }
  
    public Nutrition(String nutritionId, String calories) {
        this.nutritionId = nutritionId;
        this.setCalories(calories);
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

    public void setCalories(String calories) {
        this.calories = Double.parseDouble(calories);
    }

    @Override
    public String toString() {
        return String.format("%-15s | %-15.0s", nutritionId, calories);
    }

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }
}
