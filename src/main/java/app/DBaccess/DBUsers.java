package app.DBaccess;

import app.helper.JDBC;
import app.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBUsers class contains all queries for the users table in the database.
 * This class provides methods to read user data, validate usernames and passwords.
 *
 * @author Elexis Rox
 */
public class DBUsers {

    /**
     * Retrieves all users from the database and adds them to an ObservableList.
     *
     * @return an ObservableList containing all users in the database
     */
    public static ObservableList<User> readAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT u.User_ID, u.User_Name " +
                    "FROM users as u " +
                    "ORDER BY u.User_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");

                User u = new User(userID, userName);

                userList.add(u);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Users List): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return userList;
    }

    /**
     * Retrieves a user by their associated userID.
     *
     * @param userID the ID of the user to retrieve
     * @return a User object corresponding to the provided userID
     */
    public static User readUser(int userID) {

        int providedUserID = 0;
        String userName = null;
        String userPass = null;

        try {
            String sql = "SELECT u.User_ID, u.User_Name, u.Password " +
                    "FROM users as u " +
                    "WHERE u.User_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            rs.next();

            providedUserID = rs.getInt("User_ID");
            userName = rs.getString("User_Name");
            userPass = rs.getString("Password");
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (User):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return new User(providedUserID, userName, userPass);
    }

    /**
     * Validates that a username exists in the database.
     * This validation is case-sensitive.
     *
     * @param userName the username to validate
     * @return true if the username exists, otherwise false
     */
    public static boolean userNameValidate(String userName) {
        boolean matchFound = false;
        try {
            String sql = "SELECT * FROM users " +
                    "WHERE BINARY User_Name = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                matchFound = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Username): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return matchFound;
    }

    /**
     * Validates that a username and password match in the database.
     * This validation is case-sensitive.
     *
     * @param userName the username to validate
     * @param userPassword the password to validate
     * @return true if the username and password match, otherwise false
     */
    public static boolean userLoginValidate(String userName, String userPassword) {
        boolean loginValidated = false;
        try {
            String sql = "SELECT * FROM users " +
                    "WHERE BINARY User_Name = ? " +
                    "AND BINARY Password = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, userName);
            ps.setString(2, userPassword);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                loginValidated = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Login Database Match): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return loginValidated;
    }
}
