package LibraryManagement.controllers;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.DAO.OrderDatabase;
import LibraryManagement.DAO.UserDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.Ordering;
import LibraryManagement.commandline.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import java.util.ArrayList;

/**
 * Controller class for the dashboard view that displays borrowing data
 * in a table format and allows sorting through a ComboBox.
 */
public class Dashboard {

    private User user;

    @FXML
    private Label totalBook;

    @FXML
    private Label borrowedBook;

    @FXML
    private Label overdue;

    @FXML
    private Label members;


    @FXML
    private TableView<ArrayList<Object>> tableView;

    @FXML
    private TableColumn<ArrayList<Object>, String> bookIdColumn;

    @FXML
    private TableColumn<ArrayList<Object>, String> titleColumn;

    @FXML
    private TableColumn<ArrayList<Object>, String> isbnColumn;

    @FXML
    private TableColumn<ArrayList<Object>, String> nameColumn;

    @FXML
    private TableColumn<ArrayList<Object>, String> authorColumn;

    @FXML
    private TableColumn<ArrayList<Object>, Integer> daysLeftColumn;

    @FXML
    private TableColumn<ArrayList<Object>, String> borrowedColumn;

    @FXML
    private TableView<ArrayList<Object>> tableView1;

    @FXML
    private TableColumn<ArrayList<Object>, String> bookIdColumn1;

    @FXML
    private TableColumn<ArrayList<Object>, String> titleColumn1;

    @FXML
    private TableColumn<ArrayList<Object>, String> isbnColumn1;

    @FXML
    private TableColumn<ArrayList<Object>, String> nameColumn1;

    @FXML
    private TableColumn<ArrayList<Object>, String> authorColumn1;

    @FXML
    private TableColumn<ArrayList<Object>, Integer> QTY;

    @FXML
    private TableColumn<ArrayList<Object>, String> price;

    @FXML
    private ComboBox<String> sortComboBox;

    private ObservableList<ArrayList<Object>> borrowingList;

    private ObservableList<ArrayList<Object>> placeOrderList;

    /**
     * Sets the user for the dashboard.
     *
     * @param user the user to be set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Initializes the dashboard by setting up the table columns
     * and loading the borrowing data.
     */
    public void initialize() {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<User> users = UserDatabase.getInstance().selectAll();

        totalBook.setText(String.valueOf(documents.size()));
        borrowedBook.setText(String.valueOf(borrowings.size()));
        int i = 0;
        for (Borrowing b : borrowings) {
            if (b.getDaysLeft() < 0) {
                i++;
            }
        }
        overdue.setText(String.valueOf(i));
        members.setText(String.valueOf(users.size()));

        borrowingList = FXCollections.observableArrayList();
        loadBorrowingData();

        // Set cell value factories for each table column
        bookIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(0).toString()));
        titleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(1).toString()));
        isbnColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(2).toString()));
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(3).toString()));
        authorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(4).toString()));
        daysLeftColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty((Integer) cellData.getValue().get(5)).asObject());
        borrowedColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(6).toString()));

        tableView.setPrefHeight(Region.USE_COMPUTED_SIZE);
        tableView.setItems(borrowingList);

        placeOrderList = FXCollections.observableArrayList();
        loadPlaceOrderData();

        bookIdColumn1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(0).toString()));
        titleColumn1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(1).toString()));
        isbnColumn1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(2).toString()));
        nameColumn1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(3).toString()));
        authorColumn1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(4).toString()));
        QTY.setCellValueFactory(cellData ->
                new SimpleIntegerProperty((Integer) cellData.getValue().get(5)).asObject());
        price.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().get(6).toString()));

        tableView1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        tableView1.setItems(placeOrderList);

        // Populate the sort ComboBox with options
        sortComboBox.setItems(FXCollections.observableArrayList("Latest", "Oldest"));
    }

    /**
     * Loads the borrowing data and populates the table with the information.
     */
    private void loadBorrowingData() {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

        int i = 1;
        for (Borrowing borrowing : borrowings) {
            Document matchingDocument = findMatchingDocument(borrowing.getDocumentTitle(), documents);
            if (matchingDocument != null) {
                ArrayList<Object> row = createRow(borrowing, matchingDocument, i);
                borrowingList.add(row);
                i++;
            }
        }
    }

    private void loadPlaceOrderData() {
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Ordering> orderings = OrderDatabase.getInstance().selectAll();

        int i = 1;
        for (Ordering o : orderings) {
            Document matchingDocument = findMatchingDocument(o.getDocumentTitle(), documents);
            if (matchingDocument != null) {
                ArrayList<Object> row = new ArrayList<>();
                row.add("#" + String.valueOf(i));
                row.add(matchingDocument.getTitle());
                row.add(matchingDocument.getIsbn());
                row.add(o.getUserName());
                row.add(matchingDocument.getAuthor());
                row.add(o.getQty());
                row.add(o.getPrice());
                placeOrderList.add(row);
                i++;
            }
        }

    }


    /**
     * Finds a document by its title.
     *
     * @param documentTitle the title of the document to search for
     * @param documents     the list of documents to search through
     * @return the matching document or null if no match is found
     */
    private Document findMatchingDocument(String documentTitle, ArrayList<Document> documents) {
        for (Document document : documents) {
            if (document.getTitle().equals(documentTitle)) {
                return document;
            }
        }
        return null;
    }

    /**
     * Creates a row of data for the table from borrowing and document information.
     *
     * @param borrowing the borrowing information
     * @param document  the document information
     * @return a list of objects representing the row
     */
    private ArrayList<Object> createRow(Borrowing borrowing, Document document, int i) {
        ArrayList<Object> row = new ArrayList<>();
        row.add("#" + String.valueOf(i));
        row.add(document.getTitle());
        row.add(document.getIsbn());
        row.add(borrowing.getUserName());
        row.add(document.getAuthor());
        row.add(borrowing.getDaysLeft());
        row.add(borrowing.getStart());
        return row;
    }
}
