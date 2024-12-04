package LibraryManagement.controllers;

import LibraryManagement.Database.OrderDatabase;
import LibraryManagement.Database.UserDatabase;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.Order;
import LibraryManagement.commandline.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Controller for displaying detailed order history information for a document.
 */
public class OrderHistoryMore {

    private Document document;

    @FXML
    private ImageView bookImg;

    @FXML
    private TextField title;

    @FXML
    private TextField author;

    @FXML
    private TextField totalQuantity;

    @FXML
    private TextField borrowQuantity;

    @FXML
    private TextField avaiQuantity;

    @FXML
    private TableView<ArrayList<Object>> main;

    @FXML
    private TableColumn<ArrayList<Object>, String> user;

    @FXML
    private TableColumn<ArrayList<Object>, String> price;

    @FXML
    private TableColumn<ArrayList<Object>, String> qty;

    private ObservableList<ArrayList<Object>> borrowHistoryList;

    /**
     * Sets the document data and populates the order history details.
     *
     * @param document the document to display information for
     */
    @FXML
    public void setDocumentData(Document document) {
        this.document = document;
        displayBookImage();
        populateDocumentDetails();
        loadOrderHistory();
        configureTableColumns();
    }

    /**
     * Displays the book image in the image view.
     */
    private void displayBookImage() {
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImg.setImage(image);
        }
    }

    /**
     * Populates the document details (title, author, quantities) in the UI.
     */
    private void populateDocumentDetails() {
        title.setText(document.getTitle());
        author.setText(document.getAuthor());

        int borrowCount = calculateBorrowCount();
        totalQuantity.setText(String.valueOf(document.getBrwcopiers() + borrowCount));
        borrowQuantity.setText(String.valueOf(borrowCount));
        avaiQuantity.setText(String.valueOf(document.getBrwcopiers()));
    }

    /**
     * Calculates the number of times the document has been borrowed.
     *
     * @return the borrow count
     */
    private int calculateBorrowCount() {
        ArrayList<Order> orders = OrderDatabase.getInstance().selectAll();
        int borrowCount = 0;
        for (Order order : orders) {
            if (order.getDocumentTitle().matches(document.getTitle())) {
                borrowCount++;
            }
        }
        return borrowCount;
    }

    /**
     * Loads the order history and populates the table with order data.
     */
    @FXML
    public void loadOrderHistory() {
        borrowHistoryList = FXCollections.observableArrayList();
        ArrayList<Order> orders = OrderDatabase.getInstance().selectAll();

        for (Order order : orders) {
            if (order.getDocumentTitle().equals(document.getTitle())) {
                ArrayList<Object> row = new ArrayList<>();
                row.add(order.getUserName() != null ? order.getUserName() : "N/A");
                row.add(order.getPrice());
                row.add(order.getQty());

                borrowHistoryList.add(row);
            }
        }
        main.setItems(borrowHistoryList);
    }

    /**
     * Configures the table columns to display order data.
     */
    private void configureTableColumns() {
        user.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        price.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        qty.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
    }
}
