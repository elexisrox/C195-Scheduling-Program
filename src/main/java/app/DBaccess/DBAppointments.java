package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.*;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.sql.Timestamp;

/**
 * DBAppointments class contains all queries for the appointments table in the database. It also handles all time conversions for appointments.
 * @author Elexis Rox
 */

public class DBAppointments {
    //Establishes a reusable SQL query for appointment retrieval from the database.
    private static final String APPT_BASE_SQL =
                "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.User_ID, a.Contact_ID, a.Customer_ID " +
                "FROM appointments AS a " +
                "JOIN contacts AS c ON a.Contact_ID = c.Contact_ID ";

    // Convert LocalDateTime from user's timezone to UTC
    private static Timestamp toUTC(LocalDateTime localDateTime) {
        ZoneId userLocalZone = ZoneId.systemDefault();
        ZonedDateTime utcDateTime = localDateTime.atZone(userLocalZone).withZoneSameInstant(ZoneId.of("UTC"));
        Instant utcInstant = utcDateTime.toInstant();

        return Timestamp.from(utcInstant);
    }

    // Convert LocalDateTime from UTC to user's timezone
    private static LocalDateTime fromUTC(Timestamp utcTimestamp) {
        // Convert the Timestamp to an Instant
        Instant utcInstant = utcTimestamp.toInstant();

        // Convert the Instant to a ZonedDateTime in UTC
        ZonedDateTime utcZonedDateTime = utcInstant.atZone(ZoneId.of("UTC"));

        // Get the user's local time zone
        ZoneId userLocalZone = ZoneId.systemDefault();

        // Convert the ZonedDateTime to the user's local time zone
        ZonedDateTime userLocalZonedDateTime = utcZonedDateTime.withZoneSameInstant(userLocalZone);

        // Extract the LocalDateTime from the user's local ZonedDateTime
        LocalDateTime userLocalDateTime = userLocalZonedDateTime.toLocalDateTime();

        // Print the conversion details
        System.out.println("Converting from UTC: " + utcZonedDateTime.toLocalDateTime() + " -> " + userLocalDateTime);

        return userLocalDateTime;
    }

    //CREATE QUERIES
    //SQL Query that adds a new appointment in the database. Appointment ID is auto-incremented by the database.
    public static void addAppt(String apptTitle, String apptDesc, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptUserID, int apptContactID, int apptCustomerID) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, User_ID, Contact_ID, Customer_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            System.out.println("addAppt Database called");
            // Convert to UTC before storing
            Timestamp apptStartUTC = toUTC(apptStart);
            Timestamp apptEndUTC = toUTC(apptEnd);
            System.out.println(apptStartUTC);
            ps.setString(1, apptTitle);
            ps.setString(2, apptDesc);
            ps.setString(3, apptLocation);
            ps.setString(4, apptType);
            ps.setTimestamp(5, apptStartUTC);
            ps.setTimestamp(6, apptEndUTC);
            ps.setInt(7, apptUserID);
            ps.setInt(8, apptContactID);
            ps.setInt(9, apptCustomerID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Add Appointment): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //UPDATE QUERIES
    //SQL Query that updates a selected appointment within the database.
    public static void updateAppt(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptUserID, int apptContactID, int apptCustomerID) {
        try {
            String sql = "UPDATE appointments SET " +
                    "Title = ?, " +
                    "Description = ?, " +
                    "Location = ?, " +
                    "Type = ?, " +
                    "Start = ?, " +
                    "End = ?, " +
                    "User_ID = ?, " +
                    "Contact_ID = ?, " +
                    "Customer_ID = ? " +
                    "WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Convert to UTC before storing
            Timestamp apptStartUTC = toUTC(apptStart);
            Timestamp apptEndUTC = toUTC(apptEnd);

            ps.setString(1, apptTitle);
            ps.setString(2, apptDesc);
            ps.setString(3, apptLocation);
            ps.setString(4, apptType);
            ps.setTimestamp(5, apptStartUTC);
            ps.setTimestamp(6, apptEndUTC);
            ps.setInt(7, apptUserID);
            ps.setInt(8, apptContactID);
            ps.setInt(9, apptCustomerID);
            ps.setInt(10, apptID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Update Appointment): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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

    //READ QUERIES
    //SQL Query that retrieves an appointment from the database.
    private static Appointment retrieveAppt(ResultSet rs) throws SQLException {
        int apptID = rs.getInt("Appointment_ID");
        String apptTitle = rs.getString("Title");
        String apptDesc = rs.getString("Description");
        String apptLocation = rs.getString("Location");
        String apptType = rs.getString("Type");
        LocalDateTime apptStart = fromUTC(rs.getTimestamp("Start"));
        LocalDateTime apptEnd = fromUTC(rs.getTimestamp("End"));
        int apptUserID = rs.getInt("User_ID");
        int apptContactID = rs.getInt("Contact_ID");
        int apptCustomerID = rs.getInt("Customer_ID");

        return new Appointment(apptID, apptTitle, apptDesc, apptLocation, apptType, apptStart, apptEnd, apptUserID, apptContactID, apptCustomerID);
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
           System.out.println("SQL Exception Error (All Appointments): " + e.getErrorCode());
       } catch(Exception e) {
           System.out.println("Error: " + e.getMessage());
       }
       return apptList;
    }

    //SQL Query that retrieves all appointments beginning with the most recent Monday and spanning the next 7 days and adds them to an ObservableList.
    //TODO times?????
    public static ObservableList<Appointment> readWeekAppts() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        String sql = APPT_BASE_SQL +
                "WHERE a.Start BETWEEN ? AND ? " +
                "ORDER BY a.Appointment_ID";

        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sql)) {
            ps.setObject(1, startOfWeek);
            ps.setObject(2, endOfWeek);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    apptList.add(retrieveAppt(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Appointments by Week): " + e.getErrorCode());
        }
        return apptList;
    }

    //SQL Query that retrieves all appointments in the current calendar month and adds them to an ObservableList.
    //TODO fix
    public static ObservableList<Appointment> readMonthAppts() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        //Set the first day of the month at the start of the day
        LocalDateTime firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        System.out.println(firstDayOfMonth);

        //Set the last day of the month at the end of the day
        LocalDateTime lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);

        String sql = APPT_BASE_SQL +
                "WHERE a.Start BETWEEN ? AND ? " +
                "ORDER BY a.Appointment_ID";

        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sql)) {
            ps.setObject(1, firstDayOfMonth);
            ps.setObject(2, lastDayOfMonth);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    apptList.add(retrieveAppt(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Appointments by Month): " + e.getErrorCode());
        }
        return apptList;
    }

    //SQL Query that retrieves all appointments in the next 15 minutes.
    public static ObservableList<Appointment> readNext15MinAppts() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        LocalDateTime nowLocal = LocalDateTime.now();

        LocalDateTime nowPlus15Local = nowLocal.plusMinutes(15);
        Timestamp nowUTC = toUTC(nowLocal);
        Timestamp nowPlus15UTC = toUTC(nowPlus15Local);

        String sql = APPT_BASE_SQL + "WHERE a.Start BETWEEN ? AND ? ORDER BY a.Appointment_ID";

        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sql)) {
            ps.setTimestamp(1, nowUTC);
            ps.setTimestamp(2, nowPlus15UTC);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    apptList.add(retrieveAppt(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Appointments in next 15 minutes): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return apptList;
    }

    // SQL Query that retrieves all appointments by contact ID
    public static ObservableList<Appointment> readApptsByContactID(int contactID) {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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

                Appointment appointment = new Appointment(apptID, apptTitle, apptDesc, apptLocation, apptType,
                        apptStart, apptEnd, apptUserID, apptContactID, apptCustomerID);

                apptList.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Appointments by Contact): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return apptList;
    }

    // SQL Query that retrieves all appointments by customer ID.
    public static ObservableList<Appointment> readApptsByCustID(int customerID) {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try {
            String sql = APPT_BASE_SQL +
                    "WHERE a.Customer_ID = ? " +
                    "ORDER BY a.Appointment_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appt = retrieveAppt(rs);
                apptList.add(appt);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (All Appointments by Customer ID): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return apptList;
    }

    // SQL Query that retrieves all appointments by customer ID that overlap the inputted start and end times.
    public static ObservableList<Appointment> readOverlappingApptsByCustID(int customerID, LocalDateTime startDateTime, LocalDateTime endDateTime, ZoneId userLocalZone, int excludeApptID) {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        // Convert the provided start and end times to UTC
        Timestamp startUTC = toUTC(startDateTime);
        Timestamp endUTC = toUTC(endDateTime);

        try {
            String sql = APPT_BASE_SQL +
                    "WHERE a.Customer_ID = ? " +
                    "AND a.Appointment_ID <> ? " +  // Exclude the appointment with the same ID as the one being modified
                    "AND (" +
                    "(a.Start <= ? AND a.End >= ?) OR " +  // Overlap: newEnd is after or equal to existingStart, and newStart is before or equal to existingEnd.
                    "(a.Start < ? AND a.End > ?) OR " +  // Overlap: newStart is before existingStart and newEnd is after existingStart
                    "(a.Start >= ? AND a.Start < ?) OR " + // Overlap: newStart is equal to existingStart or within existingStart and existingEnd
                    "(a.End > ? AND a.End <= ?)" +      // Overlap: newEnd is equal to existingEnd or within existingStart and existingEnd
                    ") ORDER BY a.Start";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, customerID);
            ps.setInt(2, excludeApptID);
            ps.setTimestamp(3, endUTC);
            ps.setTimestamp(4, endUTC);
            ps.setTimestamp(5, startUTC);
            ps.setTimestamp(6, endUTC);
            ps.setTimestamp(7, startUTC);
            ps.setTimestamp(8, endUTC);
            ps.setTimestamp(9, startUTC);
            ps.setTimestamp(10, endUTC);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appt = retrieveAppt(rs);
                apptList.add(appt);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Overlapping Appointments by Customer ID): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return apptList;
    }

    // SQL Query to retrieves the next available appointment ID
    public static String readNextApptID() {
        int nextApptID = 0;
        try {
            String sql = "SELECT MAX(Appointment_ID) FROM appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nextApptID = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Get Next Appointment ID): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return String.valueOf(nextApptID);
    }

    // SQL Query that retrieves Appointments by Month and Type for Reports
    public static ObservableList<Pair<String, Pair<String, Integer>>> readApptsByMonthAndType() {
        ObservableList<Pair<String, Pair<String, Integer>>> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT MONTHNAME(Start) AS Month, Type, COUNT(*) AS Count " +
                    "FROM appointments " +
                    "GROUP BY Month, Type " +
                    "ORDER BY MONTH(Start), Type";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int count = rs.getInt("Count");

                list.add(new Pair<>(month, new Pair<>(type, count)));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Appointments by Month and Type): " + e.getErrorCode());
        }
        return list;
    }
}
