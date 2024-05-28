package app.DBaccess;

import app.helper.JDBC;
import app.model.Division;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBDivisions class contains all queries for the first level divisions table in the database.
 * This class handles reading divisions from the database based on various criteria.
 *
 * @author Elexis Rox
 */
public class DBDivisions {

    /**
     * Retrieves a first level division from the database by its division ID.
     *
     * @param divID the ID of the division to retrieve
     * @return the Division object corresponding to the provided division ID
     */
    public static Division retrieveDiv(int divID) {
        Division newDiv = null;
        try {
            String sql = "SELECT Division_ID, Division, Country_ID " +
                    "FROM first_level_divisions " +
                    "WHERE Division_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int providedDivID = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                newDiv = new Division(providedDivID, divName, countryID);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Division ID Search): " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return newDiv;
    }

    /**
     * Retrieves a list of divisions based on the correlating country ID for a ChoiceBox dropdown.
     *
     * @param countryID the ID of the country to retrieve divisions for
     * @return an ObservableList containing divisions for the specified country
     */
    public static ObservableList<Division> returnDivsByCountry(int countryID) {
        ObservableList<Division> divList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT f.Division_ID, f.Division, f.Country_ID " +
                    "FROM first_level_divisions as f " +
                    "WHERE f.Country_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return divList;
    }
}
