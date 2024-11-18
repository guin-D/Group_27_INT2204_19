package LibraryManagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class signIn {
    private start start;

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

    public void setStart(start start) {
        this.start = start;

    }
    

    @FXML
    public void showSignUp() {
        if (start != null) {
            start.showSignUp();
        }
    }

}
