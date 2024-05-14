package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
import app.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    //SQL Query that checks the user's login username and password to ensure they are correct.
    public static boolean userLoginSuccess(String userName, String userPass) {
        try {
            String sql = "SELECT u.User_Name, u.Password " +
                    "FROM Users as u " +
                    "WHERE User_Name = ? AND Password = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, userName);
            ps.setString(2, userPass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (User Login): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    //SQL Query that retrieves userID by associated userName.
    public static int readUserID(String userName) throws SQLException {
        int userID = 0;

        String sql = "SELECT u.User_ID, u.User_Name " +
                "FROM users as u " +
                "WHERE User_Name = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, userName);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            userID = rs.getInt("User_ID");
            userName = rs.getString("User_Name");
        }
        return userID;
    }

    //SQL Query that retrieves userName by associated userID.
    public static String readUserName(int userID) throws SQLException {
        String userName = null;

        String sql = "SELECT u.User_ID, u.User_Name " +
                "from users as u " +
                "WHERE User_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, userID);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            userName = rs.getString("User_Name");
        }
        return userName;
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
                System.out.println("Username found in database.");
                matchFound = true;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Username): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (!matchFound) {
            System.out.println("Username not found in database.");
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
                System.out.println("Username and password validation success!");
                loginValidated = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Login Database Match): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (!loginValidated) {
            System.out.println("Username and password validation failure.");
        }

        return loginValidated;
    }
}
