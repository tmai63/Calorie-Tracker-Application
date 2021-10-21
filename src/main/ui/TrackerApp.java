package ui;

import model.Day;
import model.Food;

import java.util.Scanner;


// Calorie tracker application
public class TrackerApp {

    private Scanner input;
    private Day day;
    private int calorieTarget;

    // EFFECTS: runs the tracker application
    public TrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runTracker() {
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
    public void processCommand(String command) {
        if (command.equals("s")) {
            setCalorieTarget();
        } else if (command.equals("e")) {
            selectMeal();
        } else if (command.equals("v")) {
            viewFoods();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void processMeal(String meal) {
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
    }

    // EFFECTS: displays the main menu to the user
    public void displayMainMenu() {
        System.out.println("Select From:");
        System.out.println("s -> set calorie target");
        System.out.println("e -> enter food item");
        System.out.println("v -> view current calories and foods eaten");
        System.out.println("q -> quit");
    }

    // EFFECTS: displays the meal menu to the user
    public void displayMealMenu() {
        System.out.println("Select Meal:");
        System.out.println("b -> breakfast");
        System.out.println("l -> lunch");
        System.out.println("d -> dinner");
        System.out.println("s -> snack");
    }

    // MODIFIES: this
    // EFFECTS: lets the user set a calorie target
    public void setCalorieTarget() {
        int calorieTarget = -1;

        while (calorieTarget <= 0) {
            System.out.println("Enter desired calorie target:");
            calorieTarget = input.nextInt();
        }
        this.calorieTarget = calorieTarget;
    }

    // MODIFIES: this
    // EFFECTS: processes user input on which meal to input
    public void selectMeal() {
        String meal = "";
        displayMealMenu();

        while (!((meal.equals("b")) || meal.equals("l") || meal.equals("d") || meal.equals("s"))) {
            meal = input.next();
        }
        processMeal(meal);
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for breakfast and adds it to the list of foods
    public void addBreakfast() {
        String food;
        int calories;
        Food breakfast;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        breakfast = new Food("Breakfast", food, calories);
        day.addMeal(breakfast);
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for lunch and adds it to the list of foods
    public void addLunch() {
        String food;
        int calories;
        Food lunch;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        lunch = new Food("Lunch", food, calories);
        day.addMeal(lunch);
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for dinner and adds it to the list of foods
    public void addDinner() {
        String food;
        int calories;
        Food dinner;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        dinner = new Food("Dinner", food, calories);
        day.addMeal(dinner);
    }

    // MODIFIES: foods list
    // EFFECTS: lets user enter a food eaten for snack and adds it to the list of foods
    public void addSnack() {
        String food;
        int calories;
        Food snack;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        snack = new Food("Snack", food, calories);
        day.addMeal(snack);
    }

    // EFFECTS: prints the user's set calorie target; consumed calories so far today;
    //          let user knows if they are over their calorie target, if not, prints
    //          how many more calories a user can consume if they are still under the calorie target;
    //          prints the list of foods eaten today;
    public void viewFoods() {
        String next = "";

        while (next.equals("")) {
            System.out.println("Calorie Target: " + calorieTarget);
            System.out.println("Current calories: " + day.returnCalories());
            if (day.returnCalories() > calorieTarget) {
                System.out.println("You are over your target!");
            } else {
                System.out.println("You can eat " + (calorieTarget - day.returnCalories()) +  " more calories!");
            }
            System.out.println("Number of foods eaten today: " + day.numItems());
            //day.returnItems();
            for (int i = 0; i < day.numItems(); i++) {
                System.out.println(day.returnItem(i));
            }
            System.out.println("Enter any key to return to main menu:");
            next = input.next();
        }
    }

}
