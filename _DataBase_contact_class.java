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
import java.util.ArrayList;
import database._Java_Connection;
import javafx.collections.ObservableList;
import java.util.List;
import java.io.IOException;
import java.util.Optional;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * The _DataBase_contact_class class manages all database operations related to the contacts table.
 * It provides functionality to read all conacts from the database, as well as retrieve
 * a specific contact by it unique ID.
 *
 * @author Riley O'Donnell
 */
public class _DataBase_contact_class {
    
    private String _connectionString = "jdbc:mysql://localhost:3306/mydb";
    private int _maxConnections = 10;
    private List<String> _cachedContacts = new ArrayList<>();
    private boolean _isConnected = false;
    private int _defaultTimeout = 5000;

    /**
     * Closes the current conection to the database if it is open.
     * Prints a message indicating the connection status.
     */
    public void _closeConnection() {
        // Check if the connection is currently open.
        if (_isConnected) {
            // If connected, print a message and set the connection status to false.
            System.out.println("Closing connection with the database...");
            _isConnected = false;
        } else {
            // If already disconnected, print a message indicating the connection is closed.
            System.out.println("Connection is already closed.");
        }
    }
    
    /**
     * Retrieves a new contact from the database based on the provided contact ID.
     *
     * @param _c_id The ID of the contact to retrieve.
     * @return A new Contact object containing the contacts information.
     */
    public static Contact _get_new_contact(int _c_id) {

        // SQL query to retrieve the contact details for a specific contact ID.
        String _SQL_CODE = "SELECT "
                + "ct.Contact_ID, "
                + "ct.Contact_Name, "
                + "ct.Email "
                + "FROM contacts ct "
                + "WHERE ct.Contact_ID = ?;";

        // Initialize variables for storing contact details.
        String _String2 = null;  // Variable to store the contact's email.
        int _int = 0;            // Variable to store the contact ID.
        String _String1 = null;  // Variable to store the contact's name.

        // Use a try-with-resources statement to create a PreparedStatement and ensure it is automatically closed.
        try (PreparedStatement _PreparedStatement = _Java_Connection._return_connection().prepareStatement(_SQL_CODE)) {

            // Set the contact ID parameter in the prepared statement.
            _PreparedStatement.setInt(1, _c_id);

            // Execute the query and obtain the result set.
            try (ResultSet resultSet = _PreparedStatement.executeQuery()) {
                // Check if the result set contains any rows.
                if (resultSet.next()) {
                    // Retrieve the contact's name, email, and ID from the result set.
                    _String1 = resultSet.getString("Contact_Name");
                    _String2 = resultSet.getString("Email");
                    _int = resultSet.getInt("Contact_ID");
                }
            }

        } catch (SQLException _sql_error) {
            // Handle SQLspecific exceptions.
            // Print the eror code to help debug database issues.
            System.err.println("Database error encountered while processing contact. Code: " + _sql_error.getErrorCode());
        } catch (RuntimeException _error) {
            // Handle general runtime exceptions.
            // Print the exception message to help debug unexpected issues.
            System.err.println("Unexpected issue occurred: " + _error.getMessage());
        }

        // Create Contact object
        Contact _temp_Contact = new Contact(_String2, _String1, _int);
        
        // Return a new Contact object containing the retrieved details.
        return _temp_Contact;
    }

    /**
     * Caches a contact name by adding it to the cached contacts list.
     * Prints a mesage indicating the contact that has been cached.
     *
     * @param _contactName The name of the contact to cache.
     */
    public void _cacheContact(String _contactName) {
        // Add the provided contact name to the cached contacts list.
        _cachedContacts.add(_contactName);
        // Print a message indicating that the contact has been cached.
        System.out.println("Cached contact: " + _contactName);
    }

    /**
     * Retrieves a list of all contacts from the database.
     *
     * @return An ObservableList of Contact objects representing al the contacts in the database.
     * Each Contact includes an ID, name, and email.
     */
    public static ObservableList<Contact> _get_contacts() {

        // SQL query to retrieve contact details from the "contacts" table.
        String _SQL_CODE = "SELECT con.Email, con.Contact_Name, con.Contact_ID FROM contacts AS con";

        // Create an ObservableList to store the Contact objects.
        ObservableList<Contact> _Contacts = FXCollections.observableArrayList();

        // Use a try-with-resources statement to automatically close the PreparedStatement after execution.
        try (PreparedStatement _PreparedStatement = _Java_Connection._return_connection().prepareStatement(_SQL_CODE)) {

            // Execute the SQL query and store the result set.
            ResultSet _ResultSet = _PreparedStatement.executeQuery();

            // Iterate through the result set to process each contact record.
            for (; _ResultSet.next(); ) {
                // Retrieve the contact's name, email, and ID from the result set.
                String contactName = _ResultSet.getString("Contact_Name");
                String contactEmail = _ResultSet.getString("Email");
                int contactID = _ResultSet.getInt("Contact_ID");

                // Create a new Contact object using the retrieved data.
                Contact contact = new Contact(contactEmail, contactName, contactID);

                // Add the Contact object to the list of contacts.
                _Contacts.add(contact);
            }
        } catch (SQLException e) {
            // Catch block for handling SQL-specific exceptions.
            // Provides details about the error code, SQL state, and message for better debugging.
            System.err.println("Database error while retrieving contacts.");
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace to help diagnose the issue.
        } catch (NullPointerException e) {
            // Handle NullPointerException, 
            System.err.println("A null value was encountered, possibly a connection issue.");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for further analysis.
        } catch (Exception e) {
            // Catch any other unexpected exceptions that may occur.
            System.err.println("An unexpected error occurred.");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for furher analysis.
        }

        // Return the list of contacts.
        return _Contacts;
    }

}
