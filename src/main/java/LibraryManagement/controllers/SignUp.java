package LibraryManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Controller for handling the sign-up functionality of the user interface.
 */
public class SignUp {

    private Start start;

    @FXML
    private Button signIn1;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField password;

    @FXML
    private ChoiceBox<String> accessLevel;

    @FXML
    private Button signUp;

    @FXML
    private Button signIn2;

    /**
     * Initializes the sign-up form, populating the access level choice box with available options.
     */
    public void initialize() {
        accessLevel.getItems().addAll("User", "Admin");
    }

    /**
     * Sets the reference to the Start controller.
     *
     * @param start the Start controller instance
     */
    public void setStart(Start start) {
        this.start = start;
    }

    /**
     * Navigates to the sign-in screen when the sign-in button is clicked.
     */
    @FXML
    public void showSignIn() {
        if (start != null) {
            start.showSignIn();
        }
    }

    /**
     * Handles the sign-up process when the sign-up button is clicked.
     *
     * @param event the ActionEvent triggered by the sign-up button
     */
    @FXML
    public void handleSignUp(ActionEvent event) {
        String userName = name.getText();
        String phone = phoneNumber.getText();
        String pass = password.getText();
        String access = accessLevel.getValue();

        if (start != null) {
            start.handleSignUp(event, userName, phone, pass, access);
        }
    }
}
