package LibraryManagement.commandline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQL {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:MySQL://127.0.0.1:3306/library_management";
            String username = "root";
            String password = "Nguyen58.";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
        }
        return connection;
    }

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
