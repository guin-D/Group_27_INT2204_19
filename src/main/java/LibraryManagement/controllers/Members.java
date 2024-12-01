package LibraryManagement.controllers;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.OrderDatabase;
import LibraryManagement.Database.UserDatabase;
import LibraryManagement.commandline.Borrowing;
import LibraryManagement.commandline.Order;
import LibraryManagement.commandline.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.ArrayList;

public class Members {

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

    public void initialize() {
        memberList = FXCollections.observableArrayList();
        loadMemberData();

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0).toString()));
        phoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1).toString()));
        accessLevel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2).toString()));
        borrowedBook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3).toString()));
        overdue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4).toString()));
        bookOrders.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5).toString()));

        setupOptionsColumn();

        contentTable.setItems(memberList);
    }

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
                            memberList.remove(rowIndex);
                            ArrayList<Object> objects = memberList.get(rowIndex);
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

    private void loadMemberData() {
        ArrayList<User> users = UserDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        ArrayList<Order> orders = OrderDatabase.getInstance().selectAll();

        for (User user : users) {
            ArrayList<Object> row = new ArrayList<>();
            row.add(user.getName());
            row.add(user.getPhonenumber());
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
            for (Order o : orders) {
                if (o.getUserName().equals(user.getName())) {
                    order++;
                }
            }
            row.add(String.valueOf(order));

            memberList.add(row);
        }
    }
}
