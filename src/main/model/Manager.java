package model;

import java.util.*;

public class Manager {

    private Map<Day, Day> calendar;

    public Manager() {
        calendar = new HashMap<>();
    }

    public void addDay(Day day) {
        calendar.put(day, day);
    }

    public Day getDay(Day day) {
        return calendar.get(day);
    }

}
