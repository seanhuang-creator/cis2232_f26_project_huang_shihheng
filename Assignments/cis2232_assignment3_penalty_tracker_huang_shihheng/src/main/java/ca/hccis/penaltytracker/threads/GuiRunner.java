package ca.hccis.penaltytracker.threads;

import ca.hccis.penaltytracker.Controller;
import ca.hccis.penaltytracker.bo.PenaltyTrackerBO;
import ca.hccis.penaltytracker.entity.PenaltyTracker;

import javax.swing.*;

/**
 * GUI Thread - Implements full A/V/X menu completely using JOptionPane.
 * Same functionality as Assignment 1 but runs completely in GUI.
 */
public class GuiRunner implements Runnable {

    @Override
    public void run() {
        System.out.println("GUI Thread Started - ID: " + Thread.currentThread().getId());
        String option;

        do {
            // Show menu using JOptionPane (GUI only)
            option = JOptionPane.showInputDialog(null,
                    Controller.MENU,
                    "Penalty Tracker - GUI Thread Menu",
                    JOptionPane.QUESTION_MESSAGE);

            // If user clicks Cancel or closes window
            if (option == null) {
                continue;
            }

            switch (option.toUpperCase()) {
                case "A":
                    processAddGUI();
                    break;
                case "V":
                    processShowGUI();
                    break;
                case "X":
                    JOptionPane.showMessageDialog(null, "GUI Thread Exiting - Goodbye!");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please enter A, V, or X.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } while (!option.equalsIgnoreCase("X"));
    }

    private void processAddGUI() {
        try {
            PenaltyTracker pt = new PenaltyTracker();
            pt.getInformationGUI(); // GUI input

            PenaltyTrackerBO bo = new PenaltyTrackerBO();
            bo.calculate(pt); // Required calculate from A2

            Controller.saveRecord(pt);
            PenaltyTracker.addToAccumulator(pt.getImpactScore());

            JOptionPane.showMessageDialog(null,
                    "Record saved successfully!\n\n" + pt.toString() +
                            "\n\nShared Total: " + PenaltyTracker.getTotalRecords() + " records, Score=" + PenaltyTracker.getTotalImpactScore(),
                    "Add Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (RuntimeException e) {
            // Cancelled
            System.out.println("GUI Add cancelled");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processShowGUI() {
        String allRecords = Controller.processShowGUI();
        // Use JTextArea for long text to make it scrollable
        JTextArea textArea = new JTextArea(allRecords);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "View Records - GUI Thread", JOptionPane.INFORMATION_MESSAGE);
    }
}