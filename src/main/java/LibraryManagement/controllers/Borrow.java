package LibraryManagement.controllers;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Document;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Borrow extends NormalUserController {

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

    private HBox createBookButton(Document document) {
        HBox bookBox = new HBox();
        bookBox.setAlignment(Pos.CENTER_LEFT);
        bookBox.setPrefWidth(250);
        bookBox.setPrefHeight(200);
        bookBox.setSpacing(10);
        bookBox.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10;");

        ImageView bookImage = new ImageView(getClass().getResource("/LibraryManagement/Image/" + document.getIsbn() + ".jpg").toExternalForm());
        bookImage.setFitHeight(200);

        VBox content = new VBox();
        content.setSpacing(10);
        ImageView stars = new ImageView(getClass().getResource("/LibraryManagement/Image/stars.png").toExternalForm());
        stars.setFitHeight(20);
        stars.setFitWidth(120);
        Label title = new Label("Title: " + document.getTitle());
        title.setStyle("-fx-text-fill: #203169; -fx-font-weight: bold");
        title.setWrapText(true);
        title.setMaxWidth(140);
        Label qty = new Label("Available qty: " + document.getQty());
        qty.setStyle("-fx-text-fill: #203169");

        Button borrowBtn = new Button();
        borrowBtn.setText("Borrow");
        borrowBtn.setStyle("-fx-background-color: #203169; -fx-text-fill: #FFFFFF");
        borrowBtn.setOnAction(event -> handleBorrowing(document));

        Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        content.getChildren().setAll(stars, title, qty, region, borrowBtn);
        bookBox.getChildren().addAll(bookImage, content);

        return bookBox;
    }


    @FXML
    private void handleBorrowing(Document document) {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        boolean j = true;
        for (Borrowing b : borrowings) {
            if (b.getDocumentTitle().equals(document.getTitle())
                    && b.getUserName().equals(user.getName())) {
                j = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Borrowing fail!");
                alert.setContentText("You have borrowed this document before!");
                alert.showAndWait();
                break;
            }
        }
        if (j) {
            if (document.getBrwcopiers() > 1) {
                Borrowing borrowing = new Borrowing(document, user);
                document.setBrwcopiers(document.getBrwcopiers() - 1);
                DocumentDatabase.getInstance().update(document);
                borrowings.add(borrowing);
                BorrowingDatabase.getInstance().insert(borrowing);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Borrowing successfully!");
                alert.setContentText("You must return the document before 14 days from now\n"
                        + "Expiry date: " + borrowing.getFinish() + "\nEnjoy!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Borrowing fail!");
                alert.setContentText("This document isn't available for borrowing!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void handleBorrowList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/BorrowList.fxml"));
            Parent addBookRoot = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Borrow list");
            stage.setScene(new Scene(addBookRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
