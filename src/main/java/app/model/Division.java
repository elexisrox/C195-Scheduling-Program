package app.model;

/**
 * Divisions class provides first level division objects.
 * @author Elexis Rox
 */

public class Division {
    private int divID;
    private String divName;

    // Getters
    public int getDivID() {
        return divID;
    }

    public String getDivName() {
        return divName;
    }

    // Setters
    public void setDivID(int divID) {
        this.divID = divID;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }
}
