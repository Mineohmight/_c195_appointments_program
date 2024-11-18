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
import javafx.event.EventHandler;
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
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.event.Event;
import java.util.concurrent.Executors;

/**
 * Handles the login functionality and user interface interactions for the Login screen.
 * Tracks login activity to improve user experience and security.
 *
 * @author Riley  O'Donnell
 */
public class _Login_Controller implements Initializable {
    
    /**
     * initializes the login interface with localized language support based on the user's system language settings.
     * the default language is set to either french or english. allows dynamic language switching
     *
     * @param _URL            the location used to resolve relative paths for the root object, or null if location is not known
     * @param __ResourceBundle the resources used to localize the root object, or null if not specified
     */
    @Override
    public void initialize(URL _URL, ResourceBundle __ResourceBundle) {
         // set the resource bundle to french if the system's language is set to "fr", otherwise default to english
        _ResourceBundle = Locale.getDefault().getLanguage().equalsIgnoreCase("fr") 
            ? ResourceBundle.getBundle("_Program_Language", Locale.FRENCH) 
            : ResourceBundle.getBundle("_Program_Language", Locale.ENGLISH);

         // update the ui labels and buttons with text from the resource bundle
        _Label5.setText(_ResourceBundle.getString("_enter_password"));
        _Button1.setText(_ResourceBundle.getString("_close_application"));
        _Label7.setText(_ResourceBundle.getString("_log_in_to_access"));
        _Button2.setText(_ResourceBundle.getString("_sign_in"));
        _Label2.setText(String.valueOf(_Default_Time_Zone)); // display current timezone
        _Label1.setText(_ResourceBundle.getString("_enter_username"));
        _Label8.setText(_ResourceBundle.getString("_select_language"));
        _Label3.setText(null); // clear any previous login error messages
        _Label6.setText(_ResourceBundle.getString("_current_time_zone"));
        _Label4.setText(_ResourceBundle.getString("_welcome_"));

         // add language optons to the combo box for user selection
        _ComboBox.getItems().addAll("English", "Francais");

            // set combo box default based on system language, selecting "francais" if the system is set to french
        _ComboBox.setValue(Locale.getDefault().getLanguage().equalsIgnoreCase("fr") ? "Francais" : "English");

        // event handler for when the user selects a language from the combo box
        _ComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 // update the resource bundle based on the selected language from the combo box
                _ResourceBundle = ResourceBundle.getBundle("_Program_Language", "Francais".equals(_ComboBox.getValue()) 
                    ? Locale.FRENCH 
                    : Locale.ENGLISH);

                    // update all ui elements to reflect the newly selected language in real time
                _Label5.setText(_ResourceBundle.getString("_enter_password"));
                _Label8.setText(_ResourceBundle.getString("_select_language"));
                _Label6.setText(_ResourceBundle.getString("_current_time_zone"));
                _Button1.setText(_ResourceBundle.getString("_close_application"));
                _Label2.setText(String.valueOf(_Default_Time_Zone));
                _Label1.setText(_ResourceBundle.getString("_enter_username"));
                _Label7.setText(_ResourceBundle.getString("_log_in_to_access"));
                _Button2.setText(_ResourceBundle.getString("_sign_in"));
                _Label3.setText(null); // clear any previous login error messages
                _Label4.setText(_ResourceBundle.getString("_welcome_"));
            }
        });
    }

    @FXML public Button _Button1, _Button2;
    @FXML public Label _Label1, _Label2, _Label3, _Label4, _Label5, _Label6, _Label7, _Label8;
    public ResourceBundle _ResourceBundle;
    @FXML public TextField _TextField1;
    
    /**
     * This method handles the application's exit process when the exit button is pressed.
     *
     * @return int Returns 0 after initiating the shutdown
     */
    @FXML
    public int _exit_button_function() {
        // Log that the exit button has been pressed by the user
        System.out.println("User has selected the exit button, initiating exit process.");

        // llog that the application resoorces are in the process of termination
        System.out.println("All application resources have been successfully terminated, preparing to close.");

        // Terminate the Java connection and any other resources tied to the app
        database._Java_Connection._terminate();

        System.out.println("The application is now shutting down and exiting the program.");

        // exit the program with status code 0 to signify a controlled shutdown
        System.exit(0);
        
        return 0;

    }
    
    public ZoneId _Default_Time_Zone = ZoneId.systemDefault();
    @FXML public PasswordField _PassWordField;
    public LocalTime _CurrentTime = LocalTime.now();
    @FXML public ComboBox<String> _ComboBox;    
    public LocalDate _CurrentDate = LocalDate.now();
    @FXML public Slider _VolumeSlider;
    @FXML public CheckBox _TermsCheckBox;

    /**
     * Handles the login action when the login button is pressed.
     * Validates user input for username and password fields and checks authentication.
     * Displays appropriat error messages and logs login attempts for auditing.
     *
     * @param event ActionEvent triggered by the login button press.
     * @throws IOException if an error occurs during view transition.
     */
    @FXML
    public void _login_button_function(ActionEvent event) throws IOException {
        // Retreive user input from text fields
        String username = _TextField1.getText();
        String password = _PassWordField.getText();
        System.out.println("Debug: Username entred - " + username); // Debbuging output for username
        System.out.println("Debug: Password enterd - " + (password.isBlank() ? "[Blank]" : "[Enterd]")); // Debuging output for password

        // Chek for both fields being blank
        if (username.isBlank() && password.isBlank()) {
            System.out.println("Debug: Both fields are blank."); // Debgging output
            _Label3.setText(_ResourceBundle.getString("_username_and_password_are_required_"));
        }
        // Chek for username being blank
        else if (username.isBlank()) {
            System.out.println("debug: Username is blank."); // Debuging output
            _Label3.setText(_ResourceBundle.getString("_enter_a_username_to_continue"));
        }
        // Chek for password being blank
        else if (password.isBlank()) {
            System.out.println("Debug: Password is blank."); // Debuggin output
            _Label3.setText(_ResourceBundle.getString("_password_is_required_"));
        }
        // Chek if username exists in the database
        else if (!_Database_profiles._check_if_username_exists(username)) {
            System.out.println("Debug: Username not found - " + username); // Debbugging output
            _Label3.setText(_ResourceBundle.getString("_no_account_found_with_this_username"));
            
            
            String _string_to_write = "\nUser " + _TextField1.getText() + " could not log in at time " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss")) + "";

            try (FileWriter _FileWriter = new FileWriter("login_activity.txt", true)) {
                _FileWriter.write(_string_to_write);
            }
        }
        // Chek if the provided username and password match
        else if (!_Database_profiles._login(username, password)) {
            System.out.println("debug: Username and password do not match."); // Debuggin output
            _Label3.setText(_ResourceBundle.getString("_the_username_and_password"));
            
            
            String _string_to_write = "\nUser " + _TextField1.getText() + " could not log in at time " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss")) + "";

            try (FileWriter _FileWriter = new FileWriter("login_activity.txt", true)) {
                _FileWriter.write(_string_to_write);
            }
        }
        
        
        // succesful login case
        else {
            System.out.println("Debug: Login sucessful."); // debuggin output
            _Label3.setText(null); // clear any error messages
            // transitoin to the appointments view
            _Scene_Transitions._change_view("View Appointments", "/main/_View_Appointment.fxml", (Stage)((Button) event.getSource()).getScene().getWindow());
            
            String _string_to_write = "\nUser " + _TextField1.getText() + " logged in at time " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss")) + "";

            try (FileWriter _FileWriter = new FileWriter("login_activity.txt", true)) {
                _FileWriter.write(_string_to_write);
            }
            
            String _appointment_strings = "In the next 15 minutes, you have these appointments-\n";

            if (_Database_appointment_class._next_fiften_minutes_function().size() == 0) 
                _Scene_Transitions._alert_2("There are no upcoming appointments.", "");
            else {

                for (Appointment _Appointment : _Database_appointment_class._next_fiften_minutes_function()) {

                _appointment_strings += 
                         "\n\n\t Day, Time: " + _Appointment.get_start_time().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ", " + _Appointment.get_start_time().format(DateTimeFormatter.ofPattern("hh:mm")) +
                            "\n\tAppointment #: " + _Appointment.get_ID() + "\n\n";
                }
                _Scene_Transitions._alert_2("Your Coming Appointments-\n", _appointment_strings);
            }
        }
        
        System.out.println("LocalTime.now(): " + LocalTime.now());
    }



}