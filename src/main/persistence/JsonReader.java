package persistence;

import model.Day;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Day read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDay(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Day parseDay(JSONObject jsonObject) {
        String date = jsonObject.getString("name");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDate date1 = LocalDate.parse(date,formatter);
        Day day = new Day(date1);
        addFood(day, jsonObject);
        return day;
    }

    private void addFood(Day day, JSONObject jsonObject) {


    }

}

