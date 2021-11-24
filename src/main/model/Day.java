package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.time.LocalDate;


// Represents a day, which keeps track of the number of foods eaten over one day
public class Day {

    private LocalDate date;
    private List<Food> foods; // list of foods eaten
    private int calorieTarget = 0;

//    // EFFECTS: creates new ArrayList of foods to store items eaten for current day
//    public Day() {
//        foods = new ArrayList<>();
//        date = LocalDate.now();
//    }

    // EFFECTS: creates new Arraylist of foods for a certain date
    public Day(LocalDate date) {
        foods = new ArrayList<>();
        this.date = date;
        EventLog.getInstance().logEvent(new Event("Added " + date + " to Calendar"));
    }

    // MODIFIES: this
    // EFFECTS: adds a food item to the list of foods
    public void addMeal(Food food) {
        foods.add(food);
        sortFoods();
        EventLog.getInstance().logEvent(new Event("Added " + food.getFoodName() + " to " + date));
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

    // REQUIRES: calorieTarget >= 0
    // EFFECTS: sets calorieTarget
    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
        EventLog.getInstance().logEvent(new Event("Set calorie target for " + date + " to " + calorieTarget));
    }

    // EFFECTS: returns calorieTarget
    public int returnCalorieTarget() {
        return calorieTarget;
    }

    // EFFECTS: returns the date for this day
    public LocalDate returnDate() {
        return date;
    }

    // EFFECTS: sorts foods by meal - breakfast -> lunch -> dinner ->
    public void sortFoods() {
        Collections.sort(foods, Comparator.comparing(Food::getMealOrder));
    }

    // EFFECTS: removes the item at index
    public void removeItem(int index) {
        EventLog.getInstance().logEvent(new Event("Removed " + foods.get(index).getFoodName()
                + " from " + date));
        foods.remove(index - 1);
    }

    // EFFECTS: returns a json Object with the fields of this Day
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Date",date);
        json.put("Calorie Target", calorieTarget);
        json.put("Meals",mealsToJson());
        return json;
    }

    // EFFECTS: returns a Json array with meals stored in this day
    private JSONArray mealsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Food f: foods) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}
