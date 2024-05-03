package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * DBAppointments class contains all queries to the appointments table in the database.
 * @author Elexis Rox
 */

public class DBAppointments {
    //CREATE
    //SQL Query that adds a new appointment in the database. Appointment ID is auto-incremented by the database.
    //READ
    //SQL Query that retrieves all appointments and adds them to an ObservableList.
    public static ObservableList<Appointment> readAllAppointments() {
       ObservableList<Appointment> apptList = FXCollections.observableArrayList();

       try {
           String sql = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID ORDER BY appointments.Appointment_ID";

           PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

           ResultSet rs = ps.execute();

           while (rs.next()) {
               int apptID = rs.getInt("Appointment_ID");
               String apptTitle = rs.getString("Title");
               String apptDesc = rs.getString("Description");
               String apptLocation = rs.getString("Location");
               String apptType = rs.getString("Type");
               LocalDateTime apptStartDate = rs.getTimestamp("Start").toLocalDateTime();
               LocalDateTime apptEndDate = rs.getTimestamp("End").toLocalDateTime();

               Appointment A = new Appointment(apptID, apptTitle, apptDesc, apptLocation);
               apptList.add(A);
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
    //UPDATE
    //SQL Query that updates a selected appointment within the database.
    //DELETE
    //SQL Query that deletes a selected appointment within the database by the appointment ID.
    //REPORTS: Other queries used for reports
    //SQL Query that retrieves and counts all appointments by appointment type.
    //SQL Query that retrieves the number of appointments per month.
}
