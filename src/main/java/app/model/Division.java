package app.model;

/**
 * Divisions class provides first level division objects.
 * Represents a division with various attributes such as ID, name, and country ID.
 * Includes constructors, getters, and setters for these attributes.
 *
 * @author Elexis Rox
 */
public class Division {
    private int divID;
    private String divName;
    private int divCountryID;

    /**
     * Overloaded Constructor for First Level Divisions.
     *
     * @param divID the division ID
     * @param divName the division name
     * @param divCountryID the country ID to which the division belongs
     */
    public Division(int divID, String divName, int divCountryID) {
        this.divID = divID;
        this.divName = divName;
        this.divCountryID = divCountryID;
    }

    /**
     * Additional Constructor for First Level Divisions that excludes Country_ID.
     * Used for searching by Division ID to obtain a Division's Name.
     *
     * @param divID the division ID
     * @param divName the division name
     */
    public Division(int divID, String divName) {
        this.divID = divID;
        this.divName = divName;
    }

    /**
     * Gets the division ID.
     *
     * @return the division ID
     */
    public int getDivID() {
        return divID;
    }

    /**
     * Sets the division ID.
     *
     * @param divID the division ID to set
     */
    public void setDivID(int divID) {
        this.divID = divID;
    }

    /**
     * Gets the division name.
     *
     * @return the division name
     */
    public String getDivName() {
        return divName;
    }

    /**
     * Sets the division name.
     *
     * @param divName the division name to set
     */
    public void setDivName(String divName) {
        this.divName = divName;
    }

    /**
     * Gets the country ID to which the division belongs.
     *
     * @return the country ID
     */
    public int getDivCountryID() {
        return divCountryID;
    }

    /**
     * Sets the country ID to which the division belongs.
     *
     * @param divCountryID the country ID to set
     */
    public void setDivCountryID(int divCountryID) {
        this.divCountryID = divCountryID;
    }
}
