package model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    private List<Meal> meals;

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

    public String returnItem(int index) {
        String meal;
        meal = meals.get(index).getMealType() + ": " + meals.get(index).getFoodName()
                + " - " + meals.get(index).getCalories() + " calories";
        return meal;
    }
}
