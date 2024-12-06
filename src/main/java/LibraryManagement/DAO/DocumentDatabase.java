package LibraryManagement.DAO;

import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.MySQL;

import java.sql.*;
import java.util.ArrayList;

public class DocumentDatabase {
    private static DocumentDatabase instance;

    public static DocumentDatabase getInstance() {
        if (instance == null) {
            instance = new DocumentDatabase();
        }
        return instance;
    }

    public int insert(Document document) {
        try {
            Connection connection = MySQL.getConnection();

            String sql = "INSERT INTO document(title, author, publisher, ISBN, qty, price, brwcopiers, imageLink) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, document.getTitle());
            preparedStatement.setString(2, document.getAuthor());
            preparedStatement.setString(3, document.getPublisher());
            preparedStatement.setString(4, document.getIsbn());
            preparedStatement.setInt(5, document.getQty());
            preparedStatement.setDouble(6, document.getPrice());
            preparedStatement.setInt(7, document.getBrwcopiers());
            preparedStatement.setString(8, document.getImageLink());

            int done = preparedStatement.executeUpdate();

            MySQL.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Document document) {
        try {
            Connection connection = MySQL.getConnection();

            String sql = "UPDATE document "
                    + "SET author = ?, publisher = ?, ISBN = ?, qty = ?, price = ?, brwcopiers = ? "
                    + "WHERE title = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, document.getAuthor());
            preparedStatement.setString(2, document.getPublisher());
            preparedStatement.setString(3, document.getIsbn());
            preparedStatement.setInt(4, document.getQty());
            preparedStatement.setDouble(5, document.getPrice());
            preparedStatement.setInt(6, document.getBrwcopiers());
            preparedStatement.setString(7, document.getTitle());

            int done = preparedStatement.executeUpdate();

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
                String imageLink = resultSet.getString("imageLink");

                Document document = new Document(title, author, publisher, ISBN, qty, price, brwcopiers, imageLink);
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
                String imageLink = resultSet.getString("imageLink");

                done = new Document(title, author, publisher, ISBN, qty, price, brwcopiers, imageLink);

            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return done;
    }

    public Document getDocumentByTitle(String title) {
        Document document = null;

        try {
            Connection connection = MySQL.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM document WHERE title = '" + title + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                document = new Document(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getString("isbn"),
                        resultSet.getInt("qty"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("brwcopiers"),
                        resultSet.getString("imageLink")
                );
            }

            MySQL.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return document;
    }

    public Document getDocumentByISBN(String isbn) {
        Document document = null;
        try {
            Connection connection = MySQL.getConnection();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM document WHERE isbn = '" + isbn + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                document = new Document();
                document.setTitle(resultSet.getString("title"));
                document.setIsbn(resultSet.getString("isbn"));
                document.setAuthor(resultSet.getString("author"));
                document.setPublisher(resultSet.getString("publisher"));
                document.setBrwcopiers(resultSet.getInt("brwcopiers"));
                document.setQty(resultSet.getInt("qty"));
                document.setPrice(resultSet.getDouble("price"));
                document.setImageLink(resultSet.getString("imageLink"));
            }

            MySQL.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public ArrayList<Document> searchByKeyword(String title) {
        String query = "SELECT * FROM document WHERE title LIKE ?";
        return executeSearchQuery(query, "%" + title + "%");
    }

    public ArrayList<Document> searchByIsbn(String isbn) {
        String query = "SELECT * FROM document WHERE isbn LIKE ?";
        return executeSearchQuery(query, "%" + isbn + "%");
    }

    public ArrayList<Document> searchByAuthor(String author) {
        String query = "SELECT * FROM document WHERE author LIKE ?";
        return executeSearchQuery(query, "%" + author + "%");
    }

    private ArrayList<Document> executeSearchQuery(String query, String parameter) {
        ArrayList<Document> documents = new ArrayList<>();
        try (Connection connection = MySQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, parameter);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                documents.add(new Document(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("ISBN"),
                        rs.getInt("qty"),
                        rs.getDouble("price"),
                        rs.getInt("brwcopiers"),
                        rs.getString("imageLink")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }


}