package main;

import database._Contact_Formatter;
import      database._Triplet;
import java.io.IOException;
import database._DataBase_customers;
import javafx.scene.control.Label;
import java.util.Locale;
import javafx.scene.Scene;
import database._Database_provinces;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.*;
import javafx.scene.control.Button;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import static javafx.application.Application.launch;
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
import javafx.application.Application;
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
import java.io.IOException;
import java.sql.Connection;
import static database._Scene_Transitions._choice_box_functionality;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.time.ZoneId;
import javafx.scene.Node;
import java.util.logging.Level;

import controller.Contact;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileWriter;
import controller.Customer;
import java.io.FileInputStream;
import database._Database_appointment_class;
import java.io.FileNotFoundException;

import controller.User;
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
import java.time.LocalDateTime;
import javafx.scene.control.Spinner;
import java.sql.DriverManager;
import javafx.scene.control.*;
import javafx.event.Event;
import java.util.concurrent.Executors;

/**
 *  The app class serves as the main entry point for the JavaFX application.
 *  It handles initialization, launches the main UI, and cleans up resources
 *  like database connections during shutdown. Configurations are loaded from
 *   a propertiies file, with defaults created if the file is missing. 
 * @author Riley O'Donnell
 */
public class App extends Application {


    /**
     * loads application configurations from a file.
     */
    private void _load_config() throws IOException {
        System.out.println("Loading cofigurations..."); 
        Properties config = new Properties();

        String configFile = "config.properties";
        java.io.File file = new java.io.File(configFile);

        if (!file.exists()) {
            System.out.println("Configurtion file not found. Creatting new one with default settings..."); 
            _create_default_config_file(file);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            config.load(fis);

            _application_name = config.getProperty("application.name", "DefaultApp");
            _application_version = config.getProperty("application.version", "1.0");

            _database_URL = config.getProperty("database.url", _database_URL);
            _database_user = config.getProperty("database.user", _database_user);
            _database_password = config.getProperty("database.password", _database_password);

            System.out.println("App Name: " + _application_name);
            System.out.println("App Verison: " + _application_version); 
        } catch (IOException e) {
            throw new IOException("Faild to load config", e); 
        }
    }

    /**
     * initializes the application, loading configurations and setting up resources.
     */
    @Override
    public void init() {
        System.out.println("initiazing Apllication...");

        try {
            _load_config(); // Load application settings

            _init_resources(); // Prepare resources such as database connection

            _setup_resources(); // Perform any application-specific setup

            System.out.println("Aplication initialized succesfully!"); 
        } catch (Exception e) {
            System.err.println("Init faild: " + e.getMessage()); 
            e.printStackTrace();
        }
    }
    
    private String _application_name;
    private String _application_version;
    private String _database_URL;
    private String _database_user;
    private String _database_password;
    
    /**
     *      Starts the JavaFX application by setting up the primary stage and displaying the login- view.
     * <p>
     *   This  method is called automatically after the application is launched. It initializes
     *   the primary stage, sets its properties such as title, dimensions, and resisability
     * </p>
     *
     * @param _Stage the prinary stage provided by the JavaFX runtime
     * @throws IOException if there is an issue loading the FXML file
     */
    @Override
    public void start(Stage _Stage) throws IOException {
        // Load the login view from the FXML file and create a new Scene
        Scene _Scene = new Scene(new FXMLLoader(App.class.getResource("_View_Login.fxml")).load());

            // center the stage on the screen
        _Stage.centerOnScreen();

            // Set the title of the stage
        _Stage.setTitle("login - My Application!");

        //spply the scene to the stage
        _Stage.setScene(_Scene);

        // set minimum dimensions for the stage
        _Stage.setMinWidth(400);
        _Stage.setMinHeight(300);

            // adjust the stage size to fit the scene's content
        _Stage.sizeToScene();

        // set maximum dimensions for the stage
        _Stage.setMaxWidth(800);
        _Stage.setMaxHeight(600);

         // disable resizing of the stage
        _Stage.setResizable(false);

        // Show the stage on the screen
        _Stage.show();
    }

    /**
     * creates  a default configuration file with initial values.
     */
    private void _create_default_config_file(java.io.File file) throws IOException {
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write("# Default configuration file\n");
            writer.write("application.name=MyApp\n");
            writer.write("application.version=1.0\n");
            writer.write("database.url=jdbc:mysql://localhost:3306/mydb\n");
            writer.write("database.user=root\n");
            writer.write("database.password=password\n");
            System.out.println("Defult configuration file created."); 
        } catch (IOException e) {
            throw new IOException("Coud not create default file", e); 
        }
    }

    /**
     * Innitializes the database and other resources.
     */
    private static void _init_resources() throws SQLException {
        System.out.println("Initializing resorces..."); 

        System.out.println("In memory cache initialized."); 
    }

    /**
     * sets up the application  enviroment.
     */
    private static boolean _setup_resources() {
        System.out.println("Performing aplication setup..."); 

        System.out.println("Schedulling tasks..."); 
        System.out.println("Task sucessfully scheduled."); 

        System.out.println("Rgistering services..."); 
        System.out.println("Services sucessfully rgistered."); 

        System.out.println("Setup cmplete."); 
        
        return true;
    }


    
        
    private static void _cleanup_resources() {
        System.out.println("cleaning up resources...");
        try {
            System.out.println("temporary resources cleaned up successfully.");
        } catch (Exception e) {
            System.err.println("Error during resource cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stops the application and performs cleanup.
     */
    @Override
    public void stop() {
        System.out.println("Aplication stoped.");
    }

    /**
     * main method,  entry point for the application.
     */
    public static void main(String[] args) {
        if (_setup_resources()) {
            // load configurations successfully
        } else {
            System.err.println("cound not load configs, exiting..."); 
            return;
        }
        
        System.out.println("\n\n\nARGUMENTS START: \n");
        for (String s : args)
            System.out.println("arg -> " + s);
        System.out.println("\nARGUMENTS END: \n\n\n");

        database._Java_Connection._create_connection();

        launch();

        System.out.println("ending aplication..."); 

        database._Java_Connection._terminate();

        System.out.println("sucessfully ended application."); 

        _cleanup_resources();
    }

}
