package app.model;

/**
 * Customers class provides customer objects.
 * @author Elexis Rox
 */

public class Customer {
    private int custID;
    private String custName;
    private String custAddress;
    private String custPostalCode;
    private String custPhone;
    //TODO GETTERS AND SETTERS FOR BELOW, IDK HOW TO AUTOPOPULATE? DO DBDIVISIONS FIRST?
    private int custDivisionID;
    private String custDivisionName;
    private int custCountryID;
    private String custCountryName;

    //TODO Overloaded Constructor for Customer
    public Customer() {

    }
    // Getters
    public int getCustID() {
        return custID;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public String getCustPostalCode() {
        return custPostalCode;
    }

    public String getCustPhone() {
        return custPhone;
    }

    // Setters
    public void setCustID(int custID) {
        this.custID = custID;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public void setCustPostalCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }
}
