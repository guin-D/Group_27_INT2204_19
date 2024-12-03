package LibraryManagement.controllers;

import LibraryManagement.commandline.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for managing user account details in the Library Management System.
 */
public class Account {

    /**
     * The current user whose details are being managed.
     */
    private User user;

    @FXML
    private TextField userName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField accessLevel;

    /**
     * Sets the user and initializes the dashboard with user details.
     *
     * @param user the user object containing account details
     */
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            updateDashboard();
        }
    }

    /**
     * Updates the dashboard fields with the current user's details.
     */
    private void updateDashboard() {
        userName.setText(user.getName());
        phoneNumber.setText(user.getPhoneNumber());
        accessLevel.setText(user.getAccessLevel());
    }
}
