package ca.hccis.penaltytracker;

import ca.hccis.penaltytracker.bo.PenaltyTrackerBO;
import ca.hccis.penaltytracker.entity.PenaltyTracker;
import ca.hccis.penaltytracker.util.CisUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to manage menu navigation, calculation execution,
 * and JSON file storage/loading for penalty records.
 *
 * @author seanhuang
 * @since 2026-09
 */
public class Controller {

    public static final String MENU = "A) Add" + System.lineSeparator()
            + "V) View" + System.lineSeparator()
            + "X) eXit" + System.lineSeparator()
            + "Option: ";

    public static final String PATH = "d:\\cis2232\\";
    public static final String FILE_NAME = "data_huang_shihheng.json"; // JSON file with my name

    public static void main(String[] args) {

        // 1. Ensure directory exists
        File directory = new File(PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 2. Ensure data file exists
        Path filePath = Paths.get(PATH + FILE_NAME);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println("Error creating data file.");
            }
        }

        String option = "";

        do {
            option = CisUtility.getInputString(MENU);

            switch (option.toUpperCase()) {
                case "A":
                    processAdd();
                    break;
                case "V":
                    processShow();
                    break;
                case "X":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please enter A, V, or X.");
                    break;
            }
        } while (!option.equalsIgnoreCase("X"));
    }

    /**
     * Helper method to get the maximum ID from BOTH penaltiesHistory.json
     * AND the user's local data file (data_huang_shihheng.json),
     * ensuring the new ID strictly follows the highest existing ID.
     */
    public static int getNextAvailableId() {
        int maxId = 78; // Default minimum base baseline using historical max ID

        Gson gson = new Gson();

        // 1. Check maximum ID from penaltiesHistory.json
        InputStream inputStream = Controller.class.getClassLoader().getResourceAsStream("penaltiesHistory.json");
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<ArrayList<PenaltyTracker>>() {}.getType();
            List<PenaltyTracker> historyList = gson.fromJson(reader, listType);

            if (historyList != null && !historyList.isEmpty()) {
                for (PenaltyTracker record : historyList) {
                    if (record.getId() > maxId) {
                        maxId = record.getId();
                    }
                }
            }
        }

        // 2. Check maximum ID from local user data file (data_huang_shihheng.json)
        try {
            Path filePath = Paths.get(PATH + FILE_NAME);
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        PenaltyTracker userRecord = gson.fromJson(line, PenaltyTracker.class);
                        if (userRecord.getId() > maxId) {
                            maxId = userRecord.getId();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading local data for ID check: " + e.getMessage());
        }

        return maxId + 1;
    }

    /**
     * Collects penalty details from the user (without asking for ID),
     * automatically assigns the next sequential ID, calculates the Impact Score,
     * and appends the JSON record to the user storage file.
     */
    public static void processAdd() {
        System.out.println("\n--- Add Penalty Record ---");
        PenaltyTracker penaltyTracker = new PenaltyTracker();
        penaltyTracker.getInformation(); // ID input is no longer requested here

        // Automatically assign the next sequential ID following the current max ID
        int nextId = getNextAvailableId();
        penaltyTracker.setId(nextId);

        // Calculate Impact Score
        PenaltyTrackerBO bo = new PenaltyTrackerBO();
        bo.calculate(penaltyTracker);

        try (FileWriter writer = new FileWriter(PATH + FILE_NAME, true)) {
            String jsonValue = penaltyTracker.toJson();
            writer.write(jsonValue + System.lineSeparator());
            writer.flush();
            System.out.println("Record saved successfully! Assigned ID: " + nextId + " | Impact Score: " + penaltyTracker.getImpactScore());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads and displays both historical JSON dataset
     * and user-added records together under View.
     */
    public static void processShow() {
        System.out.println("\n--- View All Penalty Records ---");
        Gson gson = new Gson();
        PenaltyTrackerBO bo = new PenaltyTrackerBO();

        // 1. Display historical records
        InputStream inputStream = Controller.class.getClassLoader().getResourceAsStream("penaltiesHistory.json");
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<ArrayList<PenaltyTracker>>() {}.getType();
            List<PenaltyTracker> historyList = gson.fromJson(reader, listType);

            if (historyList != null) {
                for (PenaltyTracker record : historyList) {
                    bo.calculate(record);
                    System.out.println(record.toString());
                    System.out.println("----------------------------------------");
                }
            }
        }

        // 2. Display user-added records
        try {
            Path filePath = Paths.get(PATH + FILE_NAME);
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                if (!lines.isEmpty()) {
                    for (String line : lines) {
                        if (!line.trim().isEmpty()) {
                            PenaltyTracker userRecord = gson.fromJson(line, PenaltyTracker.class);
                            bo.calculate(userRecord);
                            System.out.println(userRecord.toString());
                            System.out.println("----------------------------------------");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data file: " + e.getMessage());
        }
    }
}