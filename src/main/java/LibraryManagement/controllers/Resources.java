package LibraryManagement.controllers;

import LibraryManagement.DAO.DocumentDatabase;
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
 * Controller for managing the Resources view in the library management system.
 * Handles actions like searching, displaying books, and updating book details.
 */
public class Resources {

    private AdminController admin;

    @FXML
    private ComboBox<String> filterSearch;
    @FXML
    private TextField textField;
    @FXML
    private Button searchBtn;
    @FXML
    private GridPane gridPane;

    /**
     * Loads all documents from the database and displays them as buttons on the grid pane.
     */
    public void createBookBtn() {
        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
                Platform.runLater(() -> {
                    for (int i = 0; i < documents.size(); i++) {
                        addDocumentToGrid(documents.get(i), i);
                    }
                });
                return null;
            }
        };
        new Thread(loadBooksTask).start();
    }

    /**
     * Opens a new stage for updating the details of the given document.
     *
     * @param document The document to be updated.
     */
    private void handleUpdate(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/UpdateDocument.fxml"));
            Parent updateDocumentRoot = loader.load();

            UpdateDocument controller = loader.getController();
            controller.setData(document);

            Stage stage = new Stage();
            stage.setTitle("Update Document");
            stage.setScene(new Scene(updateDocumentRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the admin instance and initializes the view with all books.
     *
     * @param admin The admin instance to be set.
     */
    public void setAdmin(AdminController admin) {
        this.admin = admin;
        if (admin != null) {
            createBookBtn();
        }
    }

    /**
     * Handles the "Add" button action to add a new book using the admin API.
     */
    @FXML
    private void handleAdd() {
        if (admin != null) {
            admin.addBookAPI();
        }
    }

    /**
     * Handles the "Search" button action. Searches the database based on the selected filter and keyword.
     */
    @FXML
    private void handleSearch() {
        String keyword = textField.getText().trim();
        String filter = filterSearch.getValue();

        if (keyword.isEmpty()) {
            System.out.println("Keyword is empty.");
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
     * Adds a document as a button to the grid pane.
     *
     * @param document The document to be displayed.
     * @param index    The index of the document in the list.
     */
    private void addDocumentToGrid(Document document, int index) {
        Button bookBtn = new Button();
        bookBtn.setPrefWidth(275);
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
        bookBtn.setOnAction(event -> handleUpdate(document));

        int row = index / 4;
        int col = index % 4;

        gridPane.add(bookBtn, col, row);
    }

    /**
     * Updates the grid pane with the given list of documents.
     *
     * @param documents The list of documents to be displayed.
     */
    private void updateGridWithDocuments(ArrayList<Document> documents) {
        gridPane.getChildren().clear();
        for (int i = 0; i < documents.size(); i++) {
            addDocumentToGrid(documents.get(i), i);
        }
    }
}
