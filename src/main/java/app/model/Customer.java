package app.model;

/**
 * Customers class provides customer objects.
 * Represents a customer with various attributes such as ID, name, address, etc.
 * Includes constructors, getters, and setters for these attributes.
 *
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

    /**
     * Overloaded constructor for Customer with additional fields for division and country names.
     *
     * @param custID the customer ID
     * @param custName the customer name
     * @param custAddress the customer address
     * @param custPostalCode the customer postal code
     * @param custPhone the customer phone number
     * @param custDivisionID the customer division ID
     * @param custDivisionName the customer division name
     * @param custCountryID the customer country ID
     * @param custCountryName the customer country name
     */
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

    /**
     * Additional constructor for Customer that matches database fields.
     *
     * @param custID the customer ID
     * @param custName the customer name
     * @param custAddress the customer address
     * @param custPostalCode the customer postal code
     * @param custPhone the customer phone number
     * @param custDivisionID the customer division ID
     */
    public Customer(int custID, String custName, String custAddress, String custPostalCode, String custPhone, int custDivisionID) {
        this.custID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPostalCode = custPostalCode;
        this.custPhone = custPhone;
        this.custDivisionID = custDivisionID;
    }

    /**
     * Gets the customer ID.
     *
     * @return the customer ID
     */
    public int getCustID() {
        return custID;
    }

    /**
     * Sets the customer ID.
     *
     * @param custID the customer ID to set
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * Gets the customer name.
     *
     * @return the customer name
     */
    public String getCustName() {
        return custName;
    }

    /**
     * Sets the customer name.
     *
     * @param custName the customer name to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * Gets the customer address.
     *
     * @return the customer address
     */
    public String getCustAddress() {
        return custAddress;
    }

    /**
     * Sets the customer address.
     *
     * @param custAddress the customer address to set
     */
    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    /**
     * Gets the customer postal code.
     *
     * @return the customer postal code
     */
    public String getCustPostalCode() {
        return custPostalCode;
    }

    /**
     * Sets the customer postal code.
     *
     * @param custPostalCode the customer postal code to set
     */
    public void setCustPostalCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    /**
     * Gets the customer phone number.
     *
     * @return the customer phone number
     */
    public String getCustPhone() {
        return custPhone;
    }

    /**
     * Sets the customer phone number.
     *
     * @param custPhone the customer phone number to set
     */
    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    /**
     * Gets the customer division ID.
     *
     * @return the customer division ID
     */
    public int getCustDivisionID() {
        return custDivisionID;
    }

    /**
     * Sets the customer division ID.
     *
     * @param custDivisionID the customer division ID to set
     */
    public void setCustDivisionID(int custDivisionID) {
        this.custDivisionID = custDivisionID;
    }

    /**
     * Gets the customer division name.
     *
     * @return the customer division name
     */
    public String getCustDivisionName() {
        return custDivisionName;
    }

    /**
     * Sets the customer division name.
     *
     * @param custDivisionName the customer division name to set
     */
    public void setCustDivisionName(String custDivisionName) {
        this.custDivisionName = custDivisionName;
    }

    /**
     * Gets the customer country ID.
     *
     * @return the customer country ID
     */
    public int getCustCountryID() {
        return custCountryID;
    }

    /**
     * Sets the customer country ID.
     *
     * @param custCountryID the customer country ID to set
     */
    public void setCustCountryID(int custCountryID) {
        this.custCountryID = custCountryID;
    }

    /**
     * Gets the customer country name.
     *
     * @return the customer country name
     */
    public String getCustCountryName() {
        return custCountryName;
    }

    /**
     * Sets the customer country name.
     *
     * @param custCountryName the customer country name to set
     */
    public void setCustCountryName(String custCountryName) {
        this.custCountryName = custCountryName;
    }
}
