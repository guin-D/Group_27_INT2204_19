package LibraryManagement.controllers;

import LibraryManagement.commandline.Database;
import LibraryManagement.commandline.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class UserController {
    @FXML
    public Pane main;

    @FXML
    private Button account;

    @FXML
    private Button dashboard;

    @FXML
    private Button borrow;

    @FXML
    private Button member;

    @FXML
    private Button resources;

    @FXML
    private Button logout;

    private Database database;
    private User user;

    public void initialize() {
        showDashboard();
    }

    public void setUser(Database database, User user) {
        this.database = database;
        this.user = user;
    }

    @FXML
    public void showAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/account.fxml"));
            Parent accountPane = loader.load();

            // Take the Controller of account and transmit the object 'home'
            account accountController = loader.getController();
            accountController.setUser(this);

            main.getChildren().clear();
            main.getChildren().setAll(accountPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/dashboard.fxml"));
            Parent dashboardPane = loader.load();

            // Take the Controller of dashboard and transmit the object 'home'
            dashboard dashboardController = loader.getController();
            dashboardController.setUser(this);

            main.getChildren().clear();
            main.getChildren().setAll(dashboardPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/start.fxml"));
            Scene startScene = new Scene(loader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(startScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
