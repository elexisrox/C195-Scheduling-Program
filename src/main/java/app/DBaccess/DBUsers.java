package app.DBaccess;

import app.helper.JDBC;
import app.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBUsers class contains all queries for the users table in the database.
 * @author Elexis Rox
 */

public class DBUsers {
    //READ QUERIES
    //SQL Query that retrieves all users in the database and adds them to an ObservableList.
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

    //SQL Query that retrieves a user by associated userID.
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

    //VALIDATION QUERIES
    //SQL Query that validates Username is case-sensitive and correct.
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

    //SQL Query that validates Password matches the associated Username and is case-sensitive and correct.
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
