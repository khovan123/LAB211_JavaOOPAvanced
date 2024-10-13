
package model.sub;

public class Nutrition {
    private String nutritionId;
    private double calories;

    public Nutrition() {
    }

    public Nutrition(String nutritionId, double calories) {
        this.nutritionId = nutritionId;
        this.calories = calories;
    }

    public String getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(String nutritionId) {
        this.nutritionId = nutritionId;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
