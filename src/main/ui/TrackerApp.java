package ui;

import model.Day;
import model.Meal;

import java.util.Scanner;

public class TrackerApp {

    private Scanner input;
    private Day day;
    private int calorieTarget;

    public TrackerApp() {
        boolean keepGoing = true;
        String command;

        init();

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

    private void init() {
        day = new Day();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public void displayMainMenu() {
        System.out.println("Select From:");
        System.out.println("s -> set calorie target");
        System.out.println("e -> enter food item");
        System.out.println("v -> view current calories and foods eaten");
        System.out.println("q -> quit");
    }

    public void displayMealMenu() {
        System.out.println("Select Meal:");
        System.out.println("b -> breakfast");
        System.out.println("l -> lunch");
        System.out.println("d -> dinner");
        System.out.println("s -> snack");
    }

    public void setCalorieTarget() {
        int calorieTarget = -1;

        while (calorieTarget <= 0) {
            System.out.println("Enter desired calorie target:");
            calorieTarget = input.nextInt();
        }
        this.calorieTarget = calorieTarget;
    }

    public void selectMeal() {
        String meal = "";
        displayMealMenu();

        while (!((meal.equals("b")) || meal.equals("l") || meal.equals("d") || meal.equals("s"))) {
            meal = input.next();
        }
        processMeal(meal);
    }

    public void addBreakfast() {
        String food;
        int calories;
        Meal breakfast;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        breakfast = new Meal("Breakfast", food, calories);
        day.addMeal(breakfast);
    }

    public void addLunch() {
        String food;
        int calories;
        Meal lunch;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        lunch = new Meal("Lunch", food, calories);
        day.addMeal(lunch);
    }

    public void addDinner() {
        String food;
        int calories;
        Meal dinner;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        dinner = new Meal("Dinner", food, calories);
        day.addMeal(dinner);
    }

    public void addSnack() {
        String food;
        int calories;
        Meal snack;

        System.out.println("What did you eat?");
        food = input.next();
        System.out.println("How many calories was it?");
        calories = input.nextInt();

        snack = new Meal("Snack", food, calories);
        day.addMeal(snack);
    }

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
