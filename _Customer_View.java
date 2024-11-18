package controller;


import database._Contact_Formatter;
import java.io.IOException;
import database._DataBase_customers;
import javafx.scene.control.Label;
import database._Database_provinces;
import javafx.scene.control.Button;

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
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.util.Map;
import javafx.collections.FXCollections;

import java.util.HashMap;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.Contact;
import java.util.ArrayList;
import database._Scene_Transitions;
import static database._Scene_Transitions._choice_box_functionality;
import javafx.scene.control.*;
import java.time.ZoneId;
import javafx.scene.Node;
import java.util.logging.Level;

import controller.Contact;
import controller.Customer;
import controller.User;
import database._Database_appointment_class;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableView;

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
import javafx.event.Event;
import java.util.concurrent.Executors;

/**
 * 
 *      The customer View  controller here not only provides a briddge between the view  layer and the model,
 *      but also facilitates  all forms of user interactions with customer data, ensuring that all inputs,
 *      Modifications and queries  to the customer information  are handled with pricision and consistency
 * 
 * 
 * @author Riley O'Donnell
 */
public class _Customer_View implements Initializable {

    @FXML private ToggleGroup _ToggleGroup1, _MenuToggleGroup;
    @FXML private TableView<Customer> _TableView1, _CustomerHistoryTable;
    @FXML private Label _Label1, _label2, _StatusLabel, _NotificationLabel;
    ZoneId _ZoneID = ZoneId.systemDefault();
    private String _defaultCountry = "USA";
    private int _maxCustomerEntries = 100;
    @FXML private ToggleGroup _PreferenceToggleGroup;
    private boolean _isCustomerActive = true;
    @FXML private Label _InfoLabel;
    
    public int _print_variable_states() {
        System.out.println("ToggleGroup1: " + _ToggleGroup1);
        System.out.println("MenuToggleGroup: " + _MenuToggleGroup);
        System.out.println("TableView1 : " + _TableView1);
        System.out.println("CustomerHistoryTable: " + _CustomerHistoryTable);
        System.out.println("Label1: " + _Label1);
        System.out.println("Label2: " + _label2);
        System.out.println("StatusLabel: " + _StatusLabel);
        System.out.println("NotificationLabel: " + _NotificationLabel);
        System.out.println("ZoneID (User Local Zone): " + _ZoneID);
        System.out.println("DefaultCountry: " + _defaultCountry);
        System.out.println("MaxCustomerEntries: " + _maxCustomerEntries);
        System.out.println("PreferenceToggleGroup: " + _PreferenceToggleGroup);
        System.out.println("IsCustomerActive: " + _isCustomerActive);
        System.out.println("InfoLabel: " + _InfoLabel);
        
        return -1;
    }
    
    /**
     * Initializes the controller for the customer view. Sets up the toggle group listener
     *
     * @param _URL the location used to resolve relative paths for the root object, or null if unknow
     * @param _ResourceBundle the resources used to localize the root object, or null if not localized
     * @return void
     */
    @Override
    public void initialize(URL _URL, ResourceBundle _ResourceBundle) {

         // initail debug statement to check the starting state of the selected toggle
         Toggle _InitialSelectedToggle = _ToggleGroup1.getSelectedToggle();
         System.out.println("DEBUG: Initially selected toggle: " + _InitialSelectedToggle);

        _ToggleGroup1.selectedToggleProperty().addListener((_Toggle7, _Toggle8, _Toggle9) -> {

                // new variables for debugging and tracking
            String _ToggleStatus = (_Toggle9 != null) ? "A new toggle is selected." : "No new toggle selected.";
            System.out.println("DEBUG: Toggle change detected. " + _ToggleStatus);
            boolean _TransitionSuccess = false; // Track if the transition succeeded

                // Proceed if a new toggle (_Toggle9) is indeed selected
             if  (_Toggle9 != null) {
                RadioButton _SelectedRadioButton = (RadioButton) _Toggle9;
                Stage _Stage = (Stage) _SelectedRadioButton.getScene().getWindow();

                  // Log the details of the selected radio button and its stage
                String _ButtonID = _SelectedRadioButton.getId();
                String _StageTitle = (_Stage.getTitle() != null) ? _Stage.getTitle() : "Unnamed Stage";
                System.out.println("DEBUG: New toggle selected - Button ID: " + _ButtonID);
                System.out.println("DEBUG: Retrieved stage: " + _Stage + " | Title: " + _StageTitle);

                try {
                    System.out.println("DEBUG: Attempting scene transition based on selected toggle...");
                    _Scene_Transitions._if_radio_button_selected(_Stage, _ToggleGroup1);
                    _TransitionSuccess = true;
                    System.out.println("DEBUG: Scene transition successful.");
                }  catch (Exception _SQL_Exception) {
                    
                    System.out.println("ERROR: Scene transition failed in initialize function - " + _SQL_Exception.getMessage());

                } finally {
                    System.out.println("DEBUG: Transition completion status: " + _TransitionSuccess);
                }
            } else {
                System.out.println("DEBUG: No action taken as _Toggle9 is null.");
            }
        });

                    // Set up columns in the TableView, logging for each step
        ObservableList<TableColumn<Customer, ?>> _LoadedColumns = _Scene_Transitions._Customer_Table_Load().getColumns();
        _TableView1.getColumns().setAll(_LoadedColumns);
        System.out.println("DEBUG: TableView columns loaded - Column count: " + _LoadedColumns.size());

                    // Display the user' timezone in _label2, with additional debug output
        String _UserTimezone = _ZoneID.toString();
        _label2.setText(_UserTimezone);
        System.out.println("DEBUG: User timezone set in label: " + _UserTimezone);

                     // Populate the TableView wit customer data, checking and reporting success
        System.out.println("DEBUG: Initiating customer data update in TableView...");
        boolean _UpdateSuccess = _update_customer_table() == 7; // Assuming 7 indicates success
        System.out.println("DEBUG: Customer data update status: " + (_UpdateSuccess ? "Success" : "Failure"));

    }


    /**
     * This methd checks if a customer is selected in the table view before initiating a scene transition
     * to modify the selected customer's information. If no customer is selected, a promt is displayed to
     * inform the user.
     *
     * @param _ActionEvent the ActionEvent triggered by clicking the "Modify Customer" button
     * @throws IOException if an error occurs during the scene transition
     */
    @FXML
    void _modify_customer_button(ActionEvent _ActionEvent) throws IOException {

         // clear any exisitng messages in _Label1 before performing further checks
        _Label1.setText(" ");

                // vheck if a customer is selectd in _TableView1
        if (_TableView1.getSelectionModel().getSelectedItem() == null) {
            System.out.println("No customer selected."); // log if no customer is selected
            _Label1.setText("Please select a customer to modify."); // Display mesage to user
        } else if (1 == 0) {
            System.out.print("\n\n\n if statement test #3 ");
        } else {
                    // If a customer is selected, initate the scene transition to modify customer details
            _Scene_Transitions._add_or_change_customer(
                (Stage) ((Node) _ActionEvent.getSource()).getScene().getWindow(),
                false,
                this,
                _TableView1.getSelectionModel().getSelectedItem()
            );
        }
        
        _update_customer_table();

            // Log the completin of the _modify_customer_button method for debugging
        System.out.println("_modify_customer_button(ActionEvent _ActionEvent) function ran.");
    }
    
    
    /**
    * Updates the customer table view by retrieving all customers from the database
    * and setting them as items in the TableView. This method checks for null conditions
    * to ensure that _TableView1 and its items list are properly initialized before
    * attempting to populate the table.
    *
    * @return int status code, always returns 7 to indicate completion of the method
    */
    public int _update_customer_table() {
        
        //////////////////////////////////
        
        
    if (_TableView1 != null && _TableView1.getItems() != null) {
        
        ObservableList<Customer> _customers_list = FXCollections.observableArrayList();

        _customers_list.addAll(_DataBase_customers._get_every_customer());

        System.out.println(""
                + "\n\n\n\n");

        for (Customer _customers : _DataBase_customers._get_every_customer())
        {
            System.out.print("Name :" + _customers.get_name());
            System.out.print("ID: " + _customers.get_ID());
            System.out.println();
        }

        System.out.println(""
        + "\n\n\n\n");


        if (!_customers_list.isEmpty()) {
            _TableView1.setItems(_customers_list);
            System.out.println("\tdebug- all customers size: " + _customers_list.size());
        } else {
            System.out.println("\tdebug- There were no customers to populate the customer view.");
        }
        } else {
            System.out.println("\tdebug- TableView1 is either null or probably uninitialized.");
        }
        
        //////////////////////////////////
        
        return 7;
    }


    @FXML
    int _exit_button() {
                //  Clear any previous messages displayed on _Label1
        _Label1.setText("");

         //    Set up the confirmation alert with title, header, and content text
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setContentText("Are you sure you want to exit?");
        exitAlert.setTitle("Exit Confirmation");
        exitAlert.setHeaderText("Exit Application");

            // show the alert dialog and capture the user's response
        Optional<ButtonType> userResponse = exitAlert.showAndWait();

         //      If the user confirmde, proceed  to terminate the connection and exit the application
        if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
            // Attempt to terminate the database connection
            try {
                database._Java_Connection._terminate();
            } catch (Exception e) {
                // Log any errors encountered during the termination process
                System.err.println("error terminating the database connection: " + e.getMessage());
            } finally {
                // Exit the application and return -1 indicating successful exit
                System.exit(0);
                return -1;
            }
        }

            // Log the exit button click for debugging purposes
        System.out.println("EXIT BUTTON CLICKED ! ! ! ! ! !");

            // Return 1 to indicate the user canceled the exit action
        return 1;
    }
    
    
    /**
     * handles the action of pressing the "Add Customer" button.
     * this method initiates a scene transition to add a new customer, clears any existing messages,
     *
     * @param _ActionEvent the ActionEvent triggered by clicking the "Add Customer" button
     * @throws IOException if an error occurs during scene transition
     */
    @FXML
    void _add_customer_button(ActionEvent _ActionEvent) throws IOException {

        // debugging: log the start of the add customer button action
        System.out.println("DEBUG: _add_customer_button method initiated with ActionEvent: " + _ActionEvent);

        // Retrieve the currrent stage based on the source of the action event
        Stage currentStage = (Stage) ((Node) _ActionEvent.getSource()).getScene().getWindow();
        System.out.println("DEBUG: Retrieved current stage: " + currentStage);

         // Begin customer addition by calling the scene transition method
        _Scene_Transitions._add_or_change_customer(currentStage, true, this, null);

            // Clear any previous messages in _Label1 after transition is handled
        _Label1.setText("");
        System.out.println("DEBUG: Cleared _Label1 text.");

            // Confirm the metod completion for debugging purposes
        System.out.println("DEBUG: _add_customer_button(ActionEvent _ActionEvent) function ran successfully.");
        
        
    }

    @FXML
    int _logout_button(ActionEvent _ActionEvent) throws IOException {
        //Clear any existing messages on Label1
        _Label1.setText("");

        // Perform logout transittion by calling _Logout_Button_Control with the current stage
        _Scene_Transitions._Logout_Button_Control((Stage) ((Button) _ActionEvent.getSource()).getScene().getWindow());

        // r eturn a status code to indicate successful logout action
        return 777;
    }
    
    
    /**
     * Handles the action for the "Delete Customer" button. This method Checks if a customer is selected,
     * verifies if the customer has any existing appointments, and displays app ropriate alerts.
     * if no appointments are associated, the customeer is deleted from the database.
     *
     *  @return int status code indicating the action outcome:
     *          0  if no customer was selected,
     *          1  if deletion was successful,
     *          2  if customer deletion failed due to existing appointments.
     */
    @FXML
    int _delete_customer_button() { 
        // clear previous messages in _Label1
        _Label1.setText(" ");

                // Message to display when deletion cannot proceed
        String _alert_message = "You cannot delete this customer.";

            // Check if a customer is selected in the table
        if (_TableView1.getSelectionModel().getSelectedItem() != null) {
            System.out.println("DEBUG: Customer selected for deletion.");
        } else {
            System.out.println("DEBUG: No customer selected.");
            _Label1.setText("You need to select a customer first.");
            return 0;  // Exit if no customer is selected
        }

            // Retrieve customer ID and check for existing appointments
        int customerID = _TableView1.getSelectionModel().getSelectedItem().get_ID();
        if (!_Database_appointment_class._get_appointments_using_id(customerID).isEmpty()) {

                    // Prepare warning message indicating appointments must be deleted first
            StringBuilder warningContent = new StringBuilder("This customer has existing appointments-" +
                    "\n\n\nCustomer's name: " + _TableView1.getSelectionModel().getSelectedItem().get_name() +
                    "\n\n\nPlease delete these appointments before deleting the customer-\t");

             // append appointment details to the warning content
            _Database_appointment_class._get_appointments_using_id(customerID).forEach(appt -> warningContent
                    .append("\n\n\nAppointment ID: ").append(appt.get_ID())
                    .append("\n\n\nDATE: ").append(appt.get_start_time().toLocalDate()));

                    // display alert for existing appointments
            _Scene_Transitions._alert_3(_alert_message, warningContent.toString());
            System.out.println("DEBUG: Customer has existing appointments, deletion aborted.");
            return 2;
        }

        // confirm deletion action with the user
        boolean confirmed = _Scene_Transitions._alert1(
                "Customer name: " + _TableView1.getSelectionModel().getSelectedItem().get_name(),
                "Delete the customer in the records?", "Delete Customer."
        ).filter(response -> response == ButtonType.OK).isPresent();

        if (confirmed) {
              // Remove the customer from the data if confirmed
            _DataBase_customers._remove_customer(customerID);
            System.out.println("DEBUG: Customer #" + customerID + " deleted successfully.");

             // display successful deletion message
            _Scene_Transitions._alert_2(
                    "Successfully deleted customer from records.",
                    "Customer name: " + _TableView1.getSelectionModel().getSelectedItem().get_name() +
                    "\n\n\nCustomer's ID: " + customerID
            );
        }

         //update the table data after any deletion operation
        _update_customer_table();
        System.out.println("debug: Customer table data updated.");

        return 1;
    }


}
