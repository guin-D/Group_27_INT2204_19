package LibraryManagement.controllers;

import LibraryManagement.commandline.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for the user account screen.
 * Displays and updates user information on the UI.
 */
public class Account {

    // Variable to hold the user object
    private User user;

    // TextFields to display user information
    @FXML
    private TextField userName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField accessLevel;

    /**
     * Sets the user information.
     * Once the user is set, their information will be displayed on the screen.
     *
     * @param user The User object to set.
     */
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            // Update the user information on the dashboard if the user is not null
            updateDashboard();
        }
    }

    /**
     * Updates the TextFields with the user's information.
     * This method retrieves data from the User object and displays it on the screen.
     */
    private void updateDashboard() {
        // Only update the dashboard if the user object is not null
        if (user != null) {
            userName.setText(user.getName());
            phoneNumber.setText(user.getPhoneNumber());
            accessLevel.setText(user.getAccessLevel());
        }
    }
}
