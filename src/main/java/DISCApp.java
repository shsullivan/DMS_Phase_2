/*
 * Shawn Sullivan
 * CEN 3024 - Software Development 1
 * June 15, 2025
 * DISCApp.java
 * This class contains the main() method for the DISC App. When initialized this class instantiates an instance of the
 * Raker class and the main method prompts the user to select from a list of interactions with the DISC database
 * which is stored in the Raker class in the form of a HashMap. The main method then calls on other methods
 * defined in the Raker and Disc classes based on the user's input. The main method then gives the user
 * feedback based on whether the action was successful or not. Once the user has completed all their actions they can
 * exit the application via the last option in the actions list.
 */

import java.util.Scanner;

public class DISCApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Raker raker = new Raker();
        boolean done = false; // Working variable to allow app to run or user to exit

        while (!done) {
            // Menu graphic to direct user inputs
            System.out.println("----Disc Information Storage Center----");
            System.out.println("Main Menu:");
            System.out.println("1. Enter new Disc Information");
            System.out.println("2. Load Discs from text file");
            System.out.println("3. Remove a Disc from the system");
            System.out.println("4. List all Discs");
            System.out.println("5. List returned Discs");
            System.out.println("6. List sold Discs");
            System.out.println("7. Mark a disc returned");
            System.out.println("8. Mark a disc sold");
            System.out.println("9. Update contact information for a Disc");
            System.out.println("10. Search records by Contact Phone Number");
            System.out.println("11. Show total value of Discs returned to owners");
            System.out.println("12. Show total value of Discs sold to retailers");
            System.out.println("13. Exit");
            System.out.println("Make a selection by entering the corresponding number above: ");

            String selection = sc.nextLine().trim();

            switch (selection) {
                case "1": // Add Disc
                    try {
                        System.out.println("Enter Disc ID: ");
                        int discID = DiscValidator.validatePositiveInt(sc.nextLine(), "Disc ID");
                        System.out.println("Enter Manufacturer: ");
                        String manufacturer = DiscValidator.validateManufacturer(sc.nextLine().trim());
                        System.out.println("Enter Mold: ");
                        String mold = DiscValidator.validateMold(sc.nextLine().trim());
                        System.out.println("Enter Plastic Type: ");
                        String plastic = DiscValidator.validatePlastic(sc.nextLine().trim());
                        System.out.println("Enter Color: ");
                        String color = DiscValidator.validateColor(sc.nextLine().trim());
                        System.out.println("Enter Condition 1 - 10: ");
                        int condition = DiscValidator.validateCondition(sc.nextLine().trim());
                        System.out.println("Enter additional information: ");
                        String description = DiscValidator.validateDescription(sc.nextLine().trim());
                        System.out.println("Enter Contact Name if present otherwise enter 'N/A': ");
                        String contactName = DiscValidator.validateContactName(sc.nextLine().trim());
                        System.out.println("Enter Contact Phone Number if present otherwise enter 'N/A': ");
                        String contactPhone = DiscValidator.validateContactPhone(sc.nextLine().trim());
                        System.out.println("Enter name of course where the disc was found: ");
                        String foundAt = DiscValidator.validateFoundAt(sc.nextLine().trim());
                        System.out.println("Enter 'true' if the disc has been returned otherwise enter 'false': ");
                        boolean returned = DiscValidator.validateBooleanInput(sc.nextLine().trim(), "Returned");
                        System.out.println("Enter 'true' if the disc has been sold otherwise enter 'false': ");
                        boolean sold = DiscValidator.validateBooleanInput(sc.nextLine().trim(), "Sold");
                        System.out.println("Enter the MSRP for the disc: ");
                        double MSRP = DiscValidator.validatePositiveDouble(sc.nextLine().trim(), "MSRP");

                        Disc disc = new Disc(discID, manufacturer, mold, plastic, color, condition,description,
                                            contactName, contactPhone, foundAt, returned, sold, MSRP);

                        // Attempt to add new Disc to HashMap and provide feedback on success or failure
                        if (raker.addDisc(disc)) {
                            System.out.println("Disc added successfully!");
                        }
                        else {
                            System.out.println("DiscID already exists! Disc not added!");
                        }
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Invalid input: " + e.getMessage());
                    }
                    break;

                case "2": // Add disc(s) from text file
                    System.out.println("Enter file path: ");
                    String path = sc.nextLine().trim(); // Capture file path entered in working variable
                    raker.addFromFile(path);
                    break;

                case "3": // Remove a disc
                    try {
                        System.out.println("Enter Disc ID to remove: ");
                        int remove = Integer.parseInt(sc.nextLine().trim()); // Capture Disc ID in working variable
                        if (raker.removeDisc(remove)) {
                            System.out.println("Disc removed successfully!");
                        } else {
                            System.out.println("Disc ID does not exist! No discs were removed!");
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Invalid entry! Disc ID must be a number!");
                    }
                    break;

                case "4": // List all discs
                    System.out.println("---Disc List---");
                    if(raker.listAllDiscs()) {
                        System.out.println("---End of List---");
                    }
                    else {
                        System.out.println("Disc Database is empty!");
                    }
                    break;

                case "5": // List returned discs
                    System.out.println("---Returned Discs---");
                    if(raker.listReturnedDiscs()) {
                        System.out.println("---End of List---");
                    }
                    else {
                        System.out.println("Disc Database is empty!");
                    }
                    break;

                case "6": // List sold discs
                    System.out.println("---Sold Discs---");
                    if(raker.listSoldDiscs()) {
                        System.out.println("---End of List---");
                    }
                    else {
                        System.out.println("Disc Database is empty!");
                    }
                    break;

                case "7": // Mark a disc returned
                    try {
                        System.out.println("Enter Disc ID to mark returned: ");
                        int returnedDisc = Integer.parseInt(sc.nextLine().trim());
                        if (raker.returnDisc(returnedDisc)) {
                            System.out.println("Disc has been marked as returned! Great job!");
                        } else {
                            System.out.println("Disc ID does not exist! No discs were marked as returned!");
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Invalid entry! Disc ID must be a number!");
                    }
                    break;

                case "8": // Mark a disc sold
                    try {
                        System.out.println("Enter Disc ID to mark sold: ");
                        int soldDisc = Integer.parseInt(sc.nextLine().trim());
                        if (raker.sellDisc(soldDisc)) {
                            System.out.println("Disc has been marked sold! Great job!");
                        } else {
                            System.out.println("Disc ID does not exist! No discs were marked sold!");
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Invalid entry! Disc ID must be a number!");
                    }
                    break;

                case "9": // Update contact information associated with a disc
                    try {
                        System.out.println("Enter Disc ID you would like to update Contact Information for: ");
                        int discUpdate = Integer.parseInt(sc.nextLine().trim());
                        System.out.println("Enter the new Contact Name: ");
                        String contactName = sc.nextLine().trim();
                        System.out.println("Enter the new Contact Phone Number: ");
                        String contactPhone = sc.nextLine().trim();

                        if (raker.updateContactInformation(discUpdate, contactName, contactPhone)) {
                            System.out.println("Contact information updated successfully!");
                        }
                        else {
                            System.out.println("Contact information could not be updated! Disc ID does not exist!");
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Invalid entry! Disc ID must be a number!");
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Invalid entry! Contact Name and Phone Number cannot be null!");
                    }
                    break;

                case "10": // Search for a disc by provided phone number
                    System.out.println("Enter phone number to search for: ");
                    String search = sc.nextLine().trim();
                    if (raker.findByPhone(search)) {
                        System.out.println("---End of List---");
                    }
                    else {
                        System.out.println("No Discs with this contact phone number exists!");
                    }
                    break;

                case "11": // Report total value of Discs returned to owners
                    double returnedValue = raker.calculateReturnedValue();
                    System.out.println("You have returned $" + returnedValue + " in discs to owners. Good job!");
                    break;

                case "12": // Report total value of Discs sold to retailers
                    double soldValue = raker.calculateSoldValue();
                    System.out.println("You have sold $" + soldValue + " worth of discs for profit. Big Money!");
                    break;

                case "13":
                    done = true;
                    System.out.println("Thank you for using D.I.S.C.! Goodbye!");
                    break;

                default:
                    System.out.println("Invalid entry! Please try again!");
            }
        }
        sc.close();
    }
}
