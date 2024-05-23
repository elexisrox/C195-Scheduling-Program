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
    private int custDivisionID;
    private String custDivisionName;
    private int custCountryID;
    private String custCountryName;

    //Overloaded Constructor for Customer
    public Customer(int custID, String custName, String custAddress, String custPostalCode, String custPhone, int custDivisionID, String custDivisionName, int custCountryID, String custCountryName) {
        this.custID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPostalCode = custPostalCode;
        this.custPhone = custPhone;
        this.custDivisionID = custDivisionID;
        this.custDivisionName = custDivisionName;
        this.custCountryID = custCountryID;
        this.custCountryName = custCountryName;
    }

    //Additional Constructor for Customer that matches database fields
    public Customer(int custID, String custName, String custAddress, String custPostalCode, String custPhone, int custDivisionID) {
        this.custID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPostalCode = custPostalCode;
        this.custPhone = custPhone;
        this.custDivisionID = custDivisionID;
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

    public int getCustDivisionID() {
        return custDivisionID;
    }

    public String getCustDivisionName() {
        return custDivisionName;
    }

    public int getCustCountryID() {
        return custCountryID;
    }

    public String getCustCountryName() {
        return custCountryName;
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

    public void getCustPostalCodeCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public void setCustDivisionID(int custDivisionID) {
        this.custDivisionID = custDivisionID;
    }

    public void setCustDivisionName(String custDivisionName) {
        this.custDivisionName = custDivisionName;
    }

    public void setCustCountryID(int custCountryID) {
        this.custCountryID = custCountryID;
    }

    public void setCustCountryName(String custCountryName) {
        this.custCountryName = custDivisionName;
    }
}
