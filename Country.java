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
 * Represents a Country with general information and additional details.
 */
public class Country {

       
    private String _name;              
    private String _address;           
    private String _phoneNumber;       // Contact phone number for the country
    private String _email;             // Contact email address for the country
    private String _notes;             // Additional notes or comments
    private String _company;           
    private String _jobTitle;          // Job title for a related contact
    private String _department;        
    private int _id;            
    private LocalDateTime _birthday;   
    private boolean _isFavorite;       // Marks country as a favorite location

    /**
     * Constructor to initialize Country with an ID and name.
     * 
     * @param _int Unique identifier for the country
     * @param _String Name of the country
     */
    public Country(int _int, String _String) {
        this._id = _int;
        this._name = _String;
    }

    /**
     * Prints all details of the country, including basic and additional fields.
     */
    public void printAllDetails() {
        System.out.println("Country Details:");
        System.out.println("ID: " + _id);
        System.out.println("Name: " + _name);
        System.out.println("Address: " + _address);
        System.out.println("Phone Number: " + _phoneNumber);
        System.out.println("Email: " + _email);
        System.out.println("Notes: " + _notes);
        System.out.println("Company: " + _company);
        System.out.println("Job Title: " + _jobTitle);
        System.out.println("Department: " + _department);
        System.out.println("Birthday: " + _birthday);
        System.out.println("Favorite: " + (_isFavorite ? "Yes" : "No"));
    }

    /**
     * Sets the country ID.
     * 
     * @param _id New ID for the country
     */
    public void set_id(int _id) {
        this._id = _id;
    }

    /**
     * @return The name of the country
     */
    public String get_name() {
        return _name;
    }

    /**
     * Sets the name of the country.
     * 
     * @param _String New name for the country
     */
    public void set_name(String _String) {
        this._name = _String;
    }

    /**
     * @return The ID of the country
     */
    public int get_id() {
        return _id;
    }
}

