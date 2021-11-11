package persistence;

import model.Day;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import model.DayManager;
import model.Food;
import org.json.*;

// Represents a reader that reads DayManager from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads DayManager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DayManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDayManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // MODIFIES: DayManager
    // EFFECTS: creates DayManager from JSON object and returns it
    private DayManager parseDayManager(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Calendar");
        DayManager dm = new DayManager();
        for (Object json : jsonArray) {
            JSONObject nextDay = (JSONObject) json;
            dm.addDay(parseDay(nextDay).returnDate(), parseDay(nextDay));
        }
        return dm;
    }

    // MODIFIES: Day
    // EFFECTS: creates each Day from the JSONArray parsed from DayManager
    private Day parseDay(JSONObject jsonObject) {
        String date = jsonObject.getString("Date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(date,formatter);
        Day day = new Day(date1);
        addDay(day, jsonObject);
        setCalories(day, jsonObject);
        return day;
    }

    // MODIFIES: Day
    // EFFECTS: parses Meals from JSON object and adds them to Day
    private void addDay(Day day, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Meals");
        for (Object json : jsonArray) {
            JSONObject nextDay = (JSONObject) json;
            addFood(day, nextDay);
        }
    }

    // MODIFIES: Food, Day
    // EFFECTS: creates food from JSON object and adds them to Day
    private void addFood(Day day, JSONObject jsonObject) {
        String meal = jsonObject.getString("Meal");
        String foodName = jsonObject.getString("Name");
        int calories = jsonObject.getInt("Calories");
        Food food = new Food(meal,foodName,calories);
        day.addMeal(food);
    }

    // MODIFIES: Day
    // EFFECTS: parses calorieTarget from JSON object and adds it to Day
    private void setCalories(Day day, JSONObject jsonObject) {
        int calorieTarget = jsonObject.getInt("Calorie Target");
        day.setCalorieTarget(calorieTarget);
    }


}

