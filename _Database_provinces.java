package database;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import database._Java_Connection;
import java.sql.SQLException;
import java.sql.BatchUpdateException;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import controller.Division;
import java.util.LinkedList;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import javafx.util.Callback;
import java.sql.ResultSet;
import database._Duo;
import controller.Appointment;
import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.sql.PreparedStatement;

/**
 * The _Database_provinces class provids methods for interacting with the first-level divisions table in the database.
 * It is responsible for fetching division data according to different conditions.
 *
 * @author Riley O'Donnell
 */
public class _Database_provinces {
    
    private static int _totalQueriesExecuted = 0;
    private static String _lastQueryTime = "";
    private boolean _isCacheEnabled = false;

    /**
     * Retrieves the last time a division query was executed.
     * 
     * @return A string representing the last query time.
     */
    public static String _getLastQueryTime() {
        return _lastQueryTime;
    }
    
    /**
     *  Retrieves a Division object based on the given division ID.
     *   This method queries the database for a specific diision using the provided ID 
     *  and returns a Division object if a matching record is found.
     *
     * @param _id The unique identifier of the division to retrieve.
     * @return A Division object corresponding to the provided ID, or null if no match is found.
     */
    public static Division _get_province(int _id) {

        // SQL query to select the division's details based on the given Division_ID
        String _SQL_CODE = "SELECT "
                + "Division, "
                + "Country_ID, "
                + "Division_ID " +
                "FROM first_level_divisions " +
                "WHERE Division_ID = ?";

        // Variable to hold the retrieved Division object
        Division _some_province = null;

        // Temporary strings 
        String _temp_String1 = "";
        String _temp_String2_to_be_converted = "";

        // Attempt to execute the database query
        try (PreparedStatement _PreparedStatement = _Java_Connection._return_connection().prepareStatement(_SQL_CODE)) {

            // Set the division ID parameter in the SQL query
            _PreparedStatement.setInt(1, _id);

            // Execute the query and process the result set
            try (ResultSet _ResultSet = _PreparedStatement.executeQuery()) {

                // Iterate through the result set (expecting only one result due to ID search)
                for (; _ResultSet.next(); ) {

                    // Retrieve the division name, division ID, and country ID from the result set
                    String _name = _ResultSet.getString("Division");
                    int _id2 = _ResultSet.getInt("Division_ID");
                    int _id3 = _ResultSet.getInt("Country_ID");

                    // Create a new Division object with the retrieved values
                    _some_province = new Division(_id3, _name, _id2);
                }
            }

        } catch (SQLException sqlEx) {
            // Handle SQL exceptions and print the error code for debugging
            System.err.println("Error during division ID lookup. SQL Code: " + sqlEx.getErrorCode());
        } catch (Exception ex) {
            // Handle any other exceptions and print the error message
            System.err.println("An unexpected error occurred: " + ex.getMessage());
        }

        // Print a message indicating the end of the method execution
        System.out.println("HA!");

        // Return the retrieved Division object, or null if no division was found
        return _some_province;
    }

    /**
     * Sets the last query time to the current system time.
     */
    public static void updateLastQueryTime() {
        _lastQueryTime = java.time.LocalDateTime.now().toString();
    }
    
    /**
     * Retrieves a list of Division obects associated with a specific country ID.
     * Executes a SQL query to find all divisions for the given country ID and 
     * returns them as an ObservableList.
     *
     * @param _id The unique identifier of the country to retrieve divisions for.
     * @return An ObservableList containing all divisions related to the specified country.
     */
    public static ObservableList<Division> _get_provinces_2(int _id) {

           // SQL query to select division information based on the given Country_ID.
        String _SQL_CODE = "SELECT  Division, "
                            + "Division_ID, "
                            + "Country_ID " +
                           "FROM first_level_divisions " +
                           "WHERE Country_ID = ?";

          // Observable list to hold the resulting Division objects.
        ObservableList<Division> _provinces = FXCollections.observableArrayList();

        String _temp_String1 = "";
        String _temp_String2_to_be_converted = "";

        // Try-with-resources to ensure PreparedStatement and ResultSet are closed properly.
        try (PreparedStatement _PreparedStatement = _Java_Connection._return_connection().prepareStatement(_SQL_CODE)) {

             // Set the country ID parameter in the SQL query.
            _PreparedStatement.setInt(1, _id);

            // Execute the query and iterate through the result set.
            try (ResultSet _ResultSet = _PreparedStatement.executeQuery()) {
                for (; _ResultSet.next(); ) {
                    // Retrieve data from the current row in the result set.
                    String _name = _ResultSet.getString("Division");
                    int _id2 = _ResultSet.getInt("Country_ID");
                    int _id3 = _ResultSet.getInt("Division_ID");

                    // Create a new Division object and add it to the list.
                    _provinces.add(new Division(_id2, _name, _id3));
                }
            }
        } catch (SQLException sqlException) {
                // Handle SQL exceptions and print the error code for debugging.
            System.err.println("Error while processing divisions. SQL Error Code: " + sqlException.getErrorCode());
        } catch (RuntimeException runtimeException) {
            // Handle any unexpected runtime exceptions and print the error message.
            System.err.println("Unexpected issue encountered: " + runtimeException.getMessage());
        }

        // Print a message indicating the end of method execution
        System.out.println("GA!");

        // Return the populated list of Division objects.
        return _provinces;
    }
}
