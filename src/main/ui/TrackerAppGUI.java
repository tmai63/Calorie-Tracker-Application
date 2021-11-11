package ui;

import model.Day;
import model.DayManager;
import model.Food;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

public class TrackerAppGUI extends JPanel {

    private static JFrame mainFrame;
    private JList list;
    private DefaultListModel listModel;

    private static final String setCaloriesString = "Set Calories";
    private static final String enterFoodString = "Enter Food Item";
    private static final String saveString = "Save Data";
    private static final String loadString = "Load Data";
    private static final String quitString = "Quit";
    private static final String setDateString = "Set Date";

    private JButton setCaloriesButton;
    private JButton enterFoodButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton setDateButton;

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

    private JPanel calorieTargetPanel;
    private JPanel foodsPanel;
    private JPanel setDatePanel;

    private JSpinner mealSelector;


    public TrackerAppGUI() {
        super(new BorderLayout());

        dayManager = new DayManager();

        // Create buttons
        setCaloriesButton = new JButton(setCaloriesString);
        setCaloriesButton.setActionCommand(setCaloriesString);
        setCaloriesButton.addActionListener(new SetCaloriesPanel());
        enterFoodButton = new JButton(enterFoodString);
        enterFoodButton.setActionCommand(enterFoodString);
        enterFoodButton.addActionListener(new AddMealPanel());
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        quitButton = new JButton(quitString);
        quitButton.setActionCommand(quitString);
        quitButton.addActionListener(new QuitApplication());
        setDateButton = new JButton(setDateString);
        setDateButton.setActionCommand(setDateString);
        setDateButton.addActionListener(new SetDatePanel());

        // Create list
        listModel = new DefaultListModel();
        currentDay = new Day();

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setBounds(10,10,300,500);

        // Create textField
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
        rightPane.add(Box.createRigidArea(new Dimension(0,50)));
        rightPane.add(currentDate);
        rightPane.add(currentCalories);
        rightPane.add(calorieTarget);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.add(buttonPane);
        mainPanel.add(rightPane);

        // Display main panel
        add(mainPanel);
    }

    // Opens a new panel when set calories button is pressed on main screen
    class SetCaloriesPanel implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Display the window
            calorieTargetFrame.pack();
            calorieTargetFrame.setVisible(true);
        }
    }

    // Class to set calories when button is pressed
    class SetDate implements ActionListener, DocumentListener {

        private JButton button;
        private boolean alreadyEnabled = false;

        public SetDate(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
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
            } catch (NumberFormatException inputMismatchException) {
                setDateText.setText("Invalid Date!");
                Toolkit.getDefaultToolkit().beep();
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

    // Class to set calories when button is pressed
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

    // Opens a new panel when set calories button is pressed on main screen
    class SetDatePanel implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Display the window
            setDateFrame.pack();
            setDateFrame.setVisible(true);
        }
    }

    // Class to add meals when button is pressed
    class AddMealButton implements ActionListener, DocumentListener {

        private JButton button;
        private boolean alreadyEnabled = false;

        public AddMealButton(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
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
                    food = new Food(type,name,cals);
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

    // Opens a new panel when add meal is pressed on main screen
    class AddMealPanel implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // Display the window
            foodsFrame.pack();
            foodsFrame.setVisible(true);
        }
    }

    // Opens a new panel when set calories button is pressed on main screen
    class QuitApplication implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static void createAndShowGUI() {

        //Create and set up the window.
        mainFrame = new JFrame("Calorie Tracker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(800, 400, 800, 800);
        mainFrame.setResizable(true);

        //Create and set up the content pane.
        JComponent newContentPane = new TrackerAppGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        mainFrame.setContentPane(newContentPane);

        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private static void createSplash() {
        // create image
        JWindow splash = new JWindow();
        ImageIcon image = new ImageIcon("/Users/thomsonmai/Documents/School/CS/CPSC 210/project_o5a7r/data/tobs.jpg");
        JLabel tobs = new JLabel(image);
        splash.getContentPane().add(new JLabel(new ImageIcon("./data/tobs.jpg")));
        splash.setBounds(800, 400, 972, 648);
        splash.add(tobs);

        //Create and set up the content pane.
        JComponent newContentPane = new TrackerAppGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        splash.setContentPane(newContentPane);

        splash.pack();
        splash.setVisible(true);
        //Timer
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        splash.setVisible(false);
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
