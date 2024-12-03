package LibraryManagement.controllers;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.UserDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Document;
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

public class BorrowHistoryMore {
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
    private TableColumn<ArrayList<Object>, String> phoneNumber;

    @FXML
    private TableColumn<ArrayList<Object>, String> borrowedDate;

    @FXML
    private TableColumn<ArrayList<Object>, String> returnedDate;

    @FXML
    private TableColumn<ArrayList<Object>, String> status;

    private ObservableList<ArrayList<Object>> borrowHistoryList;


    @FXML
    public void setDocumentData(Document document) {

        this.document = document;
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImg.setImage(image);
        }
        title.setText(document.getTitle());
        author.setText(document.getAuthor());


        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        int borrowCount = 0;
        for (Borrowing b : borrowings) {
            if (b.getDocumentTitle().matches(document.getTitle())) {
                borrowCount++;
            }
        }

        totalQuantity.setText(String.valueOf(document.getBrwcopiers() + borrowCount));
        borrowQuantity.setText(String.valueOf(borrowCount));
        avaiQuantity.setText(String.valueOf(document.getBrwcopiers()));

        borrowHistoryList = FXCollections.observableArrayList();
        loadBorrowHistory();

        user.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        phoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        borrowedDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        returnedDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3).toString()));
        status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4).toString()));
        main.setItems(borrowHistoryList);
    }

    @FXML
    public void loadBorrowHistory() {


        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<User> users = UserDatabase.getInstance().selectAll();


        for (Borrowing borrowing : borrowings) {
            if (borrowing.getDocumentTitle().equals(document.getTitle())) {
                User user = null;
                for (User user1 : users) {
                    if (user1.getName().equals(borrowing.getUserName())) {
                        user = user1;
                        break;
                    }
                }
                if (user != null) {
                    ArrayList<Object> row = new ArrayList<>();
                    row.add(borrowing.getUserName() != null ? borrowing.getUserName() : "N/A");
                    row.add(user.getPhoneNumber() != null ? user.getPhoneNumber() : "N/A");
                    row.add(borrowing.getStart() != null ? borrowing.getStart() : "N/A");
                    row.add(borrowing.getFinish() != null ? borrowing.getFinish() : "N/A");
                    row.add(borrowing.getDaysLeft() > 0
                            ? borrowing.getDaysLeft() + " days left"
                            : "Overdue");

                    borrowHistoryList.add(row);
                }
            }
        }

    }
}