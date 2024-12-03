package LibraryManagement.controllers;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Document;
import LibraryManagement.commandline.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> sortComboBox;

    private ObservableList<ArrayList<Object>> borrowingList;

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

        // Populate the sort ComboBox with options
        sortComboBox.setItems(FXCollections.observableArrayList("Latest", "Oldest"));
    }

    /**
     * Loads the borrowing data and populates the table with the information.
     */
    private void loadBorrowingData() {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

        for (Borrowing borrowing : borrowings) {
            Document matchingDocument = findMatchingDocument(borrowing.getDocumentTitle(), documents);
            if (matchingDocument != null) {
                ArrayList<Object> row = createRow(borrowing, matchingDocument);
                borrowingList.add(row);
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
    private ArrayList<Object> createRow(Borrowing borrowing, Document document) {
        ArrayList<Object> row = new ArrayList<>();
        row.add(document.getTitle());
        row.add(document.getTitle());
        row.add(document.getIsbn());
        row.add(borrowing.getUserName());
        row.add(document.getAuthor());
        row.add(borrowing.getDaysLeft());
        row.add(borrowing.getStart());
        return row;
    }
}
