package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    Food hamburger;

    @BeforeEach
    void runBefore() {
        hamburger = new Food("Lunch", "Hamburger", 600);
    }

    @Test
    void testCalories() {
        assertEquals(600,hamburger.getCalories());
    }

    @Test
    void testMealType() {
        assertEquals("Lunch", hamburger.getFoodType());
    }

    @Test
    void testFoodName() {
        assertEquals("Hamburger", hamburger.getFoodName());
    }
}