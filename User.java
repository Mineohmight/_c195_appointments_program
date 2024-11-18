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
 * Represents a User with ID, username, password, and additional profile and account details.
 * @author Riley O'Donnell
 */
public class User {


    /**
     * Constructor to initialize User with ID, username, and password.
     *
     * @param _int User ID
     * @param _String1 username
     * @param _String2 password
     */
    public User(String _String2, int _int, String _String1) {
        this._username = _String1;
        this._id = _int;
        this._password = _String2;
    }

    /**
     * Constructor to initialize User with username and password.
     *
     * @param _String1 Username
     * @param _String2 Password
     */
    public User(String _String1, String _String2) {
        this._username = _String1;
        this._password = _String2;
    }

    /**
     * Constructor to initialize User with ID and username.
     *
     * @param _id User ID
     * @param _username Username
     */
    public User(int _id, String _username) {
        this._id = _id;
        this._username = _username;
    }

    /**
     * Sets the user's password.
     *
     * @param _S Password to set
     */
    public void set_password(String _S) {
        this._password = _S;
    }

    /**
     * @return The username of the user
     */
    public String get_username() {
        return _username;
    }

    /**
     * @return The unique ID of the user
     */
    public int get_id() {
        return _id;
    }

    /**
     * @return The password of the user
     */
    public String get_password() {
        return _password;
    }

    /**
     * Sets the user ID.
     *
     * @param _int User ID to set
     */
    public void set_id(int _int) {
        this._id = _int;
    }

    /**
     * Sets the username for the user.
     *
     * @param _S Username to set
     */
    public void set_username(String _S) {
        this._username = _S;
    }

    /**
     * @return The email address of the user
     */
    public String get_email() {
        return _email;
    }

    /**
     * Sets the email address for the user.
     *
     * @param _email Email address to set
     */
    public void set_email(String _email) {
        this._email = _email;
    }

    /**
     * @return The role of the user
     */
    public String get_role() {
        return _role;
    }

    /**
     * Sets the role of the user.
     *
     * @param _role Role to set for the user
     */
    public void set_role(String _role) {
        this._role = _role;
    }

    /**
     * @return the active status of the user
     */
    public boolean is_active() {
        return _isActive;
    }

    /**
     * sets the active status of the user.
     *
     * @param _isActive Active status to set
     */
    public void set_active(boolean _isActive) {
        this._isActive = _isActive;
    }

    /**
     * @return the timestamp of user account creation
     */
    public LocalDateTime get_created_at() {
        return _createdAt;
    }

    /**
     * sets the account creation timestamp for the user.
     *
     * @param _createdAt Creation timestamp to set
     */
    public void set_created_at(LocalDateTime _createdAt) {
        this._createdAt = _createdAt;
    }

    /**
     * @return The timestamp of the last user login
     */
    public LocalDateTime get_last_login() {
        return _lastLogin;
    }

    /**
     * Sets the timestamp of the last login for the user.
     *
     * @param _lastLogin Last login timestamp to set
     */
    public void set_last_login(LocalDateTime _lastLogin) {
        this._lastLogin = _lastLogin;
    }

    /**
     * Prints the details of the user.
     */
    public void print_user_details() {
        System.out.println("User Details:");
        System.out.println("ID: " + _id);
        System.out.println("Username: " + _username);
        System.out.println("password: " + _password);
        System.out.println("email: " + _email);
        System.out.println("Role: " + _role);
        System.out.println("Active Status: " + (_isActive ? "Active" : "Inactive"));
        System.out.println("Created At: " + _createdAt);
        System.out.println("Last Login: " + _lastLogin);
    }
    
    private int _id;                       // Unique identifier for the user
    private String _username;              // Username for user login
    private String _password;              // Password for user authentication
    private String _email;                 // User's email address
    private String _role;                  // Role of the user (e.g., admin, user)
    private boolean _isActive;             // Status indicating if the user is active
    private LocalDateTime _createdAt;      // Timestamp of user account creation
    private LocalDateTime _lastLogin;      // Timestamp of last user login
}

