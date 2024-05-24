package app.DBaccess;

import app.helper.JDBC;
import app.model.Country;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBCountries class contains all queries for the countries table in the database.
 * @author Elexis Rox
 */
public class DBCountries {

    //READ QUERIES
    //SQL Query that retrieves all countries and adds them to an ObservableList.
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

    //SQL Query that returns a country's name based on the provided country ID.
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

    //TODO May need more queries for reports
}

