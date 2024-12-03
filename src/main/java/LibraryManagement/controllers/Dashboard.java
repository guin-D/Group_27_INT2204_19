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

    public void setUser(User user) {
        this.user = user;
    }

    public void initialize() {
        borrowingList = FXCollections.observableArrayList();
        loadBorrowingData();


        bookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3).toString()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4).toString()));
        daysLeftColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue().get(5)).asObject());
        borrowedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6).toString()));
        tableView.setPrefHeight(Region.USE_COMPUTED_SIZE);
        tableView.setItems(borrowingList);
        sortComboBox.setItems(FXCollections.observableArrayList("Latest", "Oldest"));
    }

    private void loadBorrowingData() {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

        for (Borrowing borrowing : borrowings) {
            Document matchingDocument = null;
            for (Document document : documents) {
                if (document.getTitle().equals(borrowing.getDocumentTitle())) {
                    matchingDocument = document;
                    break;
                }
            }

            if (matchingDocument != null) {
                ArrayList<Object> row = new ArrayList<>();
                row.add(matchingDocument.getTitle());
                row.add(matchingDocument.getTitle());
                row.add(matchingDocument.getIsbn());
                row.add(borrowing.getUserName());
                row.add(matchingDocument.getAuthor());
                row.add(borrowing.getDaysLeft());
                row.add(borrowing.getStart());

                borrowingList.add(row);
            }
        }
    }
}
