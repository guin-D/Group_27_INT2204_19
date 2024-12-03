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

/**
 * Controller for displaying detailed borrowing history for a specific document.
 * The controller manages displaying information about a document and its borrowing history.
 */
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

    /**
     * Sets the data for the document and loads the borrow history.
     *
     * @param document the document whose data is to be displayed.
     */
    @FXML
    public void setDocumentData(Document document) {
        this.document = document;

        // Display book image if available
        if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
            Image image = new Image(document.getImageLink(), true);
            bookImg.setImage(image);
        }

        // Display document information
        title.setText(document.getTitle());
        author.setText(document.getAuthor());

        // Calculate and display quantities
        int borrowCount = countBorrowedCopies(document);
        totalQuantity.setText(String.valueOf(document.getBrwcopiers() + borrowCount));
        borrowQuantity.setText(String.valueOf(borrowCount));
        avaiQuantity.setText(String.valueOf(document.getBrwcopiers()));

        // Load and display borrowing history
        borrowHistoryList = FXCollections.observableArrayList();
        loadBorrowHistory();

        // Set up table columns with data
        user.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        phoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        borrowedDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        returnedDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3).toString()));
        status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4).toString()));
        main.setItems(borrowHistoryList);
    }

    /**
     * Counts the number of times a document has been borrowed.
     *
     * @param document the document to count borrowings for.
     * @return the number of borrowings.
     */
    private int countBorrowedCopies(Document document) {
        int borrowCount = 0;
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        for (Borrowing b : borrowings) {
            if (b.getDocumentTitle().equals(document.getTitle())) {
                borrowCount++;
            }
        }
        return borrowCount;
    }

    /**
     * Loads the borrowing history for the document.
     * Matches borrowings with the user and prepares data for display.
     */
    @FXML
    public void loadBorrowHistory() {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<User> users = UserDatabase.getInstance().selectAll();

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getDocumentTitle().equals(document.getTitle())) {
                User borrowingUser = findUserByName(borrowing.getUserName(), users);

                if (borrowingUser != null) {
                    ArrayList<Object> row = new ArrayList<>();
                    row.add(borrowing.getUserName() != null ? borrowing.getUserName() : "N/A");
                    row.add(borrowingUser.getPhoneNumber() != null ? borrowingUser.getPhoneNumber() : "N/A");
                    row.add(borrowing.getStart() != null ? borrowing.getStart() : "N/A");
                    row.add(borrowing.getFinish() != null ? borrowing.getFinish() : "N/A");
                    row.add(borrowing.getDaysLeft() > 0 ? borrowing.getDaysLeft() + " days left" : "Overdue");

                    borrowHistoryList.add(row);
                }
            }
        }
    }

    /**
     * Finds a user by their name.
     *
     * @param userName the name of the user to find.
     * @param users    the list of all users.
     * @return the user object if found, otherwise null.
     */
    private User findUserByName(String userName, ArrayList<User> users) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
