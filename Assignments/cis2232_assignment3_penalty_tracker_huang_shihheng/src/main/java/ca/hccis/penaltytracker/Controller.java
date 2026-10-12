package ca.hccis.penaltytracker;

import ca.hccis.penaltytracker.entity.PenaltyTracker;
import ca.hccis.penaltytracker.threads.ConsoleRunner;
import ca.hccis.penaltytracker.threads.GuiRunner;
import com.google.gson.Gson;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class - Main entry point for Assignment 3.
 * Launches two independent threads, both with A/V/X menu functionality.
 * One works completely in console, the other completely in JOptionPane.
 */
public class Controller {

    // Use relative path so it works on any computer
    public static final String PATH = "./data/";
    public static final String FILE_NAME = "data_huang_shihheng.json";

    // Menu definition from Assignment 1 - used by both threads
    public static final String MENU = "A) Add" + System.lineSeparator()
            + "V) View" + System.lineSeparator()
            + "X) eXit" + System.lineSeparator()
            + "Option: ";

    public static void main(String[] args) {

        // Ensure data directory exists
        File directory = new File(PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Ensure data file exists
        Path filePath = Paths.get(PATH, FILE_NAME);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println("Error creating data file: " + e.getMessage());
            }
        }

        System.out.println("========================================");
        System.out.println("Multi-threading Penalty Tracker - A3");
        System.out.println("File: " + PATH + FILE_NAME);
        System.out.println("========================================");

        // Welcome dialog to show GUI is alive immediately
        JOptionPane.showMessageDialog(null,
                "A3 Started!\n\nConsole Thread: Menu in this Run window\nGUI Thread: Menu in popup windows\n\nBoth share the same file and accumulator.",
                "Penalty Tracker A3", JOptionPane.INFORMATION_MESSAGE);

        // Start GUI thread first so its menu appears on top
        Thread guiThread = new Thread(new GuiRunner());
        guiThread.setName("GuiThread");
        guiThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Start console thread
        Thread consoleThread = new Thread(new ConsoleRunner());
        consoleThread.setName("ConsoleThread");
        consoleThread.start();
    }

    /**
     * Thread-safe save method - file accumulates entries from both threads.
     * This is the accumulator file as required.
     */
    public static synchronized void saveRecord(PenaltyTracker penaltyTracker) {
        try (FileWriter writer = new FileWriter(PATH + FILE_NAME, true)) {
            String jsonValue = penaltyTracker.toJson();
            writer.write(jsonValue + System.lineSeparator());
            writer.flush();
            System.out.println("Record saved by Thread: " + Thread.currentThread().getName());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Thread-safe method to read all records - used by both console and GUI View.
     */
    public static synchronized List<PenaltyTracker> getAllRecords() {
        List<PenaltyTracker> records = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Path filePath = Paths.get(PATH, FILE_NAME);
            if (!Files.exists(filePath)) return records;
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    PenaltyTracker record = gson.fromJson(line, PenaltyTracker.class);
                    records.add(record);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return records;
    }

    /**
     * For Console View - prints to console.
     */
    public static void processShowConsole() {
        System.out.println("\n--- View Penalty Records (Both Threads) ---");
        List<PenaltyTracker> records = getAllRecords();
        if (records.isEmpty()) {
            System.out.println("No penalty records found.");
        } else {
            for (PenaltyTracker record : records) {
                System.out.println(record.toString());
                System.out.println("----------------------------------------");
            }
            System.out.println("Total records: " + records.size());
        }
    }

    /**
     * For GUI View - returns formatted string for JOptionPane.
     */
    public static String processShowGUI() {
        List<PenaltyTracker> records = getAllRecords();
        if (records.isEmpty()) {
            return "No penalty records found.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("--- All Records (Console + GUI) ---\n\n");
        for (PenaltyTracker record : records) {
            sb.append(record.toString()).append("\n----------------------------------------\n");
        }
        sb.append("\nTotal records: ").append(records.size());
        sb.append("\nShared Accumulator: ").append(PenaltyTracker.getTotalRecords()).append(" records, Score=").append(PenaltyTracker.getTotalImpactScore());
        return sb.toString();
    }
}