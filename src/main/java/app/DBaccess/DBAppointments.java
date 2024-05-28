package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.sql.*;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * DBAppointments class contains all queries for the appointments table in the database.
 * It also handles all time conversions for appointments.
 *
 * @author Elexis Rox
 */

public class DBAppointments {

    /** Base SQL query for retrieving appointments */
    private static final String APPT_BASE_SQL =
                "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.User_ID, a.Contact_ID, a.Customer_ID " +
                "FROM appointments AS a " +
                "JOIN contacts AS c ON a.Contact_ID = c.Contact_ID ";

    /**
     * Converts LocalDateTime from user's timezone to UTC.
     *
     * @param localDateTime the local date and time to convert
     * @return the UTC timestamp
     */
    private static Timestamp toUTC(LocalDateTime localDateTime) {
        ZoneId userLocalZone = ZoneId.systemDefault();
        ZonedDateTime utcDateTime = localDateTime.atZone(userLocalZone).withZoneSameInstant(ZoneId.of("UTC"));
        Instant utcInstant = utcDateTime.toInstant();

        return Timestamp.from(utcInstant);
    }

    /**
     * Converts LocalDateTime from UTC to user's timezone.
     *
     * @param utcTimestamp the UTC timestamp to convert
     * @return the local date and time
     */
    private static LocalDateTime fromUTC(Timestamp utcTimestamp) {
        // Convert the Timestamp to an Instant
        Instant utcInstant = utcTimestamp.toInstant();

        // Convert the Instant to a ZonedDateTime in UTC
        ZonedDateTime utcZonedDateTime = utcInstant.atZone(ZoneId.of("UTC"));

        // Get the user's local time zone
        ZoneId userLocalZone = ZoneId.systemDefault();

        // Convert the ZonedDateTime to the user's local time zone
        ZonedDateTime userLocalZonedDateTime = utcZonedDateTime.withZoneSameInstant(userLocalZone);

        return userLocalZonedDateTime.toLocalDateTime();
    }

    /**
     * Adds a new appointment to the database.
     *
     * @param apptTitle the title of the appointment
     * @param apptDesc the description of the appointment
     * @param apptLocation the location of the appointment
     * @param apptType the type of the appointment
     * @param apptStart the start date and time of the appointment
     * @param apptEnd the end date and time of the appointment
     * @param apptUserID the user ID associated with the appointment
     * @param apptContactID the contact ID associated with the appointment
     * @param apptCustomerID the customer ID associated with the appointment
     */
    public static void addAppt(String apptTitle, String apptDesc, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptUserID, int apptContactID, int apptCustomerID) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, User_ID, Contact_ID, Customer_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

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

    /**
     * Updates a selected appointment within the database.
     *
     * @param apptID the ID of the appointment to update
     * @param apptTitle the new title of the appointment
     * @param apptDesc the new description of the appointment
     * @param apptLocation the new location of the appointment
     * @param apptType the new type of the appointment
     * @param apptStart the new start date and time of the appointment
     * @param apptEnd the new end date and time of the appointment
     * @param apptUserID the new user ID associated with the appointment
     * @param apptContactID the new contact ID associated with the appointment
     * @param apptCustomerID the new customer ID associated with the appointment
     */
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

    /**
     * Deletes a selected appointment within the database by the appointment ID.
     *
     * @param apptID the ID of the appointment to delete
     */
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

    /**
     * Retrieves an appointment from the database.
     *
     * @param rs the ResultSet containing the appointment data
     * @return the appointment object
     * @throws SQLException if there is an issue with the SQL query
     */
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

    /**
     * Retrieves all appointments in the database and adds them to an ObservableList.
     *
     * @return the list of appointments
     */
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

    /**
     * Retrieves all appointments beginning with the most recent Monday and spanning the next
     * 7 days and adds them to an ObservableList.
     *
     * @return the list of appointments
     */
    public static ObservableList<Appointment> readWeekAppts() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        LocalDateTime startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        Timestamp startOfWeekUTC = toUTC(startOfWeek);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6);
        Timestamp endOfWeekUTC = toUTC(endOfWeek);

        String sql = APPT_BASE_SQL +
                "WHERE a.Start BETWEEN ? AND ? " +
                "ORDER BY a.Appointment_ID";

        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sql)) {
            ps.setObject(1, startOfWeekUTC);
            ps.setObject(2, endOfWeekUTC);

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

    /**
     * Retrieves all appointments in the current calendar month and adds them to an
     * ObservableList.
     *
     * @return the list of appointments
     */
    public static ObservableList<Appointment> readMonthAppts() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        // Set the first day of the month at the start of the day
        LocalDateTime firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        Timestamp firstDayOfMonthUTC = toUTC(firstDayOfMonth);

        // Set the last day of the month at the end of the day
        LocalDateTime lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
        Timestamp lastDayOfMonthUTC = toUTC(lastDayOfMonth);

        String sql = APPT_BASE_SQL +
                "WHERE a.Start BETWEEN ? AND ? " +
                "ORDER BY a.Appointment_ID";

        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sql)) {
            ps.setObject(1, firstDayOfMonthUTC);
            ps.setObject(2, lastDayOfMonthUTC);
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

    /**
     * Retrieves all appointments in the next 15 minutes and adds them to an ObservableList.
     *
     * @return the list of appointments
     */
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

    /**
     * Retrieves all appointments by contact ID and adds them to an ObservableList.
     *
     * @param contactID the contact ID to filter by
     * @return the list of appointments
     */
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

    /**
     * Retrieves all appointments by customer ID and adds them to an ObservableList.
     *
     * @param customerID the customer ID to filter by
     * @return the list of appointments
     */
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

    /**
     * Retrieves all appointments by customer ID that overlap the inputted start and end
     * times.
     *
     * @param customerID the customer ID to filter by
     * @param startDateTime the start date and time to check for overlaps
     * @param endDateTime the end date and time to check for overlaps
     * @param excludeApptID the appointment ID to exclude from the check
     * @return the list of overlapping appointments
     */
    public static ObservableList<Appointment> readOverlappingApptsByCustID(int customerID, LocalDateTime startDateTime, LocalDateTime endDateTime, int excludeApptID) {
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

    /**
     * Retrieves appointments by month and type for reports and adds them to an ObservableList.
     *
     * @return the list of appointments grouped by month and type
     */
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
