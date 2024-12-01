package LibraryManagement.controllers;

import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Document;
import LibraryManagement.utils.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AddBook {

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

    private Book currentBook;

    public void setBookData(Book book) {
        this.currentBook = book;
        if (book != null) {
            if (book.getImageLinks() != null && !book.getImageLinks().isEmpty()) {
                Image image = new Image(book.getImageLinks(), true);
                bookImg.setImage(image);
            }
            title.setText(book.getTitle());
            isbn.setText(book.getIsbn());
            author.setText(book.getAuthor());
            publisher.setText(book.getPublisher());
        }
    }

    @FXML
    private void initialize() {
        save.setOnAction(event -> handleSave());

        cancel.setOnAction(event -> handleCancel());
    }

    private void handleSave() {
        Document document = new Document();
        if (currentBook != null) {
            document.setTitle(title.getText());
            document.setIsbn(isbn.getText());
            document.setAuthor(author.getText());
            document.setPublisher(author.getText());
            document.setBrwcopiers(Integer.parseInt(totalQuantity.getText()));
            document.setQty(Integer.parseInt(totalOrder.getText()));
            document.setPrice(Double.parseDouble(price.getText()));
            document.setImageLink(currentBook.getImageLinks());
            DocumentDatabase.getInstance().insert(document);
        }

        closeWindow();
    }

    private void handleCancel() {
        closeWindow();
    }


    private void closeWindow() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }
}
