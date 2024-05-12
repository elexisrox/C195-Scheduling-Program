package app.helper;

import javafx.scene.control.Alert;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/** Utilities class provides CRUD queries and other utility functions.
 * @author Elexis Rox
 */

public class Utilities {

    //Used for LocalDateTime formatting methods
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    //Error Messages
    public static String getErrorMsg(int errorType) {
        return switch (errorType) {
            //Login Errors
            case 1 -> "Please provide a username and password.";
            case 2 -> "Please provide a username.";
            case 3 -> "Please provide a password.";
            case 4 -> "Username not found. Please try again.";
            case 5 -> "Username and password do not match. Please try again.";
            //Appointment Dialog Box Errors
            case 7 -> "Please enter a descriptive title to continue.";
            case 8 -> "Please enter a brief description of the appointment to continue.";
            case 9 -> "Please enter a location to continue.";
            case 10 -> "Please specify the type of appointment to continue.";
            case 11 -> "Please select a Contact ID to continue.";
            case 12 -> "Please select a Customer ID to continue.";
            case 13 -> "Please select a User ID to continue.";
            case 14 -> "End Date must occur after Start Date.";
            case 15 -> "Unable to save. Please check the warnings above and try again.";
            default -> null;
        };
    }

    // Converts LocalDateTime to a formatted date string in a specific timezone
    public static String formatDate(LocalDateTime utcDateTime, ZoneId targetZone) {
        if (utcDateTime == null) return "";
        ZonedDateTime zonedDateTime = utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(targetZone);
        return DATE_FORMATTER.format(zonedDateTime);
    }

    // Converts UTC LocalDateTime to a formatted time string in a specific timezone
    public static String formatTime(LocalDateTime utcDateTime, ZoneId targetZone) {
        if (utcDateTime == null) return "";
        ZonedDateTime zonedDateTime = utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(targetZone);
        return TIME_FORMATTER.format(zonedDateTime);
    }
}
