package database;


import javafx.scene.text.TextAlignment;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import java.time.temporal.TemporalAdjusters;
import javafx.scene.control.cell.PropertyValueFactory;
import database._Java_Connection;
import java.sql.SQLException;
import java.sql.BatchUpdateException;
import java.time.chrono.Chronology;


import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.LinkedList;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.util.Pair;
import javafx.scene.input.KeyCode;
import java.sql.SQLClientInfoException;
import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import java.sql.SQLDataException;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import javafx.util.Callback;
import database._Duo;
import java.sql.Connection;
import javafx.scene.control.TableView;


import database._Triplet;
import java.time.DayOfWeek;
import controller.Appointment;
import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;
import java.time.*;
import javafx.scene.text.Text;
import java.sql.PreparedStatement;
import javafx.scene.input.MouseEvent;
import java.sql.ResultSet;
import java.time.temporal.TemporalUnit;
import java.sql.SQLNonTransientException;
import java.time.temporal.WeekFields;

/**
 * The _Database_appointment_class class is responsible for manging all database queries related 
 * to the appointments table. This includes creating, reading, updating, and deleting 
 * appointment records. Additionaly, it handles time zone conversions and other 
 * time-related operations required for appointments.
 * 
 * @author Riley O'Donnell
 */
public class _Database_appointment_class 
{

    
    
    /**
     * Converts a given UTC Timestamp to a LocalDateTime in the user's local time zone.
     *
     * This method takes a UTC Timestamp and converts it into the corresponding LocalDateTime
     * in the system's default time zone (i.e., the user's local time zone). The conversion keeps the 
     * instant in time the same, but adjusts the date and time according to the local time zone.
     *
     * @param _UTC_time_stamp The UTC Timestamp to be converted.
     * @return A LocalDateTime representing the same instant in the user's local time zone.
     *
     * This method follows these steps:
     * 1. Converts the UTC Timestamp to an Instant.
     * 2. Applies the UTC time zone to that Instant.
     * 3. Converts the ZonedDateTime from UTC to the system's local time zone, maintaining the same instant in time.
     * 4. Returns a LocalDateTime by discarding the time zone information.
     *
     * Example usage:
     * If the input UTC timestamp is '2024-09-28 12:00:00' and the user's system is in 'America/New_York' time zone (UTC-4),
     * the method will return '2024-09-28 08:00:00', reflecting the local date and time.
     */
    private static LocalDateTime _convert_to_timezone(Timestamp _UTC_time_stamp) {
        // Define the UTC time zone
        ZoneId _UTC_zone = ZoneId.of("UTC");

        // Get the user's local time zone from the system
        ZoneId _LOCAL_zone = ZoneId.systemDefault();

        // Convert the UTC timestamp (UTC_time_stamp) to an Instant, then create a ZonedDateTime in UTC
        // After that, convert the ZonedDateTime to the user's local time zone, keeping the same instant
        ZonedDateTime _LOCAL_user_date_time_zoned = _UTC_time_stamp.toInstant().atZone(_UTC_zone).withZoneSameInstant(_LOCAL_zone);

        // Return the local date and time as a LocalDateTime, discarding the time zone information
        return _LOCAL_user_date_time_zoned.toLocalDateTime();
    }

        
    /**
     * Creates an appointment within the to database.
     *
     * This method inserts a new appointment into the appointments table, with the given title, description, 
     * location, type, start time, end time, and associated user, customer, and contact IDs. The start and end 
     * times are converted to UTC before being stored in the database.
     *
     * @param _title_of_appointment The title or name of the appointment, typically a short description.
     * @param _start_time_of_appointment The start date and time of the appointment, as a LocalDateTime, which will be converted to UTC before storage.
     * @param _location_of_appointment The physical or virtual location where the appointment will occur.
     * @param _description_of_appointment A detailed description or explanation of the appointment's purpose or content.
     * @param _type_of_appointment The type or category of the appointment (e.g., meeting, consultation).
     * @param _customer_ID_of_appointment The ID of the customer associated with this appointment.
     * @param _contact_ID_of_appointment The ID of the contact person involved in the appointment.
     * @param _user_ID_of_appointment The ID of the user who created or manages the appointment.
     * @param _end_time_of_appointment The end date and time of the appointment, as a LocalDateTime, which will be converted to UTC before storage.
     *
     * This method follows these steps:
     * 1. Converts the start and end times from the user's local time to UTC.
     * 2. Prepares the SQL INSERT statement with placeholders for the appointment details.
     * 3. Binds the provided parameters to the SQL statement.
     * 4. Executes the statement to add the new appointment to the database.
     * 
     * If there is a SQL exception or other exception during execution, the method catches and handles it by printing 
     * the error message.
     */
    public static void _add_appointment(String _title_of_appointment, LocalDateTime _start_time_of_appointment, String _location_of_appointment, String _description_of_appointment, String _type_of_appointment, int _customer_ID_of_appointment, int _contact_ID_of_appointment, int _user_ID_of_appointment, LocalDateTime _end_time_of_appointment) {

        // Define the SQL query as a string. This is an INSERT statement that will add a new record into the 'appointments' table.
        // The placeholders (?) represent the values that will be dynamically set later using a PreparedStatement.
        String _sql_string = "INSERT INTO appointments SET Title = ?, "
                + "Start = ?, "
                + "Location = ?, "
                + "Description = ?, "
                + "Type = ?, "
                + "Customer_ID = ?, "
                + "Contact_ID = ?, "
                + "User_ID = ?, "
                + "End = ?;";

        try (
            // Create a PreparedStatement object using the SQL query. The JDBC._connection retrieves the database connection,
            // and the prepareStatement method prepares the SQL string to be executed with the appropriate values.
            PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_sql_string)) {

            // Convert the provided LocalDateTime (appointment start time) to UTC format. 
            // This ensures that the time is stored in the database as UTC regardless of the user's local time zone.
            Timestamp _start_time_of_appointment_in_UTC = _turn_time_to_utc(_start_time_of_appointment);

            // Convert the provided LocalDateTime (appointment end time) to UTC format, similar to the start time.
            // This guarantees that both the start and end times are in UTC when stored in the database.
            Timestamp _end_time_of_appointment_in_UTC = _turn_time_to_utc(_end_time_of_appointment);

            // Set the values for each placeholder (?) in the SQL query:
            // Set the appointment title (string) in the first placeholder.
            _PreparedStatement.setString(1, _title_of_appointment);

            // Set the converted UTC start time (Timestamp) in the second placeholder.
            _PreparedStatement.setTimestamp(2, _start_time_of_appointment_in_UTC);

            // Set the converted UTC end time (Timestamp) in the ninth placeholder. We use position 9 to match its place in the query.
            _PreparedStatement.setTimestamp(9, _end_time_of_appointment_in_UTC);

            // Set the location of the appointment (string) in the third placeholder.
            _PreparedStatement.setString(3, _location_of_appointment);

            // Set the appointment description (string) in the fourth placeholder.
            _PreparedStatement.setString(4, _description_of_appointment);

            // Set the appointment type (string) in the fifth placeholder. This could be a category like 'meeting' or 'consultation'.
            _PreparedStatement.setString(5, _type_of_appointment);

            // Set the customer ID (int) in the sixth placeholder. This is an integer representing the customer associated with the appointment.
            _PreparedStatement.setInt(6, _customer_ID_of_appointment);

            // Set the contact ID (int) in the seventh placeholder. This represents the contact person associated with the appointment.
            _PreparedStatement.setInt(7, _contact_ID_of_appointment);

            // Set the user ID (int) in the eighth placeholder. This represents the ID of the user (usually the one creating or managing the appointment).
            _PreparedStatement.setInt(8, _user_ID_of_appointment);

            System.out.println("### Test ###");

            // Execute the SQL statement that was prepared earlier. This sends the query to the database, inserting the new record into the 'appointments' table.
            _PreparedStatement.executeUpdate();

        } catch (SQLException _sql_exception_error) {
            // If there is an issue with the SQL query or database connection (e.g., invalid SQL syntax, connectivity issues),
            // this block will catch the SQLException and print the full error stack trace to help identify the problem.
            _sql_exception_error.printStackTrace();
            System.out.println("SQL ERROR in _add_appointment function:");

        } catch (Exception _exception_error) {
            // If any other type of exception occurs (e.g., null pointer exception, runtime error), this block will catch it.
            // It will print a custom message along with the exception's message to help understand what went wrong.
            System.out.print("Hello, there was an EXCEPTION: " + _exception_error.getMessage());
        }
    }
    
    /**
     * Converts a given LocalDateTime in the system's default time zone to a UTC Timestamp.
     *
     * This method takes a LocalDateTime object that is assumed to represent the user's local date and time,
     * converts it into the UTC time zone, and returns the corresponding Timestamp in UTC.
     *
     * @param _local_date_time The local date and time to be converted, represented as a LocalDateTime.
     *                         This LocalDateTime should be in the system's local time zone.
     * @return A Timestamp representing the equivalent date and time in UTC.
     */
    private static Timestamp _turn_time_to_utc(LocalDateTime _local_date_time) {

        // Step 1: Convert the provided LocalDateTime to an Instant based on UTC (no time zone information)
        // and then create a ZonedDateTime using the system's default time zone.
        // The LocalDateTime is first converted to an Instant (assuming it's already in UTC).
        // Then, the system's default time zone is applied to that Instant to create the ZonedDateTime object.
        ZonedDateTime _users_local_date_time = ZonedDateTime.ofInstant(
            _local_date_time.toInstant(ZoneOffset.UTC),  // Convert LocalDateTime to Instant assuming UTC.
            ZoneId.systemDefault()                      // Apply system's local time zone to Instant.
        );

        // Step 2: Convert the user's local ZonedDateTime to the UTC time zone without changing the actual time instant.
        // This operation shifts the ZonedDateTime to the UTC time zone while preserving the same instant in time.
        ZonedDateTime _utc_date_time = _users_local_date_time.withZoneSameInstant(ZoneOffset.UTC);

        // Step 3: Convert the ZonedDateTime (which is now in UTC) back to a LocalDateTime.
        // Finally, convert this LocalDateTime to a SQL Timestamp, which represents the exact time in UTC.
        Timestamp _utc_time_stamp = Timestamp.valueOf(_utc_date_time.toLocalDateTime());

        // Step 4: Return the resulting UTC Timestamp.
        return _utc_time_stamp;
    }
    
    /**
     * Deletes an appointment from the database using the provided appointment ID.
     *
     * This method removes an appointment from the 'appointments' table in the database based 
     * on the provided appointment ID. It prepares and executes a DELETE SQL statement 
     * with the appointment ID as the parameter. If an SQL or general exception occurs, 
     * it handles and prints the error details.
     *
     * @param _appointment_ID The ID of the appointment to be deleted from the database.
     * 
     * Exceptions:
     * - SQLException: If there is an error while executing the SQL statement (e.g., connection issues or query errors).
     * - Exception: If any other general error occurs during the execution of the method.
     */
    public static void _delete_appointment(  int _appointment_ID  ) {

        // Define the SQL query to delete an appointment from the database where Appointment_ID matches
        String _SQL_query =    "DELETE  "
                + " FROM  "
                + "  appointments "
                + "  WHERE   "
                + " Appointment_ID  "
                + " =   ?";

        // Start the try block to handle any SQL or other exceptions that may occur
        try {

            // Create a PreparedStatement object to execute the SQL query
            // The prepared statement is used to avoid SQL injection by binding parameters
            PreparedStatement  _PreparedStatement  =  _Java_Connection._connection.prepareStatement(  _SQL_query  );

            // Set the first parameter (?) in the SQL query to the appointment ID provided as input
            // The appointment ID is passed into the query, replacing the placeholder
            _PreparedStatement.setInt(   1,    _appointment_ID   );

            // Execute the SQL update, which will perform the DELETE operation in the database
            _PreparedStatement.   executeUpdate(   );

        }   catch  (  SQLException  _sql_exception  )   {
            // If there is an SQL-related issue, print the full stack trace for debugging
            _sql_exception.   printStackTrace(   );

            // Print the error code related to the SQL exception to provide more details
            System.out.print(  "SQL EXCEPTION  in _delete_appointment function: " + _sql_exception.  getErrorCode(   )  );

        }   catch  (  Exception  _normal_error  )   {
            // If a general exception occurs, print the error message to the console
            System.out.print(   "ERROR: "   +   _normal_error.getMessage(   )   );
        }
    }

    /**
     * Updates an existing appointment in the database.
     *
     * This method updates the details of an existing appointment based on the appointment ID.
     * It updates the title, description, location, type, start time, end time, user ID, 
     * contact ID, and customer ID. Start and end times are converted to UTC before being stored.
     *
     * @param _appt_id The ID of the appointment to update.
     * @param _appt_title The new title of the appointment.
     * @param _appt_start The new start time of the appointment (LocalDateTime) in the user's local time zone.
     * @param _appt_location The new location of the appointment.
     * @param _appt_desc The new description of the appointment.
     * @param _appt_type The new type/category of the appointment.
     * @param _appt_customer_id The customer ID associated with the appointment.
     * @param _appt_contact_id The contact ID associated with the appointment.
     * @param _appt_user_id The user ID managing the appointment.
     * @param _appt_end The new end time of the appointment (LocalDateTime) in the user's local time zone.
     */
    public static void _update_appointment(int _appt_contact_id, int _appt_id, int _appt_user_id, LocalDateTime _appt_start, String _appt_location, LocalDateTime _appt_end, String _appt_desc, int _appt_customer_id, String _appt_title, String _appt_type) { 

        // SQL query to update the appointment details in the database.
        String _sql = "UPDATE appointments "
                                            + "AS appts SET "
                                            + "appts.Title "
                                            + "= ?, "
                                            + "appts.Description "
                                            + "= ?, "
                                            + "appts.Location "
                                            + "= ?, "
                                            + "appts.Type "
                                            + "= ?, "
                                            + "appts.Start "
                                            + "= ?, "
                                            + "appts.End "
                                            + "= ?, "
                                            + "appts.User_ID"
                                            + " = ?, "
                                            + "appts.Contact_ID "
                                            + "= ?, "
                                            + "appts.Customer_ID "
                                            + "= ? "
                                            + "WHERE appts.Appointment_ID "
                                            + "= ?";


        // Try-with-resources block to ensure the PreparedStatement is automatically closed after execution.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_sql)) {

                // set the parameters for the SQL query, corresponding to the placeholders (?).

             // set the title of the appointment.
            _PreparedStatement.setString(1, _appt_title);

            // Set the end time of the appointment, converted to UTC.
            _PreparedStatement.setTimestamp(6, _turn_time_to_utc(_appt_end));

            // sSet the description of the appointment.
            _PreparedStatement.setString(2, _appt_desc);

            // set the user ID responsible for the appointment.
            _PreparedStatement.setInt(7, _appt_user_id);

                // set the contact ID associated with the appointment.
            _PreparedStatement.setInt(8, _appt_contact_id);

            // Set the location of the appointment.
            _PreparedStatement.setString(3, _appt_location);

            // Set the the appointment ID for the record to be updated.
            _PreparedStatement.setInt(10, _appt_id);

             // Set the type of the appointment.
            _PreparedStatement.setString(4, _appt_type);

            // Set the to customer ID associated with the appointment.
            _PreparedStatement.setInt(9, _appt_customer_id);

                // set the start time of the appointment, converted to UTC.
            _PreparedStatement.setTimestamp(5, _turn_time_to_utc(_appt_start));

            // execute the SQL update to apply the changes.
            _PreparedStatement.executeUpdate();

        } catch (SQLException _SQL_exception) {
            // Handle any SQL errors that may occur during execution.
            _SQL_exception.printStackTrace();
            System.out.println("SQL "
                    + "ERROR "
                    + "in _update_appointment "
                    + "function: " + _SQL_exception.getErrorCode());
        } catch (Exception _normal_exception) {
            // Handle any other exceptions that may occur.
            System.out.println("ERROR: " + _normal_exception.getMessage());
        }
    }



    
    /**
     * Retrieves a list of all appointments from the database.
     * 
     * This method executes an SQL query to fetch appointment data, joins with the contacts table,
     * and orders the results by Appointment_ID. It converts each record into an Appointment object
     * and stores them in an ObservableList, which is returned to the caller.
     *
     * @return ObservableList<Appointment> containing all appointments from the database
     */
    public static ObservableList<Appointment> _get_appointments() {

        // Create an ObservableList to store Appointment objects
        ObservableList<Appointment> _observable_list_appointments = FXCollections.observableArrayList();

        // SQL query to retrieve appointment details, joined with contacts, ordered by Appointment_ID
        String _sql_query = "SELECT " +
                    "appointments.User_ID, " +
                    "appointments.Appointment_ID, " +
                    "appointments.Title, " +
                    "appointments.Description, " +
                    "appointments.Location, " +
                    "appointments.Start, " +
                    "appointments.Type, " +
                    "appointments.End, " +
                    "appointments.Contact_ID, " +
                    "appointments.Customer_ID " +
                    "FROM " +
                    "contacts " +
                    "INNER JOIN " +
                    "appointments " +
                    "ON " +
                    "contacts.Contact_ID = appointments.Contact_ID ";

        // Use try-with-resources to ensure the PreparedStatement and ResultSet are closed automatically
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_sql_query);
             ResultSet _ResultSet = _PreparedStatement.executeQuery()) {

            // Check if the ResultSet contains any rows
            if (_ResultSet.next()) {
                // Loop through the ResultSet and add each Appointment to the observable list
                do {
                    _observable_list_appointments.add(_get_Appointment(_ResultSet));
                } while (_ResultSet.next()); // Continue adding until there are no more rows
            }

        } catch (SQLException _sql_error) {
            // Handle SQL-specific exceptions by printing an error message with the SQL error code
            System.err.printf("SQL error in _get_appointments_function: %d%n", _sql_error.getErrorCode());

        } catch (Exception _error) {
            // Handle general exceptions and print the error message
            System.err.print("_get_appointments_function error: " + _error);
        }

        // Return the list of Appointment objects
        return _observable_list_appointments;
    }


    /**
     * Retrieves an Appointment object from a given ResultSet.
     * 
     * This method extracts the appointment details from the given ResultSet,
     * converting relevant fields to the appropriate types, and uses them to 
     * construct an Appointment object.
     *
     * @param _result_set the ResultSet containing the appointment data from the database
     * @return an Appointment object with the data extracted from the ResultSet
     * @throws SQLException if any SQL or data retrieval error occurs
     */
    private static Appointment _get_Appointment(ResultSet _result_set) throws SQLException {
        // Convert the 'End' timestamp to LocalDateTime in the current timezone
        LocalDateTime _end_of_appointment = _convert_to_timezone(_result_set.getTimestamp("End"));

            // r etrieve the user ID associated with the appointment from the result set
        int _appointment_user_ID = _result_set.getInt("User_ID");

            //  extract the description of the appointment from the result set
        String _description_of_appointment = _result_set.getString("Description");

           // Extract the type/category of the appointment from the result set
        String _type_of_appointment = _result_set.getString("Type");

         //   Retrieve the customer ID associated with the appointment from the result set
        int _appointment_customer_id = _result_set.getInt("Customer_ID");

             // extract the location of the appointment from the result set
        String _location_of_appointment = _result_set.getString("Location");

             // Retrieve the contact ID associated with the appointment from the result set
        int _contact_ID_of_appointment = _result_set.getInt("Contact_ID");

        // extract the appointment ID from the result set
        int _appointmentID = _result_set.getInt("Appointment_ID");

          // Convert the 'Start' timestamp to LocalDateTime in the current timezone
        LocalDateTime _start_of_appointmne = _convert_to_timezone(_result_set.getTimestamp("Start"));

           // extract the title of the appointment from the result set
        String _title_of_appointment = _result_set.getString("Title");

         // Create a new Appointment object using the extracted data
        Appointment _temp_appointment = new Appointment(
                // Title of the appointment
                _description_of_appointment, // End date and time of the appointment
                _appointment_user_ID, // ID of the contact associated with the appointment
                _appointment_customer_id       // ID of the customer associated with the appointment
, _appointmentID,               // Unique ID of the appointment
            _title_of_appointment,         // Start date and time of the appointment
                _end_of_appointment,      // Location where the appointment will take place
            _type_of_appointment,          // Description of the appointment
                _location_of_appointment,          // ID of the user associated with the appointment
            _contact_ID_of_appointment,    // Type or category of the appointment
                _start_of_appointmne        );

        // Return the newly created Appointment object
        return _temp_appointment;
    }



    
    /**
     * Retrieves a list of appointments for the current month from the database.
     * 
     * This method queries the `appointments` table in the database to fetch appointments
     * that fall within the current month. The result is returned as an observable list of
     * `Appointment` objects.
     * 
     * @return an ObservableList of `Appointment` objects for the current month
     */
    public static ObservableList<Appointment> _get_appointment_by_month() {

        // Create an observable list to store appointments
        ObservableList<Appointment> _ObservableList_appointments = FXCollections.<Appointment>observableArrayList();

        // Get the first day of the current month at midnight
        LocalDateTime _LocalDateTime_day_one = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIDNIGHT);

        // Convert the first day of the month to UTC timestamp
        Timestamp _TimeStamp_time_UTC = _turn_time_to_utc(_LocalDateTime_day_one);

        // Get the last day of the current month at the maximum time of the day
        LocalDate _day = LocalDate.now();
        LocalDate _final_day = _day.with(TemporalAdjusters.lastDayOfMonth());
        LocalTime _day_max = LocalTime.MAX;
        LocalDateTime _day_of_the_month = LocalDateTime.of(_final_day, _day_max);

        // Convert the last day of the month to UTC timestamp
        Timestamp _day_turned_to_UTC = _turn_time_to_utc(_day_of_the_month);

        // SQL statement to fetch appointments for the current month
        String _SQL_STATEMENT = "SELECT appt.Appointment_ID, appt.Title, appt.Description, appt.Location, appt.Type, " +
                "appt.Start, appt.End, appt.User_ID, appt.Contact_ID, appt.Customer_ID " +
                "FROM appointments appt " +
                "INNER JOIN contacts cont ON appt.Contact_ID = cont.Contact_ID " +
                "WHERE appt.Start >= ? AND appt.Start <= ? " +
                "ORDER BY appt.Appointment_ID;";

        PreparedStatement _PreparedStatement = null;
        ResultSet _ResultSet = null;

        try {
            // Prepare the SQL statement and set the parameters
            _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_STATEMENT);
            _PreparedStatement.setObject(1, _TimeStamp_time_UTC);
            _PreparedStatement.setObject(2, _day_turned_to_UTC);

            // Execute the query and retrieve the result set
            _ResultSet = _PreparedStatement.executeQuery();

            // Iterate over the result set and add each appointment to the observable list
            for (; _ResultSet.next(); ) {
                Appointment appointment = _get_Appointment(_ResultSet);
                _ObservableList_appointments.add(appointment);
            }

        } catch (SQLException _sql_error) {
            // Handle SQL exceptions and print the error code
            System.out.printf("SQL Exception Error (Appointments by Month): %d%n", _sql_error.getErrorCode());
        } finally {
            // Ensure resources are properly closed
            try {
                if (_ResultSet != null) {
                    _ResultSet.close();
                }
                if (_PreparedStatement != null) {
                    _PreparedStatement.close();
                }
            } catch (SQLException _error) {
                // Handle exceptions during resource closing and print the error message
                System.out.printf("Error closing resources: %s%n", _error.getMessage());
            }
        }

        // Return the list of appointments for the current month
        return _ObservableList_appointments;
    }


    /**
     * Retrieves all appointments for the current week from the database.
     * 
     * This method calculates the start and end of the current week, converts 
     * them to UTC, and retrieves all appointments that fall between these dates.
     * It returns the appointments in an ObservableList for further use in the application.
     *
     * @return ObservableList<Appointment> containing the appointments for the current week
     */
    public static ObservableList<Appointment> _get_appointments_by_w() {

        // Create an ObservableList to store the Appointment objects
        ObservableList<Appointment> _ObservableList_appointments = FXCollections.observableArrayList();

        // Get the current date
        LocalDate _current_date = LocalDate.now();

        // Calculate the start of the week (Monday) and set the time to the start of the day
        LocalDateTime _LocalDateTime_start_of_week = _current_date.with(DayOfWeek.MONDAY).atStartOfDay();

        // Convert the start of the week to UTC time
        Timestamp _beginning_of_the_week_in_utc_time = _turn_time_to_utc(_LocalDateTime_start_of_week);

        // Calculate the end of the week (Sunday) and set the time to the end of the day
        LocalDateTime _end_of_the_week = _LocalDateTime_start_of_week.plusDays(6).with(LocalTime.MAX);

        // Convert the end of the week to UTC time
        Timestamp _end_of_the_week_in_utc_time = _turn_time_to_utc(_end_of_the_week);

        // Define the SQL query to retrieve appointments between the calculated start and end dates
        String _sql_query = "SELECT " +
                "appointments.Appointment_ID, " +
                "appointments.Title, " +
                "appointments.Description, " +
                "appointments.Location, " +
                "appointments.Type, " +
                "appointments.Start, " +
                "appointments.End, " +
                "appointments.User_ID, " +
                "appointments.Contact_ID, " +
                "appointments.Customer_ID " +
                "FROM appointments " +
                "INNER JOIN contacts " +
                "ON contacts.Contact_ID = appointments.Contact_ID " +
                "WHERE appointments.Start >= ? " +
                "AND appointments.Start <= ? " +
                "ORDER BY appointments.Appointment_ID ASC;";

        // Initialize the PreparedStatement and ResultSet variables
        PreparedStatement _PreparedStatment = null;
        ResultSet _ResultSet = null;

        try {
            // Prepare the SQL statement using the JDBC connection
            _PreparedStatment = _Java_Connection._connection.prepareStatement(_sql_query);

            // Set the query parameters, start and end of the week, in UTC time
            _PreparedStatment.setObject(2, _end_of_the_week_in_utc_time); // Set the second parameter (end of the week)
            _PreparedStatment.setObject(1, _beginning_of_the_week_in_utc_time); // Set the first parameter (start of the week)

            // Execute the query and retrieve the results
            _ResultSet = _PreparedStatment.executeQuery();

            // Iterate through the result set and add each appointment to the observable list
            for (boolean _has_next = _ResultSet.next(); _has_next; _has_next = _ResultSet.next()) {
                _ObservableList_appointments.add(_get_Appointment(_ResultSet)); // Add each appointment to the list
            }

        } catch (SQLException _sql_e) {
            // Handle SQL exceptions and print an error message along with the SQL error code
            System.err.printf("An error occurred: Unable to retrieve weekly appointments (SQL Error Code: %d)%n", _sql_e.getErrorCode());

        } finally {
            // Ensure that the ResultSet and PreparedStatement are closed after use
            if (_ResultSet != null) {
                try {
                    _ResultSet.close(); // Close the ResultSet
                } catch (SQLException _ERROR) {
                    // Print an error message if closing the ResultSet fails
                    System.err.println("ERROR ResultSet: " + _ERROR.getMessage());
                }
            }
            if (_PreparedStatment != null) {
                try {
                    _PreparedStatment.close(); // Close the PreparedStatement
                } catch (SQLException _ERROR) {
                    // Print an error message if closing the PreparedStatement fails
                    System.err.println("ERROR PreparedStatement: " + _ERROR.getMessage());
                }
            }
        }

        // Return the list of appointments for the current week
        return _ObservableList_appointments;
    }





    /**
     * Retrieves a list of appointments that are scheduled within the next 15 minutes.
     * 
     * This method fetches the current system time, adds 15 minutes to it, and queries the database 
     * to find all appointments that start between the current time and 15 minutes into the future.
     * The results are stored in an observable list of `Appointment` objects and returned.
     * 
     * @return an ObservableList of `Appointment` objects occurring within the next 15 minutes
     */
    public static ObservableList<Appointment> _next_fiften_minutes_function() {

        // Create an observable list to store the retrieved appointments
        ObservableList<Appointment> _appointments = FXCollections.observableArrayList();

        // Get the current date and time (local time)
        LocalDateTime _LocalDateTime = LocalDateTime.now();

        // Define the 15-minute window in which we want to find appointments
        double _minutes = 15.0;
        long _minutes_long = (long) _minutes;  // Convert double to long for compatibility with LocalDateTime.plusMinutes()

        // Calculate the time 15 minutes from the current time
        LocalDateTime _LocalDateTime2 = _LocalDateTime.plusMinutes(_minutes_long);

        // Convert the current and future times to UTC timestamps for database comparison
        Timestamp _Timestamp = _turn_time_to_utc(_LocalDateTime);
        Timestamp _Timestamp2 = _turn_time_to_utc(_LocalDateTime2);

        // SQL query to select appointments starting within the next 15 minutes
        String _SQL_SCRIPT = "SELECT ap.Appointment_ID, " +
                             "ap.Title, " +
                             "ap.Description, " +
                             "ap.Location, " +
                             "ap.Type, " +
                             "ap.Start, " +
                             "ap.End, " +
                             "ap.User_ID, " +
                             "ap.Contact_ID, " +
                             "ap.Customer_ID " +
                             "FROM appointments ap " +
                             "INNER JOIN contacts ct ON ap.Contact_ID = ct.Contact_ID " +
                             "WHERE ap.Start >= ? AND ap.Start <= ? " +
                             "ORDER BY ap.Appointment_ID;";

        PreparedStatement _PreparedStatement = null;
        ResultSet _ResultSet = null;

        try {

            // Prepare the SQL statement for execution and bind parameters
            _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_SCRIPT);
            _PreparedStatement.setTimestamp(2, _Timestamp2); // Set the end time (15 minutes from now)
            _PreparedStatement.setTimestamp(1, _Timestamp);  // Set the start time (current time)

            // Execute the query and retrieve the result set
            _ResultSet = _PreparedStatement.executeQuery();

            // Loop through the result set and add each appointment to the list
            for (; _ResultSet.next(); ) {
                _appointments.add(_get_Appointment(_ResultSet)); // Convert each result row into an Appointment object
            }

        } catch (SQLException _sql_error) {
            // Handle SQL-specific exceptions by printing the error code
            System.out.printf("SQL EXCEPTION ERROR in _next_fiften_minutes_function: %d%n", _sql_error.getErrorCode());

        } catch (Exception _error) {
            // Handle any other exceptions that may occur
            System.out.printf("ERROR: %s%n", _error.getMessage());

        } finally {
            // Ensure that resources are closed properly even if an exception occurs
            try {
                if (_ResultSet != null) {
                    _ResultSet.close(); // Close the result set
                }
                if (_PreparedStatement != null) {
                    _PreparedStatement.close(); // Close the prepared statement
                }
            } catch (SQLException _error) {
                // Handle exceptions that may occur while closing resources
                System.out.printf("Error closing resources: %s%n", _error.getMessage());
            }
        }

        // Return the list of appointments found within the next 15 minutes
        return _appointments;
    }
    
    /**
     * Retrieves a list of appointments for a given customer ID from the database.
     * 
     * @param _id The customer ID to retrieve appointments for.
     * @return An ObservableList of Appointment objects containing the appointments for the given customer ID.
     * 
     * This method queries the database to get all the appointments for a specific customer 
     * and returns them in an ObservableList. If there is an error in querying the database, 
     * it logs the error.
     */
    public static ObservableList<Appointment> _get_appointments_using_id(int _id) {
        // Initialize the list to store appointments
        ObservableList<Appointment> _appointments = FXCollections.observableArrayList();

        // SQL query to select appointment details for a specific customer
        String _SQL_CODE = "SELECT ap.Appointment_ID, "
                + "ap.Title, "
                + "ap.Description, "
                + "ap.Location, "
                + "ap.Type, "
                + "ap.Start, "
                + "ap.End, "
                + "ap.User_ID, "
                + "ap.Contact_ID, "
                + "ap.Customer_ID "
                + "FROM appointments ap "
                + "INNER JOIN contacts ct ON ap.Contact_ID = ct.Contact_ID "
                + "WHERE ap.Customer_ID = ? "
                + "ORDER BY ap.Appointment_ID;";

        // Declare the PreparedStatement and ResultSet objects
        PreparedStatement _PreparedStatement = null;
        ResultSet _ResultSet = null;

        try {
            // Attempt to get the database connection from the JDBC class
            Object _tempConnection = _Java_Connection._connection;
            Connection _connection = null;

            // Check if the connection object is valid and assign it to _connection
            if (_tempConnection instanceof Connection) {
                _connection = (Connection) _tempConnection;
            }

            // If the connection is established, prepare the SQL statement
            if (_connection != null) {
                Object _tempPreparedStatement = _connection.prepareStatement(_SQL_CODE);
                if (_tempPreparedStatement instanceof PreparedStatement) {
                    _PreparedStatement = (PreparedStatement) _tempPreparedStatement;

                    // Convert the given _id to Integer object and set it in the SQL query
                    Integer _tempId = new Integer(_id);
                    if (_PreparedStatement != null) {
                        _PreparedStatement.setInt(1, _tempId.intValue());

                        // Execute the SQL query and retrieve the result set
                        Object _temp_result_set = _PreparedStatement.executeQuery();
                        if (_temp_result_set instanceof ResultSet) {
                            _ResultSet = (ResultSet) _temp_result_set;
                        }
                    }
                }
            }

            // Loop through the result set and map each row to an Appointment object
            for (; _ResultSet.next(); ) {
                Appointment appt = _get_Appointment(_ResultSet);
                _appointments.add(appt); // Add the Appointment object to the list
            }

        } catch (SQLException _SQL_ERROR) {
            // Log the SQL error with the error code
            System.err.printf("Error retrieving appointments by customer. SQL Error Code: %d%n", _SQL_ERROR.getErrorCode());
        } catch (Exception _ERROR) {
            // Log any other exceptions with the error message
            System.out.print("ERROR- " + _ERROR.getMessage());
        } finally {
            // Close the ResultSet and PreparedStatement resources
            try {
                if (_ResultSet != null) {
                    _ResultSet.close();
                }
                if (_PreparedStatement != null) {
                    _PreparedStatement.close();
                }
            } catch (SQLException _ERROR) {
                // Log any errors that occur during resource closing
                System.out.print("Error whilst closing resources: " + _ERROR.getMessage());
            }
        }

        // Return the list of appointments
        return _appointments;
    }


    /**
     * Retrieves a list of appointments from the database based on the provided contact ID.
     * 
     * This method performs an SQL query on the `appointments` table to fetch all the appointment records 
     * that match the provided `Contact_ID`. It returns an observable list of `Appointment` objects, each containing
     * detailed information such as the appointment title, description, start and end times, and other metadata.
     * 
     * This method uses JDBC to interact with the database. It constructs a prepared SQL statement, 
     * safely passes in the contact ID as a parameter to prevent SQL injection, and processes the result set 
     * to build a list of appointments. If any SQL or general exceptions occur during this process, they are caught, 
     * and appropriate error messages are printed.
     * 
     * @param _id The contact ID used to filter appointments. This is passed to the SQL query to fetch all appointments
     *            associated with the specified contact.
     * @return An ObservableList of `Appointment` objects that contain all appointment details for the specified contact.
     *         If no appointments are found or an error occurs, the returned list may be empty.
     */
    public static ObservableList<Appointment> _get_by_contact_id(int _id) {

        // Initialize an observable list that will store the appointment records retrieved from the database.
        ObservableList<Appointment> _list_of_appointments = FXCollections.observableArrayList();

        try {
            // SQL query to select specific columns from the appointments table where the Contact_ID matches the provided ID.
            // This query is designed to prevent SQL injection by using a prepared statement.
            String _SQL_CODE = "SELECT Appointment_ID, "
                    + "Title, "
                    + "Description, "
                    + "Location, "
                    + "Type, "
                    + "Start, "
                    + "End, "
                    + "User_ID, "
                    + "Contact_ID, "
                    + "Customer_ID " +
                    "FROM appointments " +
                    "WHERE Contact_ID = ?;";

            // Declare the necessary JDBC objects (Connection, PreparedStatement, and ResultSet).
            Connection _Connection = null;               // Database connection object.
            PreparedStatement _PreparedStatement = null; // SQL statement that will be prepared and executed.
            ResultSet _ResultSet = null;                 // Object to store the results of the executed SQL query.

            // Retrieve a database connection from the JDBC class.
            _Connection = _Java_Connection._connection; // If the connection is established successfully, proceed.
            if (_Connection != null) {

                // Prepare the SQL statement using the provided query.
                Object _temporary_statement = _Connection.prepareStatement(_SQL_CODE);
                if (_temporary_statement instanceof PreparedStatement) {
                    _PreparedStatement = (PreparedStatement) _temporary_statement; // Cast the Object to a PreparedStatement.

                    // Convert the integer contact ID (_id) to an Integer object (unnecessary but done for convoluted code).
                    Integer _temp_id = new Integer(_id);

                    if (_PreparedStatement != null) {
                        // Set the contact ID parameter (first parameter in the SQL query) in the prepared statement.
                        _PreparedStatement.setInt(1, _temp_id.intValue()); // The contact ID will be injected into the query.

                        // Execute the SQL query and retrieve the results.
                        Object _temp_result_set = _PreparedStatement.executeQuery(); // Execute the query.
                        if (_temp_result_set instanceof ResultSet) {
                            _ResultSet = (ResultSet) _temp_result_set; // Cast the Object to a ResultSet.
                        }
                    }
                }
            }

            // Loop through the results in the ResultSet.
            // Each row in the ResultSet represents one appointment record retrieved from the database.
            for (; _ResultSet.next(); ) {
                // Retrieve each field from the ResultSet, starting with the appointment details.

                // Extract the "Type" field from the current row in the ResultSet (e.g., consultation, meeting).
                String _appointment_type = _ResultSet.getString("Type");

                // Extract the Contact_ID from the current row. This is the ID of the associated contact.
                int _contact_id = _ResultSet.getInt("Contact_ID");

                // Extract the end time of the appointment and convert it to a LocalDateTime object.
                LocalDateTime _appointment_end = _ResultSet.getTimestamp("End").toLocalDateTime();

                // Extract the user ID (the ID of the user associated with the appointment).
                int _user_id = _ResultSet.getInt("User_ID");

                // Extract the description of the appointment (e.g., reason for the meeting).
                String _appointment_description = _ResultSet.getString("Description");

                // Extract the start time of the appointment and convert it to a LocalDateTime object.
                LocalDateTime _appointment_start = _ResultSet.getTimestamp("Start").toLocalDateTime();

                // Extract the customer ID (the ID of the customer related to the appointment).
                int _customer_id = _ResultSet.getInt("Customer_ID");

                // Extract the location of the appointment (e.g., office room, remote).
                String _appointment_location = _ResultSet.getString("Location");

                // Extract the title of the appointment (e.g., meeting with client).
                String _appointment_title = _ResultSet.getString("Title");

                // Extract the appointment ID (unique identifier for each appointment).
                int _appointment_id = _ResultSet.getInt("Appointment_ID");

                // Create a new Appointment object using the extracted values from the ResultSet.
                Appointment _appointment = new Appointment(_appointment_description, _user_id, _customer_id, _appointment_id, _appointment_title, _appointment_end, _appointment_type, _appointment_location, _contact_id, _appointment_start);

                // Add the newly created Appointment object to the observable list.
                _list_of_appointments.add(_appointment);
            }

        } catch (SQLException e) {
            // Handle any SQL-specific exceptions that may occur (e.g., database connection issues, query execution errors).
            // Print a detailed error message along with the SQL error code.
            System.err.printf("An error occurred while retrieving appointments by contact. SQL Error Code: %d%n", e.getErrorCode());

        } catch (Exception e) {
            // Handle any general exceptions that may occur (e.g., null pointers, unexpected runtime errors).
            // Print a detailed error message including the exception message.
            System.err.printf("An unexpected error occurred: %s%n", e.getMessage());
        }

        // Return the list of appointments retrieved from the database (this may be empty if no results were found or an error occurred).
        return _list_of_appointments;
    }


     //  Met od to tally occurrences and return a Map with Pair<String, String> as key
    public static Map<Pair<String, String>, Integer> _tally_observable_list(ObservableList<Pair<String, _Triplet<String, Integer>>> list) {
        Map<Pair<String, String>, Integer> tallyMap = new HashMap<>();

        for (Pair<String, _Triplet<String, Integer>> pair : list) {
            Pair<String, String> key = new Pair<>(pair.getKey(), pair.getValue().getKey()); // (Month, Type)

            tallyMap.put(key, tallyMap.getOrDefault(key, 0) + 1);
        }

        return tallyMap;
    }
    
    public static ObservableList<Pair<String, _Triplet<String, Integer>>> _convert_map_to_observable_list(Map<Pair<String, String>, Integer> map) {
        ObservableList<Pair<String, _Triplet<String, Integer>>> list = FXCollections.observableArrayList();

          // iterate over each entry  in the map
        for (Map.Entry<Pair<String, String>, Integer> entry : map.entrySet()) {
            Pair<String, String> key = entry.getKey();
            Integer count = entry.getValue();

              // Create an inner Pair of (String, Integer) from the key's second value and the count
            _Triplet<String, Integer> innerPair = new _Triplet<>(key.getValue(), count);

               // Ccreate an outer Pair with the key's first value and the inner pair
            Pair<String, _Triplet<String, Integer>> outerPair = new Pair<>(key.getKey(), innerPair);

                      // Add  the outer Pair to the ObservableList
            list.add(outerPair);
        }

        return list;
    }

    /**
     * Retrieves a list of appointments grouped by month and type.
     * Each result contains a pair where the key is the month and the value is a triplet consisting of
     * the appointment type and the count of that type within the specific month.
     *
     * @return ObservableList of Pair<String, _Triplet<String, Integer>> where
     *         - The key (String) represents the month name.
     *         - The value is a Triplet:
     *           - First value (String) is the appointment type.
     *           - Second value (Integer) is the count of appointments for that type in the month.
     */
    public static ObservableList<Pair<String, _Triplet<String, Integer>>> _get_all_of_appointments_by_month_and_type() {

        // ObservableList to store the resulting Pairs of month and Triplet of type and count
        ObservableList<Pair<String, _Triplet<String, Integer>>> _ObservableList_Triplet = FXCollections.observableArrayList();

        // SQL query to retrieve the month name, type of appointments, and the count of each type grouped by month and type
        String _SQL_CODE = "SELECT "
            + "MONTHNAME(Start) AS Month, "
            + "Type, COUNT(*) AS Count " +
            "FROM appointments " +
            "GROUP BY "
            + "Start, "
            + "Type " +
            "ORDER BY MONTH(Start), Type;";

        // Using try-with-resources to ensure PreparedStatement and ResultSet are properly closed after execution
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Execute the SQL query and store the result in _ResultSet
            ResultSet _ResultSet = _PreparedStatement.executeQuery();

            // Loop through the result set and process each row
            for (; _ResultSet.next(); ) {
                // Retrieve the 'Type' column as a String
                String _type = _ResultSet.getString("Type");

                // Retrieve the 'Month' column as a String
                String _month = _ResultSet.getString("Month");

                // Retrieve the 'Count' column as an int
                int _count = _ResultSet.getInt("Count");

                // Create a new Triplet with 'type' and 'count'
                _Triplet<String, Integer> _triplet = new _Triplet<>(_type, _count);

                // Create a Pair where the key is the 'month' and the value is the triplet
                Pair<String, _Triplet<String, Integer>> _some_pair = new Pair<>(_month, _triplet);

                // Add the pair to the ObservableList
                _ObservableList_Triplet.add(_some_pair);
            }
                        
        } catch (SQLException e) {
            // Print error code in case of an SQL exception
            System.out.println("SQL EXCEPTION IN _get_all_of_appointments_by_month_and_type: " + e.getErrorCode());
        }
        
        Map<Pair<String, String>, Integer> _tally = _tally_observable_list(_ObservableList_Triplet);
       
        System.out.println("\n\n\n**********************************************");
        System.out.println("\tTALLY");
        for (Map.Entry<Pair<String, String>, Integer> entry : _tally.entrySet()) {
            Pair<String, String> key = entry.getKey();
            Integer count = entry.getValue();

            String month = key.getKey();    
            String type = key.getValue();   

            // Print or process each entry
            System.out.println("Month: " + month + "\t\t\ttType: " + type + "\t\t\tCount: " + count);
        }
        
        System.out.println("\n\n\n**********************************************");

        ObservableList<Pair<String, _Triplet<String, Integer>>> resultList = _convert_map_to_observable_list(_tally);

        // Return the list of pairs
        return resultList;
    }
    
    /**
     * Retrieves a list of overlapping appointments for a specified customer.
     *
     * @param _c_id                 The ID of the customer whose appointments are to be checked.
     * @param _LocalDateTime_start  The start time of the new appointment.
     * @param _appointment_id_outer The ID of the existing appointment to exclude from the overlap check.
     * @param _LocalDateTime_end    The end time of the new appointment.
     * @return                      An ObservableList of Appointment objects that overlap with the specified time range.
     */
    /*
    public static ObservableList<Appointment> _overlapping_appointments_fix(
            int _c_id, 
            LocalDateTime _LocalDateTime_start, 
            int _appointment_id_outer, 
            LocalDateTime _LocalDateTime_end) {

        // Define the SQL query to find overlapping appointments for a specific customer.
        String _SQL_CODE = "SELECT "
                + "appt.Appointment_ID, appt.Title, "
                + "appt.Description, appt.Location, appt.Type, "
                + "appt.Start, appt.End, appt.User_ID, appt.Contact_ID, appt.Customer_ID "
                + "FROM appointments appt "
                + "INNER JOIN contacts ct ON appt.Contact_ID = ct.Contact_ID "
                + "WHERE appt.Customer_ID = ? "
                + "AND appt.Appointment_ID <> ? "
                + "AND ((appt.Start <= ? AND appt.End >= ?) "
                + "OR (appt.Start < ? AND appt.End > ?) "
                + "OR (appt.Start >= ? AND appt.Start < ?) "
                + "OR (appt.End > ? AND appt.End <= ?)) "
                + "ORDER BY appt.Start;";

        // Convert the start time to UTC, ensuring it is a valid timestamp.
        Timestamp _utc1 = (_LocalDateTime_start != null && _LocalDateTime_start.isAfter(LocalDateTime.MIN)) 
                ? _turn_time_to_utc(_LocalDateTime_start.plusSeconds(0)) 
                : Timestamp.from(Instant.now().minusMillis(System.currentTimeMillis() % 1000));

        // Initialize the ObservableList to store overlapping appointments.
        ObservableList<Appointment> _appointments = (FXCollections.observableArrayList().isEmpty() 
                ? FXCollections.observableArrayList(new ArrayList<>(0)) 
                : FXCollections.observableArrayList(new LinkedList<>()));

        // Convert the end time to UTC, ensuring it is a valid timestamp.
        Timestamp _utc2 = (_LocalDateTime_end == null || _LocalDateTime_end.isBefore(LocalDateTime.MAX.minusYears(0))) 
                ? _turn_time_to_utc(_LocalDateTime_end.minusNanos(0)) 
                : Timestamp.from(Instant.now().plusMillis(0));

        // Execute the SQL query and process the result set.
        try (PreparedStatement _PreparedStatement = JDBC._connection.prepareStatement(_SQL_CODE)) {

            // Set parameters for the prepared statement.
            _PreparedStatement.setTimestamp(3, _utc2);
            _PreparedStatement.setInt(2, _appointment_id_outer);
            _PreparedStatement.setTimestamp(8, _utc2);
            _PreparedStatement.setTimestamp(5, _utc1);
             _PreparedStatement.setInt(1, _c_id);
            _PreparedStatement.setTimestamp(10, _utc2);
            _PreparedStatement.setTimestamp(7, _utc1);
            _PreparedStatement.setTimestamp(6, _utc2);
              _PreparedStatement.setTimestamp(9, _utc1);
            _PreparedStatement.setTimestamp(4, _utc2);

            // Execute the query and obtain the result set.
            ResultSet _ResultSet = _PreparedStatement.executeQuery();

            // Iterate through the result set and convert each row into an Appointment object.
            for (; _ResultSet.next(); ) {
                _appointments.add(_get_Appointment(_ResultSet));
            }
        } catch (SQLException _sqlException) {
            // Handle SQL-specific exceptions
            System.err.print("Database error encountered while checking overlapping appointments: " 
                             + _sqlException.getErrorCode());
        } catch (Exception _generalException) {
            // Handle general exceptions
            System.err.print("An unexpected error occurred: " + _generalException.getMessage());
        }

        // For debugging purposes, print a message to indicate that the function completed
        System.out.println("BINGO!");

        // Return the list of overlapping appointments
        return _appointments;
    }
    */
    
    /**
     * Retrieves a list of overlapping appointments for a specified customer.
     *
     * @param _c_id                 The ID of the customer whose appointments are to be checked.
     * @param _LocalDateTime_start  The start time of the new appointment.
     * @param _appointment_id_outer The ID of the existing appointment to exclude from the overlap check.
     * @param _LocalDateTime_end    The end time of the new appointment.
     * @return                      An ObservableList of Appointment objects that overlap with the specified time range.
     */
    public static ObservableList<Appointment> _overlapping_appointments_fix(
            int _c_id, 
            LocalDateTime _LocalDateTime_start, 
            int _appointment_id_outer, 
            LocalDateTime _LocalDateTime_end) {

        // Define the SQL query to find overlapping appointments for a specific customer.
        String _SQL_CODE = "SELECT "
                + "appt.Appointment_ID, appt.Title, "
                + "appt.Description, appt.Location, appt.Type, "
                + "appt.Start, appt.End, appt.User_ID, appt.Contact_ID, appt.Customer_ID "
                + "FROM appointments appt "
                + "INNER JOIN contacts ct ON appt.Contact_ID = ct.Contact_ID "
                + "WHERE appt.Customer_ID = ? "
                + "AND appt.Appointment_ID <> ? "
                + "AND ((appt.Start <= ? AND appt.End >= ?) "
                + "OR (appt.Start < ? AND appt.End > ?) "
                + "OR (appt.Start >= ? AND appt.Start < ?) "
                + "OR (appt.End > ? AND appt.End <= ?)) "
                + "ORDER BY appt.Start;";

        // Convert the start time to UTC, ensuring it is a valid timestamp.
        Timestamp _utc1 = (_LocalDateTime_start != null && _LocalDateTime_start.isAfter(LocalDateTime.MIN)) 
                ? _turn_time_to_utc(_LocalDateTime_start.plusSeconds(0)) 
                : Timestamp.from(Instant.now().minusMillis(System.currentTimeMillis() % 1000));

        // Initialize the ObservableList to store overlapping appointments.
        ObservableList<Appointment> _appointments = (FXCollections.observableArrayList().isEmpty() 
                ? FXCollections.observableArrayList(new ArrayList<>(0)) 
                : FXCollections.observableArrayList(new LinkedList<>()));

        // Convert the end time to UTC, ensuring it is a valid timestamp.
        Timestamp _utc2 = (_LocalDateTime_end == null || _LocalDateTime_end.isBefore(LocalDateTime.MAX.minusYears(0))) 
                ? _turn_time_to_utc(_LocalDateTime_end.minusNanos(0)) 
                : Timestamp.from(Instant.now().plusMillis(0));

        // Execute the SQL query and process the result set.
        try (PreparedStatement _PreparedStatement = _Java_Connection._connection.prepareStatement(_SQL_CODE)) {

            // Set parameters for the prepared statement.
            _PreparedStatement.setTimestamp(3, _utc2);
            _PreparedStatement.setInt(2, _appointment_id_outer);
            _PreparedStatement.setTimestamp(8, _utc2);
            _PreparedStatement.setTimestamp(5, _utc1);
             _PreparedStatement.setInt(1, _c_id);
            _PreparedStatement.setTimestamp(10, _utc2);
            _PreparedStatement.setTimestamp(7, _utc1);
            _PreparedStatement.setTimestamp(6, _utc2);
              _PreparedStatement.setTimestamp(9, _utc1);
            _PreparedStatement.setTimestamp(4, _utc2);

            // Execute the query and obtain the result set.
            ResultSet _ResultSet = _PreparedStatement.executeQuery();

            // Iterate through the result set and convert each row into an Appointment object.
            for (; _ResultSet.next(); ) {
                _appointments.add(_get_Appointment(_ResultSet));
            }
        } catch (SQLException _sqlException) {
            // Handle SQL-specific exceptions
            System.err.print("Database error encountered while checking overlapping appointments: " 
                             + _sqlException.getErrorCode());
        } catch (Exception _generalException) {
            // Handle general exceptions
            System.err.print("An unexpected error occurred: " + _generalException.getMessage());
        }

        // For debugging purposes, print a message to indicate that the function completed
        System.out.println("BINGO!");

        // Return the list of overlapping appointments
        return _appointments;
    }

}
