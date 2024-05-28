package app.DBaccess;

import app.helper.JDBC;
import app.model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBContacts class contains all queries for the contacts table in the database.
 * This class handles reading all contacts from the database and retrieving a specific contact
 * by ID.
 *
 * @author Elexis Rox
 */
public class DBContacts {

    /**
     * Retrieves all contacts from the database and adds them to an ObservableList.
     *
     * @return an ObservableList containing all contacts from the database
     */
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

    /**
     * Retrieves a specific contact from the database based on the provided Contact ID.
     *
     * @param contactID the ID of the contact to retrieve
     * @return the Contact object corresponding to the provided contact ID
     */
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
}
