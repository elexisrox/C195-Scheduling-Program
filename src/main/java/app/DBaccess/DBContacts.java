package app.DBaccess;

import app.helper.JDBC;
import app.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBContacts class contains all queries for the contacts table in the database.
 * @author Elexis Rox
 */

public class DBContacts {
    //READ QUERIES
    //SQL Query that retrieves all contacts and adds them to an ObservableList.
    public static ObservableList<Contact> readAllContacts() {

        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT c.Contact_ID, c.Contact_Name, c.Email " +
                        "FROM contacts as c";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact c = new Contact(contactID, contactName, contactEmail);

                contactList.add(c);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Contacts): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return contactList;
    }

    //SQL Query that returns a Contact based on the provided Contact ID.
    public static Contact readContact(int contactID) {

        int providedContactID = 0;
        String contactName = null;
        String contactEmail = null;

        try {
            String sql = "SELECT c.Contact_ID, c.Contact_Name, c.Email " +
                    "FROM contacts as c " +
                    "WHERE c.Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();

            rs.next();

            providedContactID = rs.getInt("Contact_ID");
            contactName = rs.getString("Contact_Name");
            contactEmail = rs.getString("Email");
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Contact):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return new Contact(providedContactID, contactName, contactEmail);
    }

    //SQL Query that returns a Contact ID based on the provided Contact Name.
    public static int readContactByName(String contactName) {

        int contactID = 0;

        try {
            String sql = "SELECT Contact_ID, Contact_Name FROM contacts WHERE Contact_Name = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contactID = rs.getInt("Contact_ID");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Contact ID):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return contactID;
    }
}
