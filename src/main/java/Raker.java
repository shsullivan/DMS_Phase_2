/*
 * Shawn Sullivan
 * CEN 3024 - Software Development 1
 * June 15, 2025
 * Raker.java
 * This class defines all the methods necessary perform administrative actions in the D.I.S.C. App. These actions
 * include adding a new disc, removing a disc, list all discs, list returned discs, list sold discs,
 * make bulk disc adds via a text file with "-" delimiters, search for a disc by contact phone, calculate value of all
 * returned discs, and calculate the value of all sold discs. In this iteration, this class with also contain the
 * hashmap that stores all disc objects with their associated ID.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.round;

public class Raker {

    // Attributes
    private final Map<Integer, Disc> discs = new HashMap<>();

    // App will sue generic constructor. No specific constructors need to be entered.

    /*
     * Method: addDisc
     * Parameter(s): Disc disc
     * Returns: boolean
     * Purpose: Adds a disc to the DISC App, specifically the discs HashMap and returns true or false for UI
     * response purposes in the main() method.
     */
    public boolean addDisc(Disc disc) {
        if (discs.containsKey(disc.getDiscID())) {
            return false;
        }
        else {
            discs.put(disc.getDiscID(), disc);
            return true;
        }
    }

    /*
     * Method: addFromFile
     * Parameters: String filePath
     * Returns: boolean
     * Purpose: Adds users from a text file with information delimited by a dash "-" character. If the text in file
     * is not properly formatted or doesn't contain all needed information a message is displayed to the user notifying
     * them of errors. Method also catches and handles exceptions thrown by the Disc constructor
     * for attributes that do not meet the validation criteria of the Disc constructor and exceptions if the file
     * cannot be found/read.
     */
    public void addFromFile(String filePath) {

        // Create buffer reader and load file containing data
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;       // Working variable to hold current line of text file
            int lineCount = 0; // Working variable to iterate through lines of file if multiple lines exist

            while ((line = br.readLine()) != null) {// While loop to iterate through lines of text file
                lineCount++;

                String[] attributes = line.split("-"); // Working list variable to hold separated attributes

                if (attributes.length != 13) {  // Verify that all 13 attributes needed for new Disc are present
                    System.out.println("Line " + lineCount + " is invalid and will be skipped");
                    continue;
                }

                try { // Try to create a new Disc object from current line

                    // Set working variables to hold attributes used in Disc constructor
                    int discID = Integer.parseInt(attributes[0].trim());
                    String manufacturer = attributes[1].trim();
                    String mold = attributes[2].trim();
                    String plastic = attributes[3].trim();
                    String color = attributes[4].trim();
                    int condition = Integer.parseInt(attributes[5].trim());
                    String description = attributes[6].trim();
                    String contactName = attributes[7].trim();
                    String contactPhone = attributes[8].trim();
                    String foundAt = attributes[9].trim();
                    boolean returned = Boolean.parseBoolean(attributes[10].trim());
                    boolean sold = Boolean.parseBoolean(attributes[11].trim());
                    double MSRP = Double.parseDouble(attributes[12].trim());

                    // Check if disc on current line already exists and notify if added or already exists
                    if (addDisc(new Disc(discID, manufacturer, mold, plastic, color, condition, description,
                                         contactName, contactPhone, foundAt, returned, sold, MSRP))) {
                        System.out.println("Disc " + discID + " added");
                    }
                    else {
                        System.out.println("Disc " + discID + " already exists");
                    }
                }
                // Catch invalid number formatting for ints and  doubles
                catch (NumberFormatException e) {
                    System.out.println("Invalid monetary value formatting for Disc at line: " + lineCount);
                }
                // Catch any validation errors thrown by Disc constructor
                catch (IllegalArgumentException e) {
                    System.out.println("Invalid entry for Disc at line" + lineCount + ": " + e.getMessage());
                }
            }

            // Notify user that Discs were added successfully
            System.out.println("Discs added from: " + filePath);
        }
        // Catch file path or file read error
        catch (IOException e) { // Catch file path or file read error
            System.out.println("Error reading file " + filePath + ": " + e.getMessage());
        }
    }

    /*
     * Method: removeDisc
     * Parameters: int discID
     * Returns: boolean
     * Purpose: Removes a Disc from the DISC App, specifically the discs HashMap and returns true if the discID
     * exists in the HashMap and false if it does not. the return value is used for UI response purposes in the
     * main() method.
     */
    public boolean removeDisc(int discID) {
        return discs.remove(discID) != null;
    }

    /*
     * Method: listAllDiscs
     * Parameters: none
     * Returns: boolean
     * Purpose: Lists all discs stored in the DISC App, specifically in the discs HashMap. If discs HashMap is
     * empty listAllDiscs returns false and the main() method notifies the user in the UI
     */
    public boolean listAllDiscs() {
        if (discs.isEmpty())
            return false;
        else {
            discs.values().forEach(System.out::println);
            return true;
        }
    }

    /*
     * Method: listReturnedDiscs
     * Parameters: none
     * Returns: boolean
     * Purpose: Lists all discs stored in the DISC App that have been returned, specifically in the discs HashMap.
     * If discs HashMap is empty listReturnedDiscs returns false and the main() method notifies the user in the UI
     */
    public boolean listReturnedDiscs() {
        if (discs.isEmpty()) {
            return false;
        }
        else {
            for (Disc disc : discs.values()) {
                if (disc.isReturned()) {
                    System.out.println(disc);
                }
            }
            return true;
        }
    }

    /*
     * Method: listSoldDiscs
     * Parameters: none
     * Returns: boolean
     * Purpose: Lists all discs stored in the DISC App that have been sold, specifically in the discs HashMap.
     * If discs HashMap is empty listReturnedDiscs returns false and the main() method notifies the user in the UI
     */
    public boolean listSoldDiscs() {
        if (discs.isEmpty()) {
            return false;
        }
        else {
            for (Disc disc : discs.values()) {
                if (disc.isSold()) {
                    System.out.println(disc);
                }
            }
            return true;
        }
    }

    /*
     * Method: findByPhone
     * Parameters: String phoneNumber
     * Returns: boolean
     * Purpose: Iterates through the discs database (Hashmap in this iteration) to locate all discs who's contactPhone
     * attribute is equal to the input phone number then prints those discs information to the console.
     */
    public boolean findByPhone(String phoneNumber) {
        if (discs.isEmpty()) {
            return false;
        }
        else {
            boolean found = false;
            for (Disc disc : discs.values()) {
                if (disc.getContactPhone().equals(phoneNumber)) {
                    System.out.println(disc);
                    found = true;
                }
            }
            return found;
        }
    }

    /*
     * Method: calculateReturnedValue
     * Parameters: None
     * Returns: double
     * Purpose: Iterates through the discs database (Hashmap in this iteration) to locate discs who's returned
     * attribute is set to true. The method then adds the MSRP of each disc with a return value of true to a running
     * total (value variable) and returns that value to be displayed to the UI.
     */
    public double calculateReturnedValue() {
        double value = 0;
        for (Disc disc : discs.values()) {
            if (disc.isReturned()) {
                value += disc.getMSRP();
            }
        }
        return value;
    }

    /*
     * Method: calculateSoldValue
     * Parameters: None
     * Returns: double
     * Purpose:Iterates through the discs database (Hashmap in this iteration) to locate discs who's sold
     * attribute is set to true. The method then adds the resale value of each disc with a sold value of true to a
     * running total (value variable) and returns that value to be displayed to the UI.
     */
    public double calculateSoldValue() {
        double value = 0;
        for (Disc disc : discs.values()) {
            if (disc.isSold()) {
                value += disc.getResaleValue();
            }
        }
        return round(value);
    }

    /*
     * Method: returnDisc
     * Parameters: int discID
     * Returns: boolean
     * Purpose: Changes the "returned" value of a given disc from false to true
     */
    public boolean returnDisc(int discID) {
        if (discs.isEmpty() || !discs.containsKey(discID)) {
            return false;
        }
        else {
            for (Disc disc : discs.values()) {
                if (disc.getDiscID() == discID) {
                    disc.setReturned(true);
                }
            }
        }
        return true;
    }

    /*
     * Method: sellDisc
     * Parameters: int discID
     * Returns: boolean
     * Purpose: Changes the "sold" value of a given disc from false to true
     */
    public boolean sellDisc(int discID) {
        if (discs.isEmpty() || !discs.containsKey(discID)) {
            return false;
        }
        else {
            for (Disc disc : discs.values()) {
                if (disc.getDiscID() == discID) {
                    disc.setSold(true);
                }
            }
        }
        return true;
    }

    /*
     * Method: updateContactInformation
     * Parameters: int discID
     * Returns: boolean
     * Purpose: Updates all contact information associated with given Disc ID
     */
    public boolean updateContactInformation(int discID, String contactName, String contactPhone) {
        if (discs.isEmpty() || !discs.containsKey(discID)) {
            return false;
        }
        else {
            for (Disc disc : discs.values()) {
                if (disc.getDiscID() == discID) {
                    disc.setContactName(contactName);
                    disc.setContactPhone(contactPhone);
                }
            }
        }
        return true;
    }
}

