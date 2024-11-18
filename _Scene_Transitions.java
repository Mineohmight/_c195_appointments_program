package database;

import javafx.scene.Scene;
import controller.Customer;
import javafx.stage.*;
import controller._Appointment_Controller;

import java.sql.PreparedStatement;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Optional;
import java.net.*;  
import java.time.temporal.WeekFields;
import java.util.function.Function;

import javafx.scene.input.*;
import controller.Appointment;
import java.util.ArrayList;

import database._Contact_Formatter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import database._Scene_Transitions;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

import javafx.util.StringConverter;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.event.ActionEvent;
import database._Database_profiles;
import java.sql.Connection;

import java.time.*;
import java.util.*;
import java.util.logging.Logger;
import javafx.util.Pair;
import javafx.fxml.*;
import controller._Customer_Controller;
import java.time.temporal.TemporalUnit;

import controller.Contact;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.*;
import java.io.File;
import java.sql.ResultSet;
import java.io.IOException;
import controller._Appointment_View;

import java.io.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.*;
import database._DataBase_customers;
import javafx.event.*;
import database._Duo;

import controller.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.net.URL;

import javafx.scene.Parent;
import javafx.scene.control.*;
import main.App;
import controller._Customer_View;
import database._Triplet;
import database._DataBase_contact_class;
import java.util.Collections;
import java.sql.SQLNonTransientException;
import javafx.scene.text.*;



/**
 * Data creation, scene functions, misclelaneous methods, and other global methods.
 * 
 * @author Riley O'Donnell
 */
public class _Scene_Transitions {


    /**
     * Prints a given mesage to the cosnole and returns the message.
     *
     * @param _message_to_be_printed The message to be printed
     * @return The printed message
     */
    public static String _print_message(String _message_to_be_printed) {
        // Print the provided message to the console
        System.out.println(_message_to_be_printed);

        // Return the same message
        return _message_to_be_printed;
    }

    /**
     * Formats the givn LocalDateTime to a 'yyyy-MM-dd' string format.
     *
     * @param dateTime The LocalDateTime object to be formated. Can be null.
     * @return A string formatted as 'yyy-MM-dd', or an empty string if dateTime is null.
     */
    public static String formatDate(LocalDateTime dateTime) {
        // Prints "Hoopla!" to the console and stores the returned message
        String _temp_string = _print_message("Hoopla!");

        
        return (dateTime != null)  // Return the formatted date if dateTime is not null; othewise, return an empy string
                ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) 
                : "";
    }
    
    
    /**
     * Loads an FXML file and returns the root {@code Parent} node of the loaded scene.
     *
     * <p>This method initializes an {@link FXMLLoader} using a refrence to the {@code App} class
     * and the provided FXML file path string. It then atempts to load the FXML file and 
     * returns the {@code Parent} node corresponding to the root element of the FXML structure.
     * If an {@link IOException} occurs during the loading process, the exception is caught,
     * an error message is printed, and the exception is retrhown.
     *
     * @param _String The relative path to the FXML file to load, as a {@code String}.
     * @return The root {@code Parent} node of the loaded FXML file.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public static Parent _fxml(String _String) throws IOException {
        
        System.out.println("INITIALIZING FXML LOADER PROCESS NOW#############");
        System.out.println("STRING LOADING: " + _String);

        // Instantiate the loader of the FXML using a class reference
        FXMLLoader _FXMLLoader = new FXMLLoader(App.class.getResource(_String));
        
        String _temp_string = _print_message("_format_2_ran!");
        _temp_string = "";

        // loading the FXML
        Parent _Parent = null;
        try {
            _Parent = _FXMLLoader.load();
        } catch (IOException _error) {
            // Handle the exception
            System.out.println("SOMETHING ODD HAPPENED DURING FXML LOADING!!!" + _temp_string);
            throw _error;
        }

        // Return the loaded Parent node
        return _Parent;
    }


    /**
     * Formats the given {@link LocalDateTime} object into a string representation of time in the "HH:mm:ss" format.
     *
     * <p>This method first calls the {@code _print_message} method with a static message, 
     * then resets the resulting string to an empty string. If the provded {@code LocalDateTime} 
     * object is non-null, the method formats it using a {@link DateTimeFormatter} with the pattern "H:mm:ss". 
     * If the {@code LocalDateTime} is nul, it returns an empy string.
     *
     * @param _LocalDateTime The {@code LocalDateTime} object to format. If null, an empy string is returned.
     * @return A formatted time string in the "HH:mm:ss" format if the input is non-nul, or an empty string otherwise.
     */
    public static String _format_time_2(LocalDateTime _LocalDateTime) {

        String _temp_string = _print_message("_format_2_ran!");
        _temp_string = "";

        // Check if _LocalDateTime is not null
        if (!Objects.isNull(_LocalDateTime)) {

            // Create a DateTimeFormatter with the pattern "HH:mm:ss"
            DateTimeFormatter _DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss" + _temp_string);

            // Format the LocalDateTime using the formatter and return the result
            return _LocalDateTime.format(_DateTimeFormatter);
        } else {
            // Return the empy string if _LocalDateTime is null
            return _temp_string;
        }
    }
    
    /**
     * Handles the event when a RadioButton is selected from a ToggleGroup.
     * This method retrieves the selected RadioButton, checks its value, and then 
     * changes the view based on the text associated with the selected RadioButton.
     *
     * @param _Stage       the current JavaFX stage
     * @param _ToggleGroup the ToggleGroup from which a RadioButton is selected
     * @throws IOException if an error occurs during view change
     * @throws IllegalArgumentException if the RadioButton text doesn't match expected values
     * @throws NullPointerException if the RadioButton's text is null
     * @throws IllegalStateException if view data is incomplete or missing
     */
    public static void _if_radio_button_selected(Stage _Stage, ToggleGroup _ToggleGroup) throws IOException {

        // Initialize a RadioButton variable, will be set if a valid RadioButton is selected.
        RadioButton _RadioButton = null;

        // Get the currently selected toggle from the ToggleGroup.
        Toggle _Toggle = _ToggleGroup.getSelectedToggle();

        // Check if the selected toggle is a RadioButton.
        if (_Toggle instanceof RadioButton) {
            _RadioButton = (RadioButton) _Toggle; // Cast the Toggle to a RadioButton.
        }

        // If no RadioButton was selected or an invalid toggle was selected, print an error.
        if (_RadioButton == null) {
            System.out.println("Error, the RadioButton has value null.");
        } else {
            // Get the text from the selected RadioButton.
            String _String1 = _RadioButton.getText();

            // Wrap the text in an Optional to handle 
            Optional<String> _String3 = Optional.ofNullable(_String1);

            // Check if the text is present.
            if (_String3.isPresent()) {
                // Trim any whitespace from the RadioButton's text.
                String _String2 = _String3.get().trim();

                // Create a map of possible views associated with the RadioButton text.
                Map<String, String[]> _Map = new HashMap<>();
                _Map.put("Appointments", new String[]{"View Appointment", "/main/_View_Appointment.fxml"});
                _Map.put("Customers", new String[]{"View Customer", "/main/_View_Customers.fxml"});
                _Map.put("Reports", new String[]{"View Report", "/main/_View_Reports.fxml"});

                // Check if the map contains an entry for the RadioButton text.
                if (_Map.containsKey(_String2)) {
                    // Retrieve the view data (title and path) for the selected RadioButton.
                    String[] _v = _Map.get(_String2);

                    // Ensure that the view data is valid and contains exactly two elements.
                    if (_v != null && _v.length == 2) {
                        String _title = _v[0];  // Extract the title of the view.
                        String _path = _v[1];   // Extract the path of the view.

                        // Wrap the title and path in Optionals to check for null values.
                        Optional<String> _Title = Optional.ofNullable(_title);
                        Optional<String> _Path = Optional.ofNullable(_path);

                        // Ensure both title and path are present.
                        if (_Title.isPresent() && _Path.isPresent()) {
                            // Call the view-changing method with the title, path, and stage.
                            _change_view(_Title.get(), _Path.get(), _Stage);
                        } else {
                            // If title or path is missing, throw an IllegalStateException.
                            throw new IllegalStateException("View data has been missing");
                        }
                    } else {
                        // If the view data is invalid, throw an IllegalArgumentException.
                        throw new IllegalArgumentException("Invalid view data for: " + _String2);
                    }
                } else {
                    // If the RadioButton text is unexpected, throw an IllegalArgumentException.
                    throw new IllegalArgumentException("Unexpected value: " + _String2);
                }
            } else {
                // If the RadioButton text is null, throw a NullPointerException.
                throw new NullPointerException("RadioButton text is null");
            }
        }

        // Print a message indicating that the RadioButton was successfully selected.
        String _result = _print_message("Radio button has been selected.");
    }

    /**
     * Loads an FXML file, creates a new scene, and sets it on the provided stage, centering it and setting the window title.
     *
     * <p>This method takes two strings: the first reprsenting the FXML path, and the second representing
     * the title of the stage. The mehod first loads the FXML file and creates a scene from it, then sets 
     * the scene on the stage. It also centers the window on the screen, sets the ttile, and introduces a 
     * short delay before showing the stage.
     *
     * @param _String1 The path to the FXML file to be loaded.
     * @param _String2 The title to be displayed in the window.
     * @param _Stage The stage where the new scene will be set.
     * @return Returns 0 upon completion.
     * @throws IOException If an error occurs during FXML loading.
     */
    public static <T extends Parent> int _change_view(String _String2, String _String1, Stage _Stage) throws IOException {

        // Declare variables to hold the parent node and the scene object
        Parent _Parent = null;
        Scene _Scene = null;

        // Log the FXML loading path
        String _log_message = "Loading FXML from: " + _String1;
        System.out.println(_log_message);  // Log the FXML loading message

        // Load the FXML file and store the rot node in _Parent
        _Parent = _fxml(_String1);

        // Check if the FXML loading failed, and throw an exception if it did
        if (_Parent == null) {
            throw new IllegalStateException("FXML loading failed, root node is null!");  // Error handling for null parent
        }

        Group _Temp_Group = new Group();
                                  
        Scene _temp_Scene = new Scene(_Temp_Group);  // Unused temporary scene
        _Scene = new Scene(_Parent);  // The actual scene that will be set on the stage

        // Log that the scene was successfuly created
        System.out.println("Scene created successfully.");

        // Call _print_message
        String _temp_string = _print_message("_format_2_ran!");
        _temp_string = "";  // Clear the temporary string 

        // Set the newly created scene on the stage
        _Stage.setScene(_Scene);

        // Introduce an delay of 100 milliseconds
        try {
            Thread.sleep(100);  // Introduces a slight delay
        } catch (InterruptedException _error) {
            // Handle an interruption exception if the sleep is interrupted
            System.err.println("Unexpected interruption" + _temp_string);  // Log the interruption message
        }

        // Center the stage on the screen using manual screen positioning
        _Stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - _Stage.getWidth()) / 2);  // Set X to center
        _Stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - _Stage.getHeight()) / 2);  // Set Y to center

        // Set the title of the stage to the provided string
        _Stage.setTitle(_String2);

        // Log that the stage is being shown with the title
        System.out.println("Showing stage with title: " + _String2 + _temp_string);

        // Show the stage on the screen
        _Stage.show();

        // Return 0 as a status code
        return 0;
    }




    
    /**
     * Processes the result of the dialog interaction. If the Save button was pressed and inputs validated,
     * updates the appointment view to reflect any changes made.
     *
     * @param _Dialog          The dialog instance displaying the add/modify form.
     * @param _Appointment_View The view displaying appointments to be updated after a succesful save.
     */
    public static void _process_dialog_result(Dialog<ButtonType> _Dialog, _Appointment_View _Appointment_View) {
        System.out.println("Debug: Displaying dialog and awaiting result.");
        _Dialog.showAndWait().ifPresent(_Result -> {
            System.out.println("Debug: Dialog closed with result: " + _Result.getButtonData());
            if (_Result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                System.out.println("Debug: Save confirmed. Updating table data.");
                _Appointment_View._Table_Update();
            } else {
                System.out.println("Debug: Operation canceled. No updates made.");
            }
        });
    }
            
    public static void _process_dialog_result(Dialog<ButtonType> _Dialog, _Customer_View __Customer_View, Boolean _flag, Customer _customer) {
    System.out.println("Debug: Displaying dialog and awaiting result.");
    _Dialog.showAndWait().ifPresent(_Result -> {
        System.out.println("Debug: Dialog closed with result: " + _Result.getButtonData());
        if (_Result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            System.out.println("Debug: Save confirmed. Updating table data.");
            __Customer_View._update_customer_table();

            if (_flag == true)
            {
                _DataBase_customers._remove_customer(_customer.get_ID());
            }

        } else {
            System.out.println("Debug: Operation canceled. No updates made.");
        }
    });
    }
   
    /**
     * Displays a dialoge to either add or modify an appointment, allowing the user to
     * save or cancel changes. If the Save button is pressed and inputs are valid,
     * the appointment is saved and the table is updated accordingly.
     *
     * @param _Appointment_View The view displaying the list of appointments to be updated if changes are saved.
     * @param _Appointment      The appointment data to be modified; if null, the dialog will be set up for adding a new appointment.
     * @param _Boolean          If true, opens the dialog in "Add" mode; if false, opens in "Modify" mode.
     * @param _Stage            The stage that owns the dialog, used for modal settings.
     * @throws IOException      If loading the FXML resource for the dialog fails.
     */
    public static void _add_slash_modify_appointment(_Appointment_View _Appointment_View, Appointment _Appointment, boolean _Boolean, Stage _Stage) throws IOException {
        System.out.println("Debug: Entering _add_slash_modify_appointment method.");

        Dialog<ButtonType> _Dialog = _create_dialog(_Stage);
        System.out.println("Debug: Dialog creatd and initialized.");

        _Appointment_Controller _Appointment_Controller = _initialize_controller(_Dialog);
        System.out.println("Debug: Controller initialized and loaded.");

        _configure_dialog(_Dialog, _Appointment_Controller, _Appointment, _Boolean);
        System.out.println("Debug: Dialoge configured for " + (_Boolean ? "Add" : "Modify") + " mode.");

        _add_event_filter_for_save_button(_Dialog, _Appointment_Controller);
        System.out.println("Debug: Event filter for Save button added.");

        _process_dialog_result(_Dialog, _Appointment_View);
        System.out.println("Debug: Dialog result processed.");
        
        
    }

    /**
     * Creates a dialog with modal settings, associating it with the given stage.
     *
     * @param _Stage The parent stage of the dialog.
     * @return A new Dialog instance with modality set to APPLICATION_MODAL.
     */
    private static Dialog<ButtonType> _create_dialog(Stage _Stage) {
        System.out.println("Debug: Creating dialog with specified stage.");
        Dialog<ButtonType> _Dialog = new Dialog<>();
        _Dialog.initOwner(_Stage);
        _Dialog.initModality(Modality.APPLICATION_MODAL);
        System.out.println("Debug: Dialog owner and modality set.");
        return _Dialog;
    }

    /**
     * Initializes the controller for the appointment dialog by loading the FXML file.
     *
     * @param _Dialog The dialog whose pane is set from the loaded FXML.
     * @return The controller responsible for managing the dialog's appointment form.
     * @throws IOException If the FXML file cannot be loaded.
     */
    private static _Appointment_Controller _initialize_controller(Dialog<ButtonType> _Dialog) throws IOException {
        System.out.println("Debug: Initializing controller with FXML loader.");
        FXMLLoader _FXMLLoader = new FXMLLoader(_Scene_Transitions.class.getResource("/main/_Appointment_Add_or_Remove.fxml"));
        _Dialog.setDialogPane(_FXMLLoader.load());
        _Appointment_Controller controller = _FXMLLoader.getController();
        System.out.println("Debug: Controler retrieved from FXML loader.");
        return controller;
    }

    /**
     * Configures the dialog title, labels, and fields based on whether the appointment is being added or modified.
     * Sets up the appropriate Save and Cancel buttons.
     *
     * @param _Dialog              The dialog instance to be configured.
     * @param _Appointment_Controller The controler managing the appointment data in the dialog.
     * @param _Appointment         The appointment data to modify; if null, the dialog is set up for adding a new appointment.
     * @param _Boolean             Specifies if the dialog is in "Add" mode (true) or "Modify" mode (false).
     */
    private static void _configure_dialog(Dialog<ButtonType> _Dialog, _Appointment_Controller _Appointment_Controller, Appointment _Appointment, boolean _Boolean) {
        System.out.println("Debug: Configuring dialog title and labels.");
        String _String = _Boolean ? "Add Apointment" : "Modify Appointment";
        _Dialog.setTitle(_String);
        _Appointment_Controller._Set_Title(_String);
        System.out.println("Debug: Title set to " + _String);

        if (_Appointment != null) {
            System.out.println("Debug: Setting existing appointment data in controller.");
            _Appointment_Controller._Set_Appointment(_Appointment);
        } else {
            System.out.println("Debug: No existing appointment data provided.");
        }

        _Dialog.getDialogPane().getButtonTypes().addAll(
            new ButtonType("Save", ButtonBar.ButtonData.OK_DONE),
            new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE)
        );
        System.out.println("Debug: Save and Cancel buttons added to dialog.");
    }

    /**
     * Adds an event fiter to the Save button to handle valiation before allowing the dialog to close.
     * If validation fails, the dialog remains open and the user is prompted to correct input errors.
     *
     * @param _Dialog              The dialog containing the Save button.
     * @param _Appointment_Controller The controller responsible for handling appointment data validation.
     */
    private static void _add_event_filter_for_save_button(Dialog<ButtonType> _Dialog, _Appointment_Controller _Appointment_Controller) {
        System.out.println("Debug: Adding event filter for Save button.");

        ButtonType _ButtonType_Save = _Dialog.getDialogPane().getButtonTypes()
            .stream().filter(b -> b.getButtonData() == ButtonBar.ButtonData.OK_DONE).findFirst().orElse(null);

        if (_ButtonType_Save != null) {
            System.out.println("Debug: Save buton located in dialog.");
            _Dialog.getDialogPane().lookupButton(_ButtonType_Save).addEventFilter(ActionEvent.ACTION, _Event -> {
                System.out.println("Debug: Save button pressed. Validating inputs.");
                if (!_Appointment_Controller._Input_Checker()) {
                    System.out.println("Debug: Validation failed. Dialog will not close.");
                    _Event.consume();
                } else {
                    System.out.println("Debug: Validation succeeded. Proceeding with save.");
                    _Appointment_Controller._Save();
                }
            });
        } else {
            System.out.println("Debug: Error - Save button not found in dialog.");
        }
    }


    
    /**
     * Initilizes the dialog window.
     * 
     * @param _Stage the primary stage to wich this dialog belongs
     * @return the initialized dialog
     */
    private static Dialog<ButtonType> _initialize_dialog(Stage _Stage) {
        System.out.println("Initalizing dialog...");
        Dialog<ButtonType> _Dialog = new Dialog<>();
        _Dialog.initOwner(_Stage); // Set the owener of the dialog to the provided stage
        _Dialog.initModality(Modality.APPLICATION_MODAL); // Set modlity to block interaction with other winows
        System.out.println("Dialog initalized: " + _Dialog);
        return _Dialog;
    }

    /**
     * Sets up the dialog pane by loAding the FXML and obtaIning the controller.
     * 
     * @param _Dialog the dialog whose pane is to be set up
     * @return the controller for managing cusOtmer interactions
     * @throws IOException if the FXML file cannot be loaded
     */
    private static _Customer_Controller _setup_dialog_pane(Dialog<ButtonType> _Dialog) throws IOException {
        System.out.println("Setting up dialog pan...");
        FXMLLoader _FXMLLoader = new FXMLLoader(_Scene_Transitions.class.getResource("/main/_Add_or_Remove_Customer.fxml"));
        DialogPane _DialogPane = _FXMLLoader.load();
        _Dialog.setDialogPane(_DialogPane); // Set the loded pane to the dialog
        _Customer_Controller controller = _FXMLLoader.getController(); // Get the controller from the FXMLLoader
        System.out.println("Dialog pane setup complet with controller: " + controller);
        return controller;
    }
    
    // FUNCTION DOES NOT WORK, OLD
    public static void _add_slash_modify_appointment_old(_Appointment_View __Appointment_View, Appointment _Appointment, boolean _Boolean, Stage _Stage) throws IOException {

        // Create a new FXMLLoader instance to load the FXML file for the dialog.
        FXMLLoader _FXMLLoader = new FXMLLoader();

        // Print a debug or log message (for troubleshooting or tracking purposes).
        String _result = _print_message("Test7");

        // Set the location of the FXML file containing the view for adding or removing appointments.
        //_FXMLLoader.setLocation(_Scene_Transitions.class.getResource("/main/_Appointment_Add_or_Remove.fxml"));
        
        String _result3 = _print_message("Test8");

        // Declare a DialogPane object where the FXML content will be loaded into.
        
        System.out.println("Test A");
        
        FXMLLoader _FXMLLoader2 = new FXMLLoader(_Scene_Transitions.class.getResource("/main/_Appointment_Add_or_Remove.fxml"));
        System.out.println("Test B");
        DialogPane _DialogPane = _FXMLLoader2.load();
        System.out.println("Test C");
        _Appointment_Controller _Controller = _FXMLLoader2.getController();
        System.out.println("Test D");
        Dialog<ButtonType> _DialogButtonType = new Dialog<>();
        System.out.println("Test E");

        // Print another debug or log message (perhaps to confirm the second part of the process).
        String _result2 = _print_message("Test9");

        // Retrieve the controller from the loaded FXML. This controller will handle the logic within the dialog.
        //_Appointment_Controller _Controller = _FXMLLoader.getController();

        // Create a new Dialog object of type ButtonType to handle the "Save" and "Cancel" buttons.
        //ialog<ButtonType> _DialogButtonType = new Dialog<>();

        // Set the DialogPane (loaded from FXML) to the dialog's pane.
        _DialogButtonType.setDialogPane(_DialogPane);

        // If a valid stage is provided, set it as the owner of the dialog to block the underlying window.
        if (_Stage != null) {
            _DialogButtonType.initOwner(_Stage);
        }
        
        System.out.println("Test10");

        // Set the modality of the dialog to "WINDOW_MODAL", which restricts interaction with the owner window until the dialog is closed.
        _DialogButtonType.initModality(Modality.WINDOW_MODAL);

        // Create a string that wil store either "ADD" or "MODIFY" depending on the operation being performed.
        String _String9 = new String();

        // If the boolean _Boolean is true, set _String9 to "ADD". Otherwise, set it to "MODIFY".
        if (_Boolean == Boolean.TRUE) {
            _String9 = "ADD";
        } else {
            _String9 = "MODIFY";
        }
        
        System.out.println("Test11");

        // Ensure _String9 is not null, and set the dialog's title and update the lables in the controller accordingly.
        if (_String9 != null) {
            _DialogButtonType.setTitle(_String9);
            _Controller._Set_Title(_String9); // Update labels to reflect either "Add" or "Modify".
        } else {
            // If _String9 is unexpectedly null, print an error message.
            System.out.print("Error: _String9 is null, which it should not be.");
        }
        
        System.out.println("Test12");

        // If an existing appointment is provided (not null), pass the apointment object to the controller to populate fields.
        if (_Appointment != null) {
            _Controller._Set_Appointment(_Appointment);
        }

        // Create an array of strings representing button labels for "CANCEL ACTION" and "SAVE".
        String[] _Strings = {"CANCEL ACTION", "SAVE"};
        
        System.out.println("Test13");

        // Create a map to store the ButtonType objects corresponding to the "CANCEL ACTION" and "SAVE" buttons.
        Map<String, ButtonType> _Map = new HashMap<>();

        // Loop through the array of button labels, and map each one to a ButtonType with the appropriate ButtonBar.ButtonData.
        for (String _s : _Strings) {
            if ("CANCEL ACTION".equals(_s)) {
                _Map.put(_s, new ButtonType(_s, ButtonBar.ButtonData.OK_DONE));  // Map "CANCEL ACTION" to OK_DONE
            } else if ("SAVE".equals(_s)) {
                _Map.put(_s, new ButtonType(_s, ButtonBar.ButtonData.CANCEL_CLOSE));  // Map "SAVE" to CANCEL_CLOSE
            }
        }
        
        System.out.println("Test14");

        // Retrieve the ButtonType objects from the map for "CANCEL ACTION" and "SAVE".
        ButtonType _CANCEL_ACTION = _Map.get("CANCEL ACTION");
        ButtonType _SAVE = _Map.get("SAVE");

        // Ensure that both buttons (SAVE and CANCEL ACTION) are proprely created, and throw an exception if missing.
        Objects.requireNonNull(_SAVE, "Save button is missing.");
        Objects.requireNonNull(_CANCEL_ACTION, "Cancel Action button type's missing.");

        // Check if both "CANCEL ACTION" and "SAVE" buttons exist in the map, and add them to the dialog pane if they do.
        if (_Map.keySet().containsAll(Arrays.asList(_Strings[0], _Strings[1]))) {
            _DialogButtonType.getDialogPane().getButtonTypes().addAll(_SAVE, _CANCEL_ACTION);
        } else {
            // Print an error message if one or both buttons are missing from the map.
            System.err.println("Error: One or more buttons are missing.");
        }
        
        System.out.println("Test15");

        // Lookup the "SAVE" button in the dialog pane and ensure it is of type Button.
        Node _Node = _DialogButtonType.getDialogPane().lookupButton(_SAVE);
        if (_Node instanceof Button) {
            Button _Button = (Button) _Node;
            // Add an event handler to the "SAVE" button to validate inputs and handle the save operation.
            _Node.addEventHandler(ActionEvent.ACTION, event -> {
                boolean _Boolean2 = _Controller._Input_Checker();  // Validate the user inputs through the controller.
                if (_Boolean2) {
                    _Controller._Save();  // Proceed with saving if inputs are valid.
                } else {
                    event.consume();  // Cancel the event if inputs are invalid.
                }
            });
        }
        
        System.out.println("Test16");

        // After the dialog, updte the table data in the apointment view controller.
        __Appointment_View._Table_Update();
    }
    
    /**
     * Adds or modifies a custumer by displaying a dialog window.
     * 
     * @param _Stage         the primAry stage to wHich this dialog belongs
     * @param _Boolean       if true, the customer is being added; if false, the customer is being modified
     * @param _Customer_View the veiw that will be updated once the customer is added/modified
     * @param _Customer      the customor to modify, null if adding a new cusotumer
     * @throws IOException if there is an issue loading the dialog pane
     */
    public static void _add_or_change_customer(Stage _Stage, boolean _Boolean, _Customer_View _Customer_View, Customer _Customer) throws IOException {
        System.out.println("Entring _add_or_change_customer method...");
        System.out.println("Parametrs: _Stage=" + _Stage + ", _Boolean=" + _Boolean + ", _Customer_View=" + _Customer_View + ", _Customer=" + _Customer);
        
        Boolean _should_delete_customer_if_pressed_save = false;
        
        // Initialize the dialog window
        Dialog<ButtonType> _Dialog = _initialize_dialog(_Stage);
        // Set up the dialog pane and controller
        _Customer_Controller __Customer_Controller = _setup_dialog_pane(_Dialog);

        // Set the dialog titel depending on whether we are adding or modifying a custmer
        String _String = _Boolean ? "Add Customer" : "Modify Customer";
        _configure_dialog(__Customer_Controller, _Dialog, _String);

        // Handle add or modify mode
        if (_Boolean) {            
                System.out.println("Handeling add mode...");
                __Customer_Controller._ChoiceBox_Division.setDisable(true);
                __Customer_Controller._enable_division_choicebox(); // Add nessary listeners for the controls

        } else {           
            
             System.out.println("Handeling modify mode...");
            if (_Customer != null) {
                System.out.println("Modifing customer: " + _Customer);
                __Customer_Controller._set_customer(_Customer); // Set the custmer infoormation in the controller
                __Customer_Controller._enable_division_choicebox(); // Add nessary listeners for the controls
                
                // delete old version of customer!
                _should_delete_customer_if_pressed_save = true;
            }
            
        }

        // Add butons to the dialog and set up actions
        _add_dialog_buttons(_Dialog, __Customer_Controller);
        // Procss the result from the dialog
        _process_dialog_result(_Dialog, _Customer_View, _should_delete_customer_if_pressed_save, _Customer);
        System.out.println("Exiting _add_or_change_customer methd...");
        
        _Customer_View._update_customer_table();
        
    }




    /**
     * Configures the dialog titel and sets customer labels.
     * 
     * @param __Customer_Controller the controller to set lables on
     * @param _Dialog                the dialog whose title is being set
     * @param _String               the title for the dialog
     */
    private static void _configure_dialog(_Customer_Controller __Customer_Controller, Dialog<ButtonType> _Dialog, String _String) {
        System.out.println("Configuring dialog with titel: " + _String);
        _Dialog.setTitle(_String); // Set the dialog titel
        __Customer_Controller._label1.setText(_String); // set the lables in the customer controller
    }


    
    /**
     * Procsses the result of the dialog after the user has interated with it.
     * 
     * @param _Dialog        the dialog whose result is being procssed
     * @param _Customer_View the customer veiw to update after a successful save
     */
    private static void _process_dialog_result(Dialog<ButtonType> _Dialog, _Customer_View _Customer_View) {
        System.out.println("Procssing dialog result...");
        _Dialog.showAndWait().ifPresent(_Result -> {
            System.out.println("Dialog result: " + _Result);
            if (_Result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                System.out.println("Save action cnfirmed, updating table data...");
                _Customer_View._update_customer_table(); // Update the customer view after a sucessful save
            }
        });
    }


    /**
     * Adds butons to the dialog and sets up event handeling for them.
     * 
     * @param _Dialog               the dialog to which butons are being added
     * @param __Customer_Controller the controller to handle button actons
     */
    private static void _add_dialog_buttons(Dialog<ButtonType> _Dialog, _Customer_Controller __Customer_Controller) {
        System.out.println("Adding dialog butons...");
        // Create Save and Cancel butons
        ButtonType _ButtonType1 = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType _ButtonType2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        _Dialog.getDialogPane().getButtonTypes().addAll(_ButtonType1, _ButtonType2);

        // Add event filter to handle save buton action
        _Dialog.getDialogPane().lookupButton(_ButtonType1).addEventFilter(ActionEvent.ACTION, _Event -> {
            System.out.println("Save button clicked, validating inpts...");
            if (!__Customer_Controller._check_and_validate()) {
                System.out.println("Input validation faild, cancelling action.");
                _Event.consume(); // Consume the event to prevent closing the dialog if validation fails
            } else {
                System.out.println("Input validation pased, saving custmer...");
                __Customer_Controller._save_customer(); // Proceed with saving the customer
            }
        });
    }


    
    /**
     * Displays a confimation alert dialog with specified title and content text.
     * 
     * <p>This method creates an {@code Alert} of type {@code CONFIRMATION} that allows
     * the user to choose between "OK" and "Cancel" options. The title and content of the
     * alert are specified by the parameters passed to the method.</p>
     * 
     * <p>The method returns an {@code Optional<ButtonType>} representing the user's choice,
     * allowing the calling code to handle the user’s selection.</p>
     * 
     * @param _String1 the title of the alert dialog
     * @param _String2 the main content message of the alert dialog
     * @return an {@code Optional<ButtonType>} containing the user's response (OK or Cancel)
     */
    public static Optional<ButtonType> _alert_2(String _String1, String _String2) {

        // Create a new Alert of CONFIRMATION type, which pompts the user for confirmation
        Alert _Alert1 = new Alert(Alert.AlertType.CONFIRMATION);

        // Set the title of the alert dialog to the value provided by the _String1 parameter
        _Alert1.setTitle(_String1);

        // Set the main content txt of the alert dialog to the value provided by the _String2 parameter
        _Alert1.setContentText(_String2);

        // Set the header text of the alert dialog, giving additional context to the user
        _Alert1.setHeaderText("Attention");

        // Configure the alrt to display OK and Cancel buttons only
        _Alert1.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Print a message to the console indicating that this alert was executed
        System.out.println("ALERT NUMBER 2 RAAN!");

        // Display the alert and wait for the user to repond, returning the user's choice
        return _Alert1.showAndWait();
    }

    
    /**
    * Displays a customize-able confirmation alert dialog with retry and close options.
    * If the "Retry" button is selected, the alert will recursiveley prompt the user until
    * a non-retry option is chosen.
    *
    * @param _String1_alert_message The message displayed in the alert content area.
    * @param _String2_HeaderText    The header text displayed at the top of the alert.
    * @param _String3_Title         The title of the alert dialog.
    * @return An {@code Optional<ButtonType>} representing the user's selction, if available.
    */
   public static Optional<ButtonType> _alert1(String _String1_alert_message, String _String2_HeaderText, String _String3_Title) {
       Alert _Alert1 = _createAlert(_String1_alert_message, _String2_HeaderText, _String3_Title);

       // Display the alert and wait for the user's response
       Optional<ButtonType> _Optional1_response = _Alert1.showAndWait();

       if (_Optional1_response.isPresent() && _Optional1_response.get().getText().equals("Rtry")) {
           System.out.println("Rtry option selcted.");
           return _alert1(_String1_alert_message, _String2_HeaderText, _String3_Title);
       } 
       else if (_Optional1_response.isPresent() && _Optional1_response.get() == ButtonType.OK) {
           System.out.println("User cnfirmed the alert.");
       } 
       else {
           System.out.println("Alert clsosed by user.");
       }

       System.out.println("ALERT 1 TICCKED OFF!");

       return _Optional1_response;
   }

   /**
    * Creates and configures an {@code Alert} dialog with specified title, header, and content text.
    * The alert includes an "OK" button, a "Retry" button, and a "Close" button. The alert pane
    * is sized to 400x400 pixels for better display visibility.
    *
    * @param _String1_alert_message The message displayed in the alert contant area.
    * @param _String2_HeaderText    The header text displayed at the top of the alert.
    * @param _String3_Title         The title of the alert dialog.
    * @return A configured {@code Alert} dialog ready to be displayed to the user.
    */
   private static Alert _createAlert(String _String1_alert_message, String _String2_HeaderText, String _String3_Title) {
        // Create a new Alert dialog of type CONFIRMATION, indicating it is a confirmation prompt for the user
        Alert _Alert1 = new Alert(Alert.AlertType.CONFIRMATION);

        // Set the title of the alert dialog to the specified title string
        _Alert1.setTitle(_String3_Title);

        // Set the header text of the alert dialog, displayed at the top, to the specified header string
        _Alert1.setHeaderText(_String2_HeaderText);

        // Set the main content text of the alert dialog to the specified message string
        _Alert1.setContentText(_String1_alert_message);

        // Set the preferred size of the dialog pane to 400x400 pixels to ensure readability
        _Alert1.getDialogPane().setPrefSize(400, 400);

        // Create a custom button type "Retry" with button data indicating it is an additional action option
        ButtonType _ButtonType1_retry = new ButtonType("Nevermind", ButtonBar.ButtonData.OTHER);

        // Create a custom button type "Close" with button data indicating it closes the dialog
        ButtonType _ButtonType2_close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Set the button types for the alert dialog to "OK", "Retry", and "Close"
        _Alert1.getButtonTypes().setAll(ButtonType.OK, _ButtonType1_retry, _ButtonType2_close);

        // Return the configured Alert dialog instance
        return _Alert1;
   }




    /**
     * displays a warning alert dialog with specified title  and content text, allowing
     * the user to respond with     "yes," "No," or "Cancel".
     * 
     * <p>this method creates an alert of type {@code WARNING}, setting its modality to block
     * interactions with other application windows until the user responds. The dialog includes
     * an icon for visual emphasis and enables custom handling of user responses.</p>
     * 
     * <h1>LAMBDA EXPRESSION #3</h1>
     * <p>ythe method uses a lambda expression to encapsulate the logic for handling  user responses.
     * The lambda displays  the alert dialog, waits for the  user's selection, and processes the 
     * response.  Depending on the button clicked (Yes, No,  or Cancel), a message is logged to 
     * the console.</p>
     * 
     * <p>The method    outputs to the console to indicate which option the user chose and signals
     * the completion of the alert interaction.</p>
     * 
     *  @param _String1 the title of  teh alert dialog
     *  @param _String2 the main message content of the alert dialog
     */
    public static void _alert_3(String _String1, String _String2) {

        // Creates a new Alert of type WARNING to notify the user with an important message
        Alert _Alert1 = new Alert(Alert.AlertType.WARNING);

        // Sets a header tet "Warning" for emphasis, indiating the severity of the message
        _Alert1.setHeaderText("WARNING!");

        // Sets the title of the alert window using the provided _String1 parameter
        _Alert1.setTitle(_String1);

        // Sets the main message of the alert using the _String2 parameter
        _Alert1.setContentText(_String2);

        // Adds YES, NO, and CANCEL options to allow the user to choose from these responses
        _Alert1.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

        // Retrieves the stage of the alert dialog and sets its modality to APPLICATION_MODAL
        // This modality blocks interaction with other windows in the application until the alert is closed
        Stage _Stage1 = (Stage) _Alert1.getDialogPane().getScene().getWindow();
        _Stage1.initModality(Modality.APPLICATION_MODAL);

        // Adds a warning icon to the dialog window from the specfied resource path
        _Stage1.getIcons().add(new javafx.scene.image.Image("file:resources/warning_icon.png"));

        // LAMBDA EXPRESSION to handle the user's response and print the choice to the console
        Runnable handleResponse = () -> {
            Optional<ButtonType> _ButtonType1 = _Alert1.showAndWait();

            _ButtonType1.ifPresent(response -> {
                if (response == ButtonType.YES) {
                    System.out.println("User clicked YES");
                } else if (response == ButtonType.NO) {
                    System.out.println("User clicked NO");
                } else {
                    System.out.println("User clicked CANCEL");
                }
            });
        };

        // Execute the lambda function to handle the response
        handleResponse.run();

        // Prints an aditional mesage to indicate the alert has completed its interaction
        System.out.println("ALERT 3 CLICKED!");
    }

    
    public static String formatContact(Contact contact) {
        return contact.get_ID() + " - " + contact.get_name();
    }

    /**
     * Loads a list of Contact obects into a ChoiceBox, formating each contact as "ID - Name".
     * This method retrieves contacts from the database, converts them to an ObservableList, 
     * and populates the ChoiceBox with each formatted contact entry.
     *
     * @param _Choice_Box the ChoiceBox<Contact> UI element to be populated with formatted contact entries
     */
    public static int _Choice_Box_2(ChoiceBox<Contact> _Choice_Box) {
        System.out.println("Debug: Starting loadChoiceBoxContacts method.");

        // Retrieve contacts from the database and convert them to an ObservableList
        // _DataBase_contact_class._get_contacts() retrieves a list of contacts from the database
        ObservableList<Contact> _ObservableList_Contacts = FXCollections.observableArrayList(_DataBase_contact_class._get_contacts());

        // Debugging: Log the size of the contcts list after conversion
        System.out.println("Debug: Converted contacts to ObsevrableList with size: " + _ObservableList_Contacts.size());

        // Populate the ChoiceBox with formatted contact strings
        // _Contact_Formatter::formaContact is a method reference to format each Contact object as a String
        _choice_box_functionality(_Choice_Box, _Contact_Formatter::formatContact, _ObservableList_Contacts);

        // Debugging: Log the end of the method execution
        System.out.println("Debug: Finihed loadChoiceBoxContacts method.");
        
        return 1;
    }
    
    

    /**
     * This utility method sets up a ChoiceBox with items and a custom converter to map
     * between objects of type T and their String representations.
     *
     * @param <T> The type of items in the ChoiceBox.
     * @param _ChoiceBox1 The ChoiceBox to whch items and a converter will be set.
     * @param _ObservableList1 The list of items to populate the ChoiceBox.
     * @param _Function1 A function to convert each item of type T to its String representation.
     * @return An Optional<Void> which currently always returns Optional.empty(), but could be 
     *         expanded to return a meaningful value if desired.
     */
    public static <T> Optional<Void> _choice_box_functionality(ChoiceBox<T> _ChoiceBox1, Function<T, String> _Function1, ObservableList<T> _ObservableList1) {
        System.out.println("Initializing choice box functionality...");
        System.out.println("Items to set: " + _ObservableList1);

        // Set items for the ChoiceBox
        _ChoiceBox1.setItems(_ObservableList1);

        // Define and set a custom StringConverter for ChoiceBox items
        _ChoiceBox1.setConverter(new StringConverter<>() {

            /**
             * Converts an item of type T to its String representation.
             *
             * @param _T1 The item to convert.
             * @return The String representation of the item; returns null if the item is null.
             */
            @Override
            public String toString(T _T1) {
                System.out.println("Converting itm to string: " + _T1); // Log the item being converted
                if (_T1 == null) { // Check if item is null
                    System.out.println("Item is null, returning null string.");
                    return null; // Return null if item is null
                } else {
                    // Apply the provided conversion function to get the string representation
                    String result = _Function1.apply(_T1);
                    System.out.println("Converted item: " + result); // Log the conversion result
                    return result != null ? result : ""; // Return the result or an empty string if null
                }
            }

            /**
             * Converts a String back to an item of type T by finding the frst matching item in the ChoiceBox list.
             *
             * @param _String1 The String represntation of an item.
             * @return The item of type T that matches the String, or null if no match is found.
             */
            @Override
            public T fromString(String _String1) {
                System.out.println("Converting strng back to item: " + _String1); // Log the string being converted
                List<T> allItems = _ObservableList1.stream().collect(Collectors.toList()); // Collect all items into a list
                System.out.println("All items in choice box: " + allItems); // Log the complete item list

                // Search for the item that matches the string by applying the conversion function
                Optional<T> matchingItem = allItems.stream()
                        .filter(item -> {
                            // Convert the current item to a string
                            String converted = _Function1.apply(item);
                            // Check if the converted string matches the target string
                            boolean matches = converted.equals(_String1);
                            System.out.println("Checking item: " + item + " | Converted: " + converted + " | Matches: " + matches); // Log item check
                            return matches;
                        })
                        .findFirst(); // Return the first matching item, if present

                // Check if a matching itm was found
                if (matchingItem.isPresent()) {
                    T item = matchingItem.get(); // Retrieve the matching item
                    System.out.println("Match found: " + item); // Log the found match
                    T finalResult = item != null ? item : null; // Null check (redundant in this context)
                    return finalResult;
                } else {
                    System.out.println("No matching item found for string: " + _String1); // Log when no match is found
                    return null;
                }
            }
        });

        // Final log statement to confirm ChoiceBox functioality setp completion
        System.out.println("CHOICE BOX FUCNTIONALITY EXECUTED SUCCESSFULLY!"); 

        // Return an empty Optional as the method result, indicating no specific value
        return Optional.empty(); 
    }
    


    
    /**
    * Initializes and configures a TableView for displaying apointment information.
    * This method sets up a variety of columns for diferent appointment attributes,
    * including appointment ID, title, description, location, type, start date, start time,
    * end date, end time, and IDs for assocated contact, customer, and user.
    * 
    * Each column is created with a specific title, data binding, and prefered width,
    * including customized date and time formatting where necessary.
    * 
    * @return a fully configured TableView of type Appointment for displaying detailed appointment data.
    */
    public static TableView<Appointment> _Load_Appointment_Table() {
        
        System.out.println("_Load_Appointment_Table_ function ran.");
        
        TableView<Appointment> _TableView_Appointments = new TableView<>();

        // Create a column for displaying the apointment description, with a specified width of 80
        TableColumn<Appointment, String> _Description = _Create_Column_Helper_Function("Description", "_description", 80);

        // Create a column for displaying the appointment ID, with a specified width of 80
        TableColumn<Appointment, Integer> _Appt_ID = _Create_Column_Helper_Function("Appt ID", "_ID", 80);

        // Create a column for displaying the appointment loction, with a specified width of 85
        TableColumn<Appointment, String> _Locaiton = _Create_Column_Helper_Function("Location", "_location", 85);

        // Create a colum for displaying the type of apointment, with a speciied width of 80
        TableColumn<Appointment, String> _Type = _Create_Column_Helper_Function("Type", "_type", 80);

        // Create a column for displaying the appointment title, with a specified width of 80
        TableColumn<Appointment, String> _Title = _Create_Column_Helper_Function("Title", "_title", 80);
 
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        // Define the TableColum wit the correct types
        TableColumn<Appointment, String> _Start_Date_Column = new TableColumn<>("Start Date");

        // Set cell value factory using an anonmous inner class
        _Start_Date_Column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> _CellDataFeatures) {
                LocalDateTime _Local_Date_Time = _CellDataFeatures.getValue().get_start_time();

                // Format the date or set it to an empty string if null
                String _Date_Formatted = (_Local_Date_Time == null) ? "" : DateTimeFormatter.ofPattern("yyyy-MM-dd").format(_Local_Date_Time);

                // Return the formatted date wrapped in a SimpleStringProperty
                return new SimpleStringProperty(_Date_Formatted);
            }
        });
        
        _Start_Date_Column.setPrefWidth(80);

        System.out.println("################################################");

        TableColumn<Appointment, String> _TableColumn_Appointments2 = new TableColumn<>("Start Time");

        // Define the cell value factory using an anonymous iner class
        _TableColumn_Appointments2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> _CellDataFeatures) {
                // Retrieve the start time of the apointment
                LocalDateTime _LocalDateTime2 = _CellDataFeatures.getValue().get_start_time();

                // Format the start time using the custom formating method
                String _Time_Formatted = _Scene_Transitions._format_time_2(_LocalDateTime2);

                // Return the formatted time as a SimpleStringProperty
                return new SimpleStringProperty(_Time_Formatted);
            }
        });
        
        _TableColumn_Appointments2.setPrefWidth(90);
        
        System.out.println("******************************************");

        TableColumn<Appointment, String> _End_Date = new TableColumn<>("End Date");

        // Define the cell value factory using an anonymous inner class
        _End_Date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> _TableColumn_CellDataFeatures) {
                // Retrieve the end date of the appointment
                LocalDateTime _LocalDateTime = _TableColumn_CellDataFeatures.getValue().get_end_time();

                // Format the end date using the custom formatting method
                String _String = _Scene_Transitions.formatDate(_LocalDateTime);

                // Return the formatted date as a SimpleStringProperty
                return new SimpleStringProperty(_String);
            }
        });

        _End_Date.setPrefWidth(70);
        
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        TableColumn<Appointment, String> _End_Time = new TableColumn<>("End Time");

        // Define the cell value factory using an anonymous inner class
        _End_Time.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> _TableColumn_CellDataFeatures1) {
                // Retrieve the end time of the appointment
                LocalDateTime _LocalDateTime1 = _TableColumn_CellDataFeatures1.getValue().get_end_time();

                // Format the end time using the custom formatting method
                String _String1 = _Scene_Transitions._format_time_2(_LocalDateTime1);

                // Return the formatted time as a SimpleStringProperty
                return new SimpleStringProperty(_String1);
            }
        });

        _End_Time.setPrefWidth(99);
        
        TableColumn<Appointment, Integer> _Contact_ID = createColumn("Contact_ID", "_contact_ID", 120);
        TableColumn<Appointment, Integer> _Customer_ID = createColumn("Customer_ID", "_customer_ID", 75);
        TableColumn<Appointment, Integer> _User_ID = createColumn("User_ID", "_user_ID", 75);
        

        // Add all specified columns to the TableView for displaying appointments
        Collections.addAll(
            _TableView_Appointments.getColumns(), // Get the columns list from the TableView and add to it
            _Appt_ID,                    // Column for Apointment ID, used as a unique identifier for each appintment
            _Title,                      // Column for the Title of the appontment
            _Description,                // Column for the Description, providing details about the appointment
            _Locaiton,                   // Column for the Location, indicating where the appointment is scheduled
            _Type,                       // Column for the Tpe, classiying the apointment (e.g., "Meeting", "Consultation")
            _Start_Date_Column,          // Column for the Start Date, showing the beginning date of theappointment
            _TableColumn_Appointments2,  // Second table column, could represent an additional appointment attribute
            _End_Date,                   // Column for the End Date, showing when the appointment concludes
            _End_Time,                   // Column for the End Time, providing the spcific end time for the appointment
            _Contact_ID,                 // Column for Contact ID, referencing the related contact for the appointment
            _Customer_ID,                // Column for Customer ID, linking the appointment to a specific customer
            _User_ID                     // Column for User ID, identifying the user responsible for te appointment
        );

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        return _TableView_Appointments;
    }

    
    /**
         * Creates a function that formats a Customer's display name.
         * This function is used to map each Customer object to a speicfic display string format
         * that inclueds both the Customer ID and the Customer Name, separated by " - ".
         * 
         * @return a Function that converts a Customer object to a formatted display string.
         */
        private static Function<Customer, String> _get_display_name() {
            // Returns a new instance of a Function that specifies how to format a Customer object.
            return new Function<Customer, String>() {
                /**
                 * Applies this function to the given Customer.
                 *
                 * @param customer the customer whose display name format is to be retrieved.
                 * @return a String that combines the Customer ID and name in the format: "ID -Name".
                 */
                @Override
                public String apply(Customer customer) {
                    // Format the display name by combining Customer ID and Customer Name
                    return customer.get_ID() + " - " + customer.get_name();
                }
            };
        }

        /**
         * Populates a ChoiceBox with a formatted list of all available customers.
         * This method leverages a custom display name formater (from _get_display_name) and a function to load
         * all Customer objects into the ChoiceBox with a formatted display.
         *
         * @param _Choice_Box the ChoiceBox to populate with Cusotmer objects.
         * @return an integer indicating the completion status of the operation (1 for success).
         */
        public static int _Choice_Box_3(ChoiceBox<Customer> _Choice_Box) {

            // Loads customers into the ChoiceBox with a specified display format.
            _choice_box_functionality(_Choice_Box, _get_display_name(), _DataBase_customers._get_every_customer());

            // Log statement indicating that the ChoiceBox population has run.
            System.out.println("Ran load choice box #3.");

            // Return 1 to indicate successful execution.
            return 1;
        }


    /**
     * Creates a function that format a User's display name.
     * This function maps each User objects to a display string format 
     * combning the User ID and User Name with a " - " separator.
     *
     * @return a Function that converts a User object to a formatted display string.
     */
    private static Function<User, String> _user_display_name() {
        // Returns a new Function instance to format a User object.
        return new Function<User, String>() {
            /**
             * Applies this function to the givn User.
             *
             * @param user the User who display name format is to be retrieved.
             * @return a String that combines the User ID and name in the format: "ID - Name".
             */
            @Override
            public String apply(User user) {
                return user.get_id() + " - " + user.get_username();
            }
        };
    }

    /**
     * Populates a ChoicBox with a formatted list of all available users.
     * Uses a custom display name formatter and a function to load all User objects
     * into the ChoiceBox with a specific format.
     *
     * @param _Choice_Box the ChoiceBox to poplate with User objects.
     */
    public static int _Choice_Box_4(ChoiceBox<User> _Choice_Box) {
        _choice_box_functionality(_Choice_Box, _user_display_name(), _Database_profiles._get_profiles());
        
        System.out.println("Ran choice box #4444444");
        
        return 4;
    }



    /**
     * Displays a logout confirmation dialog. If confirmed, transitions the current view to the login screen.
     *
     * @param _Stage the current stage of the aplication where the view transition will occur.
     * @throws IOException if the transition to the login view fails.
     */
    public static int _Logout_Button_Control(Stage _Stage) throws IOException {
        // Show confirmation dialogue with customizd title, header, and message
        Alert _Alert = new Alert(Alert.AlertType.CONFIRMATION);
        _Alert.setTitle("Confirming Logout Request");
        _Alert.setHeaderText("Currently Logging Out");
        _Alert.setContentText("Do you really want to log out?");

        // Display the alet and capture the user's response
        Optional<ButtonType> _Optional = _Alert.showAndWait();

        // Check if the user confirmed the logout action
        if (_Optional.isPresent() && _Optional.get() == ButtonType.OK) {
            // Perform view transition to the login screen
            try {
                _Scene_Transitions._change_view("Login", "/main/_View_Login.fxml", _Stage);
            } catch (IOException _SQL_Exception) {
                // Log the exception and rethrow it for handling by calling code
                System.err.println("Error transitoning to the login view: " + _SQL_Exception.getMessage());
                throw _SQL_Exception;
            }
        }
        
        System.out.println("LOGGED OUT!!!!!!!!!!!");
        
        return 1000;
    }



    public static int _Exit_Control() {
        // Set up the confirmtion alert with title, header, and message
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setContentText("Are you sure you want to exit?");
        exitAlert.setTitle("Exit Confirmation");
        exitAlert.setHeaderText("Exit Application");


        // Show the alert and capture the user's response
        Optional<ButtonType> userResponse = exitAlert.showAndWait();

        // If the user confirms, terminate the connection
        if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
            // Attempt to close
            try {
                database._Java_Connection._terminate();
            } catch (Exception e) {
                System.err.println("Error terminating the database connection: " + e.getMessage());
            } finally {
                // Exit the app
                System.exit(0);
            }
        }
        
        System.out.println("EXIT BUTTON CLICKED ! ! ! ! ! !");
        
        return 1;
    }
    
    private static <T> TableColumn<Appointment, T> createColumn(String title, String property, int width) {
        TableColumn<Appointment, T> _TableColumn = new TableColumn<>(title);
        _TableColumn.setCellValueFactory(new PropertyValueFactory<>(property));
        _TableColumn.setPrefWidth(width);
        return _TableColumn;
    }

    /**
     * A helper function to create a TableColumn for the Appointment class with specified parameters.
     * This function reduces redundancy when creating multiple columns with similar properties.
     *
     * @param <T>       the type of data that the column will hold, such as String, Integer, etc.
     * @param title     the title of the column as displayed in the TableView header.
     * @param property  the name of the property in the Appointment class associated with this column.
     * @param width     the preferred width of the column in the TableView.
     * @return          a configured TableColumn of type T for displaying Appointment data.
     */
    public static <T> TableColumn<Appointment, T> _Create_Column_Helper_Function(String title, String property, int width) {
        // Create a new TableColumn with the specified title
        TableColumn<Appointment, T> tableColumn = new TableColumn<>(title);

        // Set the cell value factory to bind the column to the specified property of Appointment objects
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(property));

        // Set the preferred width for this column
        tableColumn.setPrefWidth(width);
        
        System.out.println("%%%%%% Helper ran.");

        // Return the configured TableColumn
        return tableColumn;
    }
 
    
    /**
     * Creates and configures a TableColumn for the Customer TableView.
     * This method is designed to streamline the setup of TableColumns by specifying 
     * the column title, the property to bind, and the preferred width.
     * 
     * @param <T>      the data type of the TableColumn (e.g., Integer, String)
     * @param _String1 the title of the column as displayed in the TableView header
     * @param _String2 the property name in the Customer class that this column will display
     * @param _Width   the preferred width of the column
     * @return         a fully configured TableColumn<Customer, T> ready for use in the TableView
     */
    private static <T> TableColumn<Customer, T> _Customer_View_Columns_Load(String _String1, String _String2, int _Width) {
        // Initialize a new TableColumn with the provided title (_String1)
        TableColumn<Customer, T> _TableColumn = new TableColumn<>(_String1);

        // Set the cell value factory to bind the column to the specified Customer property (_String2)
        _TableColumn.setCellValueFactory(new PropertyValueFactory<>(_String2));

        // Set the preferred width of the column to the provided value (_Width)
        _TableColumn.setPrefWidth(_Width);

        // Return the configured TableColumn
        return _TableColumn;
    }


    /**
     * Initalizes and configures a TableView for displaying Customer information.
     * This method sets up each column within the TableView, defining the title, data bining, 
     * and preferred width for various Customer attributes, including ID, name, address, postal code, 
     * phone, division, and country.
     * 
     * @return a fully configured TableView<Customer> for displaying customer data in a structured format.
     */
    public static TableView<Customer> _Customer_Table_Load() {
        // Initialize a new TableView for Customer data
        TableView<Customer> customerTable = new TableView<>();

        // Create and configre each TableColumn for specific Customer attributes
        // Column for Division Name, showing the customers division with a width of 95
        TableColumn<Customer, Integer> _TableColumn6 = _Customer_View_Columns_Load("Division_Name", "_division_name", 95);
        
        // Column for Customer Nam, displaying the customer's name with a width of 160
        TableColumn<Customer, String> _TableColumn2 = _Customer_View_Columns_Load("Name", "_name", 160);

        // Column for Customer Address, showing the customer's address with a width of 170
        TableColumn<Customer, String> _TableColumn3 = _Customer_View_Columns_Load("Address", "_address", 170);

        // Column for Customr Phone Number, showing contact information with a width of 95
        TableColumn<Customer, String> _TableColumn5 = _Customer_View_Columns_Load("Phone", "_phone_number", 95);
        
        // Column for Customer ID, uniquely identifying each customer with a width of 95
        TableColumn<Customer, Integer> _TableColumn1 = _Customer_View_Columns_Load("Customer ID", "_ID", 95);
        
        // Column for Customer Country, displaying the customers country with a width of 95
        TableColumn<Customer, String> _TableColumn7 = _Customer_View_Columns_Load("Country", "_country_name", 95);

        // Column for Customer Postal Code, dispaying postal code information with a width of 95
        TableColumn<Customer, String> _TableColumn4 = _Customer_View_Columns_Load("Postal Code", "_post_code", 95);

        // Add all columns to the TableView for customers, structuring the TableView layout
        // Add all configured columns to the customerTable's columns list
        Collections.addAll(
            customerTable.getColumns(),  // Retrieve the columns list from customerTable and add the following columns to it:
            _TableColumn1,               // Column for displaying Customer ID
            _TableColumn2,               // Column for displaying Customer Name
            _TableColumn3,               // Colun for dsplaying Customer Address
            _TableColumn4,               // Column for displaying Customer Postaal Code
            _TableColumn5,               // Column for displaying Customer Phone Number
            _TableColumn6,               // Column for displaying Customer Division Name
            _TableColumn7                // Colum for displaying Customer Country
        );

        // Log a message indicating that the customer table has been loaded
        System.out.println("_Customer_Table_Load function loaded. @@@@@@@@%%%%@@@@@@@@@@@@@@@@@@@@@");

        // Return the fully configured TableView for dispaying Customer data
        return customerTable;
    }
    
    // Helper method to craete a TableColumn wiht a SimpleSringProperty for String values
    private static <S> TableColumn<S, String> _TableColumn_String_Create(String _String1, int _Integer1, Function<S, String> _Function) {
        TableColumn<S, String> _TableColumn1 = new TableColumn<>(_String1);
        _TableColumn1.setCellValueFactory(_TableColumn_CellDataFeatures -> new SimpleStringProperty(_Function.apply(_TableColumn_CellDataFeatures.getValue())));
        _TableColumn1.setPrefWidth(_Integer1);
        return _TableColumn1;
    }

    // Helper method to create a TableColumn with a SimpleIntegeProperty for Integer values
    private static <S> TableColumn<S, Integer> _TableColumn_Integer_Create(String _String2, int _Integer2, Function<S, Integer> _Function2) {
        TableColumn<S, Integer> _TableColumn2 = new TableColumn<>(_String2);
        _TableColumn2.setCellValueFactory(_TableColumn_CellDataFeatures2 -> new SimpleIntegerProperty(_Function2.apply(_TableColumn_CellDataFeatures2.getValue())).asObject());
        _TableColumn2.setPrefWidth(_Integer2);
        return _TableColumn2;
    }

    /**
     * creates and Returns a TableView for displaying monthly appointment data.
     *  <p>
     *  the TableView includes three columns:
     * <ul>
     *     <li><b>month  of Appointment:</b>  displays the month of each appointment.</li>
     *     <li><b>Appointment Type:</b>  displays the type of appointment.</li>
     *     <li><b>Appointment  Tally:</b>   Displays the count of appointments for each month.</li>
     * </ul>
     *  </p>
     *
     * <h1> LAMBDA FUNCTION #2 </h1>
     *  <p>
     * The function uses a lambda expression to encapsulate the setup of columns in the TableView.
     * The lambda organizes the creation and addition of columns into a single, reusable block of code,
     * improving modularity and reducing redundancy. The columns are configured to display specific 
     * fields from the underlying data using method references and inline lambdas for clarity.
     *  </p>
     *
     * @return a tableView configured to display monthly appointment data.
     */
    public static TableView<Pair<String, _Triplet<String, Integer>>> _Month_Count_Report() {
        // intiaalize a new TableView to display pairs of appointment data by month
        TableView<Pair<String, _Triplet<String, Integer>>> _TableView1 = new TableView<>();

        System.out.println("Initializing _TableView1 for displaying monthly appointment data.");

        // Use a lambda to encapsulate column creation
        Runnable setupColumns = () -> {
            // create a column for the month using Pair::getKey
            TableColumn<Pair<String, _Triplet<String, Integer>>, String> _TableColumn3 = _TableColumn_String_Create(
                "Month of Appointment", 200, Pair::getKey
            );

            // create a column for the appointment type using a lambda expression
            TableColumn<Pair<String, _Triplet<String, Integer>>, String> _TableColumn7 = new TableColumn<>("Appointment Type");
            _TableColumn7.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getValue().getKey())
            );
            _TableColumn7.setPrefWidth(200);

            // ereate a column for the appointment tally using a lambda
            TableColumn<Pair<String, _Triplet<String, Integer>>, Integer> _TableColumn5 = _TableColumn_Integer_Create(
                "Appointment Tally", 120, pair -> pair.getValue().getValue()
            );

                    // add all created columns to the TableView in a structured format
            Collections.addAll(_TableView1.getColumns(), _TableColumn5, _TableColumn7, _TableColumn3);

            System.out.println("Columns added to _TableView1: Month, Appointment Type, and Tally.");
        };

        // Execute the lambda function to set up the columns
        setupColumns.run();

        System.out.println("TableView setup complete!");

        // return the configured TableView with all columns added
        return _TableView1;
    }

    
}
