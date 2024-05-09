package app.helper;

import javafx.scene.control.Alert;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Utilities class provides CRUD queries and other utility functions.
 * @author Elexis Rox
 */

//TODO UPDATE: THIS IS NOT FOR CRUD!!
public abstract class Utilities {

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
    //FRUITS EXERCISE
    //CREATE
    public static int insert(String fruitName, int colorID) throws SQLException {
        String sql = "INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES(?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, colorID);
        return ps.executeUpdate();
    }

    public static int update(int fruitID, String fruitName) throws SQLException {
        String sql = "UPDATE FRUITS SET Fruit_Name = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, fruitID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int fruitID) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, fruitID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    //READ
    public static void select() throws SQLException {
        String sql = "SELECT * FROM FRUITS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int fruitID = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            System.out.print(fruitID + " | ");
            System.out.print(fruitName + "\n");
        }
    }

    public static void select(int colorID) throws SQLException {
        String sql = "SELECT * FROM FRUITS WHERE Color_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, colorID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int fruitID = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            int colorIDFK = rs.getInt("Color_ID");
            System.out.print(fruitID + " | ");
            System.out.print(fruitName + " | ");
            System.out.print(colorIDFK + "\n");
        }
    }

}
