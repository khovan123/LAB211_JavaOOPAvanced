package model;

import exception.InvalidDataException;
import utils.ObjectUtils;


public class Nutrition {
    
    private String nutritionId;
    private double calories;
    private String practicalDayId;

    public Nutrition() {
    }
  
    public Nutrition(String nutritionId, String calories) throws InvalidDataException {
        this.nutritionId = nutritionId;
        this.setCalories(calories);
        this.runValidate();
    }

    public String getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(String practiceDayId) {
        this.nutritionId = nutritionId;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = Double.parseDouble(calories);
    }

    public String getPracticalDayId() {
        return practicalDayId;
    }

    public void setPracticalDayId(String practicalDayId) {
        this.practicalDayId = practicalDayId;
    }

    @Override
    public String toString() {
        return String.format("%-15s | %-15.0s", nutritionId, calories);
    }

    public String getInfo() {
        return String.format("| %-10s | %-8f | %-10s |", getNutritionId(), getCalories(), getPracticalDayId());
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.validCodeNutrition(nutritionId)){
            throw new InvalidDataException("Nutrition ID must be NTYYY with YYY are numbers");
        }
        if(!ObjectUtils.validCodePracticalDay(practicalDayId)){
            throw new InvalidDataException("Practical ID must be PDYYY with YYY are numbers");
        }
        if(!ObjectUtils.validDouble(String.valueOf(calories))){
            throw new InvalidDataException("Calories must be a positive number");
        }
    }
}
