package controller;


import database._Contact_Formatter;
import java.io.IOException;
import database._DataBase_customers;
import javafx.scene.control.Label;
import database._Database_provinces;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import java.sql.SQLException;
import java.net.URL;

import controller.User;
import javafx.scene.control.ToggleGroup;
import database._DataBase_contact_class;
import controller.*;
import javafx.scene.control.*;
import database._DataBase_country;
import javafx.util.StringConverter;
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
 *   _Customer_Controller for the Customer Dialog view ( CustDialog. fxml ), used for adding and modifying customer informatoin.
 *    Handles user interactions within the cust omer form, including input validtaion and saving data to the database.
 *
 *  @author Riley O'Donnell
 */
public class _Customer_Controller implements Initializable {
    

   

    /**
     *  gets the list of countries and configures the choice box with a custom string converter.
     *  this method sets up the country choice box to display country details in a specific format 
     *  and handles converting a srt ing back into a Country object.
     */
    public void _get_countries() {
        // Log that the _get_countries() function has started
        System.out.println("_get_countries() function ran!");

        // efine a custom StringConverter for the Country objects displayed in _ChoiceBox_Country
        StringConverter<Country> _some_string_converter = new StringConverter <> () {

            @Override
            public String toString(Country _Country) {
                //If the country is null, return null, otherwise format the country ID and name as a string
                if (_Country == null) {
                    return null;
                } else if ( 1 == 0)
                {
                    System.out.println("if statement Test");
                } else {
                    _print_Variables();
                    return _Country.get_name() + ": " + _Country.get_id();
                }
                return "ERROR STRING";
            }

            @Override
            public Country fromString(String _String) {
                // iteerate through items in _ChoiceBox_Country to find a matching Country object
                int _while_loop_index = 0;
                
                  // Begin a while loop to iterate through alll items in _ChoiceBox_Country
                while (_while_loop_index < _ChoiceBox_Country.getItems().size()) {

                        // retrieve the Country object at the current index of _ChoiceBox_Country
                    Country _Country_Iterator = _ChoiceBox_Country.getItems().get(_while_loop_index);

                        // concatanete the country name and Id from the Country object to form a display string
                    String _String2 = _Country_Iterator.get_name() + ": " + _Country_Iterator.get_id();

                    // check if the constructed string (_String2) matches the input string (_String)
                    if (!_String2.equals(_String)) {
                           // Log an error message if _String2 does not match _String, implying either a mismatch or null
                        System.out.println("Error, countryString.equals(_String) was probably null or did not match.");
                    } else {
                           // If _String2 matches _String, return the current Country object as the result
                        return _Country_Iterator;
                    }

                    // Increment the inex to move to the next item in _ChoiceBox_Country on the next iteration
                    _while_loop_index++;
                }

                // Return the first item in _ChoiceBox_Country if no match is found, assuming it exists
                if (_ChoiceBox_Country.getItems().get(0) == null) {
                    System.out.println("_ChoiceBox_Country.getItems().get(0) was null");
                } else {
                    return _ChoiceBox_Country.getItems().get(0);
                }

                // return null if no match and no first item is available
                return null;
            }
        };

            // Log that the function has proceeded to the next stage
        System.out.println("_get_countries() function ran! 2");

            // Set the items in _ChoiceBox_Country to the list of cou ntries fetched from the database
        _ChoiceBox_Country.setItems(_DataBase_country._get_countries());

            // Apply the custom string converter to format Country objects in the choice box
        _ChoiceBox_Country.setConverter(_some_string_converter);
    }
    
    @FXML public Label _label1, _label2, _label3, _label4, _label5, _label6, _label7, _label8, _label9, _label10;
    @FXML public ChoiceBox<Division> _ChoiceBox_Division;
    @FXML public DialogPane _DialogPane;
    @FXML public ChoiceBox<Country> _ChoiceBox_Country;
    @FXML public TextField _TextField1, _TextField2, _TextField3, _TextField4, _TextField5;
    public Customer _Customer;
    
    /**
     * Populates the customer form fields with the details of the specified customer.
     *
     * @param customer the Customer object containing data to populate the form fields
     * @return an integer status code
     */
    public int _set_customer(Customer customer) {

        // Log that the setCustomer function has been called
        System.out.println("setCustomer() function called with Customer ID: " + customer.get_ID());

        _TextField3.setText(customer.get_address());
        System.out.println("Customer address set to: " + customer.get_address());

        // set the customer's country in the ChoiceBox by retrieving it from the database
        _ChoiceBox_Country.setValue(
            _DataBase_country._get_country(_Database_provinces._get_province(customer.get_division_id()).get_country_id())
        );
        System.out.println("Customer country set based on division ID: " + customer.get_division_id());

        // Set the customer's ID in the ID TextField
        _TextField1.setText(Integer.toString(customer.get_ID()));
        System.out.println("Customer ID set to: " + customer.get_ID());

        // Set the customer's phone number in the phone TextField
        _TextField5.setText(customer.get_phone_number());
        System.out.println("Customer phone number set to: " + customer.get_phone_number());

        _TextField2.setText(customer.get_name());
        System.out.println("Customer name set to: " + customer.get_name());

        // Set the customer's postal code in the postal code TextField
        _TextField4.setText(customer.get_post_code());
        System.out.println("Customer postal code set to: " + customer.get_post_code());

        _get_counties(customer.get_country_id());
        System.out.println("Counties retrieved for country ID: " + customer.get_country_id());

        // Set the customer's division in the Division ChoiceBox based on division ID
        _ChoiceBox_Division.setValue(_Database_provinces._get_province(customer.get_division_id()));
        System.out.println("Customer division set based on division ID: " + customer.get_division_id());
        
        return 100000;
    }


    /**
     *  retrieves and displays a list of counties (or divisions) based on a given integer paremeter.
     *  sets up a custom "StringConverter' to format division objects in the ChoiceBox for display 
     *
     * @param _int an integer used to query and retrieve specific divisions from the database
     */
    private int _get_counties(int _int) {

        // Call a helper method to print variable values for debugging purposes
        _print_Variables();

        StringConverter<Division> _some_string_converter = new StringConverter<>() {

            @Override
            public String toString(Division _County) {
                // Return null if the Division object is null
                if (_County == null) {
                    return null;
                } 
                else if (1 == 0) {
                    System.out.println("if statement Test");
                } 
                else {
                    return _County.get_id() + ": " + _County.get_name();
                }
                return null;
            }

            @Override
            public Division fromString(String _String3) {
                // Iterate through each Division item in the ChoiceBox
                for (Division _Division : _ChoiceBox_Division.getItems()) {
                    // Construct a formarted String for comparison with _String3
                    String divisionString = _Division.get_id() + ": " + _Division.get_name();

                    // if the string matches, return the corresponding Division object
                    if (divisionString.equals(_String3)) {
                        return _Division;
                    }
                }
                // return null if no match is found
                return null;
            }
        };

        // Reset the selected value in the ChoiceBox to ensure no prior selection is shown
        _ChoiceBox_Division.setValue(null);
        _ChoiceBox_Division.setConverter(_some_string_converter);
        _ChoiceBox_Division.setItems(_Database_provinces._get_provinces_2(_int));
        
        System.out.println("_get_counties(int _int) function ran!");
        
        return 888;
    }


    
    /**
    * Logs debugging information to the console.
    *
    * @param message the message to be logged
    */
   private void _debug_log_old(String message) {
       System.out.println("DEBUG: " + message);
   }
    
    /**
    * enables or disables the division ChoiceBox based on the selected country.
    * adds a listener to the country ChoiceBox to dynamically load counties
    * @return String by default is null
    */
   public String _enable_division_choicebox() {
       
            // Add a listener to the selected item in the country ChoiceBox
       _ChoiceBox_Country.getSelectionModel().selectedItemProperty().addListener((_Listener4, _Listener5, _Listener6) -> {

                // Check if a new country has been selected
            if (_Listener6 == null) 
            {
                    // disbale the division choiceBox if no country is selected
                _ChoiceBox_Division.setDisable(true);

                    // log for debugging: no country selected, division ChoiceBox disabled
                _debug_log("No country selected. Division ChoiceBox disabled.");

            } else {
                    // Load the counties for the selected country and enable the division ChoiceBox
                _get_counties(_Listener6.get_id());
                _ChoiceBox_Division.setDisable(false);

                    // log for debugging: country selection enabled division ChoiceBox
                _debug_log("Country selected: " + _Listener6.get_name() + ". Division ChoiceBox enabled.");
            }
       });
       
       return null;
   }
    
    /**
    * Logs debugging information to the console.
    *
    * @param message the message to be logged
    */
   private void _debug_log(String message) {
       System.out.println("DEBUG: " + message);
   }



   
    /**
     * handles the saving of customer information to the database.
     * if a  customer object exists it updates the customer's fields and commits changes to the database.
     * if no customer object exists, it craetes a new customer record with the current input data.
     * @return int for debugging perpuses
     */
    public int _save_customer() {
        // Check if an existing customer object is present for updating
        if (_Customer != null) {
            System.out.println("DEBUG: Existing customer found. Customer ID: " + _Customer.get_ID());
            System.out.println("Modifying customer information in the database...");

            // Update customer details with the values from input fields
            _Customer.set_division_id(_ChoiceBox_Division.getValue().get_id());
            _Customer.set_post_code(_TextField4.getText());
            _Customer.set_address(_TextField3.getText());
            _Customer.set_phone_number(_TextField5.getText());
            _Customer.set_name(_TextField2.getText());

            System.out.println("DEBUG:  Updated Customer Name: " + _TextField2.getText());
            System.out.println("DEBUG:  Updated Address: " + _TextField3.getText());
            System.out.println("DEBUG:  updated postal Code: " + _TextField4.getText()); 
            System.out.println("DEbUG:  Updated Phone Number: " + _TextField5.getText()); 
            System.out.println("DEBUG:  Updated Division ID: " + _ChoiceBox_Division.getValue().get_id());

            System.out.println("Customer update progress: " + _label10);

            // Commit the updated customer information to the database
            _DataBase_customers._on_customer_change(
                _TextField3.getText(),
                _Customer.get_ID(),
                _ChoiceBox_Division.getValue().get_id(),
                _TextField4.getText(),
                _TextField2.getText(),
                _TextField5.getText()
            );

            System.out.println("DEBUG: Customer record updated succcessfully in the database.");
        } else {
            System.out.println("debugh: No existing customer found, adding a new customer instead!"); 
            System.out.println("Initiating new customer entry into the system...");

            // Add a new customer record to the database with values from input fields
            _DataBase_customers._add_customer_to_database(
                _ChoiceBox_Division.getValue().get_id(),
                _TextField2.getText(),
                _TextField3.getText(),
                _TextField4.getText(),
                _TextField5.getText()
            );

            System.out.println("DEBUG:  New customer added to the database with name: " + _TextField2.getText());
            System.out.println("DEBUG: Division ID: " + _ChoiceBox_Division.getValue().get_id());
            System.out.println("DEBUG: Address: " + _TextField3.getText());
            System.out.println("DEBUG: Postal Code: " + _TextField4.getText());
            System.out.println("DEBUG: Phone Number: " + _TextField5.getText());
        }
        
        return 7101010;
    }


    /**
     * Validates the user input fields in the form. Checks for blank fields and required selections.
     *
     * @return booleen indicating the presence of validation errors (true if errors exist, false if all inputs are valid)
     */
    public boolean _check_and_validate() {

        // clear all error messages before starting validation
        _label2.setText(" ");
        _label3.setText(" ");
        _label4.setText(" ");
        _label5.setText(" ");
        _label6.setText(" ");
        _label7.setText(" ");
        _label8.setText(" ");

            // Flag to track the presence of errors
        boolean _flag = (boolean) false;

            // Log the current state of _label9 for debugging purposes
        System.out.println(">> " + _label9);

        // Check if a country is selected
        if (_ChoiceBox_Country.getValue() == null) {
            _label6.setText("Country selection is required."); // Set error if no country is selected
            _flag = (boolean) true;
        } else {
            // Clear the error message for country if a selection exists
            _label6.setText("");
        }

                // Check if a division is selected
        if (_ChoiceBox_Division.getValue() == null) {
            _label7.setText("Please choose a division."); // Set error if no division is selected
            _flag = (boolean) true;
        } else {
            // Clear the error message for division if a selection exists
            _label7.setText("");
        }

            // Ccheck if the customer name field is filed
        if (_TextField2.getText().isBlank()) {
            _label2.setText("Enter the customer name."); // Set error if the name field is empty
            _flag = (boolean) true;
        } else {
            // Clear the error message for name if text is present
            _label2.setText("");
        }

            // check if the postal code field is filled
        if (_TextField4.getText().isBlank()) {
            _label4.setText("Postal code cnnot be empty."); // Set error if the postal code is empty
            _flag = (boolean) true;
        } else {
            // Clear the error message for postal code if text is present
            _label4.setText("");
        }

            // Check if the address field is filled
        if (_TextField3.getText().isBlank()) {
            _label3.setText("Address is required."); // Set error if the address field is empty
            _flag = (boolean) true;
        } else {
            // clear the error message for adddress if text is present
            _label3.setText("");
        }

        // Check if the phone number field is filled
        if (_TextField5.getText().isBlank()) {
            _label5.setText("A phone number must be provided."); // Set error if the phone field is empty
            _flag = (boolean) true;
        } else {
            // Clear the error message for phone number if text is present
            _label5.setText(""); 
        }
        
        // If there are any flags indicating errors, set a general warning message
        if (_flag) {
            _label8.setText("Unable to proceed. Resolve the errors above and try again.");
        } else {
            // Clear the general error message if there are no errors
            _label8.setText("");
        }
        
        System.out.println("");

        // Return true if errors are present, otherwise false
        if (_flag == true) {
            return false;
        } else {
            return true;
        }
    }

    public void _print_Variables()
    {
        System.out.println("DialogPane: " + _DialogPane);
        System.out.println("TextFields: " + _TextField1 + _TextField2 + _TextField3 + _TextField4 + _TextField5);
    }
    
     public void initialize(URL _URL, ResourceBundle _ResourceBundle) {
         _print_Variables();
        _get_countries();
    }

}
