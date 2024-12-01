package LibraryManagement.controllers;

import LibraryManagement.commandline.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Account {
    public User user;

    @FXML
    private TextField userName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField accessLevel;

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            mainDashboard();
        }
    }

    public void mainDashboard() {
        userName.setText(user.getName());
        phoneNumber.setText(user.getPhonenumber());
        accessLevel.setText(user.getAccessLevel());
    }
}
