package app.model;

/**
 * Countries class provides country objects.
 * @author Elexis Rox
 */

public class Country {

    private int countryID;
    private String countryName;

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

    //Creates an identifier for a country within the database based on the country's ID and name.
    public Country(int cID, String cName) {
        this.countryID = cID;    this.countryName = cName;}

}
