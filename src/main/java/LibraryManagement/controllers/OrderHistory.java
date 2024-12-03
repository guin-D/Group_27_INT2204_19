package LibraryManagement.controllers;

import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Document;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for the Order History, allowing users to search and view document details.
 */
public class OrderHistory {

    @FXML
    private ComboBox<String> filterSearch;
    @FXML
    private TextField textField;
    @FXML
    private Button searchBtn;
    @FXML
    private GridPane main;

    /**
     * Initializes the order history view by loading all documents.
     */
    public void initialize() {
        uploadData();
    }

    /**
     * Loads and displays all documents available in the database.
     */
    public void uploadData() {
        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

                Platform.runLater(() -> {
                    main.getChildren().clear();
                    for (int i = 0; i < documents.size(); i++) {
                        Document document = documents.get(i);
                        Button bookBtn = createBookButton(document);

                        int row = i / 4;
                        int col = i % 4;

                        main.add(bookBtn, col, row);
                    }
                });
                return null;
            }
        };

        new Thread(loadBooksTask).start();
    }

    /**
     * Creates a button representing a document with an image and title.
     *
     * @param document The document to display.
     * @return The created button for the document.
     */
    private Button createBookButton(Document document) {
        Button bookBtn = new Button();
        bookBtn.setPrefWidth(270);
        bookBtn.setPrefHeight(300);
        bookBtn.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10;");

        ImageView bookImage = new ImageView();
        bookImage.setFitWidth(128);
        bookImage.setFitHeight(175);
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImage.setImage(image);
        }

        bookBtn.setGraphic(bookImage);
        bookBtn.setText(document.getTitle());
        bookBtn.setContentDisplay(ContentDisplay.TOP);

        bookBtn.setOnAction(event -> handleOrderHistory(document));

        return bookBtn;
    }

    /**
     * Opens a new window displaying more information about the selected document's order history.
     *
     * @param document The selected document.
     */
    @FXML
    private void handleOrderHistory(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/OrderHistoryMore.fxml"));
            Parent addBookRoot = loader.load();

            OrderHistoryMore controller = loader.getController();
            controller.setDocumentData(document);

            Stage stage = new Stage();
            stage.setTitle("Order History");
            stage.setScene(new Scene(addBookRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the search functionality for filtering documents based on keywords or ISBN.
     */
    @FXML
    private void handleSearch() {
        String keyword = textField.getText().trim();
        String filter = filterSearch.getValue();

        if (keyword.isEmpty()) {
            System.out.println("Search keyword is empty.");
            return;
        }

        Task<ArrayList<Document>> searchTask = new Task<>() {
            @Override
            protected ArrayList<Document> call() throws Exception {
                if ("Keywords".equals(filter)) {
                    return DocumentDatabase.getInstance().searchByKeyword(keyword);
                } else if ("ISBN".equals(filter)) {
                    return DocumentDatabase.getInstance().searchByIsbn(keyword);
                }
                return new ArrayList<>();
            }
        };

        searchTask.setOnSucceeded(event -> updateGridWithDocuments(searchTask.getValue()));
        searchTask.setOnFailed(event -> System.out.println("Search failed: " + searchTask.getException().getMessage()));

        new Thread(searchTask).start();
    }

    /**
     * Updates the grid with the documents returned from the search.
     *
     * @param documents The list of documents to display in the grid.
     */
    private void updateGridWithDocuments(ArrayList<Document> documents) {
        main.getChildren().clear();
        for (int i = 0; i < documents.size(); i++) {
            Document document = documents.get(i);
            Button bookBtn = createBookButton(document);

            int row = i / 4;
            int col = i % 4;

            main.add(bookBtn, col, row);
        }
    }
}
