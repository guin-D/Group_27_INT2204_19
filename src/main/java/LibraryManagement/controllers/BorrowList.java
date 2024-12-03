package LibraryManagement.controllers;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Controller for managing the borrow list of a specific user.
 * Displays the borrowed books and allows the user to return them.
 */
public class BorrowList extends Borrow {

    private User user;

    @FXML
    private VBox mainBox;

    @FXML
    private Button cancel;

    /**
     * Initializes the borrow list for a user by loading the borrowed books.
     *
     * @param user the user whose borrow list is to be displayed.
     */
    @FXML
    public void initialize(User user) {
        this.user = user;
        mainBox.getChildren().clear();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getUserName().equals(user.getName())) {
                for (Document document : documents) {
                    if (document.getTitle().matches(borrowing.getDocumentTitle())) {
                        HBox borrowBox = createBorrowedBookBox(document, borrowing);
                        mainBox.getChildren().add(borrowBox);
                    }
                }
            }
        }
    }

    /**
     * Creates a box for a borrowed document, displaying its image, title, author, etc.
     *
     * @param document  the document to display.
     * @param borrowing the borrowing record for the document.
     * @return the HBox containing the document details.
     */
    private HBox createBorrowedBookBox(Document document, Borrowing borrowing) {
        HBox bookBox = new HBox();
        bookBox.setSpacing(20);
        bookBox.setPadding(new Insets(10, 30, 0, 0));
        bookBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;");
        bookBox.setPrefWidth(1000);

        // Set book image
        ImageView bookImage = createBookImageView(document);

        // Set book info
        VBox bookInfo = createBookInfoVBox(document);

        // Set return button
        Button returnBtn = createReturnButton(document);

        // Layout the elements within the book box
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        bookBox.getChildren().addAll(bookImage, bookInfo, region, returnBtn);
        return bookBox;
    }

    /**
     * Creates an ImageView for the document's image.
     *
     * @param document the document to get the image from.
     * @return the ImageView displaying the book's image.
     */
    private ImageView createBookImageView(Document document) {
        ImageView bookImage = new ImageView();
        bookImage.setFitWidth(128);
        bookImage.setFitHeight(175);
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImage.setImage(image);
        }
        return bookImage;
    }

    /**
     * Creates a VBox to display book information such as title, ISBN, author, and publisher.
     *
     * @param document the document to get information from.
     * @return the VBox containing the book information.
     */
    private VBox createBookInfoVBox(Document document) {
        VBox bookInfo = new VBox();
        bookInfo.setSpacing(10);

        Label titleLabel = new Label("Title: " + document.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        Label isbnLabel = new Label("ISBN: " + document.getIsbn());
        Label authorLabel = new Label("Author: " + document.getAuthor());
        Label publisherLabel = new Label("Publisher: " + document.getPublisher());

        bookInfo.getChildren().addAll(titleLabel, isbnLabel, authorLabel, publisherLabel);
        return bookInfo;
    }

    /**
     * Creates a return button for returning a borrowed document.
     *
     * @param document the document to be returned.
     * @return the return button.
     */
    private Button createReturnButton(Document document) {
        Button returnBtn = new Button("Return");
        returnBtn.setStyle("-fx-background-color: #203169; -fx-text-fill: #FFFFFF");
        returnBtn.setOnAction(event -> handleReturn(document));
        return returnBtn;
    }

    /**
     * Handles the return of a borrowed document.
     * Updates the document's available copies and removes the borrowing record.
     *
     * @param document the document to be returned.
     */
    @FXML
    private void handleReturn(Document document) {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        int index = findBorrowingIndex(document);

        if (index >= 0) {
            Borrowing borrowing = borrowings.get(index);

            // Handle overdue fines if necessary
            if (borrowing.getDaysLeft() < 0) {
                showFineAlert(borrowing.getDaysLeft());
            }

            // Update document and remove borrowing record
            document.setBrwcopiers(document.getBrwcopiers() + 1);
            DocumentDatabase.getInstance().update(document);
            borrowings.remove(borrowing);
            BorrowingDatabase.getInstance().delete(borrowing);

            initialize(user);
            showReturnSuccessAlert();
        } else {
            showReturnFailureAlert();
        }
    }

    /**
     * Finds the index of a borrowing record that matches the document and user.
     *
     * @param document the document whose borrowing record needs to be found.
     * @return the index of the borrowing record, or -1 if not found.
     */
    private int findBorrowingIndex(Document document) {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        for (int i = 0; i < borrowings.size(); i++) {
            Borrowing b = borrowings.get(i);
            if (b.getDocumentTitle().matches(document.getTitle()) && b.getUserName().matches(user.getName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Shows an alert if the user has overdue fines to pay.
     *
     * @param daysLeft the number of days left, used to calculate the fine.
     */
    private void showFineAlert(int daysLeft) {
        int fine = Math.abs(daysLeft) * 50;
        System.out.println("You are late!\n" + "You have to pay " + fine + " VND as fine.");
    }

    /**
     * Shows a success alert when a document is successfully returned.
     */
    private void showReturnSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Document returned");
        alert.setContentText("Thank you!");
        alert.showAndWait();
    }

    /**
     * Shows a failure alert if the document wasn't borrowed by the user.
     */
    private void showReturnFailureAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Document return failed");
        alert.setContentText("You didn't borrow this document!");
        alert.showAndWait();
    }

    /**
     * Closes the current window.
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
