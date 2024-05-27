package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
import app.model.Contact;
import app.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * DBCustomers class contains all queries for the customers table in the database.
 * @author Elexis Rox
 */

public class DBCustomers {
    //CREATE QUERIES
    //SQL Query that adds a new customer in the database. Customer ID is auto-incremented by the database.
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

    //READ QUERIES
    //SQL Query that retrieves all customers in the database and adds them to an ObservableList.
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

    //SQL Query that returns a Customer based on the provided Customer ID.
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

    //SQL Query to retrieves the next available customer ID
    public static String readNextCustID() {
        int nextCustID = 0;
        try {
            String sql = "SELECT MAX(Customer_ID) FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nextCustID = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Get Next Customer ID): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return String.valueOf(nextCustID);
    }

    //SQL Query that returns a list of customers based on a Country ID.
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

    //UPDATE QUERIES
    //SQL Query that updates a selected customer within the database.
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

    //DELETE QUERIES
    //SQL Query that deletes a selected customer within the database
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
}
