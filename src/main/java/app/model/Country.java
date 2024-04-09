package app.model;

/**
 * Countries class provides country objects.
 * @author Elexis Rox
 */

public class Country {

    private int countryID;
    private String countryName;

    //Overloaded constructor for Country
    public Country(int cID, String cName) {
        this.countryID = cID;
        this.countryName = cName;}

    // Getters
    public int getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    // Setters
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
