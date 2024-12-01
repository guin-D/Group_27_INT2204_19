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
import javafx.scene.layout.VBox;
import LibraryManagement.utils.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class GoogleBookAPI {

    @FXML
    private ComboBox<String> filterSearch;

    @FXML
    private TextField field;

    @FXML
    private VBox main;

    @FXML
    private Button back;

    private Admin admin;

    @FXML
    protected void onSearchButtonClick() {
        main.getChildren().clear();

        Task<ArrayList<Book>> task = new Task<>() {
            @Override
            protected ArrayList<Book> call() {
                String url;
                if ("Keywords".equals(filterSearch.getValue())) {
                    String api = "https://www.googleapis.com/books/v1/volumes?q=";
                    String keyword = field.getText().trim();
                    url = api + keyword;
                } else {
                    String api = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
                    String isbn = field.getText().trim();
                    url = api + isbn;
                }

                String jsonResponse = getHttpResponse(url);
                BooksAPI booksAPI = new BooksAPI();
                return booksAPI.getBooksFromJson(jsonResponse);
            }
        };

        task.setOnSucceeded(event -> {
            ArrayList<Book> books = task.getValue();
            if (books == null || books.isEmpty()) {
                Label noBookLabel = new Label("No books found for this query.");
                main.getChildren().add(noBookLabel);
            } else {
                for (Book book : books) {
                    HBox bookBox = createBookBox(book);
                    main.getChildren().add(bookBox);
                }
            }
        });

        task.setOnFailed(event -> {
            Label errorLabel = new Label("Error occurred while fetching book info.");
            main.getChildren().add(errorLabel);
        });

        new Thread(task).start();
    }

    private HBox createBookBox(Book book) {
        HBox bookBox = new HBox();
        bookBox.setSpacing(20);
        bookBox.setPadding(new Insets(10, 30, 0, 0));
        bookBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;");
        bookBox.setPrefWidth(1000);

        // Book thumbnail
        ImageView bookImage = new ImageView();
        bookImage.setFitWidth(128);
        bookImage.setFitHeight(175);
        if (book.getImageLinks() != null && !book.getImageLinks().isEmpty()) {
            Image image = new Image(book.getImageLinks(), true);
            bookImage.setImage(image);
        }

        VBox bookInfo = new VBox();
        bookInfo.setSpacing(10);
        Label titleLabel = new Label("Title: " + book.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        Label isbnLabel = new Label("ISBN: " + book.getIsbn());
        Label authorLabel = new Label("Author: " + book.getAuthor());
        Label publisherLabel = new Label("Publisher: " + book.getPublisher());
        Label descriptionLabel = new Label("Description: " + book.getDescription());
        descriptionLabel.setWrapText(true);

        bookInfo.getChildren().addAll(titleLabel, isbnLabel, authorLabel, publisherLabel, descriptionLabel);

        Button addBookBtn = new Button();
        ImageView addImage = new ImageView(getClass().getResource("/LibraryManagement/Image/add.png").toExternalForm());
        addImage.setFitHeight(20);
        addImage.setFitWidth(20);
        addBookBtn.setGraphic(addImage);
        addBookBtn.setStyle("-fx-background-color: #FFFFFF");
        addBookBtn.setText("");
        addBookBtn.setOnAction(event -> {
            handleAddBook(book);
        });


        bookBox.getChildren().addAll(bookImage, bookInfo, addBookBtn);
        return bookBox;
    }

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

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @FXML
    public void handleBackClick() {
        if (admin != null) {
            admin.showResource();
        }
    }
}
