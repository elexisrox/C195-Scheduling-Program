package app.DBaccess;

import app.helper.JDBC;
import app.model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBCustomers class contains all queries for the customers table in the database.
 * This class handles creating, updating, deleting, and reading customers from the database.
 *
 * @author Elexis Rox
 */
public class DBCustomers {

    /**
     * Adds a new customer to the database. Customer ID is auto-incremented by the database.
     *
     * @param custName the name of the customer
     * @param custAddress the address of the customer
     * @param custPostalCode the postal code of the customer
     * @param custPhone the phone number of the customer
     * @param custDivisionID the division ID of the customer
     */
    public static void addCustomer(String custName, String custAddress, String custPostalCode, String custPhone, int custDivisionID) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, custName);
            ps.setString(2, custAddress);
            ps.setString(3, custPostalCode);
            ps.setString(4, custPhone);
            ps.setInt(5, custDivisionID);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Add Customer): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Updates a selected customer in the database.
     *
     * @param custID the ID of the customer
     * @param custName the new name of the customer
     * @param custAddress the new address of the customer
     * @param custPostalCode the new postal code of the customer
     * @param custPhone the new phone number of the customer
     * @param custDivisionID the new division ID of the customer
     */
    public static void updateCustomer(int custID, String custName, String custAddress, String custPostalCode, String custPhone, int custDivisionID) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? " +
                    "WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, custName);
            ps.setString(2, custAddress);
            ps.setString(3, custPostalCode);
            ps.setString(4, custPhone);
            ps.setInt(5, custDivisionID);
            ps.setInt(6, custID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Update Customer):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Deletes a selected customer from the database.
     *
     * @param custID the ID of the customer to delete
     */
    public static void deleteCustomer(int custID) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, custID);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Delete Customer):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Retrieves a customer from the database based on the provided Customer ID.
     *
     * @param custID the ID of the customer to retrieve
     * @return the Customer object corresponding to the provided Customer ID
     */
    public static Customer readCustomer(int custID) {

        int providedCustID = 0;
        String custName = null;
        String custAddress = null;
        String custPostalCode = null;
        String custPhone = null;
        int custDivisionID = 0;
        String custDivisionName = null;
        int custCountryID = 0;
        String custCountryName = null;

        try {
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, f.Division, f.Country_ID, countries.Country " +
                    "FROM customers as c " +
                    "JOIN first_level_divisions as f " +
                    "ON c.Division_ID = f.Division_ID " +
                    "JOIN countries " +
                    "ON countries.Country_ID = f.Country_ID " +
                    "WHERE c.Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, custID);
            ResultSet rs = ps.executeQuery();

            rs.next();

            providedCustID = rs.getInt("Customer_ID");
            custName = rs.getString("Customer_Name");
            custAddress = rs.getString("Address");
            custPostalCode = rs.getString("Postal_Code");
            custPhone = rs.getString("Phone");
            custDivisionID = rs.getInt("Division_ID");
            custDivisionName = rs.getString("Division");
            custCountryID = rs.getInt("Country_ID");
            custCountryName = rs.getString("Country");
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Customer):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return new Customer(providedCustID, custName, custAddress, custPostalCode, custPhone, custDivisionID, custDivisionName, custCountryID, custCountryName);
    }

    /**
     * Retrieves all customers from the database and adds them to an ObservableList.
     *
     * @return an ObservableList containing all customers from the database
     */
    public static ObservableList<Customer> readAllCustomers() {
        ObservableList<Customer> custList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, f.Division, f.Country_ID, countries.Country " +
                    "FROM customers as c " +
                    "JOIN first_level_divisions as f " +
                    "ON c.Division_ID = f.Division_ID " +
                    "JOIN countries " +
                    "ON countries.Country_ID = f.Country_ID " +
                    "ORDER BY c.Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custID = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostalCode = rs.getString("Postal_Code");
                String custPhone = rs.getString("Phone");
                int custDivisionID = rs.getInt("Division_ID");
                String custDivisionName = rs.getString("Division");
                int custCountryID = rs.getInt("Country_ID");
                String custCountryName = rs.getString("Country");

                Customer c = new Customer(custID, custName, custAddress, custPostalCode, custPhone, custDivisionID, custDivisionName, custCountryID, custCountryName);

                custList.add(c);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Customers): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return custList;
    }

    /**
     * Retrieves a list of customers based on a Country ID from the database.
     *
     * @param countryID the ID of the country
     * @return an ObservableList containing customers from the specified country
     */
    public static ObservableList<Customer> readCustomersByCountryID(int countryID) {
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, f.Division, f.Country_ID, countries.Country " +
                    "FROM customers as c " +
                    "JOIN first_level_divisions as f ON c.Division_ID = f.Division_ID " +
                    "JOIN countries ON countries.Country_ID = f.Country_ID " +
                    "WHERE f.Country_ID = ? " +
                    "ORDER BY c.Customer_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custID = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostalCode = rs.getString("Postal_Code");
                String custPhone = rs.getString("Phone");
                int custDivisionID = rs.getInt("Division_ID");
                String custDivisionName = rs.getString("Division");
                int custCountryID = rs.getInt("Country_ID");
                String custCountryName = rs.getString("Country");

                Customer c = new Customer(custID, custName, custAddress, custPostalCode, custPhone, custDivisionID, custDivisionName, custCountryID, custCountryName);

                custList.add(c);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Customers by Country): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return custList;
    }
}
