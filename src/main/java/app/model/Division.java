package app.model;

/**
 * Divisions class provides first level division objects.
 * @author Elexis Rox
 */

public class Division {
    private int divID;
    private String divName;
    private int divCountryID;

    //Overloaded Constructor for First Level Divisions
    public Division(int divID, String divName, int divCountryID) {
        this.divID = divID;
        this.divName = divName;
        this.divCountryID = divCountryID;
    }

    //Additional Constructor for First Level Divisions that excludes Country_ID. Used for searching by Division ID to obtain a Division's Name.
    public Division(int divID, String divName) {
        this.divID = divID;
        this.divName = divName;
    }

    //Getters
    public int getDivID() {
        return divID;
    }

    public String getDivName() {
        return divName;
    }

    public int getDivCountryID() { return divCountryID; }

    //Setters
    public void setDivID(int divID) {
        this.divID = divID;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }

    public void setDivCountryID(int divCountryID) { this.divCountryID = divCountryID; }
}
