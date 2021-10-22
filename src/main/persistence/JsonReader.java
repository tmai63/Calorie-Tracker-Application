package persistence;

import model.Day;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import model.Food;
import org.json.*;

public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Day read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDay(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Day parseDay(JSONObject jsonObject) {
        String date = jsonObject.getString("Date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(date,formatter);
        Day day = new Day(date1);
        addDay(day, jsonObject);
        setCalories(day, jsonObject);
        return day;
    }

    private void addDay(Day day, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Meals");
        for (Object json : jsonArray) {
            JSONObject nextDay = (JSONObject) json;
            addFood(day, nextDay);
        }
    }

    private void addFood(Day day, JSONObject jsonObject) {
        String meal = jsonObject.getString("Meal");
        String foodName = jsonObject.getString("Name");
        int calories = jsonObject.getInt("Calories");
        Food food = new Food(meal,foodName,calories);
        day.addMeal(food);
    }

    private void setCalories(Day day, JSONObject jsonObject) {
        int calorieTarget = jsonObject.getInt("Calorie Target");
        day.setCalorieTarget(calorieTarget);
    }


}

