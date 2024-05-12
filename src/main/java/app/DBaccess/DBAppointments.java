package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * DBAppointments class contains all queries for the appointments table in the database.
 * @author Elexis Rox
 */

public class DBAppointments {
    //Establishes a reusable SQL query for appointment retrieval from the database.
    private static final String APPT_BASE_SQL =
                "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.User_ID, a.Contact_ID, a.Customer_ID " +
                "FROM appointments AS a " +
                "JOIN contacts AS c ON a.Contact_ID = c.Contact_ID ";

    //CREATE QUERIES
    //SQL Query that adds a new appointment in the database. Appointment ID is auto-incremented by the database.
    public static void addAppt(String apptTitle, String apptDesc, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptUserID, int apptContactID, int apptCustomerID) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, User_ID, Contact_ID, Customer_ID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, apptTitle);
            ps.setString(2, apptDesc);
            ps.setString(3, apptLocation);
            ps.setString(4, apptType);
            ps.setTimestamp(5, Timestamp.valueOf(apptStart));
            ps.setTimestamp(6, Timestamp.valueOf(apptEnd));
            ps.setInt(7, apptUserID);
            ps.setInt(8, apptContactID);
            ps.setInt(9, apptCustomerID);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Add Appointment): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //READ QUERIES
    //SQL Query that retrieves an appointment from the database.
    private static Appointment retrieveAppt(ResultSet rs) throws SQLException {
        int apptID = rs.getInt("Appointment_ID");
        String apptTitle = rs.getString("Title");
        String apptDesc = rs.getString("Description");
        String apptLocation = rs.getString("Location");
        String apptType = rs.getString("Type");
        LocalDateTime apptStart = rs.getTimestamp("Start").toLocalDateTime();
        LocalDateTime apptEnd = rs.getTimestamp("End").toLocalDateTime();
        int apptUserID = rs.getInt("User_ID");
        int apptContactID = rs.getInt("Contact_ID");
        int apptCustomerID = rs.getInt("Customer_ID");

        return new Appointment(apptID, apptTitle, apptDesc, apptLocation, apptType,
                apptStart, apptEnd, apptUserID, apptContactID, apptCustomerID);
    }

    //SQL Query that retrieves all appointments in the database and adds them to an ObservableList.
    public static ObservableList<Appointment> readAllAppts() {
       ObservableList<Appointment> apptList = FXCollections.observableArrayList();

       try {
           String sql = APPT_BASE_SQL +
                        "ORDER BY a.Appointment_ID";

           PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

           ResultSet rs = ps.executeQuery();

           while (rs.next()) {
               apptList.add(retrieveAppt(rs));
           }
       } catch (SQLException e) {
           System.out.println("SQL Exception Error (Appointments): " + e.getErrorCode());
       } catch(Exception e) {
           System.out.println("Error: " + e.getMessage());
       }
       return apptList;
    }
    //SQL Query that retrieves all appointments in the next 7 days and adds them to an ObservableList.
    //SQL Query that retrieves all appointments within a specific month.
    //SQL Query that retrieves all appointments associated with the user in order to display alerts for appointments within 15 minutes.
    //SQL Query that retrieves all appointments by contact ID.

    //UPDATE QUERIES
    //SQL Query that updates a selected appointment within the database.
    public static void updateAppt(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptUserID, int apptContactID, int apptCustomerID) {
        try {
            String sql = APPT_BASE_SQL;

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, apptTitle);
            ps.setString(2, apptDesc);
            ps.setString(3, apptLocation);
            ps.setString(4, apptType);
            ps.setTimestamp(5, Timestamp.valueOf(apptStart));
            ps.setTimestamp(6, Timestamp.valueOf(apptEnd));
            ps.setInt(7, apptUserID);
            ps.setInt(8, apptContactID);
            ps.setInt(9, apptCustomerID);
            ps.setInt(10, apptID);

            ps.execute();

        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Update Appointment):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    //DELETE QUERIES
    //SQL Query that deletes a selected appointment within the database by the appointment ID.
    public static void deleteAppt(int apptID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, apptID);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Delete Appointment):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    //REPORTS QUERIES: Other queries used for reports
    //SQL Query that retrieves and counts all appointments by appointment type.
    //SQL Query that retrieves the number of appointments per month.
}
