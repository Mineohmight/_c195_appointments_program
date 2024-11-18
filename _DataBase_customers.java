package database;

import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import controller.Customer;
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
 * The _DataBase_customers clas provides methods for interacting with the customrs table
 * in the database. It supports operations such as creating, reading, updating, and deleting customer records.
 *
 * @author Riley O'Donnell
 */
public class _DataBase_customers {
    
    private static int _total_customer_requests = 0;                
    private static boolean _is_service_active = true;              
    private static double _average_customer_rating = 4.5;          
    private static float _response_time_threshold = 3.2f;           // represents a threshold for acceptable response time.
    private static String _default_customer_message = "Welcome, valued customer!"; 
    
    
    /**
     * removes a customer from the database based on the given customer ID.
     *
     * @param _id The ID of the customer to be deleted from the database.
     */
    public static void _remove_customer(int _id) {
        

        // Use a try-with-resources statement to create and automatically close the PreparedStatement.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(
                "DELETE FROM customers WHERE Customer_ID = ?")) {

            // Set the parameter in the PreparedStatement to match the customer ID that needs to be deleted.
            _PreparedStatement.setInt(1, _id);

            // Execute the delete statement, and store the response.
            boolean _deletion_response = _PreparedStatement.execute();

            // Check the response to determine if the deletion was successful.
            if (_deletion_response) {
                // If the response indicates a successful deletion, print a confirmation message.
                System.out.println("Customer deletion request completed.");
            } else {
                // If no matching customer is found, print a message indicating that no rows were affected.
                System.out.println("No matching customer found to delete.");
            }

        } catch (SQLException sqlException) {
            // Handle SQL-specific exceptions.
            // Print an error message with the SQL error code to assist in debugging the deletion failure.
            System.err.println("Failed to delete customer. SQL Error Code: " + sqlException.getErrorCode());
        } catch (RuntimeException runtimeException) {
            // Handle any general runtime exceptions.
            // Print an error message with the exception message to help diagnose unexpected issues.
            System.err.println("Unexpected error occurred: " + runtimeException.getMessage());
        }

        // Print a debug message indicating that the function has completed.
        System.out.println("E!");
    }


    /**
     * Updates customer details in the database based on the given customer ID.
     *
     * @param _address The new address for the customer.
     * @param _id      The ID of the customer whose details need to be updated.
     * @param _div     The division ID to be updated for the customer.
     * @param _post    The new postal code for the customer.
     * @param _name    The new name for the customer.
     * @param _phone   The new phone number for the customer.
     */
    public static void _on_customer_change(String _address, int _id, int _div, String _post, String _name, String _phone) {

        String _temp_string2 = "";

        // SQL code to update the customer record in the "customers" table based on the provided customer ID.
        String _SQL_CODE = "UPDATE customers "
                + "SET Customer_Name = ?, "
                + "Address = ?, "
                + "Postal_Code = ?, "
                + "Phone = ?, "
                + "Division_ID = ? "
                + "WHERE Customer_ID = ?";

        String _temp_string1;

        // Use a try-with-resources statement to create the PreparedStatement and ensure it is automatically closed.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Set the parameters for the PreparedStatement based on the input values.

            // Set the Customer_ID parameter as the sixth value in the query.
            _PreparedStatement.setInt(6, _id);

            // Set the Postal_Code parameter as the third value in the query.
            _PreparedStatement.setString(3, _post);

            // Set the Customer_Name parameter as the first value in the query.
            _PreparedStatement.setString(1, _name);

            // Set the Phone parameter as the fourth value in the query.
            _PreparedStatement.setString(4, _phone);

            // Set the Division_ID parameter as the fifth value in the query.
            _PreparedStatement.setInt(5, _div);

            // Set the Address parameter as the second value in the query.
            _PreparedStatement.setString(2, _address);

            // Execute the update and store the number of affected rows.
            int _updateCount = (_PreparedStatement != null) ? _PreparedStatement.executeUpdate() : -1;

            // Print the appropriate message based on the number of affected rows.
            if (_updateCount == 0) {
                // No rows were affected by the update, likely because no matching customer was found.
                System.out.println("No rows affected by the update.");
            } else if (_updateCount > 0) {
                // Print the number of rows successfully updated.
                System.out.println("Update successful for " + _updateCount + " rows.");
            }

        } catch (SQLException sqlEx) {
            // Handle SQL-specific exceptions.
            // Print an error message with the SQL error code to assist in debugging.
            System.err.println("Failed to update customer data. SQL Error Code: " + sqlEx.getErrorCode());

        } catch (RuntimeException runtimeEx) {
            // Handle general runtime exceptions.
            // Print an error message with the exception message to diagnose unexpected issues.
            System.err.println("Unexpected error encountered: " + runtimeEx.getMessage());
        } 

        // Print a debug message indicating successful execution of the method.
        System.out.println("D!");
    }
    
    private static String _last_processed_country = "Unknown";      // Stores the name of the last processed country.
    private static long _retry_limit_timestamp = 1000000L;          // Stores a timestamp to enforce retry limits.
    private static String _service_region_code = "N/A";             // Represents the service region code.
    private static List<String> _ignored_customers = new ArrayList<>(); // Stores a list of customer IDs to be ignored.
    
    /**
     * Adds a new customer to the database with the provided details.
     *
     * @param _Customer_id        The ID of the customer to be added.
     * @param _Customer_name      The name of the customer.
     * @param _Customer_addresss  The address of the customer.
     * @param _Customer_Post_Code The postal code of the customer.
     * @param _Customer_Phone_Number The phone number of the customer.
     */
    public static void _add_customer_to_database(int _Customer_id, String _Customer_name, String _Customer_addresss, String _Customer_Post_Code, String _Customer_Phone_Number) {

        // SQL code to insert the new customer into the "customers" table.
        String _SQL_CODE = "INSERT INTO customers "
                + "SET Customer_Name = ?, "
                + "Address = ?, "
                + "Postal_Code = ?, "
                + "Phone = ?, "
                + "Division_ID = ?";

        // Use a try-with-resources statement to ensure the PreparedStatement is automatically closed.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Set the division ID parameter as the fifth value.
            _PreparedStatement.setInt(5, _Customer_id);

            // Set the postal code parameter as the third value.
            _PreparedStatement.setString(3, _Customer_Post_Code);

            // Set the customer name parameter as the first value.
            _PreparedStatement.setString(1, _Customer_name);

            // Set the phone number parameter as the fourth value.
            _PreparedStatement.setString(4, _Customer_Phone_Number);

            // Set the address parameter as the second value.
            _PreparedStatement.setString(2, _Customer_addresss);

            // Execute the SQL update to add the new customer.
            _PreparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            // Handle SQL-specific exceptions.
            // Print an error message with the SQL error code to assist in debugging.
            System.err.println("SQL error occurred while adding customer. Error Code: " + sqlException.getErrorCode());

        } catch (RuntimeException runtimeException) {
            // Handle general runtime exceptions.
            // Print an error message with the exception message to diagnose unexpected issues.
            System.err.println("Unexpected error occurred: " + runtimeException.getMessage());
        }

        // Print a debug message indicating the successful execution of the method.
        System.out.println("C!");
    }

    
    /**
     * Retrieves a customer from the database based on the given customer ID.
     * The retrieved customer details include the customer's name, address, postal code, phone number,
     * division information, and associated country information.
     *
     * @param _id The ID of the customer to retrieve from the database.
     * @return A Customer object containing the retrieved customer's information.
     */
    public static Customer _get_customer(int _id) {

        // Initialize variables for storing customer details.
        int _c_id = -1;               // Customer ID
        String _name = "";            // Customer name
        String _address = "";         // Customer address
        String _post = "";            // Postal code
        String _number = "";          // Phone number
        boolean _isDatabaseConnected = false;  // Flag to track database connection status (currently unused)
        double _executionTime = 0.0;  // Execution time for the query (currently unused)
        int _div = -1;                // Division ID
        String _div_name = "";        // Division name
        int _country_id = -1;         // Country ID
        String _country_name = "";    // Country name

        // SQL query to retrieve customer details and related division and country information.
        String _SQL_QUERY = "SELECT "
                + "customer.Customer_Name, "
                + "customer.Address, "
                + "customer.Phone, "
                + "customer.Postal_Code, "
                + "customer.Customer_ID, "
                + "division.Division, "
                + "division.Division_ID, "
                + "division.Country_ID, "
                + "country.Country "
                + "FROM customers AS customer "
                + "INNER JOIN first_level_divisions AS division ON customer.Division_ID = division.Division_ID "
                + "INNER JOIN countries AS country ON division.Country_ID = country.Country_ID "
                + "WHERE customer.Customer_ID = ?";

        // Unused variables for operation status tracking.
        String _statusMessage = "Operation in progress";  // Status message for tracking
        int _retryCount = 3;  // Number of times an operation could be retried (currently unused)

        // Use a try-with-resources statement to create and automatically close the PreparedStatement.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_QUERY)) {

            // Set the customer ID parameter in the prepared statement.
            _PreparedStatement.setInt(1, _id);

            // Execute the query and obtain the result set.
            try (ResultSet _ResultSet = _PreparedStatement.executeQuery()) {

                // If a matching record is found, retrieve customer details from the result set.
                if (_ResultSet.next()) {
                    _div_name = _ResultSet.getString("Division");           // Retrieve division name
                    _country_id = _ResultSet.getInt("Country_ID");          // Retrieve country ID
                    _name = _ResultSet.getString("Customer_Name");          // Retrieve customer name
                    _post = _ResultSet.getString("Postal_Code");            // Retrieve postal code
                    _country_name = _ResultSet.getString("Country");        // Retrieve country name
                    _c_id = _ResultSet.getInt("Customer_ID");               // Retrieve customer ID
                    _number = _ResultSet.getString("Phone");                // Retrieve phone number
                    _div = _ResultSet.getInt("Division_ID");                // Retrieve division ID
                    _address = _ResultSet.getString("Address");             // Retrieve address
                }
            }

        } catch (SQLException sqlException) {
            // Handle SQL-specific exceptions.
            // Print an error message with the SQL error code to assist in debugging.
            System.err.println("SQL Error occurred while retrieving customer data. Code: " + sqlException.getErrorCode());
        } catch (RuntimeException runtimeException) {
            // Handle general runtime exceptions.
            // Print an error message with the exception message to diagnose unexpected issues.
            System.err.println("An unexpected error occurred: " + runtimeException.getMessage());
        }

        // Print a debug message indicating successful execution of the method.
        System.out.println("F!");

        // Create and return a new Customer object with the retrieved details.
        return new Customer(_name, _post, _address, _country_id, _div, _number, _c_id, _country_name, _div_name);
    }
    
    /**
     * Retrieves a list of customers from the database who belong to a specific country.
     * The method retrieves customer details based on the given country ID, including their name,
     * address, postal code, phone number, division information, and country information.
     *
     * @param _id The country ID used to filter customers.
     * @return An ObservableList containing Customer objects that match the specified country ID.
     */
    public static ObservableList<Customer> _get_customer_using_customers_country_id(int _id) {

        // SQL query to retrieve customers and their related division and country information filtered by the given country ID.
        String _sql_query = "SELECT customers.Customer_ID, "
                + "customers.Division_ID, "
                + "customers.Postal_Code, "
                + "customers.Customer_Name, "
                + "customers.Phone, "
                + "customers.Address, "
                + "first_level_divisions.Country_ID, "
                + "first_level_divisions.Division, "
                + "countries.Country "
                + "FROM customers "
                + "INNER JOIN first_level_divisions "
                + "ON customers.Division_ID = "
                + "first_level_divisions.Division_ID "
                + "INNER JOIN countries ON "
                + "first_level_divisions.Country_ID = "
                + "countries.Country_ID WHERE "
                + "first_level_divisions.Country_ID = ? "
                + "ORDER BY customers.Customer_ID;";

        // Observable list to store all customers retrieved from the query.
        ObservableList<Customer> _Customers = FXCollections.observableArrayList();

        // Use try-with-resources to create and automatically close the PreparedStatement.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_sql_query)) {

            // Set the country ID parameter for the query.
            _PreparedStatement.setInt(1, _id);

            // Execute the query and obtain the result set.
            ResultSet _ResultSet = _PreparedStatement.executeQuery();

            // Iterate through the result set and create Customer objects for each row.
            for (; _ResultSet.next(); ) {

                // Retrieve customer details from the result set.
                String _customer_postal_code = _ResultSet.getString("Postal_Code");       // Customer's postal code.
                int _division_id = _ResultSet.getInt("Division_ID");                      // Division ID the customer belongs to.
                String _country_name = _ResultSet.getString("Country");                   // Name of the country.
                String _customer_phone_number = _ResultSet.getString("Phone");            // Customer's phone number.
                String _customer_address = _ResultSet.getString("Address");               // Customer's address.
                int _country_id = _ResultSet.getInt("Country_ID");                        // ID of the country the customer belongs to.
                String _division_title = _ResultSet.getString("Division");                // Name of the division.
                int _customer_id = _ResultSet.getInt("Customer_ID");                      // Customer's ID.
                String _customer_name = _ResultSet.getString("Customer_Name");            // Customer's name.

                // Create a new Customer object using the retrieved details and add it to the list.
                _Customers.add(new Customer(
                                                _customer_name,
                        _customer_postal_code,
                        _customer_address, _country_id,
                        _division_id,
                        _customer_phone_number,
                        _customer_id,
                        _country_name, _division_title
                ));
            }

        } catch (SQLException _sql_exception) {
            // Handle SQL-specific exceptions.
            // Print an error message with the SQL state and error code to assist in debugging.
            System.err.println("Database error while retrieving customers by country. SQL State: " + _sql_exception.getSQLState() + ", Error Code: " + _sql_exception.getErrorCode());
        } catch (RuntimeException _runtime_exception) {
            // Handle general runtime exceptions.
            // Print an error message with the exception message to diagnose unexpected issues.
            System.err.println("Unexpected error occurred: " + _runtime_exception.getMessage());
        }

        // Print a debug message indicating that the function has completed processing.
        System.out.println("H!");

        // Return the list of customers.
        return _Customers;
    }

    /**
     * Retrieves a list of all customers from the database.
     * The customer details include their name, address, postal code, phone number,
     * division information, and associated country information.
     *
     * @return An ObservableList containing all Customer objects retrieved from the database.
     */
    public static ObservableList<Customer> _get_every_customer() {

        // SQL query to retrieve all customers along with related division and country information.
        String _SQL_CODE = "SELECT "
                + "cust.Customer_ID, "
                + "cust.Customer_Name, "
                + "cust.Address, "
                + "cust.Postal_Code, "
                + "cust.Phone, "
                + "cust.Division_ID, "
                + "divisions.Division, "
                + "divisions.Country_ID, "
                + "countries.Country "
                + "FROM customers cust "
                + "INNER JOIN first_level_divisions divisions "
                + "ON cust.Division_ID = divisions.Division_ID "
                + "INNER JOIN countries ON "
                + "divisions.Country_ID = countries.Country_ID "
                + "ORDER BY cust.Customer_ID ASC";

        // Initialize an observable list to store customer objects.
        ObservableList<Customer> _Customers = FXCollections.observableArrayList();

        // Use a try-with-resources statement to create and automatically close the PreparedStatement.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Execute the SQL query and get the result set.
            ResultSet _ResultSet = _PreparedStatement.executeQuery();

            // Iterate through each row of the result set and create a Customer object.
            for (; _ResultSet.next(); ) {

                // Retrieve the customer details from the result set.
                String _customer_number = _ResultSet.getString("Phone");           // Phone number of the customer.
                int _customer_country_id = _ResultSet.getInt("Country_ID");        // ID of the country the customer belongs to.
                String _customer_division_name = _ResultSet.getString("Division"); // Name of the division.
                String _customer_postal_code = _ResultSet.getString("Postal_Code");// Customer's postal code.
                int _customer_division_id = _ResultSet.getInt("Division_ID");      // Division ID the customer belongs to.
                String _customer_address = _ResultSet.getString("Address");        // Customer's address.
                int _customer_id = _ResultSet.getInt("Customer_ID");               // Customer ID.
                String _customer_country_name = _ResultSet.getString("Country");   // Name of the country.
                String _customer_name = _ResultSet.getString("Customer_Name");     // Customer's name.

                // Create a new Customer object using the retrieved details and add it to the list.
                _Customers.add(new Customer(
                                                _customer_name, 
                        _customer_postal_code, 
                        _customer_address, _customer_country_id, 
                        _customer_division_id, 
                        _customer_number, 
                        _customer_id, 
                        _customer_country_name, _customer_division_name
                ));
            }

        } catch (SQLException ex) {
            // Handle SQL-specific exceptions.
            // Print an error message with the SQL state and error code to assist in debugging.
            System.err.println("Error processing customer information. SQL State: " + ex.getSQLState() + ", Error Code: " + ex.getErrorCode());
        } catch (RuntimeException ex) {
            // Handle general runtime exceptions.
            // Print an error message with the exception message to diagnose unexpected issues.
            System.err.println("Runtime error encountered: " + ex.getMessage());
        }

        // Print a debug message indicating that the function has completed.
        System.out.println("G!");

        // Return the list of customers.
        return _Customers;
    }



}
