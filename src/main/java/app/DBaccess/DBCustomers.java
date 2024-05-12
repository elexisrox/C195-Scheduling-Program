package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
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
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country " +
                    "FROM customers as c " +
                    "JOIN first_level_divisions as f " +
                    "ON c.Division_ID = f.Division_ID " +
                    "JOIN countries " +
                    "ON c.Country_ID = f.Country_ID " +
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
