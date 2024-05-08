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
    public static void getErrorMsg(int errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (errorType) {

            case 1:
                alert.setTitle("Test");
                alert.setContentText("Test");
                alert.showAndWait();
                break;

        }
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
