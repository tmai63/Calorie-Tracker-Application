package model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    private List<Meal> meals;
    private int day;
    private int calorieTarget;

    public Day() {
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public int returnCalories() {
        int totalCals = 0;
        for (Meal meal: meals) {
            totalCals += meal.calories;
        }
        return totalCals;
    }

    public int numItems() {
        return meals.size();
    }



    //meals = new ArrayList<Meal>();
}
