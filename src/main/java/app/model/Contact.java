package app.model;

/**
 * Contacts class provides contact objects.
 * Represents a contact with the attributes: ID, name, and email.
 * Includes constructors, getters, and setters for these attributes.
 *
 * @author Elexis Rox
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Overloaded constructor for Contact.
     *
     * @param cID the contact ID
     * @param cName the contact name
     * @param cEmail the contact email
     */
    public Contact(int cID, String cName, String cEmail) {
        this.contactID = cID;
        this.contactName = cName;
        this.contactEmail = cEmail;
    }
    /**
     * Gets the contact ID.
     *
     * @return the contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contact ID.
     *
     * @param contactID the contact ID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name.
     *
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the contact email.
     *
     * @return the contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the contact email.
     *
     * @param contactEmail the contact email to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
