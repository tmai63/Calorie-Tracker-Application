package model;

import java.util.ArrayList;
import java.util.List;

public class Meal {

    protected int calories;
    protected String foodName;
    protected String mealType;
    private List<Meal> meals;

    public Meal(String mealType, String foodName, int calories) {
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
    }

    // EFFECTS: returns the number of calories
    public int getCalories() {
        return calories;
    }

    // TODO: Look up instanceof


}
