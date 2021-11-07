package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayManagerTest {

    private DayManager dayManager;

    @BeforeEach
    void testConstructor() {
        dayManager = new DayManager();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dayManager.addDay(LocalDate.parse("2021-05-24"), new Day(LocalDate.parse("2021-05-24")));
        dayManager.addDay(LocalDate.parse("2020-01-02"), new Day(LocalDate.parse("2020-01-02")));
    }

//    @Test
//    void addDates() {
//        dayManager.addDay(LocalDate.parse("2020-01-02"), new Day(LocalDate.parse("2020-01-02")));
//        dayManager.addDay(LocalDate.parse("2008-01-02"), new Day(LocalDate.parse("2008-01-02")));
//        dayManager.addDay(LocalDate.parse("1950-01-02"), new Day(LocalDate.parse("1950-01-02")));
//        System.out.println(dayManager.returnCal().keySet());
//    }



}
