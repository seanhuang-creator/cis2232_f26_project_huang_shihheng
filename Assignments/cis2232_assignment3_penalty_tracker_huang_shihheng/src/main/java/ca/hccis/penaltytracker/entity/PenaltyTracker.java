package ca.hccis.penaltytracker.entity;

import ca.hccis.penaltytracker.Controller;
import ca.hccis.penaltytracker.util.CisUtility;
import com.google.gson.Gson;
import javax.swing.*;

public class PenaltyTracker {

    private int id;
    private String homeTeam;
    private String awayTeam;
    private String date;
    private String penalty;
    private int quarter;
    private String penalizedTeam;
    private String ageDivision;
    private String referee;
    private double impactScore;

    // ===== Static Accumulator shared by both threads (Assignment 3 requirement) =====
    // This holds the combined data from console and GUI threads
    private static int totalRecords = 0;
    private static double totalImpactScore = 0;

    /**
     * Thread-safe method to update the accumulator.
     * Synchronized to prevent race conditions when both threads update at the same time.
     * This method also displays the accumulated information as required.
     * @param impactScore The impact score of the newly added record
     */
    public static synchronized void addToAccumulator(double impactScore) {
        totalRecords++;
        totalImpactScore += impactScore;
        System.out.println("\n[Accumulator Updated - Shared by Both Threads]");
        System.out.println("Total Records (Console + GUI): " + totalRecords);
        System.out.println("Total Impact Score (Console + GUI): " + totalImpactScore);
        System.out.println("--------------------------------------------");
        // Print menu right after accumulator so console menu doesn't "run away"
        System.out.println(Controller.MENU + " -->");
    }

    public static synchronized int getTotalRecords() {
        return totalRecords;
    }

    public static synchronized double getTotalImpactScore() {
        return totalImpactScore;
    }

    /**
     * Gather input from console using CisUtility.
     * This method is used completely by the Console thread.
     */
    public void getInformation() {
        id = CisUtility.getInputInt("Enter Penalty ID: ");
        homeTeam = CisUtility.getInputString("Enter Home Team: ");
        awayTeam = CisUtility.getInputString("Enter Away Team: ");
        date = CisUtility.getInputString("Enter Date (yyyy-MM-dd): ");
        penalty = CisUtility.getInputString("Enter Penalty/Infraction Type: ");
        quarter = CisUtility.getInputInt("Enter Quarter (1-4): ");
        penalizedTeam = CisUtility.getInputString("Enter Penalized Team: ");
        ageDivision = CisUtility.getInputString("Enter Age Division: ");
        referee = CisUtility.getInputString("Enter Referee Name: ");
        impactScore = 0.0;
    }

    /**
     * Gather input from user using JOptionPane dialogs.
     * This method has exactly the same functionality as getInformation(),
     * but runs completely using JOptionPane as required for the second thread.
     */
    public void getInformationGUI() {
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Penalty ID: "));
            homeTeam = JOptionPane.showInputDialog(null, "Enter Home Team: ");
            awayTeam = JOptionPane.showInputDialog(null, "Enter Away Team: ");
            date = JOptionPane.showInputDialog(null, "Enter Date (yyyy-MM-dd): ");
            penalty = JOptionPane.showInputDialog(null, "Enter Penalty/Infraction Type: ");
            quarter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Quarter (1-4): "));
            penalizedTeam = JOptionPane.showInputDialog(null, "Enter Penalized Team: ");
            ageDivision = JOptionPane.showInputDialog(null, "Enter Age Division: ");
            referee = JOptionPane.showInputDialog(null, "Enter Referee Name: ");
            impactScore = 0.0;
        } catch (Exception e) {
            // If user clicks Cancel or closes the dialog, throw exception to cancel current entry
            throw new RuntimeException("GUI Input Cancelled");
        }
    }

    // ===== Getters and Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public String getPenalizedTeam() {
        return penalizedTeam;
    }

    public void setPenalizedTeam(String penalizedTeam) {
        this.penalizedTeam = penalizedTeam;
    }

    public String getAgeDivision() {
        return ageDivision;
    }

    public void setAgeDivision(String ageDivision) {
        this.ageDivision = ageDivision;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public double getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(double impactScore) {
        this.impactScore = impactScore;
    }

    /**
     * Convert this object to JSON string using Gson.
     * Used for file accumulation.
     * @return JSON representation of this record
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Display all penalty data in a readable format.
     * @return Formatted string of the penalty record
     */
    @Override
    public String toString() {
        return "Penalty Record ID: " + id +
                "\n  Date: " + date + " | Division: " + ageDivision +
                "\n  Match: " + homeTeam + " vs " + awayTeam +
                "\n  Penalized Team: " + penalizedTeam +
                "\n  Infraction: " + penalty + " (Q" + quarter + ")" +
                "\n  Official: " + referee +
                "\n  Impact Score: " + impactScore;
    }
}
