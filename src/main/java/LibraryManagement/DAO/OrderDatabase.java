package LibraryManagement.DAO;

import LibraryManagement.commandline.MySQL;
import LibraryManagement.commandline.Ordering;

import java.sql.*;
import java.util.ArrayList;

public class OrderDatabase {
    private static OrderDatabase instance;

    public static OrderDatabase getInstance() {
        if (instance == null) {
            instance = new OrderDatabase();
        }
        return instance;
    }
    public int insert(Ordering ordering) {
        try {
            Connection connection = MySQL.getConnection();

            String sql = "INSERT INTO `order` (document, user, price, qty) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, ordering.getDocumentTitle());
            preparedStatement.setString(2, ordering.getUserName());
            preparedStatement.setDouble(3, ordering.getPrice());
            preparedStatement.setInt(4, ordering.getQty());

            int done = preparedStatement.executeUpdate();

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Ordering> selectAll() {
        ArrayList<Ordering> orderings = new ArrayList<Ordering>();

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

                Ordering ordering = new Ordering(document, user, price, qty);
                orderings.add(ordering);
            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderings;
    }
}
