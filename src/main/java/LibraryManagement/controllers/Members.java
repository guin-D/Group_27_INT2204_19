package LibraryManagement.controllers;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.OrderDatabase;
import LibraryManagement.DAO.UserDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Ordering;
import LibraryManagement.commandline.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.ArrayList;

public class Members {

    @FXML
    private TextField textField;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<ArrayList<Object>> contentTable;

    @FXML
    private TableColumn<ArrayList<Object>, String> name;

    @FXML
    private TableColumn<ArrayList<Object>, String> phoneNumber;

    @FXML
    private TableColumn<ArrayList<Object>, String> accessLevel;

    @FXML
    private TableColumn<ArrayList<Object>, String> borrowedBook;

    @FXML
    private TableColumn<ArrayList<Object>, String> overdue;

    @FXML
    private TableColumn<ArrayList<Object>, String> bookOrders;

    @FXML
    private TableColumn<ArrayList<Object>, Void> options;

    private ObservableList<ArrayList<Object>> memberList;

    /**
     * Initializes the members table with user data and setups columns.
     */
    public void initialize() {
        memberList = FXCollections.observableArrayList();
        loadMemberData();

        // Set up table columns to display data
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        phoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        accessLevel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        borrowedBook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3).toString()));
        overdue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4).toString()));
        bookOrders.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5).toString()));

        setupOptionsColumn();

        contentTable.setItems(memberList);
    }

    /**
     * Set up the options column to allow deletion of users from the table.
     */
    private void setupOptionsColumn() {
        options.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ArrayList<Object>, Void> call(final TableColumn<ArrayList<Object>, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button();

                    {
                        deleteButton.setStyle("-fx-background-color: transparent;");
                        ImageView deleteImg = new ImageView(getClass().getResource("/LibraryManagement/Image/trash.png").toExternalForm());
                        deleteImg.setFitHeight(15);
                        deleteImg.setFitWidth(15);
                        deleteButton.setGraphic(deleteImg);

                        deleteButton.setOnAction(event -> {
                            int rowIndex = getIndex();
                            ArrayList<Object> objects = memberList.get(rowIndex);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("");
                            alert.setHeaderText("Delete user successfully!");
                            alert.showAndWait();
                            memberList.remove(rowIndex);
                            UserDatabase.getInstance().remove(objects.get(0).toString());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        });
    }

    /**
     * Loads the members' data into the table from the database.
     */
    private void loadMemberData() {
        ArrayList<User> users = UserDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<Ordering> orderings = OrderDatabase.getInstance().selectAll();

        for (User user : users) {
            ArrayList<Object> row = new ArrayList<>();
            row.add(user.getName());
            row.add(user.getPhoneNumber());
            row.add(user.getAccessLevel());

            int borrow = 0;
            int over = 0;
            for (Borrowing b : borrowings) {
                if (b.getUserName().equals(user.getName())) {
                    borrow++;
                    if (b.getDaysLeft() < 0) {
                        over++;
                    }
                }
            }
            row.add(String.valueOf(borrow));
            row.add(String.valueOf(over));

            int order = 0;
            for (Ordering o : orderings) {
                if (o.getUserName().equals(user.getName())) {
                    order++;
                }
            }
            row.add(String.valueOf(order));

            memberList.add(row);
        }
    }

    /**
     * Handles the search functionality. Filters members based on the search input.
     */
    @FXML
    private void handleSearch() {
        String searchText = textField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            loadMemberData();
            return;
        }

        ObservableList<ArrayList<Object>> filteredList = FXCollections.observableArrayList();

        for (ArrayList<Object> row : memberList) {
            String name = row.get(0).toString().toLowerCase();
            String phoneNumber = row.get(1).toString().toLowerCase();
            String accessLevel = row.get(2).toString().toLowerCase();

            if (name.contains(searchText) || phoneNumber.contains(searchText) || accessLevel.contains(searchText)) {
                filteredList.add(row);
            }
        }

        contentTable.setItems(filteredList);
    }
}
