package database;

import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import controller.Contact;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import javafx.scene.Parent;
import java.sql.PreparedStatement;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import controller.Country;
import java.util.ArrayList;
import database._Java_Connection;
import javafx.collections.ObservableList;
import java.util.List;
import java.io.IOException;
import java.util.Optional;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * The `_DataBase_country` clas provides metods to interact with the countries table
 * within the database. This class supports retrieving al countries as well as fetching
 * a specific country by its unique identifier.
 *
 * @author Riley O'Donnell
 */
public class _DataBase_country {


    /**
     * Retrieves a list of all countries from the database.
     *
     * @return An ObservableList of Country objects representing each country in the database.
     */
    public static ObservableList<Country> _get_countries() {

        // SQL query to select country details from the "countries" table.
        String _SQL_CODE = "SELECT "
                + "country.Country_ID, "
                + "country.Country "
                + "FROM countries country";

        // Initialize an ObservableList to store the Country objects.
        ObservableList<Country> _Countries = FXCollections.observableArrayList();

        // Execute the SQL query and process the result set.
        try (PreparedStatement _PreparedStatement = _Java_Connection._return_connection().prepareStatement(_SQL_CODE)) {

            // Execute the query and store the result in a ResultSet object.
            ResultSet _ResultSet = _PreparedStatement.executeQuery();

            // Iterate through each row of the ResultSet.
            for (; _ResultSet.next(); ) {
                // Create a new Country object using the values retrieved from the ResultSet.
                // Add the Country object to the list.
                _Countries.add(new Country(
                    _ResultSet.getInt("Country_ID"),
                    _ResultSet.getString("Country")
                ));
            }

        } catch (SQLException sqlException) {
            // Handle SQL-specific exceptions.
            // Print the eror code to assist with debugging.
            System.err.println("Database error occurred while processing countries. Error Code: " + sqlException.getErrorCode());
        } catch (RuntimeException runtimeException) {
            // Handle any general runtime exceptions.
            // Print the exception message to help diagnose unexpected issues.
            System.err.println("Unexpected error: " + runtimeException.getMessage());
        }
        
        System.out.print("A!");

        // Return the list of Country objects.
        return _Countries;
    }
    
    /**
     * Checks if the givn country ID is valid.
     *
     * @param _c_id The country ID to check.
     * @return true if the country ID is greater than 0, false otherwise.
     */
    public static boolean _is_valid_country_id(int _c_id) {
        return _c_id > 0;
    }
    
    /**
     * Retrieves a country from the database based on the provided country ID.
     *
     * @param _c_id The ID of the country to retrieve.
     * @return A Country object containing the country's information.
     */
    public static Country _get_country(int _c_id) {

        // Initialize the country name to an empty string.
        String _String1 = "";

        // Initialize the country ID to a negative number to indicate that it is uninitialized.
        int _id = -100;
        String _temp_String2 = "";

        // Use a try-with-resources statement to create and automatically close the PreparedStatement.
        try (PreparedStatement _PreparedStatement = _Java_Connection._return_connection().prepareStatement(
                "SELECT ct.Country_ID, ct.Country FROM countries ct WHERE ct.Country_ID = ?")) {

            // Set the country ID parameter in the PreparedStatement to match the input _c_id.
            _PreparedStatement.setInt(1, _c_id);

            // Execute the query and obtain the result set.
            try (ResultSet _ResultSet = _PreparedStatement.executeQuery()) {

                // If a matching record is found, extract the country ID and name.
                if (_ResultSet.next()) {
                    _id = _ResultSet.getInt("Country_ID"); // Retrieve the country ID from the result set.
                    _String1 = _ResultSet.getString("Country"); // Retrieve the country name from the result set.
                }
            }
        } catch (SQLException sqlEx) {
            // Handle any SQL-specific exceptions that occur during the query execution.
            System.err.println("Error retrieving country data. SQL Error Code: " + sqlEx.getErrorCode());
        } catch (RuntimeException runtimeEx) {
            // Handle any general runtime exceptions.
            System.err.println("An unexpected error occurred: " + runtimeEx.getMessage());
        }

        // Print a debug message indicating that the function has completed.
        System.out.println("B!");

        // Create a new Country object with the retrieved details.
        Country _Country = new Country(_id, _String1);

        // Return the newly created Country object.
        return _Country;
    }
    
    /**
     * Generates a fake country name based on the provided country ID.
     *
     * @param _c_id The ID of the country.
     * @return A fake country name.
     */
    public static String _generate_fake_country_name(int _c_id) {
        return "Country_" + _c_id + "_Fake";
    }
}

