package app.model;

/**
 * Contacts class provides contact objects.
 * @author Elexis Rox
 */

public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    //Overloaded constructor for Contact
    public Contact(int cID, String cName, String cEmail) {
        this.contactID = cID;
        this.contactName = cName;
        this.contactEmail = cEmail;
    }
    // Getters
    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    // Setters
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactEmail(String cEmail) {
        this.contactEmail = cEmail;
    }
}
