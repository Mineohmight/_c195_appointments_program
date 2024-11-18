package controller;

import database._Contact_Formatter;
import java.io.IOException;
import database._DataBase_customers;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Timestamp;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import java.sql.SQLException;
import java.net.URL;

import controller.User;
import javafx.scene.control.ToggleGroup;
import database._DataBase_contact_class;
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
 * Controller for managing interactions within the Appointment Vi ew 
 * Facilitates user interaction with appointment related components and functionality.
 *
 * @author Riley O'Donnell
 */
public class _Appointment_View implements Initializable {
    
    /**
     * Handles the action of modifing an existing appointment when the modify button is clicked.
     * Clears any previous messages and checks if an appointment is selected.
     * If no appointment is selected, displays a mess age prompting the user to select one.
     * If an appointment is selected opens the appointment modification view for editing.
     *
     * @param _ActionEvent the event triggered by clicking the modify appointment button
     * @throws IOException if there is an error during the scene transition
     */
    @FXML
    public void _modify_appointment_button(ActionEvent _ActionEvent) throws IOException {
        // Clear any previous messages displayed on _Label1
        _Label1.setText("");

        //Check if an appointment is selected
        if (_TableView_Appointments.getSelectionModel().getSelectedItem() == null) {
            // Prompt user to select an appointment if none is selected
            _Label1.setText("You need to SELECT an appointment to modify.");
        } else {
            //Transition to the add/modify apointment view with the selected appointment for editing
            _Scene_Transitions._add_slash_modify_appointment(
                this, 
                _TableView_Appointments.getSelectionModel().getSelectedItem(), 
                false, 
                (Stage) ((Node) _ActionEvent.getSource()).getScene().getWindow()
            );
        }
    }
    
    @FXML 
    private Tab _Tab1, _Tab2, _Tab3;
    @FXML
    private ToggleGroup _ToggleGroup;
    @FXML
    private Label _Label1, _Label2;
    @FXML
    private TableView<Appointment> _TableView_Appointments;


    /**
     * Handles the action of adding a new appointment when the add button is clicked.
     * Clears any previous error messages and opens the appointment modification view.
     *
     * @param _ActionEvent the event triggered by clicking the add appointment button
     * @throws IOException if there is an error during the scene transition
     */
    @FXML
    public int _add_appointment_button(ActionEvent _ActionEvent) throws IOException {
        // Clear any existing messages on _Label1
        _Label1.setText("");

        // Transition to the add/modify appointment view, passing the current controller and stage
        _Scene_Transitions._add_slash_modify_appointment(
            this, 
            null, 
            true, 
            (Stage) ((Node) _ActionEvent.getSource()).getScene().getWindow()
        );
        
        System.out.println("Add appointment button selected. 797");
        
        return 1;
    }


    
    /**
     * Handles the action of logging out when the logout button is clicked.
     * Clears any existing messages, performs the logout transition, and returns a status code.
     *
     * @param _ActionEvent the event trigggered by clicking the logout button
     * @return an integer status code (777) indicating successful logout initiation
     * @throws IOException if there is an error during the scene transition
     */
    @FXML
    public int _logout_button(ActionEvent _ActionEvent) throws IOException {
        //Clear any existing messages on Label1
        _Label1.setText("");

        // Perform logout transittion by calling _Logout_Button_Control with the current stage
        _Scene_Transitions._Logout_Button_Control((Stage) ((Button) _ActionEvent.getSource()).getScene().getWindow());

        //Return a status code to indicate successful logout action
        return 777;
    }


    /**
     * Handles the ac tion of deleting a selected appointment when the delete button is clicked.
     * Clears any previous messages, verifies the selection and prompts the user for confirmation.
     * If the user confirms the appointment is deleeted from the database, and the table view is updated.
     */
    @FXML
    public int _delete_appointment_button() {
        // Clear any existing messages on Label1
        _Label1.setText("");

        // Retreive details of the selected appointment
        String _title = _TableView_Appointments.getSelectionModel().getSelectedItem().get_title();
        int _appointment_id = _TableView_Appointments.getSelectionModel().getSelectedItem().get_ID();
        String _appointment_type = _TableView_Appointments.getSelectionModel().getSelectedItem().get_type();

        // Check if an appiontment is selected
        if (_TableView_Appointments.getSelectionModel().getSelectedItem() == null) {
            // Prompt the user to select an appointment if none is selected
            _Label1.setText("Please select an appointment to delete.");
        } else {
            // Show confirmation dialog with appointment details
            Optional<ButtonType> _function = _Scene_Transitions._alert1(
                "Here is the information about your selected appointment:\n" + 
                "Appointment Title: " + _title + "\n" +
                "Appointment ID: " + _appointment_id + "\n" +
                "Appointment Type: " + _appointment_type + "\n",
                "Please confirm that you want to delete this appointment.",
                "Delete Appointment"
            );

            // Check if the user confirms the deletion
            if (_function.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                // Show deletion success message with appointment details
                _Scene_Transitions._alert_2(
                    "Appointment Deleted",
                    "This appointment has been deleted:\n" +
                    "Appointment ID: " + _appointment_id + "\n" +
                    "Appointment Title: " + _title + "\n" +
                    "Appointment Type: " + _appointment_type + "\n"
                );

                // Deleted the appointment from the database
                _Database_appointment_class._delete_appointment(_appointment_id);

                // Updated the table view to reflect deletion
                _Table_Update();

                // Loggged the successful deletion of the appointment
                System.out.println("ATTENTION ##### Appointment with ID " + _appointment_id + " has been successfully deleted.");
            }
        }
        
        return 1000;
    }



    
    /**
     * Handles the event of changing tabs in the application. 
     * If the event source is a Tab, the table view is updated accordingly.
     *  Additionaly, logs a message indicating the tab change.
     *
     * @param _ActionEvent the event  triggered when the tab is changed
     */
    @FXML
    void _change_tab(Event _ActionEvent) {
         //  Check if the event source is an instance of Tab, and update the table if true
        if (_ActionEvent.getSource() instanceof Tab) {
            _Table_Update();
        }

         //Log a message to confirm the tab change  event
        System.out.println("\n\nTAB HAS CHANGED!!!");
    }


    /**
     * Exits the the application when the exit button is clicked.
     * Displays a confimration alert to the user, and if confirmed, terminates the database 
     * connection and exits the application.
     *
     * @return an integer status code: -1 if the application exits, 1 if the user cancels the exit
     */
    @FXML
    public int _exit_button_action() {
        //  Clear any previous messages displayed on _Label1
        _Label1.setText("");

        //    Set up the confirmation alert with title, header, and content text
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setContentText("Are you sure you want to exit?");
        exitAlert.setTitle("Exit Confirmation");
        exitAlert.setHeaderText("Exit Application");

        //  Show the alert dialog and capture the user's response
        Optional<ButtonType> userResponse = exitAlert.showAndWait();

        //      If the user confirmde, proceed  to terminate the connection and exit the application
        if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
            // Attempt to terminate the database connection
            try {
                database._Java_Connection._terminate();
            } catch (Exception e) {
                // Log any errors encountered during the termination process
                System.err.println("Error terminating the database connection: " + e.getMessage());
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
     * Loads appointments filtered by the current month into the table view.
     * If the table view is not initialized, logs an error message.
     * Otherwise, fetches monthly appointments from the database and displays them in the table view.
     */
    @FXML
    public int _monthly_appointments() {
        // Check if the table view is initialized
        if (_TableView_Appointments == null) {
            // Log an error message if the table view is uninitialized
            System.out.println("Error- there were no TableView Appointments by month!");
        } else {
            // Set the table view items to the list of appointments filtered by month from the database
            _TableView_Appointments.setItems(_Database_appointment_class._get_appointment_by_month());
        }
        
        return 1;
    }


    /**
    * Updatess the table view based on the currently selected tab.
    * Uses a map of tab selection states and corresponding actions to determine which method to invoke.
    * If no tab is selected, an exception is thrown.
    */
    public int _Table_Update() {
        
        // Check if the first tab (_Tab1) is currently selected
        if (_Tab1.isSelected()) {
            // Call the method to load all  appointments if _Tab1 is selected
            _load_appointments();
        } 
        // If the first tab is not  selected, check if the second tab (_Tab2) is selected
        else if (_Tab2.isSelected()) {
            // Call the method to load only the monthly apointments if _Tab2 is selected
            _monthly_appointments();
        } 
        // If neither the first nor teh second tab is selected, check if the third tab (_Tab3) is selected
        else if (_Tab3.isSelected()) {
            // Call the method to load appointments filtered by the current week if _Tab3 is selected
            _load_weekly_appointments();
        } 
        // If none of the tabs are selected, log an error message
        else {
            // Print an error message to the console indicating that no tab was selected in this function
            System.out.println("Error,  there was no tab selected in _Table_Update function.");
        }
        
        System.out.println("_Table_Update function ran.");
        
        return 1;
    }

    /**
     *  Creates the table view with all available appointments providing the table view is initialized.
     *   Fetches   appointments from the database and asisgns them to the table view for display.
     *  Logs a  message if the table view is uninitialized or if there are no appointments to display.
     */
    @FXML
    public void _load_appointments() {
    if (_TableView_Appointments == null) {
        System.out.println("Table View has not been loaded yet.");
        return;
    }

    // Define custom column order only if the TableView is empty (optional safeguard)
    if (_TableView_Appointments.getColumns().isEmpty()) {
        // Define TableColumns in the desired order
        TableColumn<Appointment, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Appointment, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Appointment, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Appointment, Timestamp> startColumn = new TableColumn<>("Start Time");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<Appointment, Timestamp> endColumn = new TableColumn<>("End Time");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<Appointment, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));

        TableColumn<Appointment, Integer> userIDColumn = new TableColumn<>("User ID");
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        TableColumn<Appointment, Integer> appointmentIDColumn = new TableColumn<>("Appointment ID");
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));

        TableColumn<Appointment, Integer> contactIDColumn = new TableColumn<>("Contact ID");
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        TableColumn<Appointment, Integer> customerIDColumn = new TableColumn<>("Customer ID");
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        // Add columns to TableView in the desired order
        _TableView_Appointments.getColumns().addAll(
            titleColumn,
            descriptionColumn,
            locationColumn,
            startColumn,
            endColumn,
            typeColumn,
            userIDColumn,
            appointmentIDColumn,
            contactIDColumn,
            customerIDColumn
        );
    }

        // only set items if appointments are not empty
    if (_Database_appointment_class._get_appointments() != null && !_Database_appointment_class._get_appointments().isEmpty()) {
        // Set the data in the TableView while preserving SQL query's row order
        _TableView_Appointments.setItems(FXCollections.observableArrayList(_Database_appointment_class._get_appointments()));
    } else {
        System.out.println("There weren't any appointments that existed to load.");
    }
        
        System.out.println("_load_appointments function ran!");
    }


    
    /**
    *  Creates the controller after its root element has been fully proccesed
    *  Sets up UI components such as labels, toggles, and table views, and establishes listeners for interactive elements
    *
    *  @param _URL the location used to resolve relative paths for the root object, or null if not known
    *  @param _ResourceBundle the resource bundle containing locale-specific data, or null if not applicable
    */
    @Override
    public void initialize(URL _URL, ResourceBundle _ResourceBundle) {
        
         // Aadd a listener to monitor changes in the selected toggle of _ToggleGroup, with debug info
        Toggle _InitialToggle = _ToggleGroup.getSelectedToggle();
        System.out.println("DEBUG: Initial selected toggle: " + _InitialToggle);

        _ToggleGroup.selectedToggleProperty().addListener((_Toggle7, _Toggle8, _Toggle9) -> {

             // check that the new togagle (_Toggle9) is selected; proceed if so, log otherwise
            if (_Toggle9 != null) {
                System.out.println("DEBUG: New toggle selected - " + _Toggle9);

                 // Retrieve and verify the stage from the selected RadioButton's scene window
                Stage _Stage = (Stage) ((RadioButton) _Toggle9).getScene().getWindow();
                System.out.println("DEBUG: Retrieved stage: " + _Stage);

                String _StageTitle = _Stage.getTitle() != null ? _Stage.getTitle() : "Unnamed Stage";
                System.out.println("DEBUG: Stage title - " + _StageTitle);

                // boolean flag
                boolean _TransitionSuccess = false;

                try {
                        // Perform the scene transition based on the selected radio button
                    System.out.println("DEBUG: Initiating scene transition with _ToggleGroup: " + _ToggleGroup);
                    _Scene_Transitions._if_radio_button_selected(_Stage, _ToggleGroup);
                    _TransitionSuccess = true;
                } catch (Exception _SQL_Exception) {
                        // log any exceptions encountered during the scene transition
                    System.out.println("\nError in initialize function - Transition failed: " + _SQL_Exception.getMessage());
                } finally {
                      // Log whether the transition was successful
                    System.out.println("DEBUG: Scene transition success: " + _TransitionSuccess);
                }
            } else {
                System.out.println("DEBUG: No new toggle selected; _Toggle9 is null.");
            }
        });

        System.out.println("DEBUG: Loading appointment table columns...");
        _TableView_Appointments.getColumns().setAll(_Scene_Transitions._Load_Appointment_Table().getColumns());
        System.out.println("DEBUG: Appointment table columns loaded.");

        // Display the user's current timezone, with additional information
        String _CurrentZone = ZoneId.systemDefault().toString();
        _Label2.setText(_CurrentZone);
        System.out.println("DEBUG: User timezone set to: " + _CurrentZone);

        // Repopulate the TableView with customer data, adding debug info
        System.out.println("DEBUG: Updating customer data in TableView...");
        _Table_Update();
        System.out.println("DEBUG: Customer data update complete.");
    }


    /**
     *  Loads appointments filtered by the current week into the table view.
     *
     *  @return an integer status code (100) indicating successful execution
     */  
    @FXML
    public int _load_weekly_appointments() {
        // Check if the _TableView_Appointments table is initialized
        if (_TableView_Appointments == null) {
            // Log an error if _TableView_Appointments is not initialized
            System.out.println("Error, _TableView_Appointments was null in function _load_weekly_appointments.");
        } 
        // If the table view is initialized, load weekly appointments into the table
        else {
            // Retrieve weekly appointments from the database and set them as items in the table view
            _TableView_Appointments.setItems(_Database_appointment_class._get_appointments_by_w());
        }

        // Return a status code of 100 to indicate successful method execution
        return 100;
    }



}