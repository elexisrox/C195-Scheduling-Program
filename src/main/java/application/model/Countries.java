package application.model;

/**
 * Countries class provides country objects.
 */
public class Countries {

    int countryID;
    String countryName;

    public Countries(int cID, String cName) {
        this.countryID = cID;
        this.countryName = cName;
    }

    public int getID() {
        return countryID;
    }

    public void setID(int cID) {
        this.countryID = cID;
    }

    public String getName() {
        return countryName;
    }

    public void setName(String cName) {
        this.countryName = cName;
    }
}
