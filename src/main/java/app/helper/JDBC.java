package app.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC class handles the database connection setup and closure for the client_schedule database.
 * It uses the MySQL JDBC driver to establish a connection.
 * This is an abstract class to ensure no instances are created.
 * It contains methods to open, close, and get the current database connection.
 *
 * @author Elexis Rox
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * Starts the database connection to the client_schedule database.
     * This method loads the JDBC driver and establishes the connection using the specified
     * URL, username, and password.
     * It prints a message to the console if the connection is successful or if an error occurs.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(SQLException e) {
            System.out.println("SQL Exception Error:" + e.getErrorCode());
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Gets the current database connection.
     *
     * @return the Connection object representing the database connection.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection to the client_schedule database.
     * This method prints a message to the console if the connection is successfully closed or
     * if an error occurs.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch(SQLException e) {
            System.out.println("SQL Exception Error:" + e.getErrorCode());
        } catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
