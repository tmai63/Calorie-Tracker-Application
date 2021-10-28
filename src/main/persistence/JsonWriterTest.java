package persistence;

import model.Day;
import model.Food;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testInvalidWriteFile() {
        try {
            JsonWriter writer = new JsonWriter(".data/nonexistingfile.json");
            writer.open();
            fail("IO exception was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testEmptyDay() {
        try {
            Day day = new Day();
            JsonWriter writer = new JsonWriter("./data/emptyDay.json");
            writer.open();
            writer.write(day);
            writer.close();

            JsonReader reader = new JsonReader("./data/emptyDay.json");
            day = reader.read();
            assertEquals(LocalDate.now(),day.returnDate());
            assertEquals(0,day.numItems());
            assertEquals(0,day.returnCalorieTarget());
        } catch (IOException e) {
            fail("No exception should have been thrown");
        }
    }

    @Test
    void testFullDay() {
        try {
            Day day = new Day();
            day.setCalorieTarget(1500);
            day.addMeal(new Food("Breakfast","Bagel",200));
            JsonWriter writer = new JsonWriter("./data/fullDay.json");
            writer.open();
            writer.write(day);
            writer.close();

            JsonReader reader = new JsonReader("./data/fullDay.json");
            day = reader.read();
            assertEquals(LocalDate.now(),day.returnDate());
            assertEquals(1,day.numItems());
            assertEquals(1500,day.returnCalorieTarget());
            assertEquals(200,day.returnCalories());
        } catch (IOException e) {
            fail("No exception should have been thrown");
        }
    }

}
