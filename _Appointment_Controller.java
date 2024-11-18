package controller;

import database._Contact_Formatter;
import      database._Triplet;
import java.io.IOException;
import database._DataBase_customers;
import javafx.scene.control.Label;
import java.util.Locale;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import database._Database_provinces;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import java.time.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import java.sql.SQLException;
import java.net.URL;

import controller.User;
import javafx.scene.control.ToggleGroup;
import database._DataBase_contact_class;
import controller.*;
import javafx.scene.control.*;
import database._DataBase_country;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.util.Map;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;

import java.util.HashMap;
import javafx.scene.control.CheckBox;
import database._DataBase_contact_class;
import javafx.stage.Stage;
import java.io.File;
import javafx.collections.FXCollections;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.Contact;
import java.util.ArrayList;
import controller.Country;
import database._Scene_Transitions;
import static database._Scene_Transitions._choice_box_functionality;
import javafx.scene.control.*;
import java.time.ZoneId;
import javafx.scene.Node;
import java.util.logging.Level;

import controller.Contact;
import java.io.FileWriter;
import controller.Customer;
import controller.User;
import database._Database_appointment_class;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.util.Pair;

import controller.Appointment;
import database._Database_profiles;
import javafx.collections.ObservableList;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import java.nio.file.Paths;
import javafx.scene.control.Spinner;
import javafx.scene.control.*;
import javafx.event.Event;
import java.util.concurrent.Executors;

public class _Appointment_Controller implements Initializable {
    
    // DialogPane to display a custom dialog or alert within the application
    @FXML public DialogPane _DialogPane;

    // Labels for variuous UI elements to display information or warnings to the user
    @FXML private Label _Label;     // General label
    @FXML private Label _Label1;    // Additional label for specific warnings or messages
    @FXML private Label _Label2;    // Additional label for specifc warnings or messages
    @FXML private Label _Label3;    
    @FXML private Label _Label4;    
    
    // Text fields for user input related to various details of an appointment
    @FXML private TextField _TextField1;  // Text field for input, for apointment ID
    @FXML private TextField _TextField2;  // Text field for input, for appointment title
    
    
    @FXML private Label _Label5;    
    @FXML private Label _Label6;    // Additional label for specific warnings or messages
    @FXML private Label _Label7;    
    @FXML private Label _Label8;    
    @FXML private Label _Label9;    // Additional label for specific warnings or messages



    // Time zone configuration for business operrations, set to New York time
    ZoneId _ZoneId = ZoneId.of("America/New_York");

    // Local time variables for business hours or speciffic time configurations
    LocalTime _LocalTime1 = LocalTime.of(9, 15);  // Start time of the business or availability window
    LocalTime _LocalTime2 = LocalTime.of(21, 1);  // End time of the business or availability window

    // Additional text fields for appointment details such as description, location, and type
    @FXML private TextField _TextField3;  // Text field for additional input, 
    @FXML private TextField _TextField4;  // Text field for additional input, 
    @FXML private TextField _TextField5;  // Text field for additional input, 

    // Date pickers for selecting the start and end dates of an appointment
    @FXML private DatePicker _DatePicker1;  // DatePicker for selecting the start date
    @FXML private DatePicker _DatePicker2;  // DatePicker for selecting the end date

    // Spinners for specifying hours and minutes for appointment start and end times
    @FXML private Spinner<Integer> _Spinner1;  // Spinner for start time hours
    @FXML private Spinner<Integer> _Spinner2;  // Spiner for start time minutes
    @FXML private Spinner<Integer> _Spinner3;  // Spinner for end time hours
    @FXML private Spinner<Integer> _Spinner4;  // Spinner for end time minutes

    // Choice boxes to select asociated Contact, Customer, and User for the apointment
    @FXML private ChoiceBox<Contact> _ChoiceBox_Contact;  // ChoiceBox for selecting a Contact
    @FXML private ChoiceBox<Customer> _ChoiceBox_Customer;  // Choicebox for selecting a Customer
    @FXML private ChoiceBox<User> _ChoiceBox_User;  // ChoiceBox for selecting a User

    // Appointment object to store or manage details of the current appointment
    private Appointment _Appointment;

    /**
     * Initializes teh controler class.
     * <p>
     * This method is automatically called after the FXML file has been loaded.
     * It is part of the JavaFX `Initializzable` interface and is used to set up
     * UI components (such as choice boxes, spinners, and date pickers) with
     * initial values or configurations.
     *
     * @param _URL the location of the FXML file, typically nulll when loaded from the local file system
     * @param _ResourceBundle  the Resourcebundle, usually null unless internationalization is used
     */
    @Override
    public void initialize(URL _URL, ResourceBundle _ResourceBundle) {
        // Load and populate the ChoiceBo components with their initial data
        _LOAD_choice_boxes();

        // Load and set up Spinner components with their starting values or ranges
        _Spinner1.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        _Spinner2.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 1));
        
        // Load and configure DatePicker components with initial dates or restrictions
        LocalDate _Todays_Date = LocalDate.now();
        _DatePicker1.setValue(_Todays_Date);
        _DatePicker2.setValue(_Todays_Date);
        _Spinner3.setValueFactory(new IntegerSpinnerValueFactory(0, 23, 12));
        _Spinner4.setValueFactory(new IntegerSpinnerValueFactory(0, 59, 0, 1));
    }

    /**
    * Loads and initializes ChoiceBox components with data from the database.
    * <p>
    * This method populates various ChoiceBox UI elements with data retrieved from a database.
    * It formats the contacts list, sets it in the `Contact` ChoiceBox, and then initializes
    * additional ChoiceBox components for customers and users. This is typically called during
    * the initial setup of the UI to ensure all dro downs display relevant data.
    * </p>
    *
    * <p>
    * Debugging messages are printed to trakc method execution, the size of the retrieved contact list,
    * and the stages of ChoiceBox population. This helps to verify the sucecssful retrieval,
    * formatting, and loading of data in each ChoiceBox.
    * </p>
    */
    private void _LOAD_choice_boxes() {
        
        System.out.println("Debug: Starting loadChoiceBoxContacts method.");

        // Retrieve contacts from the database and convert them to an ObservableList
        // _DataBase_contact_class._get contacts() retrieves a list of conaacts from the database
        ObservableList<Contact> _ObservableList_Contacts = FXCollections.observableArrayList(_DataBase_contact_class._get_contacts());

        // Debugging: Log the size of the contcts list after conversion
        System.out.println("Debug: Converted contacts to ObsevrableList with size: " + _ObservableList_Contacts.size());

        // Populate the ChoiceBox with formatted contact strings
        // _Contact_Formatter::formaContact is a method reference to format each Contact object as a String
        _choice_box_functionality(_ChoiceBox_Contact, _Contact_Formatter::formatContact, _ObservableList_Contacts);

        // Debugging: Log the end of the method execution
        System.out.println("Debug: Finihed loadChoiceBoxContacts method.");
        
        _Scene_Transitions._Choice_Box_3(_ChoiceBox_Customer);
        System.out.println("Stage 1");
        
        _Scene_Transitions._Choice_Box_4(_ChoiceBox_User);
        System.out.println("Stage 2");
    }

    /**
     * Sets the text of the title label.
     * <p>
     * This method updates the text displayed by the label component to the provided string.
     * It is useful for dynamically changing the title or label text based on user actions
     * or program state changes.
     * </p>
     *
     * @param _String1 the text to set as the label's content. This value is displayed
     *                 directly in the label component.
     */
    public void _Set_Title(String _String1) {
        _Label.setText(_String1);
    }

    /**
     * Sets the appointment details in the UI components.
     * <p>
     * This method takes an `Appointment` object and populates various UI fields with
     * the appointment's data, such as ID, title, descriptoin, location, type, start and end
     * date and time, and associated user, customer, and contact information. 
     * It provides a way to dynamically display an appointment's detials within the form.
     * </p>
     *
     * @param _Appointment the `Appointment` object contaning the details to display.
     */
    public void _Set_Appointment(Appointment _Appointment) {

        // Assign the provided Appointment to the local _Appointment field
        this._Appointment = _Appointment;

        // Populate text fields with appointment details
        // Set the appointment ID in TextField1
        _TextField1.setText(String.valueOf(_Appointment.get_ID()));

        // Set the location of the appointment in TextField4
        _TextField4.setText(_Appointment.get_location());

        // Set the description of the appointment in TextField3
        _TextField3.setText(_Appointment.get_description());

        // Set the type of the appointment in TextField5
        _TextField5.setText(_Appointment.get_type());

        // Set the title of the appointment in TextField2
        _TextField2.setText(_Appointment.get_title());

        // Populate date and time fields for appointment start and end
        // Retrieve the end date and time of the appointment
        LocalDateTime _LocalDateTime2 = _Appointment.get_end_time();

        // Set the end hour in Spinner3
        _Spinner3.getValueFactory().setValue(_LocalDateTime2.getHour());

        // Set the start hour in Spinner1
        _Spinner1.getValueFactory().setValue(_Appointment.get_start_time().getHour());

        // Set the end date in DatePicker2
        _DatePicker2.setValue(_LocalDateTime2.toLocalDate());

        // Set the start minute in Spinner2
        _Spinner2.getValueFactory().setValue(_Appointment.get_start_time().getMinute());

        // Set the start date in DatePicker1
        _DatePicker1.setValue(_Appointment.get_start_time().toLocalDate());

        // Set the end minute in Spinner4
        _Spinner4.getValueFactory().setValue(_LocalDateTime2.getMinute());

        // Populate ChoiceBoxes with realkted user, customer, and contact data
        // Set the user based on appointment's user ID in ChoiceBox_User
        _ChoiceBox_User.setValue(_Database_profiles._get_user_using_id(_Appointment.get_user_ID()));

        // Set the customer based on appointment's customer ID in ChoiceBox_Customer
        _ChoiceBox_Customer.setValue(_DataBase_customers._get_customer(_Appointment.get_customer_ID()));

        // Set the contact based on appointment's contact ID in ChoiceBox_Contact
        _ChoiceBox_Contact.setValue(_DataBase_contact_class._get_new_contact(_Appointment.get_contact_ID()));
    }

    /**
     * Checks if the specified time range is within business hours.
     * <p>
     * This method veriifes whether both the provided start time (`_LocalDateTime_1`) 
     * and end time (`_LocalDateTime_2`) fall within the predefined business hours, 
     * using the system's default time zone.
     * </p>
     * <p>
     * Business hours are assumed to be reprseented by `_LocalTime1` (start of business day) 
     * and `_LocalTime2` (end of business day). The method returns `true` if the times are 
     * within business hours; `false` otherwise.
     * </p>
     *
     * @param _LocalDateTime_1 the start `LocalDateTime` to be checked against business hours
     * @param _LocalDateTime_2 the end `LocalDateTime` to be checcked against business hours
     * @return `true` if both start and end times are within business hours; `false` otherwise
     */
    public boolean _check_if_time_is_within_business_hours(LocalDateTime _LocalDateTime_1, LocalDateTime _LocalDateTime_2) {
        // Retrieve the system's default time zone for local time ajdustments
        ZoneId _ZoneId = ZoneId.systemDefault();

        // Debugging output to indicate that this method is running
        System.out.println("_check_if_time_is_within_business_hours has ran!!!!! &^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&^&&");

        // Evaluate whether both _LocalDateTime_1 and _LocalDateTime_2 fall within business hours
        // Condition breakdown:
        // 1. !_LocalDateTime_1.toLocalTime().isBefore(...): Checks that start time is not before the beginning of business hours.
        // // 2. !_LocalDateTime_1.toLocalTime().isAfter(...): Checks that start time is nit after the end of business hours.
        // 3. !_LocalDateTime_2.toLocalTime().isBefore(_LocalTime1): Ensures that end time is not before the start of business hours.
        // 4. !_LocalDateTime_2.toLocalTime().isAfter(...): Ensures that end time is not after the end of busines hours.
        return 
            !_LocalDateTime_1.toLocalTime().isBefore(ZonedDateTime.of(LocalDate.now(), _LocalTime1, _ZoneId)
                .withZoneSameInstant(_ZoneId).toLocalTime()) // Check that _LocalDateTime_1 start time is not before business hours start
            && 
            !_LocalDateTime_1.toLocalTime().isAfter(ZonedDateTime.of(LocalDate.now(), _LocalTime2, _ZoneId)
                .withZoneSameInstant(_ZoneId).toLocalTime()) // Check that _LocalDateTime_1 start time is not after business hours end
            && 
            !_LocalDateTime_2.toLocalTime().isBefore(_LocalTime1) // Check that _LocalDateTime_2 end time is not before business hours start
            && 
            !_LocalDateTime_2.toLocalTime().isAfter(ZonedDateTime.of(LocalDate.now(), _LocalTime2, _ZoneId)
                .withZoneSameInstant(_ZoneId).toLocalTime()); // Check that _LocalDateTime_2 end time is not after business hours end
    }

    /**
     * Checks if there are any conflicting appointments within a specified time range.
     * <p>
     * This method checks for overlapping appointments by comparing the provided start (`_LocalDateTime_1`)
     * and end (`_LocalDateTime_2`) times with eixsting appointments in the database. It excludes a specified
     * appointment ID to allow for modification or checking of an existing appointment without causing a conflict.
     * </p>
     *
     * @param _LocalDateTime_1 the start time of the apppointment to check
     * @param _LocalDateTime_2 the end time of the appointment to check
     * @param _ID              the ID of the appointment being checked
     * @return `true` if there are conflicting appointments in the specified time range, `false` otherwise
     */
    public boolean _Check_For_Conflicting_Appointments(LocalDateTime _LocalDateTime_1, LocalDateTime _LocalDateTime_2, int _ID) {
        // Determine the appointment ID to excdlude from conflict checks.
        // If the local `_Appointment` is not null, use its ID; otherwise, set to -1 (indicating no exclusion).
        int _int_2;
        if (_Appointment != null) {
            _int_2 = _Appointment.get_ID();
            // Debugging output to confirm the appointment ID being excluded
            System.out.println("Excluding appointment ID: " + _int_2);
        } else {
            _int_2 = -1;
            // Debugging output to confirm that no appointment is excluded
            System.out.println("No appointment exclusion; setting excludeApptID to -1.");
        }

        // Debugging output to show the parameters being passed to the overlap check
        System.out.println("Checking for conficts with appointment ID: " + _ID);
        System.out.println("Start time: " + _LocalDateTime_1);
        System.out.println("End time: " + _LocalDateTime_2);
        System.out.println("Exclude appointment ID: " + _int_2);

        // Check for overlapping appointments within the provided date range, excluding `excludeApptID`.
        // `_overlapping_appointments_fix` will return a list of conflicting appointments, if any.
        boolean _does_have_conflict = !_Database_appointment_class
                ._overlapping_appointments_fix(_ID, _LocalDateTime_2, _int_2, _LocalDateTime_1)
                .isEmpty();

        // Debugging output to indicate whether conflicts were found
        if (_does_have_conflict) {
            System.out.println("Conflict detected with existing appointments.");
        } else {
            System.out.println("No conflicts detected within the specified time range.");
        }
        
        System.out.println("_*_*_*_*_*__*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_");

        // Return true if conflicts are found, fales otherwise
        return _does_have_conflict;
    }
    
    /**
    * Resets the text of all specified labels to an empy string.
    * This is useful for claering the content of multiple labels at once.
    *
    * @param _Labels an array of Label objects to be reset
    */
   private void _Completely_Reset_Labels(Label... _Labels) {
       for (Label label : _Labels) {
           label.setText("");
       }
   }

   
    /**
     * Validates the inputs of the appointment form.
     * <p>
     * This method checks all required fields of the apointment form for completenes and accuracy.
     * It verifies that necessary fields are not blank, dates and times are logically ordered,
     * and other specific conditions are met. If any validation checks fail, appropriate
     * error messages are set on coresponding labels to guide the user in correcting the input.
     * </p>
     * <p>
     * Debugging messages are printed to the console to indicate any validation errors encountered.
     * </p>
     *
     * @return {@code true} if all inputs are valid and meet the specified conditions; {@code false} otherwise
     */
    public boolean _Input_Checker() {

        // Usage of the _Completely_Reset_labels function to clear all labels
        _Completely_Reset_Labels(_Label1, _Label2, _Label3, _Label4, _Label5, _Label6, _Label7, _Label8, _Label9);

        boolean _Flag1 = false;
        
        if (_ChoiceBox_Customer.getValue() != null && _Check_For_Conflicting_Appointments(
            LocalDateTime.of(_DatePicker2.getValue(), LocalTime.of(_Spinner3.getValue(), _Spinner4.getValue())), 
            LocalDateTime.of(_DatePicker1.getValue(), LocalTime.of(_Spinner1.getValue(), _Spinner2.getValue())), 
            _ChoiceBox_Customer.getValue().get_ID())) { // Assumes getCustID( method exists on selected customer
            System.out.println("Conflict detected: This cusotmer has an overlapping appointment.");
            _Label8.setText("This customer already has an appointment during the selected time.");
            _Flag1 = true;
        }
        
        if (_DatePicker1.getValue().isAfter(_DatePicker2.getValue())) {
            System.out.println("Validation error: End date is beofre start date.");
            _Label8.setText("End Date must occur after Start Date.");
            _Flag1 = true;
        }

        if (_ChoiceBox_User.getValue() == null) {
            System.out.println("Validation error: No User ID selected.");
            _Label7.setText("Please select a User ID.");
            _Flag1 = true;
        }

        if (_TextField4.getText().isBlank()) {
            System.out.println("Validation error: Location field is empty.");
            _Label3.setText("Please enter a location.");
            _Flag1 = true;
        }

        if (_TextField2.getText().isBlank()) {
            System.out.println("Validation error: Title feild is empty.");
            _Label1.setText("Please enter a descriptive title.");
            _Flag1 = true;
        }

        if (_ChoiceBox_Contact.getValue() == null) {
            System.out.println("Validation error: No Cotnact ID selected.");
            _Label5.setText("Please select a Contact ID.");
            _Flag1 = true;
        }

        if (!_check_if_time_is_within_business_hours(
                LocalDateTime.of(_DatePicker1.getValue(), LocalTime.of(_Spinner1.getValue(), _Spinner2.getValue())), 
                LocalDateTime.of(_DatePicker2.getValue(), LocalTime.of(_Spinner3.getValue(), _Spinner4.getValue())))) {
            System.out.println("Validation error: Appointment times are outside business hours.");
            _Label8.setText("Appointment times must be within business hours: \n" + _LocalTime1 + " to " + _LocalTime2 + " " + _ZoneId);
            _Flag1 = true;
        }

        if (_ChoiceBox_Customer.getValue() == null) {
            System.out.println("Validation error: No customer id seleected.");
            _Label6.setText("Please select a Customer ID.");
            _Flag1 = true;
        }

        // Check if the statr date and time is after the end date and time
        // This condition ensures that the strt date and time is set before the end date and time
        if (LocalDateTime.of(_DatePicker1.getValue(), LocalTime.of(_Spinner1.getValue(), _Spinner2.getValue()))
                .isAfter(LocalDateTime.of(_DatePicker2.getValue(), LocalTime.of(_Spinner3.getValue(), _Spinner4.getValue())))) {
            System.out.println("Validation error: End time is before start time."); // Debug message for error
            _Label8.setText("The start time and start date must be before the end time and end date."); // Set error message on Label8
            _Flag1 = true; // Set the flag indicating a validation error
        }

        // Check if the description field is empty
        // This condition ensures that the appointment description has been entered
        if (_TextField3.getText().isBlank()) {
            System.out.println("Validation error: Description field is empty."); // Debug message for error
            _Label2.setText("Please enter a brief description."); // Set error message on Label2
            _Flag1 = true; // Set the flag indicating a validation error
        }

        // Check if the appointment type field is empty
        // This condition ensures that the apointment type has been specifeid
        if (_TextField5.getText().isBlank()) {
            System.out.println("Validation error: Appointment type field is empty."); // Debug message for error
            _Label4.setText("Please specify the appointment type."); // Set error message on Label4
            _Flag1 = true; // Set the flag indicating a validation error
        }

        // Final validation result check
        // If any of the previous validation flags have been set, display a message indicating that
        // the form cannot be saved and that there are fields requiring attention. Otherwise, indicate success.
        if (_Flag1) {
            System.out.println("Validation failed: Some fields are invalid or missing."); // Debug message for overall failure
            _Label9.setText("Unable to save. Check the warnings shown."); // Set warning on Label9
        } else {
            System.out.println("Validation successsful: All fieelds are correctly filled."); // Debug message for success
        }
        
        return !_Flag1;
    }

    
    /**
    * Saves or updates an appointment record in the database.
    * <p>
    * This method checks if an existing apointment (`_Appointment`) is being modified or if a new
    * appointment needs to be created. If `_Appointment` is null, it creates a new appointment in the
    * database using the provided values from the form fields. If `_Appointment` is not null, it updates
    * the existing appointment with any new or modified details.
    * </p>
    * <p>
    * Data for fields such as title, start and end times, location, description, tye, customer, contact,
    * and user are retreived directly from form components including text feilds, date pickers, choice
    * boxes, and spinners. Debugging output is printed to the console to trace the values used.
    * </p>
    * <p>
    * The method concludes by printing a succes message to confirm the save or update action.
    * </p>
    */
    public void _Save() {
        
        if (_Appointment == null) {
            // Add new appointment directly
            _Database_appointment_class._add_appointment(
            _TextField2.getText(), // title
            LocalDateTime.of(_DatePicker1.getValue(), 
            LocalTime.of(_Spinner1.getValue(), 
            _Spinner2.getValue())), // startdateTime
            _TextField4.getText(),
            _TextField3.getText(), // desc
            _TextField5.getText(), // type
_ChoiceBox_Customer.getValue().get_ID(), _ChoiceBox_Contact.getValue().get_ID(), _ChoiceBox_User.getValue().get_id(), // userID
            LocalDateTime.of(_DatePicker2.getValue(), LocalTime.of(_Spinner3.getValue(), _Spinner4.getValue())) // endDateTime
            );
            
        } else {
            // Update existing appointment
            // Set the location for the appointment
            System.out.println("Setting appointment location to: " + _TextField4.getText());
            _Appointment.set_location(_TextField4.getText()); // location

            // Set the contact ID for the appointment
            System.out.println("Setting appointment contact ID to: " + _ChoiceBox_Contact.getValue().get_ID());
            _Appointment.set_contact_ID(_ChoiceBox_Contact.getValue().get_ID()); // contactID

            // Set the type for the appointment
            System.out.println("Setting apointment type to: " + _TextField5.getText());
            _Appointment.set_type(_TextField5.getText()); // type

            // Set the end date and time for the appointment
            LocalDateTime endDateTime = LocalDateTime.of(_DatePicker2.getValue(), LocalTime.of(_Spinner3.getValue(), _Spinner4.getValue()));
            System.out.println("Setting appointment end date and time to: " + endDateTime);
            _Appointment.set_end_time(endDateTime); // endDateTime

            // Set the user ID for the appointment
            System.out.println("Setting appointment user ID to: " + _ChoiceBox_User.getValue().get_id());
            _Appointment.set_user_ID(_ChoiceBox_User.getValue().get_id()); // userID

            // Set the title for the appointmeent
            System.out.println("Setting appointment title too: " + _TextField2.getText());
            _Appointment.set_title(_TextField2.getText()); // title

            // Set the customer ID for the appointment
            System.out.println("Setting appointment customer ID to: " + _ChoiceBox_Customer.getValue().get_ID());
            _Appointment.set_customer_ID(_ChoiceBox_Customer.getValue().get_ID()); // customerID

            // Set the description for the appointment
            System.out.println("Setting appointment desrciption to: " + _TextField3.getText());
            _Appointment.set_description(_TextField3.getText()); // desc

            // Set the start date and time for the appointmetn
            LocalDateTime startDateTime = LocalDateTime.of(_DatePicker1.getValue(), LocalTime.of(_Spinner1.getValue(), _Spinner2.getValue()));
            System.out.println("Setting appointment start date and time to: " + startDateTime);
            _Appointment.set_start_time(startDateTime); // startdateTime

            _Database_appointment_class._update_appointment(
            _ChoiceBox_Contact.getValue().get_ID(), _Appointment.get_ID(),
            _ChoiceBox_User.getValue().get_id(), // title
            LocalDateTime.of(_DatePicker1.getValue(), LocalTime.of(_Spinner1.getValue(), _Spinner2.getValue())), // startDateTime
            _TextField4.getText(), // userid
            LocalDateTime.of(_DatePicker2.getValue(), LocalTime.of(_Spinner3.getValue(), _Spinner4.getValue())) // endDateTime
            , // location
            _TextField3.getText(), // type
_ChoiceBox_Customer.getValue().get_ID(), _TextField2.getText(), // desc
            _TextField5.getText());
        }
        
        System.out.println("Successfully SAVED. 030303030303030303030303030303030!");
    }
    
}