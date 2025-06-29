/*
 * Shawn Sullivan
 * CEN 3024 - Software Development 1
 * June 15, 2025
 * Disc.java
 * This file defines the Disc class for the Disc Information Storage Center (D.I.S.C.) App, verifies the arguments are
 * in the correct format, and overrides the toString method to properly format the console print for a Patron object.
 */


public class Disc {

    // Attributes
    private final int discID;
    private String manufacturer;
    private String mold;
    private String plastic;
    private String color;
    private int condition;
    private String description;
    private String contactName;
    private String contactPhone;
    private String foundAt; // Course where disc was found
    private boolean returned;
    private boolean sold;
    private double MSRP;
    private double resaleValue;

    // Constructor
    public Disc(int discID, String manufacturer, String mold, String plastic, String color, int condition,
                String description, String contactName, String contactPhone, String foundAt, boolean returned,
                boolean sold, double MSRP) {

        this.discID = discID;
        this.manufacturer = manufacturer;
        this.mold = mold;
        this.plastic = plastic;
        this.color = color;
        this.condition = condition;
        this.description = description;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.foundAt = foundAt;
        this.returned = returned;
        this.sold = sold;
        this.MSRP = MSRP;

        // Resale value is calculated by multiplying MSRP by a % based on condition
        // condition 1-5 = 40% MSRP; condition 6-7 = 60% MSRP; condition 8-10 = 80% MSRP
        if (condition <= 5) {
            this.resaleValue = this.MSRP * 0.4;
        }
        else if ((condition == 6) || (condition == 7)) {
            this.resaleValue = this.MSRP * 0.6;
        }
        else {
            this.resaleValue = this.MSRP * 0.8;
        }
    }

    // Getters and Setters

    public int getDiscID() {
        return discID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getMold() {
        return mold;
    }

    public String getPlastic() {
        return plastic;
    }

    public String getColor() {
        return color;
    }

    public int getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getFoundAt() {
        return foundAt;
    }

    public boolean isReturned() {
        return returned;
    }

    public boolean isSold() {
        return sold;
    }

    public double getMSRP() {
        return MSRP;
    }

    public double getResaleValue() {
        return resaleValue;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer cannot be null");
        }
        else if (manufacturer.isEmpty()) {
            throw new IllegalArgumentException("Manufacturer cannot be empty");
        }
        else {
            this.manufacturer = manufacturer;
        }
    }

    public void setMold(String mold) {
        if (mold == null) {
            throw new IllegalArgumentException("Mold cannot be null");
        }
        else if (mold.isEmpty()) {
            throw new IllegalArgumentException("Mold cannot be empty");
        }
        else {
            this.mold = mold;
        }
    }

    public void setPlastic(String plastic) {
        if (plastic == null) {
            throw new IllegalArgumentException("Plastic cannot be null");
        }
        else if (plastic.isEmpty()) {
            throw new IllegalArgumentException("Plastic cannot be empty");
        }
        else {
            this.plastic = plastic;
        }
    }

    public void setColor(String color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        else if (color.isEmpty()) {
            throw new IllegalArgumentException("Color cannot be empty");
        }
        else {
            this.color = color;
        }
    }

    public void setCondition(int condition) {
        if (condition < 1 || condition > 10) {
            throw new IllegalArgumentException("Condition must be between 1 and 10");
        }
        else {
            this.condition = condition;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContactName(String contactName) {
        if (contactName == null) {
            throw new IllegalArgumentException("ContactName cannot be null");
        }
        else if (contactName.isEmpty()) {
            throw new IllegalArgumentException("ContactName cannot be empty");
        }
        else {
            this.contactName = contactName;
        }
    }

    public void setContactPhone(String contactPhone) {
        if (contactPhone == null) {
            throw new IllegalArgumentException("ContactPhone cannot be null");
        }
        else if (contactPhone.isEmpty()) {
            throw new IllegalArgumentException("ContactPhone cannot be empty");
        }
        else {
            this.contactPhone = contactPhone;
        }
    }

    public void setFoundAt(String foundAt) {
        if (foundAt == null) {
            throw new IllegalArgumentException("FoundAt cannot be null");
        }
        else if (foundAt.isEmpty()) {
            throw new IllegalArgumentException("FoundAt cannot be empty");
        }
        else {
            this.foundAt = foundAt;
        }
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setMSRP(double MSRP) {
        if (MSRP <= 0) {
            throw new IllegalArgumentException("MSRP cannot be negative");
        }
        else {
            this.MSRP = MSRP;
        }
    }

    public void setResaleValue(double resaleValue) {
        if (resaleValue < 0) {
            throw new IllegalArgumentException("ResaleValue cannot be negative");
        }
        else {
            this.resaleValue = resaleValue;
        }
    }

    @Override
    public String toString() {
        return discID + "|" + manufacturer + "|" + mold + "|" + plastic + "|" + color + "|" + condition + "|" +
                description + "|" + contactName + "|" + contactPhone + "|" + foundAt + "|" + returned + "|" +
                sold + "|" + MSRP + "|" + resaleValue;
    }
}
