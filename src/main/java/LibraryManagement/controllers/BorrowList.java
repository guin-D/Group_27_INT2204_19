package LibraryManagement.controllers;


import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Document;
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

public class BorrowList extends Borrow {

    @FXML
    private VBox mainBox;

    @FXML
    private Button cancel;

    @FXML
    public void initialize() {
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        for (Borrowing borrowing : borrowings) {
            for (Document document : documents) {
                if (document.getTitle().matches(borrowing.getDocumentTitle())) {
                    HBox borrowBox = addBookToBorrowedList(document, borrowing);
                    mainBox.getChildren().add(borrowBox);
                }
            }
        }
    }

    private HBox addBookToBorrowedList(Document document, Borrowing borrowing) {
        HBox bookBox = new HBox();
        bookBox.setSpacing(20);
        bookBox.setPadding(new Insets(10, 30, 0, 0));
        bookBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;");
        bookBox.setPrefWidth(1000);

        // Book thumbnail
        ImageView bookImage = new ImageView();
        bookImage.setFitWidth(128);
        bookImage.setFitHeight(175);
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImage.setImage(image);
        }

        // Book info
        VBox bookInfo = new VBox();
        bookInfo.setSpacing(10);
        Label titleLabel = new Label("Title: " + document.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        Label isbnLabel = new Label("ISBN: " + document.getIsbn());
        Label authorLabel = new Label("Author: " + document.getAuthor());
        Label publisherLabel = new Label("Publisher: " + document.getPublisher());

        bookInfo.getChildren().addAll(titleLabel, isbnLabel, authorLabel, publisherLabel);

        ImageView addImage = new ImageView(getClass().getResource("/LibraryManagement/Image/add.png").toExternalForm());
        addImage.setFitHeight(20);
        addImage.setFitWidth(20);


        bookBox.getChildren().addAll(bookImage, bookInfo);
        return bookBox;
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

}
