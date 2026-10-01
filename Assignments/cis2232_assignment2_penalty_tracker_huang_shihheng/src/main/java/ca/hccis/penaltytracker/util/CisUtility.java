package ca.hccis.penaltytracker.util;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

/**
 * Has some useful methods to be used in our programs.
 *
 * @author bjmaclean
 * @since Oct 19, 2021
 */
public class CisUtility {

    private static Scanner input = new Scanner(System.in);

    /**
     * Return the default currency String value of the double passed in as a
     * parameter.
     *
     * @param inputDouble double to be formatted
     * @return String in default currency format
     *
     * @since 20211020
     * @author BJM
     */
    public static String toCurrency(double inputDouble) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(inputDouble);
    }

    /**
     * Get input from the user using the console.
     *
     * @param prompt Prompt message for the user
     * @return String entered by the user
     * @since 20211020
     * @author BJM
     */
    public static String getInputString(String prompt) {

        System.out.print(prompt + " --> ");
        String output = input.nextLine();
        return output;
    }

    /**
     * Get input from the user using the console with validation for min/max length.
     *
     * @param prompt Prompt message for the user
     * @param minLength Minimum acceptable length for input
     * @param maxLength Maximum acceptable length for input
     * @return The validated String entered by the user
     * @since 2026-09
     * @author seanhuang
     */
    public static String getInputStringWithValidation(String prompt, int minLength, int maxLength) {
        String output = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt + " (" + minLength + "-" + maxLength + " chars): --> ");
            output = input.nextLine();

            if (output.length() >= minLength && output.length() <= maxLength) {
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter between " + minLength + " and " + maxLength + " characters.");
            }
        }

        return output;
    }

    /**
     * Get input int from the user with validation for min/max range.
     *
     * @param prompt Prompt message for the user
     * @param minValue Minimum acceptable value for input
     * @param maxValue Maximum acceptable value for input
     * @return The validated int entered by the user
     * @since 2026-09
     * @author seanhuang
     */
    public static int getInputIntWithValidation(String prompt, int minValue, int maxValue) {
        int output = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print(prompt + " (" + minValue + "-" + maxValue + "): --> ");
                String inputString = input.nextLine();
                output = Integer.parseInt(inputString);

                if (output >= minValue && output <= maxValue) {
                    valid = true;
                } else {
                    System.out.println("Invalid input. Please enter a value between " + minValue + " and " + maxValue + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return output;
    }


    /**
     * Get input double from the user using the console.
     *
     * @param prompt Prompt message for the user
     * @return The double entered by the user
     * @since 20211020
     * @author BJM
     */
    public static double getInputDouble(String prompt) {

        String inputString = getInputString(prompt);
        double output = Double.parseDouble(inputString);
        return output;
    }

    /**
     * Get input int from the user using the console.
     *
     * @param prompt Prompt message for the user
     * @return The int entered by the user
     * @since 20211020
     * @author BJM
     */
    public static int getInputInt(String prompt) {

        String inputString = getInputString(prompt);
        int output = Integer.parseInt(inputString);
        return output;
    }

    /**
     * Get input boolean from the user using the console.
     *
     * @param prompt Prompt message for the user
     * @return boolean value as specified by user input (y/n)
     * @since 20211108
     * @author BJM
     */
    public static boolean getInputBoolean(String prompt) {

        String inputString = getInputString(prompt + " (y/n)");
        if (inputString.equalsIgnoreCase("y")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Get input boolean from the user using the console with custom options.
     *
     * @param prompt Prompt message for the user
     * @param affirmative The string value representing "yes"
     * @param negative The string value representing "no"
     * @return boolean value as specified by user input
     * @since 20211108
     * @author BJM
     */
    public static boolean getInputBoolean(String prompt, String affirmative, String negative) {

        String inputString = getInputString(prompt + " (" + affirmative + "/" + negative + ")");
        if (inputString.equalsIgnoreCase(affirmative)) {
            return true;
        } else {
            return false;
        }

    }


    
    /**
     * Provide today's date in the specified format.
     *
     * @param format Date format pattern (e.g., "yyyy-MM-dd")
     * @return Today's date in specified format
     * @since 20211021
     * @author BJM
     */
    public static String getTodayString(String format) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Get a random number between min and max (inclusive).
     *
     * @param min Minimum value for random number
     * @param max Maximum value for random number
     * @return Random integer between min and max
     * @since 20211109
     * @author BJM
     */
    public static int getRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    
    
}
