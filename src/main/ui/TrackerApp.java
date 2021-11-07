package ui;

import model.Day;
import model.Food;
import model.DayManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


// Calorie tracker application
public class TrackerApp {

    private static final String JSON_STORE = "./data/calendar.json";
    private Scanner input;
    private DayManager dayManager;
    private Day currentDay;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the tracker application
    public TrackerApp() throws FileNotFoundException {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command;

        init();
        setDate();

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Have a nice day!");
    }

    // MODIFIES: this, currentDay
    // EFFECTS: gets input on the date which the user wants to make changes to
    private void setDate() {
        boolean keepGoing = true;
        String date;
        LocalDate date1;

        System.out.println("Enter the date you would like to add: (YYYY-MM-DD)");

        while (keepGoing) {
            try {
                date = input.next();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date1 = LocalDate.parse(date, formatter);
                setCurrentDay(date1);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date, please try again");
                input.nextLine();
            }
        }
    }

    // MODIFIES: currentDate
    // EFFECTS: changes the current Day
    private void setCurrentDay(LocalDate date) {
        if (dayManager.existsDay(date)) {
            currentDay = dayManager.getDay(date);
        } else {
            currentDay = new Day(date);
            dayManager.addDay(date, currentDay);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processCommand(String command) {
        switch (command) {
            case "c":
                setCalorieTarget();
                break;
            case "e":
                selectMeal();
                break;
            case "v":
                viewFoods();
                break;
            case "l":
                loadFoods();
                break;
            case "s":
                saveFoods();
                break;
            case "d":
                setDate();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void addMeal(String meal) {
        Food food;
        switch (meal) {
            case "b":
                food = new Food("Breakfast", inputFoodName(), inputCalories());
                currentDay.addMeal(food);
                break;
            case "l":
                food = new Food("Lunch", inputFoodName(), inputCalories());
                currentDay.addMeal(food);
                break;
            case "d":
                food = new Food("Dinner", inputFoodName(), inputCalories());
                currentDay.addMeal(food);
                break;
            case "s":
                food = new Food("Snack", inputFoodName(), inputCalories());
                currentDay.addMeal(food);
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new day object
    private void init() {
        dayManager = new DayManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: loads foods from file
    private void loadFoods() {
        try {
            dayManager = jsonReader.read();
            System.out.println("Loaded " + currentDay.returnDate() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves foods to file
    private void saveFoods() {
        try {
            jsonWriter.open();
            jsonWriter.write(dayManager);
            jsonWriter.close();
            System.out.println("Saved " + currentDay.returnDate() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays the main menu to the user
    private void displayMainMenu() {
        System.out.println(currentDay.returnDate());
        System.out.println("Select From:");
        System.out.println("c -> set calorie target");
        System.out.println("e -> enter food item");
        System.out.println("v -> view current calories and foods eaten");
        System.out.println("q -> quit");
        System.out.println("l -> load data");
        System.out.println("d -> change date");
        //if (currentDay.numItems() > 0) {
        System.out.println("s -> save data");
        //}
    }

    // EFFECTS: displays the meal menu to the user
    private void displayMealMenu() {
        System.out.println("Select Meal:");
        System.out.println("b -> breakfast");
        System.out.println("l -> lunch");
        System.out.println("d -> dinner");
        System.out.println("s -> snack");
    }

    // MODIFIES: this
    // EFFECTS: lets the user set a calorie target
    private void setCalorieTarget() {
        int calorieTarget = -1;

        while (calorieTarget <= 0) {
            System.out.println("Enter desired calorie target:");
            try {
                calorieTarget = input.nextInt();
                currentDay.setCalorieTarget(calorieTarget);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number");
                input.nextLine();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input on which meal to input
    private void selectMeal() {
        String meal = "";
        displayMealMenu();

        while (!((meal.equals("b")) || meal.equals("l") || meal.equals("d") || meal.equals("s"))) {
            meal = input.next();
        }
        addMeal(meal);
    }

    // EFFECTS: asks user to enter name of food eaten
    private String inputFoodName() {
        boolean keepAsking = true;
        String food = "";

        while (keepAsking) {
            try {
                System.out.println("What did you eat?");
                food = input.next();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid food name");
                input.nextLine();
            }
        }
        return food;
    }

    // EFFECTS: asks user to enter the number of calories of the food eaten
    private int inputCalories() {
        boolean keepAsking = true;
        int calories = -1;

        while (keepAsking) {
            try {
                System.out.println("How many calories was it?");
                calories = input.nextInt();
                keepAsking = false;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                input.nextLine();
            }
        }
        return calories;
    }

    // EFFECTS: prints the user's set calorie target; consumed calories so far today;
    //          let user knows if they are over their calorie target, if not, prints
    //          how many more calories a user can consume if they are still under the calorie target;
    //          prints the list of foods eaten today;
    private void viewFoods() {
        String next = "";

        while (next.equals("")) {
            System.out.println("Calorie Target: " + currentDay.returnCalorieTarget());
            System.out.println("Current calories: " + currentDay.returnCalories());
            if (currentDay.returnCalories() > currentDay.returnCalorieTarget()) {
                System.out.println("You are over your target!");
            } else {
                System.out.println("You can eat " + (currentDay.returnCalorieTarget() - currentDay.returnCalories())
                        + " more calories!");
            }
            System.out.println("Number of foods eaten today: " + currentDay.numItems());
            //day.returnItems();
            for (int i = 0; i < currentDay.numItems(); i++) {
                System.out.println(currentDay.returnItem(i));
            }

            while (!next.equals("m")) {
                System.out.println("Enter m to return to main menu:");
                next = input.next();
            }
        }
    }

}
