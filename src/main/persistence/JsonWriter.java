package persistence;

import model.Day;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {

    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    public void write(Day day) {
        JSONObject json = day.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
