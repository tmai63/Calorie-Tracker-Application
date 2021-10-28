package ui;

import model.Day;
import model.Food;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


// Calorie tracker application
public class TrackerApp {

    private static final String JSON_STORE = "./data/day.json";
    private Scanner input;
    private Day day;
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

        System.out.println(day.returnDate());

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

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processCommand(String command) {
        if (command.equals("c")) {
            setCalorieTarget();
        } else if (command.equals("e")) {
            selectMeal();
        } else if (command.equals("v")) {
            viewFoods();
        } else if (command.equals("l")) {
            loadFoods();
        } else if (command.equals("s")) {
            saveFoods();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processMeal(String meal) {
        if (meal.equals("b")) {
            addBreakfast();
        } else if (meal.equals("l")) {
            addLunch();
        } else if (meal.equals("d")) {
            addDinner();
        } else if (meal.equals("s")) {
            addSnack();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new day object
    private void init() {
        day = new Day();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: loads foods from file
    private void loadFoods() {
        try {
            day = jsonReader.read();
            System.out.println("Loaded " + day.returnDate() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves foods to file
    private void saveFoods() {
        try {
            jsonWriter.open();
            jsonWriter.write(day);
            jsonWriter.close();
            System.out.println("Saved " + day.returnDate() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays the main menu to the user
    private void displayMainMenu() {
        System.out.println("Select From:");
        System.out.println("c -> set calorie target");
        System.out.println("e -> enter food item");
        System.out.println("v -> view current calories and foods eaten");
        System.out.println("q -> quit");
        System.out.println("l -> load data");
        if (day.numItems() > 0) {
            System.out.println("s -> save data");
        }
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
        boolean keepAsking = true;

        while (keepAsking) {
            try {
                System.out.println("Enter desired calorie target:");
                calorieTarget = input.nextInt();
                keepAsking = false;
            } catch (InputMismatchException e) {
                System.out.println("Must be a number!");
            }
        }
        day.setCalorieTarget(calorieTarget);
    }

    // MODIFIES: this
    // EFFECTS: processes user input on which meal to input
    private void selectMeal() {
        String meal = "";
        displayMealMenu();

        while (!((meal.equals("b")) || meal.equals("l") || meal.equals("d") || meal.equals("s"))) {
            meal = input.next();
        }
        processMeal(meal);
    }

    // EFFECTS: asks user to enter name of food eaten
    private String inputFoodName() {
        String food;

        System.out.println("What did you eat?");
        food = input.next();

        return food;
    }

    // EFFECTS: asks user to enter the number of calories of the food eaten
    private int inputCalories() {
        int calories;

        System.out.println("How many calories was it?");
        calories = input.nextInt();

        return calories;
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for breakfast and adds it to the list of foods
    private void addBreakfast() {
        Food breakfast;

        breakfast = new Food("Breakfast", inputFoodName(), inputCalories());
        day.addMeal(breakfast);
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for lunch and adds it to the list of foods
    private void addLunch() {
        Food lunch;

        lunch = new Food("Lunch", inputFoodName(), inputCalories());
        day.addMeal(lunch);
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for dinner and adds it to the list of foods
    private void addDinner() {
        Food dinner;

        dinner = new Food("Dinner", inputFoodName(), inputCalories());
        day.addMeal(dinner);
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for snack and adds it to the list of foods
    private void addSnack() {
        Food snack;

        snack = new Food("Snack", inputFoodName(), inputCalories());
        day.addMeal(snack);
    }

    // EFFECTS: prints the user's set calorie target; consumed calories so far today;
    //          let user knows if they are over their calorie target, if not, prints
    //          how many more calories a user can consume if they are still under the calorie target;
    //          prints the list of foods eaten today;
    private void viewFoods() {
        String next = "";

        while (next.equals("")) {
            System.out.println("Calorie Target: " + day.returnCalorieTarget());
            System.out.println("Current calories: " + day.returnCalories());
            if (day.returnCalories() > day.returnCalorieTarget()) {
                System.out.println("You are over your target!");
            } else {
                System.out.println("You can eat " + (day.returnCalorieTarget() - day.returnCalories())
                        + " more calories!");
            }
            System.out.println("Number of foods eaten today: " + day.numItems());
            //day.returnItems();
            for (int i = 0; i < day.numItems(); i++) {
                System.out.println(day.returnItem(i));
            }

            while (!next.equals("m")) {
                System.out.println("Enter m to return to main menu:");
                next = input.next();
            }
        }
    }

}
