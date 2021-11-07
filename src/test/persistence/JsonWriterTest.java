package persistence;

import model.Day;
import model.DayManager;
import model.Food;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

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
    void testEmptyDayManager() {
        try {
            DayManager dm = new DayManager();
            JsonWriter writer = new JsonWriter("./data/emptyDayManager.json");
            writer.open();
            writer.write(dm);
            writer.close();


            JsonReader reader = new JsonReader("./data/emptyDayManager.json");
            dm = reader.read();
            assertEquals(LocalDate.now(),dm.returnDate());
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
            JsonWriter writer = new JsonWriter("./data/fullDayManager.json");
            writer.open();
            writer.write(day);
            writer.close();

            JsonReader reader = new JsonReader("./data/fullDayManager.json");
            String result = "Breakfast: Bagel - 200 calories";

            day = reader.read();
            assertEquals(LocalDate.now(),day.returnDate());
            assertEquals(1,day.numItems());
            assertEquals(1500,day.returnCalorieTarget());
            assertEquals(200,day.returnCalories());
            assertEquals(result, day.returnItem(0));
        } catch (IOException e) {
            fail("No exception should have been thrown");
        }
    }

}
