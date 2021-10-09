package model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    protected List<Meal> meals;

    public Day() {
        meals = new ArrayList<>();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public int returnCalories() {
        int totalCals = 0;
        for (Meal meal: meals) {
            totalCals += meal.getCalories();
        }
        return totalCals;
    }

    public int numItems() {
        return meals.size();
    }

    public void returnItems() {
        for (Meal meal: meals) {
            System.out.println(meal.getMealType() + ": " + meal.foodName + "-" + meal.getCalories() + " calories");
        }
    }



    //meals = new ArrayList<Meal>();
}
