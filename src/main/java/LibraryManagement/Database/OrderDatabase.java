package LibraryManagement.Database;

import LibraryManagement.commandline.MySQL;
import LibraryManagement.commandline.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderDatabase {
    public static OrderDatabase getInstance() {
        return new OrderDatabase();
    }

    public int insert(Order order) {
        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO `order`(document, user, price, qty)"
                    + " VALUE('" + order.getDocumentTitle() + "' ,'"
                    + order.getUserName() + "' ,'"
                    + order.getPrice() + "' ,'"
                    + order.getQty() + "')";

            int done = statement.executeUpdate(sql);

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Order> selectAll() {
        ArrayList<Order> orders = new ArrayList<Order>();

        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM `order`";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                String document = resultSet.getString("document");
                String user = resultSet.getString("user");
                double price = resultSet.getDouble("price");
                int qty = resultSet.getInt("qty");

                Order order = new Order(document, user, price, qty);
                orders.add(order);
            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
