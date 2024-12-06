package LibraryManagement.commandline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The MySQL class provides methods to establish and close connections to a MySQL database.
 */
public class MySQL {

    /**
     * Establishes a connection to the MySQL database.
     *
     * @return Connection object if successful, or null if unable to connect.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:MySQL://127.0.0.1:3306/library_management";
            String username = "root";
            String password = "123456";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
        }
        return connection;
    }

    /**
     * Closes the provided database connection.
     *
     * @param connection The Connection object to be closed.
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
