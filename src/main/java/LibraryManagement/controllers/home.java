package LibraryManagement.controllers;

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

public class home {

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

    public void initialize() {
        showDashboard();
    }

    public void showAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/account.fxml"));
            Parent accountPane = loader.load();

            // Take the Controller of account and transmit the object 'home'
            account accountController = loader.getController();
            accountController.setHome(this);

            main.getChildren().clear();
            main.getChildren().setAll(accountPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/dashboard.fxml"));
            Parent dashboardPane = loader.load();

            // Take the Controller of dashboard and transmit the object 'home'
            dashboard dashboardController = loader.getController();
            dashboardController.setHome(this);

            main.getChildren().clear();
            main.getChildren().setAll(dashboardPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBorrow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/borrow.fxml"));
            Parent borrowPane = loader.load();

            borrow borrowController = loader.getController();
            borrowController.setHome(this);

            main.getChildren().clear();
            main.getChildren().setAll(borrowPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMember() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/members.fxml"));
            Parent memberPane = loader.load();

            members memberController = loader.getController();
            memberController.setHome(this);

            main.getChildren().clear();
            main.getChildren().setAll(memberPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showResource() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/resources.fxml"));
            Parent resourcePane = loader.load();

            resources resourcesController = loader.getController();
            resourcesController.setHome(this);

            main.getChildren().clear();
            main.getChildren().setAll(resourcePane);
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
