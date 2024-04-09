package app.DBaccess;

import app.helper.JDBC;
import app.model.Country;
import java.sql.*;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBCountries class contains all queries to the countries table in the database.
 * @author Elexis Rox
 */
public class DBCountries {

    //SQL Query that gets all countries and adds them to an ObservableList
    public static ObservableList<Country> readAllCountries() {

        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryID, countryName);
                countryList.add(C);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Error (Countries):" + e.getErrorCode());
        } catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return countryList;
    }

    //SQL Query that returns a country's name based on the provided country ID
    public static Country readCountry(int countryID) {

        int providedCountryID = 0;
        String countryName = null;

        try {
            String sql = "SELECT Country_ID, Country FROM countries WHERE Country_ID = ?";
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

