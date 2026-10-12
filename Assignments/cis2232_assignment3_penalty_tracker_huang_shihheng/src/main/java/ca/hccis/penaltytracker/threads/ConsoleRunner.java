package ca.hccis.penaltytracker.threads;

import ca.hccis.penaltytracker.Controller;
import ca.hccis.penaltytracker.bo.PenaltyTrackerBO;
import ca.hccis.penaltytracker.entity.PenaltyTracker;
import ca.hccis.penaltytracker.util.CisUtility;

/**
 * Console Thread - Implements full A/V/X menu completely in console.
 * Same functionality as Assignment 1 but runs in its own thread.
 */
public class ConsoleRunner implements Runnable {

    @Override
    public void run() {
        System.out.println("Console Thread Started - ID: " + Thread.currentThread().getId());
        String option;

        do {
            // Show menu using CisUtility (console only)
            option = CisUtility.getInputString(Controller.MENU);

            switch (option.toUpperCase()) {
                case "A":
                    processAddConsole();
                    break;
                case "V":
                    Controller.processShowConsole();
                    break;
                case "X":
                    System.out.println("Console Thread Exiting - Goodbye!");
                    // Don't use System.exit here or it will kill GUI thread too
                    // Just break the loop for this thread
                    return;
                default:
                    System.out.println("Invalid option. Please enter A, V, or X.");
                    break;
            }
        } while (!option.equalsIgnoreCase("X"));
    }

    /**
     * Add logic for console thread - includes calculate() from A2
     */
    private void processAddConsole() {
        try {
            System.out.println("\n--- Console: Add Penalty Record ---");
            PenaltyTracker pt = new PenaltyTracker();
            pt.getInformation(); // Console input

            PenaltyTrackerBO bo = new PenaltyTrackerBO();
            bo.calculate(pt); // Required calculate from A2

            Controller.saveRecord(pt);
            PenaltyTracker.addToAccumulator(pt.getImpactScore());

            System.out.println("Record saved successfully!");
            System.out.println(pt.toString());
        } catch (Exception e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }
}