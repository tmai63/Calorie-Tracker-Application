package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    Meal hamburger;

    @BeforeEach
    void runBefore() {
        hamburger = new Meal("Lunch", "Hamburger", 600);
    }

    @Test
    void testCalories() {
        assertEquals(600,hamburger.getCalories());
    }

    @Test
    void testMealType() {
        assertEquals("Lunch", hamburger.getMealType());
    }

    @Test
    void testFoodName() {
        assertEquals("Hamburger", hamburger.getFoodName());
    }
}