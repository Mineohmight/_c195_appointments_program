package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * This class manages the conection setup and termination for the client_schedule database.
 * Using the MySQL JDBC driver, it establishes and maintains the database connection.
 *
 * @author Riley O'Donnell
 */
public abstract class _Java_Connection {

    /**
     * Holds the active database connection.
     */
    public static Connection _connection;
    
    // Flag indicating if the connection is currently active
    private static boolean _is_connected_to_database = false;

    // Timeout duration in seconds for connecting to the database
    private static final int _timeout_time = 30;

    // Last error message captured during a failed database operation
    private static String _last_error = null;

    // Log of recent SQL queries executed (for debugging purposes)
    private static List<String> _recent_query_log = new ArrayList<>();
    
    /**
     * Closes the active database connection.
     * 
     * This method attempts to clse the current conection to the client_schedule database.
     * It handles any SQL or general exceptions that may occur during the closing process.
     * If an error occurs, an appropriate error mesage is displayed.
     * 
     * A flag is used to determine if the connection was closed successfully.
     */
    public static float _terminate() {

        // Initialize the closed flag to track if the connection was successfully closed
        String _closed_flag = "UNSUCCESSFUL";

        try {
            // Attempt to close the database connection
            _connection.close();
            _closed_flag = "SUCCESSFUL";  // Updte flag on successful closure
        } 
        catch (SQLException _sql_error) {
            // Handle any SQL exceptions that occur during the closing process
            System.err.println("SQL Error while closing connection: " + _sql_error.getErrorCode());
        } 
        catch (Exception _error) {
            // Handle any general excptions that occur
            System.err.println("Error while closing connection: " + _error.getMessage());
        }

        // Check if the connection closure was successful and print the corresponding message
        if (_closed_flag.equals("UNSUCCESSFUL")) {
            System.err.print("Closed unsuccessfully.");
            return -1.0f;
        } else {
            System.err.print("Closed successfully.");
            return 1.0f;
        }
    }
    
    /**
     * Retrieves the current active database connection.
     * 
     * This method returns the existing conection to the client_schedule database.
     * If the connection has not been established, it will return null.
     * 
     * @return the active database connection, or null if no connection exists
     */
    public static Connection _return_connection() {
        // Return the stored connection to the database
        return _connection;
    }

    /**
     * Establishes a conection to the client_schedule database using MySQL JDBC.
     * This method loads the MySQL driver and attempts to conect to the database using 
     * specified credentials.
     * 
     * If successful, the connection is stored in the _connection variable.
     * If an error occurs, the appropriate error message is printed.
     */
    public static void _create_connection() {

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            System.out.println("Trying connection...");

            // Establish connection to the database with provided URL, username, and password
            _connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1/client_schedule?connectionTimeZone=SERVER", 
                    "sqlUser", 
                    "Passw0rd!"
            );

            System.out.print("Connected.");
            
            DatabaseMetaData metaData = _connection.getMetaData();
            String driverName = metaData.getDriverName();
            String driverVersion = metaData.getDriverVersion();

            System.out.println("Driver Name: " + driverName);
            System.out.println("Driver Version: " + driverVersion);
        }
        // Handle SQL errors
        catch (SQLException _sql_error) {
            System.err.println("SQL Error while connecting: " + _sql_error.getErrorCode());
        }
        // Handle other general errors
        catch (Exception _error) {
            System.err.println("Error while connecting: " + _error.getMessage());
        }

        // Test print statement
        System.out.println("Test#1");
    }

}
