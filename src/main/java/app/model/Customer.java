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
