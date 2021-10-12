package model;

import java.util.ArrayList;
import java.util.List;


// Represents a day, which keeps track of the number of foods eaten over one day
public class Day {

    private List<Food> foods; // list of foods eaten

    // EFFECTS: creates new ArrayList of foods to store items eaten
    public Day() {
        foods = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a food item to the list of foods
    public void addMeal(Food food) {
        foods.add(food);
    }

    // EFFECTS: loops through the food list and returns the sum of total calories
    public int returnCalories() {
        int totalCals = 0;
        for (Food food : foods) {
            totalCals += food.getCalories();
        }
        return totalCals;
    }

    // EFFECTS: returns the number of items in the foods list
    public int numItems() {
        return foods.size();
    }

    // REQUIRES: index >= 0; foods has to be non-empty
    // EFFECTS: returns an item in the foods list in the following String format:
    //          mealType: foodName - calories (e.g. Breakfast: Waffles - 300)
    public String returnItem(int index) {
        String meal;
        meal = foods.get(index).getFoodType() + ": " + foods.get(index).getFoodName()
                + " - " + foods.get(index).getCalories() + " calories";
        return meal;
    }
}
