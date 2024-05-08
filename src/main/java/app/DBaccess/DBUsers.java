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
            String sql = "SELECT User_ID, User_Name FROM users";

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
            String sql = "SELECT User_Name, Password FROM Users WHERE User_Name = ? AND Password = ?";

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

        String sql = "SELECT User_ID, User_Name from users WHERE User_Name = ?";

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

        String sql = "SELECT User_ID, User_Name from users WHERE User_ID = ?";

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
    //SQL Query that validates Password is case-sensitive and correct.
}
