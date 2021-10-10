package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DayTest {

    private Day day;
    Meal hamburger;
    Meal steak;

    @BeforeEach
    void runBefore() {
        day = new Day();
        hamburger = new Meal("Lunch","Hamburger",600);
        steak = new Meal("Dinner","Steak",700);
    }

    @Test
    void testAddMeal() {
        assertEquals(0,day.numItems());
        day.addMeal(hamburger);
        day.addMeal(steak);
        assertEquals(2,day.numItems());
    }

    @Test
    void testReturnCalories() {
        day.addMeal(hamburger);
        assertEquals(hamburger.calories,day.returnCalories());
        day.addMeal(steak);
        assertEquals(hamburger.calories+ steak.calories, day.returnCalories());
    }

    @Test
    void testNumItems() {
        day.addMeal(hamburger);
        day.addMeal(steak);
        assertEquals(2,day.numItems());
    }

    @Test
    void testReturnItems() {
        String result = "Lunch: Hamburger - 600 calories";
        day.addMeal(hamburger);
        assertEquals(result, day.returnItem(0));
    }

}
