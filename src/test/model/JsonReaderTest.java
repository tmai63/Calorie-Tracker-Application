package model;

import model.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Day day = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDay.json");
        try {
            Day day = reader.read();
            assertEquals(LocalDate.now(), day.returnDate());
            assertEquals(0, day.numItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderFullDay.json");
        try {
            Day day = reader.read();
            assertEquals(LocalDate.now(), day.returnDate());
            assertEquals(5, day.numItems());
            assertEquals(1500, day.returnCalorieTarget());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
