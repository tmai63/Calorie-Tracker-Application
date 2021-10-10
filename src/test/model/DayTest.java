package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DayTest {

    private Day day;

    @BeforeEach
    void runBefore() {
        day = new Day();
        Meal hamburger = new Meal("Lunch","Hamburger",600);
        Meal steak = new Meal("Dinner","Steak",700);

    }

    @Test
    void testAddMeal() {

    }


}
