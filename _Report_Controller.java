package controller;


import database._Contact_Formatter;
import      database._Triplet;
import java.io.IOException;
import database._DataBase_customers;
import javafx.scene.control.Label;
import java.util.Locale;
import database._Database_provinces;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.*;
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
import java.time.format.DateTimeFormatter;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.util.Map;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;

import java.util.HashMap;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import java.io.File;
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

/**
 * 
 * class for report functionality. Includes the code for month counting report, country report, and user report.
 *
 * @author Riley O'Donnell
 */
public class _Report_Controller implements Initializable {
        
    public void _print_values() {
        System.out.println("ToggleGroup1: " + _ToggleGroup1);
        System.out.println("MainToggleGroup: " + _MainToggleGroup);
        System.out.println("ZoneID: " + _ZoneID);
        System.out.println("Label1: " + _Label1.getText());
        System.out.println("Label2: " + _Label2.getText());
        System.out.println("Label3: " + _Label3.getText());
        System.out.println("StatusLabel: " + _StatusLabel.getText());
        System.out.println("NotificationLabel: " + _NotificationLabel.getText());
        System.out.println("ChoiceBox: " + _ChoiceBox.getValue());
        System.out.println("FilterChoiceBox: " + _FilterChoiceBox.getValue());
        /// tabs
        System.out.println("Tab1: " + _Tab1.getText());
        System.out.println("Tab2: " + _Tab2.getText());
        System.out.println("Tab3: " + _Tab3.getText());
        System.out.println("settingsTab: " + _SettingsTab.getText());
        System.out.println("HelpTab: " + _HelpTab.getText());
        System.out.println("anchorPane: " + _AnchorPane);
        System.out.println("ContentPane: " + _ContentPane);
        System.out.println("SidebarPane: " + _SidebarPane);
    }
    
    
    /**
     * initialized the _REPORT_CONTROLLER, setting up UI listeners and triggering initial data loading.
     * to handle radio button selection changes, invoking scene transitions as needed.
     *
     * @param _URL The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param _ResourceBundle The resources used to localize the root object, or null if resources are not available.
     */
    @Override
    public void initialize(URL _URL, ResourceBundle _ResourceBundle) {

        // Debug: Log initialization start
        System.out.println("Initializing _REPORT_CONTROLLER...");

        _Label3.setText("" + _ZoneID);
        
        // listener to the selectedToggleProperty of _ToggleGroup1, which listens for changes in toggle selection.
        _ToggleGroup1.selectedToggleProperty().addListener((_Value, _value2, _value3) -> {

                // if the new selected toggle (_value3) is null, indicating no selection.
            if (_value3 == null) {
                    // Debug: Log that no toggle is selected
                System.out.println("No toggle selected.");
            } else {
                    // debug- Log the selected toggle for traceability
                System.out.println("Toggle selected: " + ((RadioButton) _value3).getText());

                try {
                     // if and only if a toggle is selected, initiate the scene transition based on the selected RadioButton.
                    _Scene_Transitions._if_radio_button_selected(
                        (Stage) ((RadioButton) _value3).getScene().getWindow(), _ToggleGroup1
                    );

                } catch (Exception _SQL_EXEPTION) {
                        // Ccatch any exceptions thrown during scene transition and log the error.
                    System.out.println("ERROR IN INITIALIZATION FUNCTION IN FILE NAME _REPORT_CONTROLLER.JAVA: " 
                        + _SQL_EXEPTION.getMessage());
                }
            }
        });

        // Trigger initial data loading for the country report.
        System.out.println("Loading country report...");
        _load_user_report();

        // Debug: Log completion of toggle setup
        System.out.println("Toggle setup complete.\n\n\n\n\nTOGGLE\n\n\n\n");

        // Debug: Log end of initialization
        System.out.println("Initialization of _REPORT_CONTROLLER complete.");
    }


    @FXML private Tab _Tab1, _Tab2, _Tab3, _SettingsTab, _HelpTab;
    @FXML private AnchorPane _AnchorPane, _ContentPane, _SidebarPane;
    ZoneId _ZoneID = ZoneId.systemDefault();
    @FXML private Label _Label1, _Label2, _Label3, _StatusLabel, _NotificationLabel;
    @FXML private ToggleGroup _ToggleGroup1, _MainToggleGroup;
    @FXML private ChoiceBox<Object> _ChoiceBox, _FilterChoiceBox;

    /**
    *   handles the logout button  action, clearing any displayed messages and performing the logout transition.
    *   Tthis method is triggered by an  ActionEvent when the logout button is clicked.
    *
    * @param _ActionEvent The ActionEvent triggered by the logout button click.
    * @return An integer status code (777) indicating that the logout action was successfully performed.
    * @throws IOException If an input/output exception occurs during the logout transition.
    */
    @FXML
    int _logout_button(ActionEvent _ActionEvent) throws IOException {
        //Clear any existing messages on Label1
        _Label1.setText("");

        // perform logout transittion by calling _Logout_Button_Control with the current stage
        _Scene_Transitions._Logout_Button_Control((Stage) ((Button) _ActionEvent.getSource()).getScene().getWindow());

        // r eturn a status code to indicate successful logout action
        return 777;
    }
    
    
    /** 
     * Updates the UI components to prompt the user to pick a country and loads a table view
     * of customer appointments for the selected country.
     * 
     * <h>
     * LAMBDA FUNCTION #1
     * </h>
     * <p>The method uses a lambda function to populate the ChoiceBox with country names and IDs,
     * and attaches a listener to update the TableView with customer data based on the selected
     * country. This approach encapsulates the ChoiceBox setup and improves readability.</p>
     */
    public void _country_report() {
        _Label1.setText("Pick a Country Now:");
        _Label2.setText("--------------------");

        TableView<Customer> _TableView_Appointments = _Scene_Transitions._Customer_Table_Load();

        // Positioning and styling the AnchorPane
        AnchorPane.setLeftAnchor(_TableView_Appointments, 0.0005);
        _ChoiceBox.getSelectionModel().clearSelection();
        _AnchorPane.getChildren().clear();
        _AnchorPane.setStyle("-fx-background-color: blue;");
        _ChoiceBox.getItems().clear();
        AnchorPane.setBottomAnchor(_TableView_Appointments, 0.0001);
        _AnchorPane.getChildren().add(_TableView_Appointments);
        AnchorPane.setRightAnchor(_TableView_Appointments, 0.0001);
        _ChoiceBox.setDisable(false);
        _AnchorPane.setEffect(new javafx.scene.effect.DropShadow());
        AnchorPane.setTopAnchor(_TableView_Appointments, 0.0001);

        if (_AnchorPane != null) {
            _ChoiceBox.setDisable(false);
            System.out.println("_ChoiceBox value #1: " + _ChoiceBox.getValue());
            _ChoiceBox.setDisable(true);
            System.out.println("_ChoiceBox value #2: " + _ChoiceBox.getValue());
            _ChoiceBox.setDisable(false);
            System.out.println("_ChoiceBox value #3: " + _ChoiceBox.getValue());

            // Lambda function to populate the ChoiceBox and attach a listener
            Runnable populateChoiceBox = () -> {
                ObservableList<Object> _Item_list = FXCollections.observableArrayList();

                // Populate _Item_list with country data
                _DataBase_country._get_countries().forEach(_country -> 
                    _Item_list.add(_country.get_name() + ": " + _country.get_id())
                );

                // Add listener for selected items
                _ChoiceBox.getSelectionModel().selectedItemProperty().addListener((_value1, _value2, _value3) -> {
                    if (_value3 != null) {
                        _TableView_Appointments.setItems(
                            _DataBase_customers._get_customer_using_customers_country_id(
                                Integer.parseInt(_value3.toString().split(": ")[1])
                            )
                        );
                    }
                });

                _ChoiceBox.setItems(_Item_list);
            };

            // Execute the lambda function to populate the ChoiceBox
            populateChoiceBox.run();

            _ChoiceBox.setConverter(new StringConverter<>() {

                @Override
                public String toString(Object object) {
                    if (object == null) {
                        return null;
                    }
                    return object.toString();
                }

                @Override
                public Object fromString(String string) {
                    return string;
                }
            });
        }
    }
    
    @FXML
    String _change_tab(Event _Event) {
        
        if (_Event.getSource() instanceof Tab) 
        {
            if (_Tab1.isSelected()) {
                _load_user_report();
                return "";
            } else if (_Tab2.isSelected()) {
               _load_appointments_by_month_and_type_report();
               return "";
            } else if (_Tab3.isSelected()) {
               _load_contact_report();
               return "";
            } else {
               // debugging case- no tab is selected
               System.out.println("DEBUG: No tab is currently selected.");
               return "None";
           }
           
        } else {
            // do nothing
        }
        
        return "None";
    }


    /*
    *
    * FXML function for exit button.
    *
    */
    @FXML
    int _exit_button() {
                      //  clear any previous messages displayed on _Label1
        _Label1.setText("");

         //    Set up the confirmation alert with title, header, and content text
        Alert _Alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        _Alert.setHeaderText("Application Exit Confirmation");
        _Alert.setTitle("Confirm Exit Now");
        _Alert.setContentText("Do you really want to leave this application?");

            // show the alert dialog and capture the user's response
        Optional<ButtonType> _Optional_ButtonType = _Alert.showAndWait();

         //      If the user confirmde, proceed  to terminate the connection and exit the application
        if (_Optional_ButtonType.isPresent() && _Optional_ButtonType.get() == ButtonType.OK) {
            // attempt to terminate the database connection
            try {
                database._Java_Connection._terminate();
            } catch (Exception e) {
                 // log any errors encountered during the termination process
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
     * Reports and displays a report of appointment counts by month within an AnchorPane.
     * this method updates the UI components to reflect the report data and applies
     * Sstyling and positioning to the displayed elements.
     *
     * @return An integer status code indicating successful completion of the report loading process.
     */
    public int _load_appointments_by_month_and_type_report() {
        // Set text labels accordingly
        _Label1.setText("Appointment Count By Month and Type");
        _Label2.setText("--------------------------");
        
        if (_AnchorPane != null) {
        
            TableView<Pair<String, _Triplet<String, Integer>>> _TableView_Appointments = _Scene_Transitions._Month_Count_Report();


            // positioning it close to the left edge of the AnchorPane.
            AnchorPane.setLeftAnchor(_TableView_Appointments, 0.0001);

            // clear the current selection in the ChoiceBox, ensuring no item is selected.
            _ChoiceBox.getSelectionModel().clearSelection();

            // clear all children (UI elements) from the _AnchorPane, removing any existing content.
            _AnchorPane.getChildren().clear();

            // Set the background color of the _AnchorPane to light gray for a neutral visual appearance.
            _AnchorPane.setStyle("-fx-background-color: blue;");

                // Clear all items in the ChoiceBox, removing any currently displayed options.
            _ChoiceBox.getItems().clear();

             // positioning it close to the bottom edge of the AnchorPane.
            AnchorPane.setBottomAnchor(_TableView_Appointments, 0.0001);

            // Add _TableView_Appointments as a child of _AnchorPane, displaying it within the pane.
            _AnchorPane.getChildren().add(_TableView_Appointments);

            // positioning it close to the right edge of the AnchorPane.
            AnchorPane.setRightAnchor(_TableView_Appointments, 0.0001);

            // Enable the ChoiceBox, ensuring that it is active and selectable by the user.
            _ChoiceBox.setDisable(false);

            // Apply a drop shadow effect to the _AnchorPane, adding a subtle shadow for visual depth.
            _AnchorPane.setEffect(new javafx.scene.effect.DropShadow());

            // positioning it close to the top edge of the AnchorPane.
            AnchorPane.setTopAnchor(_TableView_Appointments, 0.0001);

            if (_AnchorPane == null) {
               // do nothing
            } else {

                            // positioning it close to the left edge of the AnchorPane.
                AnchorPane.setLeftAnchor(_TableView_Appointments, 0.0001);
                // clear the current selection in the ChoiceBox, ensuring no item is selected.
                _ChoiceBox.getSelectionModel().clearSelection();
                // clear all children (UI elements) from the _AnchorPane, removing any existing content.
                _AnchorPane.getChildren().clear();
                // Set the background color of the _AnchorPane to light gray for a neutral visual appearance.
                _AnchorPane.setStyle("-fx-background-color: blue;");
                    // Clear all items in the ChoiceBox, removing any currently displayed options.
                _ChoiceBox.getItems().clear();
                 // positioning it close to the ottom edge of the AnchorPane.
                AnchorPane.setBottomAnchor(_TableView_Appointments, 0.0001);
                // Add _TableView_Appointments as a child of _AnchorPane, displaying it within the pane.
                _AnchorPane.getChildren().add(_TableView_Appointments);
                    // positioning it close to the right edge of the AnchorPane.
                AnchorPane.setRightAnchor(_TableView_Appointments, 0.0001);
                // reenable the ChoiceBox, ensuring that it is active and selectable by the user.
                _ChoiceBox.setDisable(false);
                // apply a drop shadow effect to the _AnchorPane, adding a subtle shadow for visual depth.
                _AnchorPane.setEffect(new javafx.scene.effect.DropShadow());
                AnchorPane.setTopAnchor(_TableView_Appointments, 0.0001);

                _TableView_Appointments.setItems(_Database_appointment_class._get_all_of_appointments_by_month_and_type());
            }
        }
        
        return 1000000;
    }
    
    public int  _load_contact_report() {
        
        _Label1.setText("Select a Contact:");
        _Label2.setText("----------------------");

        if (_AnchorPane == null) {
            // do nothing
        } else {
            
            TableView<Appointment> _TableView_Appointments = _Scene_Transitions._Load_Appointment_Table();
            
                    // positioning it close to the left edge of the AnchorPane.
            AnchorPane.setLeftAnchor(_TableView_Appointments, 0.0001);

            // clear the current selection in the ChoiceBox, ensuring no item is selected.
            _ChoiceBox.getSelectionModel().clearSelection();

            // clear all children (UI elements) from the _AnchorPane, removing any existing content.
            _AnchorPane.getChildren().clear();

            // Set the background color of the _AnchorPane to light gray for a neutral visual appearance.
            _AnchorPane.setStyle("-fx-background-color: lightgray;");

                // Clear all items in the ChoiceBox, removing any currently displayed options.
            _ChoiceBox.getItems().clear();

             // positioning it close to the bottom edge of the AnchorPane.
            AnchorPane.setBottomAnchor(_TableView_Appointments, 0.0001);
            
            // add a listener to the seleced item property of the ChoiceBox
            _ChoiceBox.getSelectionModel().selectedItemProperty().addListener((_value_1, _value_2, _value_3) -> {

                    // check if the new selected value (_value_3) is null
                if (!(!(_value_3 == null))) { 
                    // do nothing !
                } else {
                     // If value_3 is not null, proceed with processing

                       // parse the user ID from the selected item string (assuming format "Name : ID")
                    int _contact_id = Integer.parseInt(_value_3.toString().split(": ")[1]);

                          // initialize an ObservableList to hold appointments for the selected user
                    ObservableList<Appointment> _appointments_to_show = FXCollections.observableArrayList();

                        // Retrieve all appointments from the database
                    ObservableList<Appointment> _all_appointments = _Database_appointment_class._get_appointments();
                    
                    

                        // iterate over all appointments
                    for (Appointment _app : _all_appointments) {
                        
                        // Check if the appointment belongs to the selected user ID
                        if (_app.get_contact_ID() == _contact_id) {
                            // If the appointment matches the user ID, add it to the list to show
                            _appointments_to_show.add(_app);
                        }
                    }

                            // Determine the number of appointments found for the user
                    int _appointment_count = _appointments_to_show.size();

                            // Check if there are any appointments to display
                    if (_appointment_count != 0) {
                            // If appointments are found, set them to display in the TableView
                        _TableView_Appointments.setItems(_appointments_to_show);

                            // Update the label to show the number of appointments found
                        _Label2.setText("Number of Appointments: " + _appointments_to_show.size());
                    } else {
                                // If no appointments are found, display 
                        _Label2.setText("--------------------------------");
                    }

                        // Set the filtered list of appointments to the TableView again (redundant line)
                    _TableView_Appointments.setItems(_appointments_to_show);
                }
            });

            // Add _TableView_Appointments as a child of _AnchorPane, displaying it within the pane.
            _AnchorPane.getChildren().add(_TableView_Appointments);

            // positioning it close to the right edge of the AnchorPane.
            AnchorPane.setRightAnchor(_TableView_Appointments, 0.0001);

            // Enable the ChoiceBox, ensuring that it is active and selectable by the user.
            _ChoiceBox.setDisable(false);

            // Apply a drop shadow effect to the _AnchorPane, adding a subtle shadow for visual depth.
            _AnchorPane.setEffect(new javafx.scene.effect.DropShadow());

            // positioning it close to the top edge of the AnchorPane.
            AnchorPane.setTopAnchor(_TableView_Appointments, 0.0003);

            
            ObservableList<Contact> _contacts = _DataBase_contact_class._get_contacts();
            
            ObservableList<Object> _contact_names_and_ids = FXCollections.observableArrayList();
            
            for (Contact _contact : _contacts)
            {
                _contact_names_and_ids.add(_contact.get_name() + ": " + _contact.get_ID());
            }

            _ChoiceBox.setItems(_contact_names_and_ids);

            _ChoiceBox.setConverter(new StringConverter<>() {

                /**
                 * Converts an object to its string represntation.
                 * @param object The object to be convreted to a String.
                 * @return The string represntation of the object, or null if the object is null.
                 */
                @Override
                public String toString(Object object) {
                    // chek if the object is null; return null if so
                    if (object == null) {
                        return null;
                    }
                    // Otherwise, return the object's string represntation
                    return object.toString();
                }

                /**
                 * Converts a string back to an object.
                 * This implementation simply returns the string as it is, without additional processing.
                 * @param string The string to be converted.
                 * @return The string itself, unchagned.
                 */
                @Override
                public Object fromString(String string) {
                    return string;
                }

                /**
                 * Converts the object's string  to uppercase.
                 * This is useful for display  where upercase  is desired.
                 * @param object The object to convert to an uppercase string.
                 * @return The uppercase string  of the object, or null if the object is null.
                 */

            });
            


        }
        return 88237;
    }
    
    public int  _load_user_report() {
        
        _Label1.setText("Select a User:");
        _Label2.setText("----------------------");

        if (_AnchorPane == null) {
            // do nothing
        } else {
            
            TableView<Appointment> _TableView_Appointments = _Scene_Transitions._Load_Appointment_Table();
            
                    // positioning it close to the left edge of the AnchorPane.
            AnchorPane.setLeftAnchor(_TableView_Appointments, 0.0001);

            // clear the current selection in the ChoiceBox, ensuring no item is selected.
            _ChoiceBox.getSelectionModel().clearSelection();

            // clear all children (UI elements) from the _AnchorPane, removing any existing content.
            _AnchorPane.getChildren().clear();

            // Set the background color of the _AnchorPane to light gray for a neutral visual appearance.
            _AnchorPane.setStyle("-fx-background-color: lightgray;");

                // Clear all items in the ChoiceBox, removing any currently displayed options.
            _ChoiceBox.getItems().clear();

             // positioning it close to the bottom edge of the AnchorPane.
            AnchorPane.setBottomAnchor(_TableView_Appointments, 0.0001);
            
            // add a listener to the seleced item property of the ChoiceBox
            _ChoiceBox.getSelectionModel().selectedItemProperty().addListener((_value_1, _value_2, _value_3) -> {

                    // check if the new selected value (_value_3) is null
                if (!(!(_value_3 == null))) { 
                    // do nothing !
                } else {
                     // If value_3 is not null, proceed with processing

                       // parse the user ID from the selected item string (assuming format "Name : ID")
                    int _user_id = Integer.parseInt(_value_3.toString().split(": ")[1]);

                          // initialize an ObservableList to hold appointments for the selected user
                    ObservableList<Appointment> _appointments_to_show = FXCollections.observableArrayList();

                        // Retrieve all appointments from the database
                    ObservableList<Appointment> _all_appointments = _Database_appointment_class._get_appointments();

                        // iterate over all appointments
                    for (Appointment _app : _all_appointments) {
                        // Check if the appointment belongs to the selected user ID
                        if (_app.get_user_ID() == _user_id) {
                            // If the appointment matches the user ID, add it to the list to show
                            _appointments_to_show.add(_app);
                        }
                    }

                            // Determine the number of appointments found for the user
                    int _appointment_count = _appointments_to_show.size();

                            // Check if there are any appointments to display
                    if (_appointment_count != 0) {
                            // If appointments are found, set them to display in the TableView
                        _TableView_Appointments.setItems(_appointments_to_show);

                            // Update the label to show the number of appointments found
                        _Label2.setText("Number of Appointments: " + _appointments_to_show.size());
                    } else {
                                // If no appointments are found, display 
                        _Label2.setText("--------------------------------");
                    }

                        // Set the filtered list of appointments to the TableView again (redundant line)
                    _TableView_Appointments.setItems(_appointments_to_show);
                }
            });

            // Add _TableView_Appointments as a child of _AnchorPane, displaying it within the pane.
            _AnchorPane.getChildren().add(_TableView_Appointments);

            // positioning it close to the right edge of the AnchorPane.
            AnchorPane.setRightAnchor(_TableView_Appointments, 0.0001);

            // Enable the ChoiceBox, ensuring that it is active and selectable by the user.
            _ChoiceBox.setDisable(false);

            // Apply a drop shadow effect to the _AnchorPane, adding a subtle shadow for visual depth.
            _AnchorPane.setEffect(new javafx.scene.effect.DropShadow());

            // positioning it close to the top edge of the AnchorPane.
            AnchorPane.setTopAnchor(_TableView_Appointments, 0.0003);

        
            String _user_1 = "test : 1";
            String _user_2 = "admin : 2";

            ObservableList<Object> _user_strings = FXCollections.observableArrayList();
            _user_strings.add(_user_1); 
            _user_strings.add(_user_2);

            _ChoiceBox.setItems(_user_strings);

            _ChoiceBox.setConverter(new StringConverter<>() {

                /**
                 * Converts an object to its string represntation.
                 * @param object The object to be convreted to a String.
                 * @return The string represntation of the object, or null if the object is null.
                 */
                @Override
                public String toString(Object object) {
                    // chek if the object is null; return null if so
                    if (object == null) {
                        return null;
                    }
                    // Otherwise, return the object's string represntation
                    return object.toString();
                }

                /**
                 * Converts a string back to an object.
                 * This implementation simply returns the string as it is, without additional processing.
                 * @param string The string to be converted.
                 * @return The string itself, unchagned.
                 */
                @Override
                public Object fromString(String string) {
                    return string;
                }

                /**
                 * Converts the object's string  to uppercase.
                 * This is useful for display  where upercase  is desired.
                 * @param object The object to convert to an uppercase string.
                 * @return The uppercase string  of the object, or null if the object is null.
                 */

            });
            


        }
        return 88237;
    }


    


}
