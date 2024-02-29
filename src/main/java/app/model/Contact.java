package app.model;

/**
 * Contacts class provides contact objects.
 * @author Elexis Rox
 */

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    // Getters
    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
