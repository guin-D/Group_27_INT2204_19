package LibraryManagement.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import LibraryManagement.utils.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * Controller class responsible for handling the Google Books API integration,
 * allowing the admin to search for books by keyword or ISBN, and display the results.
 */
public class GoogleBookAPI {

    @FXML
    private ComboBox<String> filterSearch;

    @FXML
    private TextField field;

    @FXML
    private VBox main;

    @FXML
    private Button back;

    private AdminController admin;

    /**
     * Handles the search button click event. Initiates a background task to fetch book data
     * from the Google Books API based on the search filter and field input.
     */
    @FXML
    protected void onSearchButtonClick() {
        main.getChildren().clear();

        Task<ArrayList<Book>> task = new Task<>() {
            @Override
            protected ArrayList<Book> call() {
                String url = buildApiUrl();
                String jsonResponse = getHttpResponse(url);
                BooksAPI booksAPI = new BooksAPI();
                return booksAPI.getBooksFromJson(jsonResponse);
            }
        };

        task.setOnSucceeded(event -> handleSearchSuccess(task));
        task.setOnFailed(event -> handleSearchFailure());

        new Thread(task).start();
    }

    /**
     * Builds the appropriate Google Books API URL based on the selected search filter.
     *
     * @return the constructed URL for the API request
     */
    private String buildApiUrl() {
        String baseApiUrl = "https://www.googleapis.com/books/v1/volumes?q=";
        String query = field.getText().trim();

        if ("Keywords".equals(filterSearch.getValue())) {
            return baseApiUrl + query;
        } else {
            return baseApiUrl + "isbn:" + query;
        }
    }

    /**
     * Handles the successful completion of the book search task, updating the UI with
     * the retrieved book data.
     *
     * @param task the background task that completed successfully
     */
    private void handleSearchSuccess(Task<ArrayList<Book>> task) {
        ArrayList<Book> books = task.getValue();
        if (books == null || books.isEmpty()) {
            main.getChildren().add(new Label("No books found for this query."));
        } else {
            books.forEach(book -> main.getChildren().add(createBookBox(book)));
        }
    }

    /**
     * Handles the failure of the book search task, displaying an error message.
     */
    private void handleSearchFailure() {
        main.getChildren().add(new Label("Error occurred while fetching book info."));
    }

    /**
     * Creates a VBox containing detailed information and actions for a single book.
     *
     * @param book the book to display information for
     * @return an HBox containing the book details and an add button
     */
    private HBox createBookBox(Book book) {
        HBox bookBox = new HBox(20);
        bookBox.setPadding(new Insets(10, 30, 0, 0));
        bookBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 1; -fx-border-radius: 5;");
        bookBox.setPrefWidth(1000);

        // Set up book image
        ImageView bookImage = new ImageView();
        bookImage.setFitWidth(128);
        bookImage.setFitHeight(175);
        if (book.getImageLinks() != null && !book.getImageLinks().isEmpty()) {
            bookImage.setImage(new Image(book.getImageLinks(), true));
        }

        // Set up book details
        VBox bookInfo = new VBox(10);
        Label titleLabel = new Label("Title: " + book.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        bookInfo.getChildren().addAll(
                titleLabel,
                new Label("ISBN: " + book.getIsbn()),
                new Label("Author: " + book.getAuthor()),
                new Label("Publisher: " + book.getPublisher()),
                new Label("Description: " + book.getDescription())
        );

        // Add book button
        Button addBookBtn = new Button();
        ImageView addImage = new ImageView(getClass().getResource("/LibraryManagement/Image/add.png").toExternalForm());
        addImage.setFitHeight(20);
        addImage.setFitWidth(20);
        addBookBtn.setGraphic(addImage);
        addBookBtn.setStyle("-fx-background-color: #FFFFFF");
        addBookBtn.setOnAction(event -> handleAddBook(book));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        bookBox.getChildren().addAll(bookImage, bookInfo, region, addBookBtn);
        return bookBox;
    }

    /**
     * Makes an HTTP GET request to the given URL and returns the response body as a string.
     *
     * @param url the URL to send the request to
     * @return the response body as a string, or null if the request fails
     */
    private String getHttpResponse(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP Error: " + response.statusCode());
            }
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handles the process of adding a book to the library.
     *
     * @param book the book to add
     */
    private void handleAddBook(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Book.fxml"));
            Parent addBookRoot = loader.load();

            AddBook controller = loader.getController();
            controller.setBookData(book);

            Stage stage = new Stage();
            stage.setTitle("Add Book");
            stage.setScene(new Scene(addBookRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the admin instance for the controller.
     *
     * @param admin the admin instance to set
     */
    public void setAdmin(AdminController admin) {
        this.admin = admin;
    }

    /**
     * Handles the back button click, returning to the previous screen.
     */
    @FXML
    public void handleBackClick() {
        if (admin != null) {
            admin.showResource();
        }
    }
}
