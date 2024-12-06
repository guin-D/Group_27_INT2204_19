package LibraryManagement.controllers;

import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.DAO.OrderDatabase;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.Ordering;
import LibraryManagement.commandline.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Controller for displaying the list of books borrowed by a user.
 */
public class OrderList {

    private User user;

    @FXML
    private VBox mainBox;

    @FXML
    private Button cancel;

    /**
     * Initializes the order list with the user's borrowed documents.
     *
     * @param user the user whose borrowed books are to be displayed
     */
    @FXML
    public void initialize(User user) {
        this.user = user;
        mainBox.getChildren().clear();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Ordering> orderings = OrderDatabase.getInstance().selectAll();

        for (Ordering o : orderings) {
            if (o.getUserName().equals(user.getName())) {
                for (Document document : documents) {
                    if (document.getTitle().matches(o.getDocumentTitle())) {
                        HBox borrowBox = createBorrowBox(document, o);
                        mainBox.getChildren().add(borrowBox);
                    }
                }
            }
        }
    }

    /**
     * Creates a UI box for a borrowed document.
     *
     * @param document the document to display
     * @param ordering    the order record of the user
     * @return the HBox containing the book's details
     */
    private HBox createBorrowBox(Document document, Ordering ordering) {
        HBox bookBox = new HBox();
        bookBox.setSpacing(20);
        bookBox.setPadding(new Insets(10, 30, 0, 0));
        bookBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;");
        bookBox.setPrefWidth(1000);

        ImageView bookImage = new ImageView();
        bookImage.setFitWidth(128);
        bookImage.setFitHeight(175);
        setBookImage(document, bookImage);

        VBox bookInfo = new VBox();
        bookInfo.setSpacing(10);
        addBookInfoToVBox(document, bookInfo);

        bookBox.getChildren().addAll(bookImage, bookInfo);
        return bookBox;
    }

    /**
     * Sets the image for the book.
     *
     * @param document  the document containing the image link
     * @param bookImage the ImageView to set the image to
     */
    private void setBookImage(Document document, ImageView bookImage) {
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImage.setImage(image);
        }
    }

    /**
     * Adds book information (title, ISBN, author, publisher) to the VBox.
     *
     * @param document the document to extract information from
     * @param bookInfo the VBox to add the labels to
     */
    private void addBookInfoToVBox(Document document, VBox bookInfo) {
        Label titleLabel = new Label("Title: " + document.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        Label isbnLabel = new Label("ISBN: " + document.getIsbn());
        Label authorLabel = new Label("Author: " + document.getAuthor());
        Label publisherLabel = new Label("Publisher: " + document.getPublisher());

        bookInfo.getChildren().addAll(titleLabel, isbnLabel, authorLabel, publisherLabel);
    }

    /**
     * Closes the window when the cancel button is clicked.
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
