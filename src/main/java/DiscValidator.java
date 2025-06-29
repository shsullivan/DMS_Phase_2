/*
 * Shawn Sullivan
 * CEN 3024 - Software Development 1
 * June 29, 2025
 * DiscValidator.java
 * This file contains all logic to ensure user inputs are valid when creating a new Disc object. Placing the logic in
 * its own class allows for better maintainability and scalability.
 */

public final class DiscValidator {

    // This class is strictly for utility. To protect against instantiation, the constructor must be made private
    private DiscValidator() {

    }

    public static int validateDiscID(int discID) {
        if (discID < 0) {
            throw new IllegalArgumentException("Disc ID cannot be a negative number");
        } else if (discID == 0) {
            throw new IllegalArgumentException("Disc ID cannot be zero");
        } else {
            return discID;
        }
    }
    // Helper method to validate all text fields with a max length
    public static String validateTextField(String input, String fieldName, int maxLength) {
        if (input == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        } else if (input.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        } else if (input.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot be longer than " + maxLength + " characters");
        }
        return input;
    }
    // Helper method to validate text fields that do not have a max length restriction
    public static String validateNotEmpty(String input, String fieldName) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return input;
    }

    public static String validateManufacturer(String input) {
        return validateTextField(input, "Manufacturer", 20);
    }

    public static String validateMold(String input) {
        return validateTextField(input, "Mold", 20);
    }

    public static String validatePlastic(String input) {
        return validateTextField(input, "Plastic", 20);
    }

    public static String validateColor(String input) {
        return validateTextField(input, "Color", 20);
    }

    public static int validateCondition(int condition) {
        if (condition < 1 || condition > 10) {
            throw new IllegalArgumentException("Condition must be between 1 and 10");
        }
        return condition;
    }

    public static String validateDescription(String input) {
        return validateNotEmpty(input, "Description");
    }

    public static String validateContactName(String input) {
        return validateTextField(input, "ContactName", 40);
    }

    public static String validateContactPhone(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Contact Phone cannot be empty");
        } else if (input.length() != 10) {
            throw new IllegalArgumentException("Contact Phone must be 10 digits");
        }
        return input;
    }

    public static String validateFoundAt(String input) {
        return validateTextField(input, "Found at location", 50);
    }

    public static double validateMSRP(double MSRP) {
        if (MSRP < 0) {
            throw new IllegalArgumentException("MSRP cannot be negative");
        }
        return MSRP;
    }



}
