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
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

public class Resources {

    private Admin admin;

    @FXML
    private GridPane gridPane;


    public void createBookBtn() {
        Task<Void> loadBooksTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
                Platform.runLater(() -> {
                    for (int i = 0; i < documents.size(); i++) {
                        Document document = documents.get(i);
                        Button bookbtn = new Button();
                        bookbtn.setPrefWidth(275);
                        bookbtn.setPrefHeight(300);
                        bookbtn.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10;");

                        ImageView bookImage = new ImageView();
                        bookImage.setFitWidth(128);
                        bookImage.setFitHeight(175);
                        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
                            Image image = new Image(document.getImageLink(), true);
                            bookImage.setImage(image);
                        }


                        bookbtn.setGraphic(bookImage);
                        bookbtn.setText(document.getTitle());
                        bookbtn.setContentDisplay(ContentDisplay.TOP);
                        bookbtn.setOnAction(event -> handleUpdate(document));
                        int row = i / 4;
                        int col = i % 4;

                        gridPane.add(bookbtn, col, row);
                    }
                });
                return null;
            }
        };

        new Thread(loadBooksTask).start();
    }

    private void handleUpdate(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/UpdateDocument.fxml"));
            Parent addBookRoot = loader.load();

            UpdateDocument controller = loader.getController();
            controller.setData(document);

            Stage stage = new Stage();
            stage.setTitle("Add Book");
            stage.setScene(new Scene(addBookRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        if (admin != null) {
            createBookBtn();
        }
    }

    @FXML
    private void handleAdd() {
        if (admin != null) {
            admin.addBookAPI();
        }
    }


}