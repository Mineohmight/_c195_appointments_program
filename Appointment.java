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
 * Represents an Appointment with various details like ID, title, description, and times.
 * @author Riley O'Donnell
 */
public class Appointment {

    public String _priority, _location, _status, _description, _type, _title;
    public int _contact_ID, _duration, _customer_ID, _user_ID, _ID;
    public LocalDateTime _time_modified, _end_time, _created_on, _start_time;


    /** @return appointment ID */
    public int get_ID() {
        return _ID;
    }

    /** @return appointment title */
    public String get_title() {
        return _title;
    }

    /** Sets the contact ID */
    public void set_contact_ID(int _int) {
        this._contact_ID = _int;
    }

    /** @return  appointment end time */
    public LocalDateTime get_end_time() {
        return _end_time;
    }

    /** Sets the customer ID */
    public void set_customer_ID(int _int) {
        this._customer_ID = _int;
    }

    /** @return user ID */
    public int get_user_ID() {
        return _user_ID;
    }

    /** Sets the  end time of the appointment */
    public void set_end_time(LocalDateTime _LocalDateTime) {
        this._end_time = _LocalDateTime;
    }

    /** @return contact ID */
    public int get_contact_ID() {
        return _contact_ID;
    }
    

    /** @return  appointment location */
    public String get_location() {
        return _location;
    }

    /** @return appointment description */
    public String get_description() {
        return _description;
    }

    /** Sets the location of the appointment */
    public void set_location(String _String) {
        this._location = _String;
    }

    /** Sets the start time of the appointment */
    public void set_start_time(LocalDateTime _LocalDateTime) {
        this._start_time = _LocalDateTime;
    }

    /** Sets the title of the appointment */
    public void set_title(String _title) {
        this._title = _title;
    }

    /** Sets the appointment ID */
    public void set_ID(int _int) {
        this._ID = _int;
    }

    /** @return Start time of the appointment */
    public LocalDateTime get_start_time() {
        return _start_time;
    }

    /** @return customer ID */
    public int get_customer_ID() {
        return _customer_ID;
    }

    /** Sets the description of the appointment */
    public void set_description(String _String) {
        this._description = _String;
    }

    /** Sets the type of the appointment */
    public void set_type(String _String) {
        this._type = _String;
    }

    /** Sets the user ID */
    public void set_user_ID(int _int) {
        this._user_ID = _int;
    }

    /** @return type of the appointment */
    public String get_type() {
        return _type;
    }
    
    /**
     * Constructor to initialize Appointment with basic details.
     */
    public Appointment(String _description, int _user_ID, int _customer_ID, int _ID, String _title, LocalDateTime _end_time, String _type, String _location, int _contact_ID, LocalDateTime _start_time) {
        this._location = _location;
        this._user_ID = _user_ID;
        this._description = _description;
        this._title = _title;
        this._start_time = _start_time;
        this._customer_ID = _customer_ID;
        this._contact_ID = _contact_ID;
        this._type = _type;
        this._ID = _ID;
        this._end_time = _end_time;
    }
}

