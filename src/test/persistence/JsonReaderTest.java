package persistence;

import model.Day;
import model.DayManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            DayManager dm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDayManager.json");
        try {
            DayManager dm = reader.read();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = "2021-10-28";
            LocalDate date1 = LocalDate.parse(date,formatter);
            Day day = dm.getDay(date1);
            assertEquals(date1, day.returnDate());
            assertEquals(0, day.numItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderFullDayManager.json");
        try {
            DayManager dm = reader.read();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = "2021-10-28";
            LocalDate date1 = LocalDate.parse(date,formatter);
            Day day = dm.getDay(date1);
            assertEquals(date1, day.returnDate());
            assertEquals(date1, day.returnDate());
            assertEquals(5, day.numItems());
            assertEquals(1500, day.returnCalorieTarget());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
