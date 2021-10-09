package model;

import java.util.ArrayList;
import java.util.List;

public class Meal {

    protected int calories;
    protected String foodName;
    protected String mealType;

    public Meal(String mealType, String foodName, int calories) {
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
    }

    // EFFECTS: returns the number of calories
    public int getCalories() {
        return calories;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getMealType() {
        return mealType;
    }

    // TODO: Look up instanceof


}
