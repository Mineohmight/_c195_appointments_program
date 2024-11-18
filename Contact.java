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
 * Represents a Contact with personal and appointment-related information.
 * @author Riley O'Donnell
 */
public class Contact {


    private String _name;
    private String _address;
    private String _phoneNumber;        // Contact phone number
    private String _email;              // Contact email address
    private String _notes;              // Additional notes for the appointment
    private String _appointmentStatus;  // Status of the appointment
    private LocalDateTime _createdOn;   // Creation timestamp
    private LocalDateTime _lastUpdated; // Last update timestamp
    private int _ID;
    private String _priorityLevel;      // Priority level
    private String _assignedTo;         // Person responsible for the appointment


    /**
     * Sets the contact's name.
     * 
     * @param _String New name for the contact
     */
    public void set_name(String _String) {
        this._name = _String;
    }

    /** @return contact ID */
    public int get_ID() {
        return _ID;
    }

    /** @return contact address */
    public String get_address() {
        return _address;
    }

    /**
     * Sets the contact's address.
     * 
     * @param _String New address for the contact
     */
    public void set_address(String _String) {
        this._address = _String;
    }

    /**
     * Sets the contact's ID.
     * 
     * @param _int New ID for the contact
     */
    public void set_ID(int _int) {
        this._ID = _int;
    }

    /** @return contact name */
    public String get_name() {
        return _name;
    }

    /**
     * Prints all details of the contact.
     */
    public void printDetails() {
        System.out.println("Contact Details:");
        System.out.println("ID: " + _ID);
        System.out.println("Name: " + _name);
        System.out.println("Address: " + _address);
        System.out.println("Phone Number: " + _phoneNumber);
        System.out.println("Email: " + _email);
        System.out.println("Notes: " + _notes);
        System.out.println("Status: " + _appointmentStatus);
        System.out.println("Created On: " + _createdOn);
        System.out.println("Last Updated: " + _lastUpdated);
        System.out.println("Priority Level: " + _priorityLevel);
        System.out.println("Assigned To: " + _assignedTo);
    }
    
    
    /**
     * Constructor to initialize Contact with ID, name, and address.
     * 
     * @param _int ID of the contact
     * @param _name Name of the contact
     * @param _address Address of the contact
     */
    public Contact(String _address, String _name, int _int) {
        this._ID = _int;
        this._name = _name;
        this._address = _address;
    }
}

