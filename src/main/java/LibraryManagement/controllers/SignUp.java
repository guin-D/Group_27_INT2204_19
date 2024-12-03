package LibraryManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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

    public void initialize() {
        accessLevel.getItems().addAll("User", "Admin");
    }

    public void setStart(Start start) {
        this.start = start;

    }

    @FXML
    public void showSignIn() {
        if (start != null) {
            start.showSignIn();
        }
    }

    @FXML
    public void handleSignUp(ActionEvent event) {
        if (start != null) {
            start.handleSignUp(event, name.getText(), phoneNumber.getText(), password.getText(), accessLevel.getValue());
        }
    }
}
