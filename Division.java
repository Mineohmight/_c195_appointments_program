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
 * Represents a Division with detailed contact, location, and status information.
 * @author Riley O'Donnell
 */
public class Division {


    /**
     * Constructs a Division with ID, name, and region.
     * 
     * @param _id Unique ID of the division
     * @param _name Name of the division
     * @param _region Region of the division
     */
    public Division(int _id, String _name, String _region) {
        this._id = _id;
        this._name = _name;
        this._region = _region;
    }

    /**
     * Constructs a Division with country ID, name, and division ID.
     * 
     * @param _country_id Country ID associated with the division
     * @param _name Name of the division
     * @param _id Unique ID of the division
     */
    public Division(int _country_id, String _name, int _id) {
        this._country_id = _country_id;
        this._name = _name;
        this._id = _id;
    }

    /**
     * Constructs a Division with an ID and name.
     * 
     * @param _id Unique ID of the division
     * @param _String Name of the division
     */
    public Division(int _id, String _String) {
        this._id = _id;
        this._name = _String;
    }

    /**
     * Sets the country ID for the division.
     * 
     * @param _int Country ID to set
     */
    public void set_country_id(int _int) {
        this._country_id = _int;
    }

    /**
     * @return The unique ID of the division
     */
    public int get_id() {
        return _id;
    }

    /**
     * @return The name of the division
     */
    public String get_name() {
        return _name;
    }

    /**
     * @return The country ID associated with the division
     */
    public int get_country_id() {
        return _country_id;
    }

    /**
     * Sets the name of the division.
     * 
     * @param _S Name to set for the division
     */
    public void set_name(String _S) {
        this._name = _S;
    }

    /**
     * Sets the unique ID of the division.
     * 
     * @param _int ID to set for the division
     */
    public void set_id(int _int) {
        this._id = _int;
    }
    
    private int _id;                     
    private String _name;                // Name of the division
    private int _country_id;             
    private String _email;               // email address for division contact
    private String _phone_number;       
    private String _address;             // Physical address of the division
    private String _city;              
    private String _state;              
    private String _postal_code;         // Postal code for the division's address
    private String _preferred_contact_method; // Preferred contact method (e.g., email, phone)
    private boolean _is_active;          
    private LocalDateTime _created_at;  
    private LocalDateTime _updated_at;  
    private String _region;              // Region associated with the division

    /**
     * Prints all variables and details associated with the division.
     */
    public void _print_variables() {
        System.out.println("Division Details:");
        System.out.println("ID: " + _id);
        System.out.println("Name: " + _name);
        System.out.println("Country ID: " + _country_id);
        System.out.println("Email: " + _email);
        System.out.println("Phone Number: " + _phone_number);
        System.out.println("Address: " + _address);
        System.out.println("City: " + _city);
        System.out.println("State: " + _state);
        System.out.println("Postal Code: " + _postal_code);
        System.out.println("Preferred Contact Method: " + _preferred_contact_method);
        System.out.println("Active Status: " + (_is_active ? "Active" : "Inactive"));
        System.out.println("Created At: " + _created_at);
        System.out.println("Updated At: " + _updated_at);
    }
}

