package database;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import database._Java_Connection;
import java.sql.SQLException;
import java.sql.BatchUpdateException;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import controller.User;

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
 * The UserDatabase clas manages interactions with the users table in the database (profiles).
 * It offers methods to retrieve profile information and verify logn credentials.
 *
 * @author Riley O'Donnell
 */
public class _Database_profiles {
    
    /**
     * Concatenates the two given strings, converts the result to uppercase, 
     * and trims any leading or trailing white space.
     *
     * @param _intpu1 The first input string.
     * @param _input2 The second input string to be converted.
     * @return A formatted and uppercase concatenation of the two strings.
     */
    public static String _format_string(String _intpu1, String _input2) {

        // Concatenate the strings, convert to uppercase, and trim whitespace.
        String result = (_intpu1 + _input2).toUpperCase().trim();

        return result;
    }

    /**
     * Retrieves the role of a user (e.g., admin, regular user).
     *
     * @param _username the username of the user whose role is being requested.
     * @return the role of the user as a String.
     */
    public static String _get_user_role(String _username) {
        // Placeholder logic for getting user role
        if (_username.equals("admin")) {
            return "Administrator";
        } else {
            return "User";
        }
    }



    /**
     * Retrieves a User object based on the provided user ID. 
     * The method executes a SQL query to fetch the user's creation date, ID, password, and name.
     *
     * @param _id the user ID used to look up the user.
     * @return a User object containing the user's ID, name, and password. Returns an empty User object if no matching user is found.
     */
    public static User _get_user_using_id(int _id) {

        // Variables to store user data retrieved from the database.
        String _create_date = "";
        String _password_global = "";

        // SQL query to select user details by User_ID.
        String _SQL_CODE = "SELECT Create_Date, User_ID, Password, User_Name " +
                           "FROM users " +
                           "WHERE User_ID = ?";

        int _given_id_global = -100;  // Placeholder for user ID.
        String _user_global = "";     // Placeholder for user name.

        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Set the user ID in the prepared statement.
            _PreparedStatement.setInt(1, _id);

            // Execute the query and process the result set.
            try (ResultSet _ResultSet = _PreparedStatement.executeQuery()) {

                // If a user record is found, extract the user details.
                if (_ResultSet.next()) {
                    _user_global = _ResultSet.getString("User_Name");
                    _create_date = _ResultSet.getString("Create_Date");

                    // Output the user's creation date to the console.
                    System.out.println("The creation date of the user is " + _create_date);

                    _given_id_global = _ResultSet.getInt("User_ID");
                    _password_global = _ResultSet.getString("Password");
                } else {
                    // If no user is found, print a message.
                    System.out.println("No user found with the given criteria.");
                }
            }
        } catch (SQLException e) {
            // Log SQL exceptions with error code and message.
            System.err.println("Error occurred while processing user data. SQL Error Code: " + e.getErrorCode() + " - " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Log any other exceptions that may occur.
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        // Debugging output to indicate that the method execution reached this point.
        System.out.println("BLAH BLAH!");

        // Create and return a User object with the retrieved details.
        User _temp_user = new User(_password_global, _given_id_global, _user_global);

        return _temp_user;
    }
    
    /**
     * Changes the password for the specified user.
     *
     * @param _username the username of the user.
     * @param _new_password the new password to set for the user.
     * @return true if the password was successfully changed, false otherwise.
     */
    public static boolean _change_password(String _username, String _new_password) {
        // Placeholder logic for changing password
        System.out.println("Password changed successfully for " + _username);
        return true;
    }



    /**
     * Checks if a given username exists in the 'users' table.
     * The method performs a case-sensitive check on the username field and returns true if a match is found.
     *
     * @param _username the username to check for existence in the database.
     * @return true if the username exists, false otherwise.
     */
    public static boolean _check_if_username_exists(String _username) {

        // SQL query to select all columns from the 'users' table where the username matches the given value, using case-sensitive comparison.
        String _SQL_CODE = "SELECT * FROM "  // Select all columns from the table.
                   + "users "          // Specify the table to retrieve data from, in this case, 'users'.
                   + "WHERE "          // Begin the condition for filtering rows.
                   + "BINARY "         // Enforce case-sensitive matching in the comparison.
                   + "User_Name = ?";  // Filter by the 'User_Name' field, using a parameterized value.

        // Variable to track if the username is found.
        String _currently_matched = "FALSE";

        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Set the username parameter in the prepared statement.
            _PreparedStatement.setString(1, _username);

            // Execute the query and process the result set.
            try (ResultSet _ResultSet = _PreparedStatement.executeQuery()) {
                for (; _ResultSet.next(); ) {
                    _currently_matched = "TRUE";  // If a result is found, mark the username as matched.
                }
            }

        } catch (SQLException e) {
            // Log SQL exceptions with error code and message.
            System.err.println("SQL Exception occurred while processing username. Error Code: " + e.getErrorCode() + ", Message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Log any other exceptions that may occur.
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace(); 
        }
        
        System.out.println("FOO!");

        // Return true if the username was found, otherwise return false.
        return _currently_matched.equals("TRUE");
    }
    
    /**
     * Checks if the given user is an admin.
     *
     * @param _username the username to check.
     * @return true if the user is an admin, false otherwise.
     */
    public static boolean _is_admin(String _username) {
        // Placeholder logic for checking admin status
        return _username.equals("admin");
    }

    
    
    /**
     * Registers a new user by checking if the username already exists and, if not, inserting the username and password into the database.
     * The username is case-sensitive, and the method ensures that duplicate usernames are not allowed.
     *
     * @param _username the username input by the user.
     * @param _password the password input by the user.
     * @return true if the user was successfully registered, false if the username already exists or an error occurs.
     */
    public static boolean _register(String _username, String _password) {

        // Query to check if the username already exists
        String _check_user_query = "SELECT "
                                    + "u.User_Name " +
                                    "FROM users AS u " +
                                    "WHERE BINARY "
                                    + "u.User_Name = ?";

        // Query to insert a new user if the username does not exist
        String _insert_user_query = "INSERT INTO "
                + "users ("
                + "User_Name, "
                + "Password) "
                + "VALUES "
                + "(?, ?)";

        try (PreparedStatement _check_statement = _Java_Connection._connection.prepareStatement(_check_user_query)) {

            _check_statement.setString(1, _username);

            // Check if the username exists
            try (ResultSet _result_set = _check_statement.executeQuery()) {
                if (_result_set.next()) {
                    System.err.println("Username already exists.");
                    return false;
                }
            }

            // Insert new user
            try (PreparedStatement _insert_statement = _Java_Connection._connection.prepareStatement(_insert_user_query)) {
                _insert_statement.setString(1, _username);
                _insert_statement.setString(2, _password);

                int _rows_affected = _insert_statement.executeUpdate();
                if (_rows_affected > 0) {
                    System.out.println("User registered successfully.");
                    return true;
                }
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("SQL Exception occurred during user registration. Error Code: " + e.getErrorCode() + ", Message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retrieves a list of user profiles from the database. 
     * The profiles contain user ID and user name information, 
     * and the list is ordered by User_ID in ascending order.
     *
     * @return an ObservableList of User objects, each representing a user profile with an ID and name.
     */
    public static ObservableList<User> _get_profiles() {

        // SQL query to select User_ID and User_Name from the users table, ordered by User_ID.
        String _SQL_CODE = "SELECT User_ID, User_Name " +
                           "FROM users " +
                           "ORDER BY User_ID ASC";

        // Create an empty ObservableList to store the user profiles.
        ObservableList<User> _profiles = FXCollections.observableArrayList();

        String _temp_String1 = "";
        String _temp_String2_to_be_converted = "";


        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Execute the query and retrieve the result set.
            try (ResultSet resultSet = _PreparedStatement.executeQuery()) {
                // Iterate through the result set and populate the ObservableList with user profiles.
                for (; resultSet.next(); ) {
                    String _name = resultSet.getString("User_Name"); // Retrieve the user name
                    int _id2 = resultSet.getInt("User_ID");          // Retrieve the user ID
                    
                    String temp_string3 = _format_string(_temp_String1, _temp_String2_to_be_converted);

                    // Add the new User object to the list.
                    _profiles.add(new User(_id2, _name));
                }
            }
        } catch (SQLException sqlEx) {
            // Log the SQL error if any.
            System.err.println("Failed to retrieve user data. SQL Error Code: " + sqlEx.getErrorCode());
        } catch (Exception ex) {
            // Catch any other unexpected exceptions.
            System.err.println("An unexpected error occurred while processing users: " + ex.getMessage());
        }

        // Debugging output to indicate that the method has reached this point.
        System.out.println("FFF!");

        // Return the populated list of user profiles.
        return _profiles;
    }

    /**
     * Validates a user's login by checking the username and password against the database.
     * This method performs a case-sensitive comparison for both the username and password.
     *
     * @param _user the username input by the user.
     * @param _pass the password input by the user.
     * @return true if the username and password match an entry in the database, false otherwise.
     */
    public static boolean _login(String _user, String _pass) {

        // SQL query to select all columns from the 'users' table where both the username and password match in a case-sensitive manner.
        String _SQL_CODE = "SELECT u.* " +
                 "FROM " // Specify the table to retrieve data from.
                    + "users AS u " + // Assign an alias 'u' for the 'users' table.
                 "WHERE "  // Begin the condition for filtering rows.
                    + "BINARY u.User_Name = ? " + // Case-sensitive check for matching username.
                 "AND "  // Combine the conditions for username and password.
                    + "BINARY u.Password = ?";  // Case-sensitive check for matching password.

        // Variable to track if the login credentials are valid.
        String _currently_is_valid = "FALSE";
        
        System.out.println("User's role: " + _get_user_role(_user));

        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Set the second parameter (password) in the prepared statement.
            _PreparedStatement.setString(2, _pass);
            // Set the first parameter (username) in the prepared statement.
            _PreparedStatement.setString(1, _user);

            // Execute the query and check if the result set contains any rows (i.e., if the credentials match).
            try (ResultSet _ResultSet = _PreparedStatement.executeQuery()) {
                if (_ResultSet.next()) {
                    _currently_is_valid = "TRUE";  // If a match is found, set valid status to true.
                }
            }

        } catch (SQLException e) {
            // Log SQL exceptions, providing both the error code and message.
            System.err.println("SQL Exception occurred during login database match. Error Code: " + e.getErrorCode() + ", Message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Log any other unexpected exceptions.
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        // Debugging output to indicate that the method execution reached this point.
        System.out.println("BAR!");

        // Return true if the credentials matched, otherwise return false.
        if (_currently_is_valid == "FALSE") {
            return false;
        } else {
            return true;
        }
    }
}
