package app.model;

/**
 * Countries class provides country objects.
 * Represents a country with the attributes: ID and name.
 * Includes constructors, getters, and setters for these attributes.
 *
 * @author Elexis Rox
 */
public class Country {
    private int countryID;
    private String countryName;

    /**
     * Overloaded constructor for Country.
     *
     * @param cID the country ID
     * @param cName the country name
     */
    public Country(int cID, String cName) {
        this.countryID = cID;
        this.countryName = cName;
    }

    /**
     * Gets the country ID.
     *
     * @return the country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the country ID.
     *
     * @param countryID the country ID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Gets the country name.
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the country name.
     *
     * @param countryName the country name to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
