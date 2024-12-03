package LibraryManagement.controllers;

import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Document;
import LibraryManagement.utils.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Controller for adding a book to the library.
 * Handles the functionality for saving and canceling the addition of a new book.
 */
public class AddBook {

    // UI elements for book details input
    @FXML
    private ImageView bookImg;

    @FXML
    private TextField title;

    @FXML
    private TextField isbn;

    @FXML
    private TextField author;

    @FXML
    private TextField publisher;

    @FXML
    private TextField totalQuantity;

    @FXML
    private TextField totalOrder;

    @FXML
    private TextField price;

    @FXML
    private Button save;

    @FXML
    private Button cancel;

    // The current book being edited
    private Book currentBook;

    /**
     * Sets the book data to the fields if available.
     *
     * @param book The book to be set.
     */
    public void setBookData(Book book) {
        this.currentBook = book;
        if (book != null) {
            // Display the book image if available
            if (book.getImageLinks() != null && !book.getImageLinks().isEmpty()) {
                Image image = new Image(book.getImageLinks(), true);
                bookImg.setImage(image);
            }
            // Set book details in the input fields
            title.setText(book.getTitle());
            isbn.setText(book.getIsbn());
            author.setText(book.getAuthor());
            publisher.setText(book.getPublisher());
        }
    }

    /**
     * Initializes the action handlers for save and cancel buttons.
     */
    @FXML
    private void initialize() {
        save.setOnAction(event -> handleSave()); // Save button handler
        cancel.setOnAction(event -> handleCancel()); // Cancel button handler
    }

    /**
     * Handles the save action for adding a new book to the library.
     * Checks if the book already exists, then inserts a new document if it doesn't.
     */
    private void handleSave() {
        Document document = new Document();

        // Check if the current book data exists
        if (currentBook != null) {
            ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
            int existingDocIndex = -1;

            // Check if the book already exists in the database
            if (documents != null) {
                for (Document d : documents) {
                    if (d.getTitle().matches(title.getText())) {
                        existingDocIndex = documents.indexOf(d);
                        break;
                    }
                }
            }

            // Show alert if the book is already in the library
            if (existingDocIndex > -1) {
                showAlert(Alert.AlertType.INFORMATION, "This document is already in the library.");
            } else {
                // Set document details
                document.setTitle(title.getText());
                document.setIsbn(isbn.getText());
                document.setAuthor(author.getText());
                document.setPublisher(publisher.getText()); // Corrected to set publisher properly
                document.setBrwcopiers(Integer.parseInt(totalQuantity.getText()));
                document.setQty(Integer.parseInt(totalOrder.getText()));
                document.setPrice(Double.parseDouble(price.getText()));
                document.setImageLink(currentBook.getImageLinks());

                // Show success alert
                showAlert(Alert.AlertType.INFORMATION, "Document added successfully!");

                // Insert the new document into the database
                DocumentDatabase.getInstance().insert(document);
            }

            closeWindow();
        }
    }

    /**
     * Handles the cancel action, closing the window without saving changes.
     */
    private void handleCancel() {
        closeWindow();
    }

    /**
     * Closes the current window (stage).
     */
    private void closeWindow() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an alert with the given type and message.
     *
     * @param alertType The type of the alert (e.g., INFORMATION, WARNING).
     * @param message   The message to display in the alert.
     */
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
