package app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Appointments class provides appointment objects.
 * @author Elexis Rox
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

    //Overloaded Constructor for Appointment
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

    //Getters
    public int getApptID() {
        return apptID;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public String getApptDesc() {
        return apptDesc;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public String getApptType() {
        return apptType;
    }

    public LocalDateTime getApptStart() {
        return apptStart;
    }

    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    public int getApptUserID() {
        return apptUserID;
    }

    public int getApptContactID() {
        return apptContactID;
    }

    public int getApptCustomerID() {
        return apptCustomerID;
    }

    //Setters
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public void setApptDesc(String apptDesc) {
        this.apptDesc = apptDesc;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }

    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
    }

    public void setApptUserID(int apptUserID) {
        this.apptUserID = apptUserID;
    }

    public void setApptContactID(int apptContactID) {
        this.apptContactID = apptContactID;
    }

    public void setApptCustomerID(int apptCustomerID) {
        this.apptCustomerID = apptCustomerID;
    }

}
