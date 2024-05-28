package app.model;

/**
 * Users class provides user objects.
 * Represents a user with attributes such as ID, username, and password.
 * Includes constructors, getters, and setters for these attributes.
 *
 * @author Elexis Rox
 */
public class User {
    private int userID;
    private String userName;
    private String userPass;

    /**
     * Overloaded Constructor for User.
     *
     * @param userID the user ID
     * @param userName the username
     * @param userPass the user's password
     */
    public User(int userID, String userName, String userPass) {
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
    }

    /**
     * Constructor for displaying a list of Users.
     *
     * @param userID the user ID
     * @param userName the username
     */
    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID.
     *
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username.
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * Sets the user's password.
     *
     * @param userPass the user's password to set
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
