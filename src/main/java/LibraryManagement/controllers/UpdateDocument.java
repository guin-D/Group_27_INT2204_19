package LibraryManagement.controllers;

import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Document;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UpdateDocument {
    @FXML
    private ImageView documentImg;

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

    private Document currentDocument;

    public void setData(Document document) {
        this.currentDocument = document;
        if (this.currentDocument != null) {
            if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
                Image image = new Image(document.getImageLink(), true);
                documentImg.setImage(image);
            }
            title.setText(document.getTitle());
            isbn.setText(document.getIsbn());
            author.setText(document.getAuthor());
            publisher.setText(document.getPublisher());
            totalQuantity.setText(String.valueOf(document.getBrwcopiers()));
            totalOrder.setText(String.valueOf(document.getQty()));
            price.setText(String.valueOf(document.getPrice()));
        }
    }

    @FXML
    private void initialize() {
        save.setOnAction(event -> handleSave());

        cancel.setOnAction(event -> handleCancel());
    }

    private void handleSave() {
        DocumentDatabase.getInstance().update(currentDocument);

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