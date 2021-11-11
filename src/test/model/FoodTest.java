package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;

class FoodTest {

    Food hamburger;
    Food cereal;
    Food pizza;
    Food yogurt;

    @BeforeEach
    void runBefore() {
        cereal = new Food("Breakfast", "cereal", 200);
        hamburger = new Food("Lunch", "Hamburger", 600);
        pizza = new Food("Dinner", "Pizza", 700);
        yogurt = new Food("Snack", "Yogurt", 150);
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

    @Test
    void testFoodOrder() {
        assertEquals(2,hamburger.getMealOrder());
        assertEquals(1,cereal.getMealOrder());
        assertEquals(3,pizza.getMealOrder());
        assertEquals(4,yogurt.getMealOrder());
    }
}