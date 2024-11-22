package LibraryManagement.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import LibraryManagement.utils.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class googleBookAPI {

    @FXML
    private ComboBox<String> filterSearch;

    @FXML
    private TextField field;

    @FXML
    private VBox main;

    @FXML
    protected void onSearchButtonClick() {
        main.getChildren().clear(); // Xóa nội dung cũ

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
        bookBox.setPadding(new Insets(10));
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

        // Book info
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

        bookBox.getChildren().addAll(bookImage, bookInfo);
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
}
