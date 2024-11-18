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
 * Represents a Customer with contact and location information, as well as organizational details.
 * @author Riley O'Donnell
 */
public class Customer {


    /**
     * Prints all details of the customer to the console.
     */
    public void printAllDetails() {
        System.out.println("Customer Details:");
        System.out.println("ID: " + _ID);
        System.out.println("Name: " + _name);
        System.out.println("Address: " + _address);
        System.out.println("Post Code: " + _post_code);
        System.out.println("Phone Number: " + _phone_number);
        System.out.println("Division ID: " + _division_id);
        System.out.println("Division Name: " + _division_name);
        System.out.println("Country ID: " + _country_id);
        System.out.println("Country Name: " + _country_name);
        System.out.println("City: " + _city);
        System.out.println("Region Code: " + _region_code);
        System.out.println("Email: " + _email);
        System.out.println("Contact Person: " + _contact_person);
    }
    
    private String _country_name;    // Name of the country
    private int _ID;             
    private String _name;       
    private String _city;      
    private String _division_name;   // Division name within the region
    private int _region_code;        
    private String _phone_number;    // ccustomer's phone number
    private int _division_id;     
    private String _address;        
    private String _email;         
    private int _country_id;         
    private String _post_code;      
    private String _contact_person;  // Primary contact person for customer


    /**
     * Constructor to initialize a customer with basic details, defaulting unspecified fields.
     * 
     * @param _name Name of the customer
     * @param _address Address of the customer
     * @param _ID Unique identifier for the customer
     * @param _phone_number Customer’s phone number
     */
    public Customer(String _name, String _address, int _ID, String _phone_number) {
        this._name = _name;
        this._address = _address;
        this._ID = _ID;
        this._phone_number = _phone_number;
        this._post_code = "0000";
        this._country_id = 1;
        this._division_id = 1;
        this._country_name = "Unknown";
        this._division_name = "Unknown";
    }

    /**
     * Constructor for partial details with specified phone number, postal code, ID, division, name, and address.
     * 
     * @param _phone_number Customer’s phone number
     * @param _post_code Postal code for the customer’s address
     * @param _ID Unique identifier for the customer
     * @param _division_id Division ID within the country
     * @param _name Name of the customer
     * @param _address Address of the customer
     */
    public Customer(String _phone_number, String _post_code, int _ID, int _division_id, String _name, String _address) {
        this._ID = _ID;
        this._name = _name;
        this._address = _address;
        this._post_code = _post_code;
        this._phone_number = _phone_number;
        this._division_id = _division_id;
    }
    
    /**
     * Constructor to initialize a customer with complete details.
     * 
     * @param _name Name of the customer
     * @param _post_code Postal code for the customer’s address
     * @param _address Address of the customer
     * @param _country_id Country ID where the customer is located
     * @param _division_id Division ID within the country
     * @param _phone_number Customer’s phone number
     * @param _ID Unique identifier for the customer
     * @param _country_name Name of the country
     * @param _division_name Name of the division
     */
    public Customer(String _name, String _post_code, String _address, int _country_id, int _division_id, String _phone_number, int _ID, String _country_name, String _division_name) {
        this._division_id = _division_id;
        this._country_name = _country_name;
        this._address = _address;
        this._name = _name;
        this._ID = _ID;
        this._post_code = _post_code;
        this._division_name = _division_name;
        this._phone_number = _phone_number;
        this._country_id = _country_id;
    }

    /**
     * Sets the customer's phone number.
     * 
     * @param _S Phone number to set
     */
    public void set_phone_number(String _S) { this._phone_number = _S; }

    /**
     * @return The name of the customer's division
     */
    public String get_division_name() { return _division_name; }

    /**
     * Sets the customer's ID.
     * 
     * @param _int ID to set
     */
    public void set_ID(int _int) { this._ID = _int; }

    /**
     * @return The address of the customer
     */
    public String get_address() { return _address; }

    /**
     * @return The ID of the customer's division
     */
    public int get_division_id() { return _division_id; }

    /**
     * Sets the customer's name.
     * 
     * @param _String Name to set
     */
    public void set_name(String _String) { this._name = _String; }

    /**
     * @return The unique ID of the customer
     */
    public int get_ID() { return _ID; }

    /**
     * Sets the customer's country name.
     * 
     * @param _S Country name to set
     */
    public void set_country_name(String _S) { this._country_name = _S; }

    /**
     * Sets the customer's division ID.
     * 
     * @param _int Division ID to set
     */
    public void set_division_id(int _int) { this._division_id = _int; }

    /**
     * @return The postal code of the customer's address
     */
    public String get_post_code() { return _post_code; }

    /**
     * Sets the customer's address.
     * 
     * @param _String Address to set
     */
    public void set_address(String _String) { this._address = _String; }

    /**
     * @return The name of the customer
     */
    public String get_name() { return _name; }

    /**
     * @return The country ID of the customer's location
     */
    public int get_country_id() { return _country_id; }

    /**
     * @return The phone number of the customer
     */
    public String get_phone_number() { return _phone_number; }

    /**
     * Sets the customer's postal code.
     * 
     * @param _String Postal code to set
     */
    public void set_post_code(String _String) { this._post_code = _String; }

    /**
     * Sets the customer's division name.
     * 
     * @param _S Division name to set
     */
    public void set_division_name(String _S) { this._division_name = _S; }

    /**
     * @return The country name of the customer's location
     */
    public String get_country_name() { return _country_name; }

    /**
     * Sets the customer's country ID.
     * 
     * @param _int Country ID to set
     */
    public void set_country_id(int _int) { this._country_id = _int; }
}
