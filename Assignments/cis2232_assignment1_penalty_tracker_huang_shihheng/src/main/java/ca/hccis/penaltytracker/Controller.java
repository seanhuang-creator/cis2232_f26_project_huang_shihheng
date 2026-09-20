package ca.hccis.penaltytracker;

import ca.hccis.penaltytracker.entity.PenaltyTracker;
import ca.hccis.penaltytracker.util.CisUtility;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controller class to manage menu navigation and JSON file storage for penalty records.
 *
 * @author seanhuang
 * @since 2026-09
 */
public class Controller {

    public static final String MENU = "A) Add" + System.lineSeparator()
            + "V) View" + System.lineSeparator()
            + "X) eXit" + System.lineSeparator()
            + "Option: ";

    public static final String PATH = "c:\\cis2232\\";
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
     * Collects penalty details from the user and appends the JSON record to the file.
     */
    public static void processAdd() {
        System.out.println("\n--- Add Penalty Record ---");
        PenaltyTracker penaltyTracker = new PenaltyTracker();
        penaltyTracker.getInformation();

        try (FileWriter writer = new FileWriter(PATH + FILE_NAME, true)) {
            String jsonValue = penaltyTracker.toJson();
            writer.write(jsonValue + System.lineSeparator());
            writer.flush();
            System.out.println("Record saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads all stored JSON penalty records from the file and displays them on the console.
     */
    public static void processShow() {
        System.out.println("\n--- View Penalty Records ---");
        Gson gson = new Gson();

        try {
            Path filePath = Paths.get(PATH + FILE_NAME);
            if (!Files.exists(filePath)) {
                System.out.println("No penalty records found.");
                return;
            }

            List<String> lines = Files.readAllLines(filePath);
            if (lines.isEmpty()) {
                System.out.println("No penalty records found.");
            } else {
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        PenaltyTracker record = gson.fromJson(line, PenaltyTracker.class);
                        System.out.println(record.toString());
                        System.out.println("----------------------------------------");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}