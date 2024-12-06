package LibraryManagement.controllers;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for the Borrow section, allowing users to search, view, and borrow documents.
 */
public class Borrow extends NormalUserController {

    protected User user;

    @FXML
    private ComboBox<String> filterSearch;
    @FXML
    private TextField textField;
    @FXML
    private Button searchButton;
    @FXML
    private GridPane main;

    /**
     * Loads and displays all documents available for borrowing.
     *
     * @param user The current logged-in user.
     */
    public void uploadData(User user) {
        this.user = user;

        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

                Platform.runLater(() -> {
                    main.getChildren().clear();
                    for (int i = 0; i < documents.size(); i++) {
                        Document document = documents.get(i);
                        HBox bookBtn = createBookButton(document);

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
     * Creates a horizontal box containing details and a borrow button for a document.
     *
     * @param document The document to display.
     * @return The constructed HBox containing document details.
     */
    private HBox createBookButton(Document document) {
        HBox bookBox = new HBox();
        bookBox.setAlignment(Pos.CENTER_LEFT);
        bookBox.setPrefWidth(250);
        bookBox.setPrefHeight(200);
        bookBox.setSpacing(10);
        bookBox.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10;");

        ImageView bookImage = new ImageView();
        bookImage.setFitWidth(128);
        bookImage.setFitHeight(175);
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImage.setImage(image);
        }

        VBox content = new VBox();
        content.setSpacing(10);

        ImageView stars = new ImageView(getClass().getResource("/LibraryManagement/Image/stars.png").toExternalForm());
        stars.setFitHeight(20);
        stars.setFitWidth(120);

        Label title = new Label("Title: " + document.getTitle());
        title.setStyle("-fx-text-fill: #203169; -fx-font-weight: bold");
        title.setWrapText(true);
        title.setMaxWidth(140);

        Label qty = new Label("Borrow copies: " + document.getQty());
        qty.setStyle("-fx-text-fill: #203169");

        Button borrowBtn = new Button("Borrow");
        borrowBtn.setStyle("-fx-background-color: #203169; -fx-text-fill: #FFFFFF");
        borrowBtn.setOnAction(event -> handleBorrowing(document, qty));

        Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);

        content.getChildren().setAll(stars, title, qty, region, borrowBtn);
        bookBox.getChildren().addAll(bookImage, content);

        return bookBox;
    }

    /**
     * Handles the borrowing of a document, ensuring constraints like availability and duplicates.
     *
     * @param document The document to borrow.
     */
    @FXML
    private void handleBorrowing(Document document, Label qty) {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        boolean alreadyBorrowed = borrowings.stream()
                .anyMatch(b -> b.getDocumentTitle().equals(document.getTitle())
                        && b.getUserName().equals(this.user.getName()));

        if (alreadyBorrowed) {
            showAlert("Borrowing fail!", "You have borrowed this document before!");
            return;
        }

        if (document.getBrwcopiers() > 0) {
            Borrowing borrowing = new Borrowing(document, user);
            document.setBrwcopiers(document.getBrwcopiers() - 1);
            String borrowCopies = qty.getText();
            int numberBorrowCopies = Integer.parseInt(borrowCopies.replaceAll("[^0-9]", ""));
            qty.setText("Borrow copies: " + String.valueOf(numberBorrowCopies - 1));
            DocumentDatabase.getInstance().update(document);
            BorrowingDatabase.getInstance().insert(borrowing);

            showAlert("Borrowing successfully!", "You must return the document before 14 days from now.\n"
                    + "Expiry date: " + borrowing.getFinish() + "\nEnjoy!");
        } else {
            showAlert("Borrowing fail!", "This document isn't available for borrowing!");
        }
    }

    /**
     * Displays the borrow list for the current user.
     */
    @FXML
    public void handleBorrowList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/BorrowList.fxml"));
            Parent borrowListPane = loader.load();

            BorrowList controller = loader.getController();
            controller.initialize(user);

            Stage stage = new Stage();
            stage.setTitle("Borrow list");
            stage.setScene(new Scene(borrowListPane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the search functionality to filter and display documents based on keywords or ISBN.
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
     * Updates the grid pane with the given list of documents.
     *
     * @param documents The list of documents to display.
     */
    private void updateGridWithDocuments(ArrayList<Document> documents) {
        main.getChildren().clear();
        for (int i = 0; i < documents.size(); i++) {
            Document document = documents.get(i);
            HBox bookBtn = createBookButton(document);

            int row = i / 4;
            int col = i % 4;

            main.add(bookBtn, col, row);
        }
    }

    /**
     * Displays an alert with the given title and content.
     *
     * @param title   The title of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
