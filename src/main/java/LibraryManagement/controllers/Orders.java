package LibraryManagement.controllers;

import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.DAO.OrderDatabase;
import LibraryManagement.commandline.*;
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
 * Controller for the Orders section, allowing users to search, view, and order documents.
 */
public class Orders {

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
     * Loads and displays all documents available for ordering.
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
     * Creates a horizontal box containing details and an order button for a document.
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

        Label qty = new Label("Available qty: " + document.getQty());
        qty.setStyle("-fx-text-fill: #203169");

        TextField quantity = new TextField("1");
        quantity.setPrefWidth(30);
        quantity.setAlignment(Pos.CENTER);

        Button incrementBtn = new Button("+");
        incrementBtn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #203169");
        Button decrementBtn = new Button("-");
        decrementBtn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #203169");

        incrementBtn.setOnAction(event -> {
            int currentValue = Integer.parseInt(quantity.getText());
            quantity.setText(String.valueOf(currentValue + 1));
        });

        decrementBtn.setOnAction(event -> {
            int currentValue = Integer.parseInt(quantity.getText());
            if (currentValue > 1) {
                quantity.setText(String.valueOf(currentValue - 1));
            }
        });

        HBox quantityBox = new HBox();
        quantityBox.setSpacing(5);
        quantityBox.setAlignment(Pos.CENTER_LEFT);
        quantityBox.getChildren().addAll(decrementBtn, quantity, incrementBtn);

        Button orderBtn = new Button("Order");
        orderBtn.setStyle("-fx-background-color: #203169; -fx-text-fill: #FFFFFF");
        orderBtn.setOnAction(event -> handleOrder(document, Integer.valueOf(quantity.getText())));

        Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);

        content.getChildren().setAll(stars, title, qty, region, quantityBox, orderBtn);
        bookBox.getChildren().addAll(bookImage, content);

        return bookBox;
    }

    /**
     * Handles the ordering of a document, ensuring constraints like availability and duplicates.
     *
     * @param document The document to order.
     */
    @FXML
    private void handleOrder(Document document, int qty) {
        ArrayList<Ordering> orderings = OrderDatabase.getInstance().selectAll();
        if (document.getQty() > 0) {
            Ordering ordering = new Ordering(document.getTitle(), user.getName(), document.getPrice(), qty);
            document.setQty(document.getQty() - qty);
            DocumentDatabase.getInstance().update(document);
            orderings.add(ordering);
            OrderDatabase.getInstance().insert(ordering);

            showAlert("Order placed successfully!", "Your order has been placed.");
        } else {
            showAlert("Order fail!", "This document isn't available for order.");
        }
    }

    /**
     * Displays the order list for the current user.
     */
    @FXML
    public void handleOrderList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/OrderList.fxml"));
            Parent orderListPane = loader.load();

            OrderList controller = loader.getController();
            controller.initialize(user);

            Stage stage = new Stage();
            stage.setTitle("Orders list");
            stage.setScene(new Scene(orderListPane));
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
