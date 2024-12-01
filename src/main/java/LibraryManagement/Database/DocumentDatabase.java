package LibraryManagement.Database;

import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DocumentDatabase {
    public static DocumentDatabase getInstance() {
        return new DocumentDatabase();
    }

    public int insert(Document document) {
        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO document(title, author, publisher, ISBN, "
                    + "status, qty, price, brwcopiers, description, imageLink)"
                    + " VALUE('" + document.getTitle() + "', '"
                    + document.getAuthor() + "', '"
                    + document.getPublisher() + "', '"
                    + document.getIsbn() + "', '"
                    + document.getStatus() + "', '"
                    + document.getQty() + "', '"
                    + document.getPrice() + "', '"
                    + document.getBrwcopiers() + "', '"
                    + document.getImageLink() + "')";

            int done = statement.executeUpdate(sql);

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Document document) {
        int done = 0;
        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "UPDATE document"
                    + " SET " + "author = '" + document.getAuthor() + "',"
                    + "ISBN = '" + document.getIsbn() + "',"
                    + "Status = '" + document.getStatus() + "',"
                    + "qty = '" + document.getQty() + "',"
                    + "price = '" + document.getPrice() + "',"
                    + "brwcopiers = '" + document.getBrwcopiers() + "'"
                    + "imageLink = '" + document.getImageLink() + "'"
                    + "WHERE title = '" + document.getTitle() + "'";

            done = statement.executeUpdate(sql);

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int remove(Document document) {
        int done = 0;
        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "DELETE FROM document "
                    + "WHERE title = '" + document.getTitle() + "'";

            done = statement.executeUpdate(sql);

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public ArrayList<Document> selectAll() {
        ArrayList<Document> documents = new ArrayList<Document>();

        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM document";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                String ISBN = resultSet.getString("ISBN");
                int qty = resultSet.getInt("qty");
                double price = resultSet.getDouble("price");
                int brwcopiers = resultSet.getInt("brwcopiers");
                String description = resultSet.getString("description");
                String imageLink = resultSet.getString("imageLink");

                Document document = new Document(title, author, publisher, ISBN, qty, price, brwcopiers, description, imageLink);
                documents.add(document);
            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    public Document selectBy(Document document) {
        Document done = null;

        try {
            Connection connection = MySQL.getConnection();

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM document where title = '"
                    + document.getTitle() + "'";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                String ISBN = resultSet.getString("ISBN");
                int qty = resultSet.getInt("qty");
                double price = resultSet.getDouble("price");
                int brwcopiers = resultSet.getInt("brwcopiers");
                String description = resultSet.getString("description");
                String imageLink = resultSet.getString("imageLink");

                done = new Document(title, author, publisher, ISBN, qty, price, brwcopiers, description, imageLink);

            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return done;
    }


}
