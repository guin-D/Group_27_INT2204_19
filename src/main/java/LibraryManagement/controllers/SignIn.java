package LibraryManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for handling the sign-in functionality of the user interface.
 */
public class SignIn {

    private Start start;

    @FXML
    private Button signUp1;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField password;

    @FXML
    private Button signIn;

    @FXML
    private Button signUp2;

    /**
     * Sets the reference to the Start controller.
     *
     * @param start the Start controller instance
     */
    public void setStart(Start start) {
        this.start = start;
    }

    /**
     * Navigates to the sign-up screen when the sign-up button is clicked.
     */
    @FXML
    public void showSignUp() {
        if (start != null) {
            start.showSignUp();
        }
    }

    /**
     * Handles the sign-in process when the sign-in button is clicked.
     *
     * @param event the ActionEvent triggered by the sign-in button
     */
    @FXML
    public void handleSignIn(ActionEvent event) {
        String phone = phoneNumber.getText();
        String pass = password.getText();

        if (start != null) {
            start.handleSignIn(event, phone, pass);
        }
    }
}
