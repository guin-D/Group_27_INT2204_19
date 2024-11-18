package LibraryManagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class signUp {
    private start start;
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

    public void setStart(start start) {
        this.start = start;

    }

    @FXML
    public void showSignIn() {
        if (start != null) {
            start.showSignIn();
        }
    }
}
