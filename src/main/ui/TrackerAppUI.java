package ui;

import javax.swing.*;
import java.awt.*;

public class TrackerAppUI extends JPanel {

    private JList list;
    private DefaultListModel listModel;

    private static final String setCaloriesString = "Set Calories";
    private static final String enterFoodString = "Enter Food Item";
    private JButton setCaloriesButton;
    private JButton enterFoodButton;
    private JTextField employeeName;

    public TrackerAppUI() {
        super(new BorderLayout());

        //Create buttons
        setCaloriesButton = new JButton(setCaloriesString);
        setCaloriesButton.setActionCommand(setCaloriesString);
        enterFoodButton = new JButton(enterFoodString);
        enterFoodButton.setActionCommand(enterFoodString);



        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.add(setCaloriesButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(enterFoodButton);

        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }


    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,500,500);

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
