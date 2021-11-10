package ui;

import model.Day;
import model.Food;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class TrackerAppUI extends JPanel {

    private static JFrame mainFrame;
    private JList list;
    private DefaultListModel listModel;

    private static final String setCaloriesString = "Set Calories";
    private static final String enterFoodString = "Enter Food Item";
    private static final String saveString = "Save Data";
    private static final String loadString = "Load Data";
    private static final String quitString = "Quit";

    private JButton setCaloriesButton;
    private JButton enterFoodButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;

    private JLabel currentDate;
    private JLabel currentCalories;
    private JLabel calorieTarget;
    private JLabel calorieTargetPanelText;

    private JTextField calorieTargetInput;

    private Day currentDay;

    private JFrame calorieTargetFrame;

    private JPanel calorieTargetPanel;


    public TrackerAppUI() {
        super(new BorderLayout());

        // Create buttons
        setCaloriesButton = new JButton(setCaloriesString);
        setCaloriesButton.setActionCommand(setCaloriesString);
        setCaloriesButton.addActionListener(new SetCaloriesPanel());
        enterFoodButton = new JButton(enterFoodString);
        enterFoodButton.setActionCommand(enterFoodString);
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        quitButton = new JButton(quitString);
        quitButton.setActionCommand(quitString);
        quitButton.addActionListener(new QuitApplication());

        // Create list
        listModel = new DefaultListModel();
        currentDay = new Day();
        Food bfast = new Food("Breakfast", "Eggs", 200);
        Food bfast2 = new Food("Breakfast", "Sausages", 150);
        Food lunch = new Food("Lunch", "Sandwich", 500);
        Food lunch2 = new Food("Lunch", "Fries", 200);
        Food dinner = new Food("Dinner", "Steak", 800);
        Food snack = new Food("Snack", "Chips", 100);
        currentDay.addMeal(bfast);
        currentDay.addMeal(bfast2);
        currentDay.addMeal(lunch);
        currentDay.addMeal(lunch2);
        currentDay.addMeal(dinner);
        currentDay.addMeal(snack);
        currentDay.setCalorieTarget(1500);

        for (int i = 0; i < currentDay.numItems(); i++) {
            listModel.addElement(currentDay.returnItem(i));
        }

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        // Create textField
        currentDate = new JLabel();
        currentDate.setText("Selected Date: " + (LocalDate.now().toString()));
        currentCalories = new JLabel();
        currentCalories.setText("Current Calories: " + currentDay.returnCalories());
        calorieTarget = new JLabel();
        calorieTarget.setText("Calorie Target: " + currentDay.returnCalorieTarget());

        // Create new frame to enter calorieTarget
        calorieTargetFrame = new JFrame();
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

        // Create panel to display the buttons on left side
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.add(setCaloriesButton);
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
    class QuitApplication implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new JFrame("Calorie Tracker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(0, 0, 500, 500);
        mainFrame.setResizable(false);

        //Create and set up the content pane.
        JComponent newContentPane = new TrackerAppUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        mainFrame.setContentPane(newContentPane);

        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
