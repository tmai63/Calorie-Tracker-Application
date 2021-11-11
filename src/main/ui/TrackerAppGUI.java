package ui;

import model.Day;
import model.DayManager;
import model.Food;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// GUI for Calorie Tracker
public class TrackerAppGUI extends JPanel {

    private static JFrame mainFrame;
    private JLabel splash;
    private JList list;
    private DefaultListModel listModel;

    private static final String JSON_STORE = "./data/calendar.json";

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private static final String setCaloriesString = "Set Calories";
    private static final String enterFoodString = "Enter Food Item";
    private static final String saveString = "Save Data";
    private static final String loadString = "Load Data";
    private static final String quitString = "Quit";
    private static final String setDateString = "Set Date";
    private static final String removeMealString = "Remove";
    private static final String startAppString = "Continue";

    private JButton setCaloriesButton;
    private JButton enterFoodButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton setDateButton;
    private JButton removeMeal;
    private JButton startApp;

    private JLabel currentDate;
    private JLabel currentCalories;
    private JLabel calorieTarget;
    private JLabel calorieTargetPanelText;
    private JLabel foodPanelText;
    private JLabel setDateText;

    private JTextField calorieTargetInput;
    private JTextField mealName;
    private JTextField mealCalories;
    private JTextField setDateField;

    private Day currentDay;
    private DayManager dayManager;

    private JFrame calorieTargetFrame;
    private JFrame foodsFrame;
    private JFrame setDateFrame;
    private JFrame loadingScreen;

    private JPanel calorieTargetPanel;
    private JPanel foodsPanel;
    private JPanel setDatePanel;

    private JSpinner mealSelector;

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public TrackerAppGUI() {
        super(new BorderLayout());

        // Initialization of model objects
        currentDay = new Day();
        dayManager = new DayManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Create buttons on main panel
        setCaloriesButton = new JButton(setCaloriesString);
        setCaloriesButton.setActionCommand(setCaloriesString);
        setCaloriesButton.addActionListener(new SetCaloriesPanel());
        enterFoodButton = new JButton(enterFoodString);
        enterFoodButton.setActionCommand(enterFoodString);
        enterFoodButton.addActionListener(new AddMealPanel());
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveData());
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadData());
        quitButton = new JButton(quitString);
        quitButton.setActionCommand(quitString);
        quitButton.addActionListener(new QuitApplication());
        setDateButton = new JButton(setDateString);
        setDateButton.setActionCommand(setDateString);
        setDateButton.addActionListener(new SetDatePanel());
        removeMeal = new JButton(removeMealString);
        removeMeal.setActionCommand(removeMealString);
        removeMeal.addActionListener(new RemoveMeal(removeMeal));

        // Create list of foods
        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setBounds(10, 10, 300, 500);

        // Create textFields on right panel
        currentDate = new JLabel();
        currentDate.setText("Selected Date: ");
        currentCalories = new JLabel();
        currentCalories.setText("Current Calories:");
        calorieTarget = new JLabel();
        calorieTarget.setText("Calorie Target: " + currentDay.returnCalorieTarget());

        // Create new frame to enter calorieTarget
        calorieTargetFrame = new JFrame();
        calorieTargetFrame.setBounds(800, 400, 800, 800);
        calorieTargetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        calorieTargetPanel = new JPanel();
        calorieTargetPanel.setLayout(new BoxLayout(calorieTargetPanel, BoxLayout.PAGE_AXIS));
        calorieTargetPanelText = new JLabel();
        calorieTargetPanelText.setText("Please enter calorie target for today:");
        calorieTargetInput = new JTextField(1);
        JButton calorieTargetSetButton = new JButton(setCaloriesString);
        calorieTargetSetButton.setActionCommand(setCaloriesString);
        calorieTargetSetButton.addActionListener(new SetCaloriesButton(calorieTargetSetButton));
        calorieTargetPanel.add(calorieTargetPanelText);
        calorieTargetPanel.add(calorieTargetInput);
        calorieTargetPanel.add(calorieTargetSetButton);
        calorieTargetFrame.add(calorieTargetPanel);

        // Create new frame to set date
        setDateFrame = new JFrame();
        setDateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setDateFrame.setBounds(800, 400, 800, 800);
        setDatePanel = new JPanel();
        setDatePanel.setLayout(new BoxLayout(setDatePanel, BoxLayout.PAGE_AXIS));
        setDateText = new JLabel("Enter date in YYYY-MM-DD format");
        setDateField = new JTextField();
        JButton setDateButtonFrame = new JButton(setDateString);
        setDateButtonFrame.setActionCommand(setDateString);
        setDateButtonFrame.addActionListener(new SetDate(setDateButtonFrame));
        setDatePanel.add(setDateText);
        setDatePanel.add(setDateField);
        setDatePanel.add(setDateButtonFrame);
        setDateFrame.add(setDatePanel);

        // Create new frame to enter foods
        foodsFrame = new JFrame();
        foodsFrame.setBounds(800, 400, 800, 800);
        foodsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        foodsPanel = new JPanel();
        foodsPanel.setLayout(new BoxLayout(foodsPanel, BoxLayout.PAGE_AXIS));
        JButton addFood = new JButton("Add");
        addFood.setActionCommand("Add");
        addFood.addActionListener(new AddMealButton(addFood));
        JLabel foodType = new JLabel();
        JLabel foodName = new JLabel();
        JLabel foodCal = new JLabel();
        foodPanelText = new JLabel();
        foodPanelText.setText("");
        foodType.setText("Select Meal Type:");
        foodName.setText("Enter Food Name: ");
        foodCal.setText("Enter Food Calories: ");
        String[] meals = {"Breakfast", "Lunch", "Dinner", "Snack"};
        SpinnerListModel model = new SpinnerListModel(meals);
        mealSelector = new JSpinner(model);
        mealName = new JTextField();
        mealCalories = new JTextField();
        foodsPanel.add(foodType);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(mealSelector);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(foodName);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(mealName);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(foodCal);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(mealCalories);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(foodPanelText);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(addFood);
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        foodsPanel.add(Box.createVerticalStrut(5));
        foodsFrame.add(foodsPanel);

        // Create panel to display the buttons on left side
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.add(setCaloriesButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(setDateButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(enterFoodButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(saveButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(loadButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(quitButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create right panel
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.add(listScrollPane);
        rightPane.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPane.add(removeMeal);
        rightPane.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPane.add(currentDate);
        rightPane.add(currentCalories);
        rightPane.add(calorieTarget);

        // Create background frame
        loadingScreen = new JFrame();
        loadingScreen.setBounds(600,0,50,50);
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.PAGE_AXIS));
        JLabel loadingText = new JLabel("Welcome to Calorie Tracker!");
        startApp = new JButton(startAppString);
        startApp.setActionCommand(startAppString);
        startApp.addActionListener(new StartApp());
        ImageIcon image = new ImageIcon("./data/splashart.jpg");
        splash = new JLabel(image);
        loadingPanel.add(loadingText);
        loadingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loadingPanel.add(splash);
        loadingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loadingPanel.add(startApp);
        loadingScreen.add(loadingPanel);
        loadingScreen.pack();
        loadingScreen.setVisible(true);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.add(buttonPane);
        mainPanel.add(rightPane);

        // Display main panel
        add(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: Opens a new panel when set calories button is pressed on main screen
    class StartApp implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display the window
            calorieTargetFrame.pack();
            loadingScreen.setVisible(false);
            mainFrame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: Opens a new panel when set calories button is pressed on main screen
    class SetCaloriesPanel implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Display the window
            calorieTargetFrame.pack();
            calorieTargetFrame.setVisible(true);
        }
    }

    // MODIFIES: DayManager
    // EFFECTS: Saves DayManager Data when button is pressed
    class SaveData implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(dayManager);
                jsonWriter.close();
                System.out.println("Saved data to " + JSON_STORE);
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: DayManager
    // EFFECTS: Lets user load the data into a new DayManager when button is pressed
    class LoadData implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                dayManager = jsonReader.read();
                System.out.println("Loaded data from " + JSON_STORE);
            } catch (IOException ioException) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: Day
    // EFFECTS: removes meal from selected index
    class RemoveMeal implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public RemoveMeal(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }
            currentDay.removeItem(index);
            // clears list
            listModel.removeAllElements();
            // add the food to list
            for (int i = 0; i < currentDay.numItems(); i++) {
                listModel.addElement(currentDay.returnItem(i));
            }
            currentCalories.setText("Current Calories:" + currentDay.returnCalories());
            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: Day
    // EFFECTS: Sets currentDate to entered date when button is pressed
    class SetDate implements ActionListener, DocumentListener {

        private JButton button;
        private boolean alreadyEnabled = false;

        public SetDate(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        public void actionPerformed(ActionEvent e) {
            String input;

            input = setDateField.getText();

            try {
                LocalDate date = LocalDate.parse(input);

                if (dayManager.existsDay(date)) {
                    currentDay = dayManager.getDay(date);
                    // clear list
                    listModel.removeAllElements();
                    // add the food to list
                    for (int i = 0; i < currentDay.numItems(); i++) {
                        listModel.addElement(currentDay.returnItem(i));
                    }
                    calorieTarget.setText("Calorie Target: " + currentDay.returnCalorieTarget());
                    currentDate.setText("Selected Date: " + currentDay.returnDate());
                    currentCalories.setText("Current Calories:" + currentDay.returnCalories());
                    mainFrame.pack();
                } else {
                    currentDay = new Day(date);
                    dayManager.addDay(date, currentDay);
                    // clears list
                    listModel.removeAllElements();
                    // add the food to list
                    for (int i = 0; i < currentDay.numItems(); i++) {
                        listModel.addElement(currentDay.returnItem(i));
                    }
                    calorieTarget.setText("Calorie Target: " + currentDay.returnCalorieTarget());
                    currentDate.setText("Selected Date: " + currentDay.returnDate());
                    currentCalories.setText("Current Calories:" + currentDay.returnCalories());
                    mainFrame.pack();
                }
            } catch (DateTimeParseException dateTimeParseException) {
                setDateText.setText("Invalid Date! Use YYYY-MM-DD Format");
                Toolkit.getDefaultToolkit().beep();
                setDateFrame.pack();
                setDateField.requestFocusInWindow();
                setDateField.selectAll();
            }
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // Enable the button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // Handles error if textField is empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: calorieTarget
    // EFFECTS: Sets caloriesTarget to user entered calorie when button is pressed
    class SetCaloriesButton implements ActionListener, DocumentListener {

        private JButton button;
        private boolean alreadyEnabled = false;

        public SetCaloriesButton(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String input;

            input = calorieTargetInput.getText();

            try {
                int calories = Integer.parseInt(input);

                if (calories >= 0) {
                    //Reset the text field.
                    calorieTargetInput.requestFocusInWindow();
                    calorieTargetInput.setText("");
                    currentDay.setCalorieTarget(calories);
                    calorieTarget.setText("Calorie Target: " + currentDay.returnCalorieTarget());
                } else {
                    calorieTargetPanelText.setText("Target can't be negative!");
                    Toolkit.getDefaultToolkit().beep();
                    calorieTargetInput.requestFocusInWindow();
                    calorieTargetInput.selectAll();
                }
            } catch (NumberFormatException inputMismatchException) {
                calorieTargetPanelText.setText("Enter a number!");
                Toolkit.getDefaultToolkit().beep();
                calorieTargetInput.requestFocusInWindow();
                calorieTargetInput.selectAll();
            }
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // Enable the button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // Handles error if textField is empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Opens a new panel when set calories button is pressed on main screen
    class SetDatePanel implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Display the window
            setDateFrame.pack();
            setDateFrame.setVisible(true);
        }
    }

    // MODIFIES: foods
    // EFFECTS: Adds meals to add meals when button is pressed
    class AddMealButton implements ActionListener, DocumentListener {

        private JButton button;
        private boolean alreadyEnabled = false;

        public AddMealButton(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        @SuppressWarnings({"checkstyle:SuppressWarnings", "checkstyle:MethodLength"})
        public void actionPerformed(ActionEvent e) {
            String type;
            String name;
            int cals;
            Food food;

            try {
                type = (String) mealSelector.getValue();
                name = mealName.getText();
                cals = Integer.parseInt(mealCalories.getText());

                if (cals >= 0 && name != "") {
                    //Reset the text field.
                    food = new Food(type, name, cals);
                    currentDay.addMeal(food);
                    currentCalories.setText("Current Calories: " + currentDay.returnCalories());

                    // clears list
                    listModel.removeAllElements();
                    // add the food to list
                    for (int i = 0; i < currentDay.numItems(); i++) {
                        listModel.addElement(currentDay.returnItem(i));
                    }
                    mainFrame.pack();

                } else if (cals < 0) {
                    foodPanelText.setText("Negative Calories!");
                    Toolkit.getDefaultToolkit().beep();
                    mealCalories.requestFocusInWindow();
                    mealCalories.selectAll();
                } else {
                    foodPanelText.setText("Invalid food name!");
                    Toolkit.getDefaultToolkit().beep();
                    mealName.requestFocusInWindow();
                    mealName.selectAll();
                }
            } catch (NumberFormatException inputMismatchException) {
                foodPanelText.setText("Calories needs to be a number!");
                Toolkit.getDefaultToolkit().beep();
                calorieTargetInput.requestFocusInWindow();
                calorieTargetInput.selectAll();
            }
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // Enable the button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // Handles error if textField is empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: GUI
    // EFFECTS: Opens a new panel for user to add meal
    class AddMealPanel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display the window
            foodsFrame.pack();
            foodsFrame.setVisible(true);
        }
    }

    // EFFECTS: quits the application
    class QuitApplication implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    // EFFECTS: creates a new GUI and shows it
    private static void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new JFrame("Calorie Tracker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(600, 200, 800, 800);
        mainFrame.setResizable(true);

        //Create and set up the content pane.
        JComponent newContentPane = new TrackerAppGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        mainFrame.setContentPane(newContentPane);

        mainFrame.pack();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //createSplash();
                createAndShowGUI();
            }
        });
    }

}
