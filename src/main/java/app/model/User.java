package app.model;

/**
 * Users class provides user objects.
 * @author Elexis Rox
 */

public class User {
    private int userID;
    private String userName;
    private String userPass;

    //Overloaded Constructor for User
    public User(int userID, String userName, String userPass) {
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
    }

    //Constructor for displaying list of Users
    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    //Getters
    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    //Setters
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
