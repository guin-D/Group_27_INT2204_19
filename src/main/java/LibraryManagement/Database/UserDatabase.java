package LibraryManagement.Database;

import LibraryManagement.commandline.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDatabase {

    public static UserDatabase getInstance() {
        return new UserDatabase();
    }

    public int insert(User user) {
        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO user(name, phoneNumber, password, accessLevel) "
                    + " VALUE('" + user.getName() + "', '"
                    + user.getPhonenumber() + "', '"
                    + user.getPassword() + "', '"
                    + user.getAccessLevel() + "')";

            int done = statement.executeUpdate(sql);

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(User user) {
        return 0;
    }

    public int remove(User user) {
        return 0;
    }

    //todo
    public int remove(String name) {
        return 0;
    }

    public ArrayList<User> selectAll() {
        ArrayList<User> users = new ArrayList<User>();

        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM user";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phoneNumber");
                String password = resultSet.getString("password");
                String accessLevel = resultSet.getString("accessLevel");

                User user;
                if (accessLevel.matches("Admin")) {
                    user = new Admin(name, phoneNumber, password, accessLevel);
                } else {
                    user = new NormalUser(name, phoneNumber, password, accessLevel);
                }
                users.add(user);
            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User selectBy(User user) {
        return null;
    }

    public ArrayList<User> selectByCondition(String condition) {
        return null;
    }
}
