package LibraryManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Controller class responsible for handling the actions and navigation
 * for normal users within the Library Management application.
 */
public class NormalUserController extends UserController {

    /**
     * Displays the borrow pane for the user to borrow books.
     *
     * @param event the event triggered when the user clicks the borrow button
     */
    public void showBorrow(ActionEvent event) {
        loadPane("/LibraryManagement/FXML/Borrow.fxml", event, controller -> {
            Borrow borrowController = (Borrow) controller;
            borrowController.uploadData(this.user);
        });
    }

    /**
     * Displays the member pane, showing user-related membership information.
     *
     * @param event the event triggered when the user clicks the member button
     */
    public void showMember(ActionEvent event) {
        loadPane("/LibraryManagement/FXML/Members.fxml", event, controller -> {
            // No specific controller actions needed for member pane
        });
    }

    /**
     * Displays the order pane, showing the user's orders.
     *
     * @param event the event triggered when the user clicks the order button
     */
    public void showOrder(ActionEvent event) {
        loadPane("/LibraryManagement/FXML/Orders.fxml", event, controller -> {
            Orders orderController = (Orders) controller;
            orderController.uploadData(this.user);
        });
    }

    /**
     * Generic method to load a pane from the specified FXML file and update the UI.
     *
     * @param fxmlPath         the path to the FXML file
     * @param event            the event triggered by the user action
     * @param controllerAction a lambda function to handle controller-specific actions
     */
    private void loadPane(String fxmlPath, ActionEvent event, PaneControllerAction controllerAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent pane = loader.load();

            Object controller = loader.getController();
            controllerAction.apply(controller);

            main.getChildren().clear();
            main.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Functional interface for handling controller-specific actions when loading a pane.
     */
    @FunctionalInterface
    private interface PaneControllerAction {
        void apply(Object controller);
    }
}
