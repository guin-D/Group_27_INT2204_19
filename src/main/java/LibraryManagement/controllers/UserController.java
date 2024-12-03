package LibraryManagement.controllers;

import LibraryManagement.commandline.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class UserController {

    @FXML
    public Pane main;

    public User user;

    /**
     * Show the account details page.
     * This method loads the account view and sets the user to the controller.
     *
     * @param event The event triggered by the action (e.g., button click).
     */
    @FXML
    public void showAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Account.fxml"));
            Parent accountPane = loader.load();

            Account accountController = loader.getController();
            accountController.setUser(this.user);

            // Clear the current main content and add the account view
            main.getChildren().clear();
            main.getChildren().setAll(accountPane);
        } catch (IOException e) {
            showErrorAlert("Failed to load Account view", e.getMessage());
        }
    }

    /**
     * Show the dashboard page.
     * This method loads the dashboard view and sets the user to the controller.
     */
    @FXML
    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Dashboard.fxml"));
            Parent dashboardPane = loader.load();

            Dashboard dashboardController = loader.getController();
            dashboardController.setUser(user);

            // Clear the current main content and add the dashboard view
            main.getChildren().clear();
            main.getChildren().setAll(dashboardPane);
        } catch (IOException e) {
            showErrorAlert("Failed to load Dashboard view", e.getMessage());
        }
    }

    /**
     * Handle the logout action.
     * This method switches the scene to the Start screen.
     *
     * @param event The event triggered by the action (e.g., button click).
     */
    @FXML
    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Start.fxml"));
            Scene startScene = new Scene(loader.load());

            // Get the current stage and change the scene
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(startScene);
        } catch (Exception e) {
            showErrorAlert("Logout Error", e.getMessage());
        }
    }

    /**
     * Set the user object and show the dashboard.
     *
     * @param user The user object to be set.
     */
    public void setUser(User user) {
        this.user = user;
        if (main != null) {
            showDashboard();
        }
    }

    /**
     * Show an error alert with the given title and message.
     *
     * @param title   The title of the error alert.
     * @param message The error message to be displayed.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
