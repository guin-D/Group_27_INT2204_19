package LibraryManagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class start {
    @FXML
    public Pane main;

    public void initialize() {
        showSignIn();
    }

    @FXML
    public void showSignIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/signIn.fxml"));
            Parent signInPane = loader.load();

            // Take the Controller of signIn and transmit the object 'start'
            signIn signInController = loader.getController();
            signInController.setStart(this);  // Truyền đối tượng 'start' vào controller 'signIn'

            main.getChildren().clear();
            main.getChildren().setAll(signInPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/signUp.fxml"));
            Parent signUpPane = loader.load();

            signUp signUpController = loader.getController();
            signUpController.setStart(this);
            main.getChildren().clear();
            main.getChildren().setAll(signUpPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
