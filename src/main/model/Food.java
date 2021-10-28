package model;

import org.json.JSONObject;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

// Represents a food having a meal type (breakfast, lunch, etc.), name of the food, and number of calories
public class Food {

    protected int calories;     // number of calories
    protected String foodName;  // name of the food
    protected String mealType;  // which meal this food was for
    private LocalDate today;
    protected String date;


    // REQUIRES: mealType to be one of: "Breakfast", "Lunch", "Dinner", or "Snack"
    //           foodName has non-zero length
    // EFFECTS: mealType on food is set to mealType; name of the food is set to foodName;
    //              and number of calories is set to calories

    public Food(String mealType, String foodName, int calories) {
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
        //today = LocalDate.now();
    }

    // EFFECTS: returns the number of calories
    public int getCalories() {
        return calories;
    }

    // EFFECTS: returns the name of the food
    public String getFoodName() {
        return foodName;
    }

    // EFFECTS: returns the meal the food was eaten for
    public String getFoodType() {
        return mealType;
    }

    // EFFECTS: returns a list of json objects
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Meal", mealType);
        json.put("Name", foodName);
        json.put("Calories",calories);
        //json.put("Date Eaten",today);
        return json;
    }

}
