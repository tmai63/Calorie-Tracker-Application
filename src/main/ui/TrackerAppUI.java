package ui;

import model.Day;
import model.Food;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TrackerAppUI extends JPanel {

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

    private Day currentDay;

    public TrackerAppUI() {
        super(new BorderLayout());

        // Create buttons
        setCaloriesButton = new JButton(setCaloriesString);
        setCaloriesButton.setActionCommand(setCaloriesString);
        enterFoodButton = new JButton(enterFoodString);
        enterFoodButton.setActionCommand(enterFoodString);
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        quitButton = new JButton(quitString);
        quitButton.setActionCommand(quitString);

        // Create list
        listModel = new DefaultListModel();
        currentDay = new Day();
        Food bfast = new Food("Breakfast","Eggs",200);
        Food bfast2 = new Food("Breakfast","Sausages",150);
        Food lunch = new Food("Lunch","Sandwich",500);
        Food lunch2 = new Food("Lunch","Fries",200);
        Food dinner = new Food("Dinner","Steak",800);
        Food snack = new Food("Snack","Chips",100);
        currentDay.addMeal(bfast);
        currentDay.addMeal(bfast2);
        currentDay.addMeal(lunch);
        currentDay.addMeal(lunch2);
        currentDay.addMeal(dinner);
        currentDay.addMeal(snack);

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
        currentDate.setText("Current Date: " + (LocalDate.now().toString()));
        currentCalories = new JLabel();
        currentCalories.setText("Current Calories: " + currentDay.returnCalories());

        // Create a panel that uses BoxLayout.
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
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // Create right panel
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.add(listScrollPane);
        rightPane.add(currentDate);
        rightPane.add(currentCalories);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.add(buttonPane);
        mainPanel.add(rightPane);

        // Display main panel
        add(mainPanel);
    }



    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Calorie Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,500,500);
        frame.setResizable(false);

        //Create and set up the content pane.
        JComponent newContentPane = new TrackerAppUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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
