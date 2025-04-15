package com.SeedGenerator;
// SeedGenerator.java

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SeedGenerator is a calculator which is used to generate a range of seeds
 * for use in RNG manipulation of Pokémon games. It requires users to input two values:
 * the hex value of the target seed, and the desired range, which will always round up
 * to the nearest odd number; i.e., if the range is 10, the calculator will output the following:
 * ...
 * 5 previous seeds
 * the provided seed
 * 5 next seeds
 * ...
 * written by Ethan Crawford 10/24/2024
 */
public class SeedGenerator {
    public static void main(String[] args) {
        // define the frame
        JFrame frame = new JFrame("Seed Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 375);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        // add a description to the top of the frame
        JLabel descriptionLabel = new JLabel("Calculate hex seeds within a given");
        JLabel descriptionLabel2 = new JLabel("range of a given hex value:");
        frame.add(descriptionLabel);
        frame.add(descriptionLabel2);

        // define the panels
        JPanel hexPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rangePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel prefixPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // add components to the hexPanel
        JLabel hexLabel = new JLabel("Enter a Hex Seed:");
        JTextField hexField = new JTextField(10);
        hexPanel.add(hexLabel);
        hexPanel.add(hexField);

        // add components to the rangePanel
        JLabel rangeLabel = new JLabel("Enter Desired Range:");
        JTextField rangeField = new JTextField(10);
        rangePanel.add(rangeLabel);
        rangePanel.add(rangeField);

        // add components to the buttonPanel
        JButton calculateButton = new JButton("Calculate");
        buttonPanel.add(calculateButton);

        // add components to the prefixPanel
        JLabel outputLabel = new JLabel("Seed Output:");
        prefixPanel.add(outputLabel);

        // add components to the outputPanel
        JTextArea outputTextArea = new JTextArea(10,20);
        outputTextArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        // add the panels to the frame
        frame.add(hexPanel);
        frame.add(rangePanel);
        frame.add(buttonPanel);
        frame.add(prefixPanel);
        frame.add(outputPanel);

        // add right-click listener to the outputTextArea
        outputTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            }

            public void mouseReleased(java.awt.event.MouseEvent e) {
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            }

            private void showPopup(java.awt.event.MouseEvent e) {
            JPopupMenu popup = new JPopupMenu();
            JMenuItem copyItem = new JMenuItem("Copy");
            copyItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                String selectedText = outputTextArea.getSelectedText();
                if (selectedText != null && !selectedText.isEmpty()) {
                    StringSelection stringSelection = new StringSelection(selectedText);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                } else {
                    StringSelection stringSelection = new StringSelection(outputTextArea.getText());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
                }
            });
            popup.add(copyItem);
            popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        // add function to the button
        calculateButton.addActionListener(new ActionListener() {
            /**
             * The Calculator
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String hexValue = hexField.getText();                   // get the hex value from the frame
                    int range = Integer.parseInt(rangeField.getText());     // get the range from the frame
                    int baseValue = Integer.parseInt(hexValue, 16);    // convert the hex value to int


                    // create the output string
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < range; i++) {
                        // calculate each number
                        int currentValue = baseValue - (range / 2) + i;

                        // convert the number to hex, append it to the string, append new line
                        result.append(Integer.toHexString(currentValue).toUpperCase()).append("\n");
                    }

                    // print the output to the outputTextArea
                    outputTextArea.setText(result.toString());

                } catch (NumberFormatException ex) {

                    // create a popup window with a basic error message
                    JOptionPane.showMessageDialog(frame,
                            "Please enter valid numbers",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // make the window pop up in the middle of the screen
        frame.setLocationRelativeTo(null);

        // make the window visible
        frame.setVisible(true);
    }
}
