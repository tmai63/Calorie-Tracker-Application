package model;

import org.json.JSONObject;

// Represents a food having a meal type (breakfast, lunch, etc.), name of the food, and number of calories
public class Food {

    protected int calories;     // number of calories
    protected String foodName;  // name of the food
    protected String mealType;  // which meal this food was for
    protected int mealOrder; // used to sort order when added to day: breakfast - 1, lunch - 2, dinner - 3, snack - 4


    // REQUIRES: mealType to be one of: "Breakfast", "Lunch", "Dinner", or "Snack"
    //           foodName has non-zero length
    // EFFECTS: mealType on food is set to mealType; name of the food is set to foodName;
    //              and number of calories is set to calories
    public Food(String mealType, String foodName, int calories) {
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
        if (mealType.equals("Breakfast")) {
            mealOrder = 1;
        } else if (mealType.equals("Lunch")) {
            mealOrder = 2;
        } else if (mealType.equals("Dinner")) {
            mealOrder = 3;
        } else if (mealType.equals("Snack")) {
            mealOrder = 4;
        }
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

    // EFFECTS: returns mealOrder for sorting
    public int getMealOrder() {
        return mealOrder;
    }

    // EFFECTS: returns a list of json objects
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Meal", mealType);
        json.put("Name", foodName);
        json.put("Calories", calories);
        //json.put("Date Eaten",today);
        return json;
    }

}
