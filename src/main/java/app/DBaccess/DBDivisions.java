package app.DBaccess;

import app.helper.JDBC;
import app.model.Appointment;
import app.model.Division;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * DBDivisions class contains all queries for the first level divisions table in the database.
 * @author Elexis Rox
 */

public class DBDivisions {
    //READ QUERIES
    //SQL Query that retrieves all first level divisions in the database and adds them to an ObservableList.
    public static ObservableList<Division> readAllDivisions() {
        ObservableList<Division> divList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT f.Division_ID, f.Division, f.Country_ID " +
                    "FROM first_level_divisions as f";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divID = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int divCountryID = rs.getInt("Country_ID");

                Division d = new Division(divID, divName, divCountryID);

                divList.add(d);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Divisions): " + e.getErrorCode());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return divList;
    }

    //SQL Query that returns a first level division's name from the database by its correlating division ID.
    public static Division returnDivisionName(int divID) {
        Division d = null;
        try {
            String sql = "SELECT f.Division_ID, f.Division " +
                    "FROM first_level_divisions as f" +
                    "WHERE Division_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, divID);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();
            int providedDivID = rs.getInt("Division_ID");
            String divName = rs.getString("Division");

            d = new Division(providedDivID, divName);
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Division ID Search): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return d;
    }

    //TODO: SQL Query that selects a divisionID/Name based on country for combo box dropdown
}
