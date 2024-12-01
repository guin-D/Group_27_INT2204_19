package LibraryManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    public void setStart(Start start) {
        this.start = start;

    }

    @FXML
    public void showSignUp() {
        if (start != null) {
            start.showSignUp();
        }
    }

    @FXML
    public void handleSignIn(ActionEvent event) {
        if (start != null) {
            start.handleSignIn(event, phoneNumber.getText(), password.getText());
        }
    }
}
