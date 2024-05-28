package app.DBaccess;

import app.helper.JDBC;
import app.model.Country;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBCountries class contains all queries for the countries table in the database.
 * This class handles reading all countries from the database and retrieving a specific country
 * by ID.
 *
 * @author Elexis Rox
 */
public class DBCountries {

    /**
     * Retrieves all countries from the database and adds them to an ObservableList.
     *
     * @return an ObservableList containing all countries from the database
     */
    public static ObservableList<Country> readAllCountries() {

        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT cu.Country_ID, cu.Country " +
                        "FROM countries as cu";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryID, countryName);

                countryList.add(c);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Countries):" + e.getErrorCode());
        } catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return countryList;
    }

    /**
     * Retrieves a specific country from the database based on the provided Country ID.
     *
     * @param countryID the ID of the country to retrieve
     * @return the Country object corresponding to the provided Country ID
     */
    public static Country retrieveCountry(int countryID) {

        int providedCountryID = 0;
        String countryName = null;

        try {
            String sql = "SELECT c.Country_ID, c.Country " +
                        "FROM countries as c " +
                        "WHERE Country_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();

            rs.next();

            providedCountryID = rs.getInt("Country_ID");
            countryName = rs.getString("Country");
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Country):" + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return new Country(providedCountryID, countryName);
    }
}

