package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new TrackerApp();
        } catch (FileNotFoundException e) {
            System.out.println("No file found!");
        }
    }
}
