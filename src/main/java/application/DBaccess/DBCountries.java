package application.DBaccess;

import application.helper.*;
import application.model.Countries;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DBCountries class contains all queries to the countries table in the database.
 */
public class DBCountries {

    public static ObservableList<Countries> getAllCountries() {

        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryID, countryName);
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
}

