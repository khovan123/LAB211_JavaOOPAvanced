package model;

import exception.InvalidDataException;
import java.text.ParseException;
import utils.ObjectUtils;

public class Nutrition {
    
    private int nutritionId;
    private double calories;
    private int practicalDayId;
    
    public Nutrition() {
    }
    
    public Nutrition(String nutritionId, String calories, String practicalDayId) throws InvalidDataException, ParseException {
        this.setNutritionId(nutritionId);
        this.setCalories(calories);
        this.setPracticalDayId(practicalDayId);
        this.runValidate();
    }
    
    public int getNutritionId() {
        return nutritionId;
    }
    
    public void setNutritionId(String nutritionId)throws ParseException {
        this.nutritionId = Integer.parseInt(nutritionId);
    }
    
    public double getCalories() {
        return calories;
    }
    
    public void setCalories(String calories) throws InvalidDataException {
        try {
            this.calories = Double.parseDouble(calories);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Calories must be a positive number");
        }
    }
    
    public int getPracticalDayId() {
        return practicalDayId;
    }
    
    public void setPracticalDayId(String practicalDayId) throws ParseException{
        this.practicalDayId = Integer.parseInt(practicalDayId);
    }
    
    public String getInfo() {
        return String.format("%s", getCalories());
    }
    
    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.validDouble(String.valueOf(calories))) {
            throw new InvalidDataException("Calories must be a positive number");
        }
    }
}
