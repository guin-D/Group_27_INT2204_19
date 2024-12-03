package LibraryManagement.controllers;

import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Document;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class BorrowHistory extends Admin {

    @FXML
    private ComboBox<String> filterSearch;

    @FXML
    private TextField keywords;

    @FXML
    private TextField qty;

    @FXML
    private Button searchButton;

    @FXML
    private GridPane main;

    public void initialize() {
        uploadData();
    }

    public void uploadData() {
        Task<Void> loadBooksTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

                Platform.runLater(() -> {
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

        bookBtn.setOnAction(event -> handleBorrowHistory(document));

        return bookBtn;
    }


    @FXML
    private void handleBorrowHistory(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/BorrowHistoryMore.fxml"));
            Parent addBookRoot = loader.load();

            BorrowHistoryMore controller = loader.getController();
            controller.setDocumentData(document);

            Stage stage = new Stage();
            stage.setTitle("Borrow History");
            stage.setScene(new Scene(addBookRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}