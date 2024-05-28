package app.model;

import java.time.LocalDateTime;

/**
 * Appointment class provides appointment objects.
 * Represents an appointment with various attributes such as ID, title, description, location, type, start and end times, user ID, contact ID, and customer ID.
 * Includes constructors, getters, and setters for these attributes.
 *
 * @author
 * Elexis Rox
 */
public class Appointment {

    private int apptID;
    private String apptTitle;
    private String apptDesc;
    private String apptLocation;
    private String apptType;
    private LocalDateTime apptStart;
    private LocalDateTime apptEnd;
    private int apptUserID;
    private int apptContactID;
    private int apptCustomerID;

    /**
     * Overloaded Constructor for Appointment.
     *
     * @param apptID        the appointment ID
     * @param apptTitle     the appointment title
     * @param apptDesc      the appointment description
     * @param apptLocation  the appointment location
     * @param apptType      the appointment type
     * @param apptStart     the appointment start time
     * @param apptEnd       the appointment end time
     * @param apptUserID    the user ID associated with the appointment
     * @param apptContactID the contact ID associated with the appointment
     * @param apptCustomerID the customer ID associated with the appointment
     */
    public Appointment(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptUserID, int apptContactID, int apptCustomerID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptUserID = apptUserID;
        this.apptContactID = apptContactID;
        this.apptCustomerID = apptCustomerID;
    }

    /**
     * Gets the appointment ID.
     *
     * @return the appointment ID
     */
    public int getApptID() {
        return apptID;
    }

    /**
     * Sets the appointment ID.
     *
     * @param apptID the appointment ID to set
     */
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    /**
     * Gets the appointment title.
     *
     * @return the appointment title
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * Sets the appointment title.
     *
     * @param apptTitle the appointment title to set
     */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * Gets the appointment description.
     *
     * @return the appointment description
     */
    public String getApptDesc() {
        return apptDesc;
    }

    /**
     * Sets the appointment description.
     *
     * @param apptDesc the appointment description to set
     */
    public void setApptDesc(String apptDesc) {
        this.apptDesc = apptDesc;
    }

    /**
     * Gets the appointment location.
     *
     * @return the appointment location
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     * Sets the appointment location.
     *
     * @param apptLocation the appointment location to set
     */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * Gets the appointment type.
     *
     * @return the appointment type
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * Sets the appointment type.
     *
     * @param apptType the appointment type to set
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * Gets the appointment start time.
     *
     * @return the appointment start time
     */
    public LocalDateTime getApptStart() {
        return apptStart;
    }

    /**
     * Sets the appointment start time.
     *
     * @param apptStart the appointment start time to set
     */
    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }

    /**
     * Gets the appointment end time.
     *
     * @return the appointment end time
     */
    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    /**
     * Sets the appointment end time.
     *
     * @param apptEnd the appointment end time to set
     */
    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
    }

    /**
     * Gets the user ID associated with the appointment.
     *
     * @return the user ID
     */
    public int getApptUserID() {
        return apptUserID;
    }

    /**
     * Sets the user ID associated with the appointment.
     *
     * @param apptUserID the user ID to set
     */
    public void setApptUserID(int apptUserID) {
        this.apptUserID = apptUserID;
    }

    /**
     * Gets the contact ID associated with the appointment.
     *
     * @return the contact ID
     */
    public int getApptContactID() {
        return apptContactID;
    }

    /**
     * Sets the contact ID associated with the appointment.
     *
     * @param apptContactID the contact ID to set
     */
    public void setApptContactID(int apptContactID) {
        this.apptContactID = apptContactID;
    }

    /**
     * Gets the customer ID associated with the appointment.
     *
     * @return the customer ID
     */
    public int getApptCustomerID() {
        return apptCustomerID;
    }

    /**
     * Sets the customer ID associated with the appointment.
     *
     * @param apptCustomerID the customer ID to set
     */
    public void setApptCustomerID(int apptCustomerID) {
        this.apptCustomerID = apptCustomerID;
    }
}
