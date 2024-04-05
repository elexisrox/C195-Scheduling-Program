package app.model;

import java.time.LocalDate;
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
    private LocalDate apptStartDate;
    private LocalDate apptEndDate;
    private LocalTime apptStartTime;
    private LocalTime apptEndTime;
    private int apptUserID;
    private int apptContactID;
    private int apptCustomerID;
    public Appointment(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, LocalDate apptStartDate, LocalDate apptEndDate, LocalTime apptStartTime, LocalTime apptEndTime, int apptUserID, int apptContactID, int apptCustomerID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartDate = apptStartDate;
        this.apptEndDate = apptEndDate;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
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

    public LocalDate getApptStartDate() {
        return apptStartDate;
    }

    public LocalDate getApptEndDate() {
        return apptEndDate;
    }

    public LocalTime getApptStartTime() {
        return apptStartTime;
    }

    public LocalTime getApptEndTime() {
        return apptEndTime;
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

    public void setApptStartDate(LocalDate apptStartDate) {
        this.apptStartDate = apptStartDate;
    }

    public void setApptEndDate(LocalDate apptEndDate) {
        this.apptEndDate = apptEndDate;
    }

    public void setApptStartTime(LocalTime apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public void setApptEndTime(LocalTime apptEndTime) {
        this.apptEndTime = apptEndTime;
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
