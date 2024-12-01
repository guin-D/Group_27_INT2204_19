package LibraryManagement.controllers;

import LibraryManagement.commandline.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class UserController {
    @FXML
    public Pane main;

    public User user;


    @FXML
    public void showAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Account.fxml"));
            Parent accountPane = loader.load();

            Account accountController = loader.getController();
            accountController.setUser(this.user);

            main.getChildren().clear();
            main.getChildren().setAll(accountPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Dashboard.fxml"));
            Parent dashboardPane = loader.load();

            Dashboard dashboardController = loader.getController();
            dashboardController.setUser(user);

            main.getChildren().clear();
            main.getChildren().setAll(dashboardPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Start.fxml"));
            Scene startScene = new Scene(loader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(startScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        this.user = user;
        if (main != null) {
            showDashboard();
        }
    }
}
