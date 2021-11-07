package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.*;

// Represents a calendar and manages the list of days the user wants to track foods for
public class DayManager {

    private Map<LocalDate, Day> calendar;

    // EFFECTS: creates a new TreeMap to store the list of days
    public DayManager() {
        calendar = new TreeMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a Day object into the calendar
    public void addDay(LocalDate date, Day day) {
        calendar.put(date, day);
    }

    // EFFECTS: returns the Day object associated with the date
    public Day getDay(LocalDate date) {
        return calendar.get(date);
    }

    // EFFECTS: returns if a Day object is associated with the date
    public boolean existsDay(LocalDate date) {
        return calendar.containsKey(date);
    }

    // TODO: tbd
    public Map returnCal() {
        return calendar;
    }

    // EFFECTS: returns a json Object with this DayManager
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Date", daysToJson());
        return json;
    }

    // EFFECTS: returns a Json array with Days stored in calendar
    private JSONArray daysToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Day day: calendar.values()) {
            jsonArray.put(day.toJson());
        }
        return jsonArray;
    }

}
