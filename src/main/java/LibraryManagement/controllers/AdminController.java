package LibraryManagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Controller class for handling administrative tasks in the Library Management System.
 * This class manages the navigation between different administrative views such as Borrow History, Order History, Member Management, Resources, and the Google Book API.
 */
public class AdminController extends UserController {

    public AdminController() {
        super();
    }


    public AdminController(String name, String phoneNumber, String password, String accessLevel) {
        super();
    }

    /**
     * Loads and displays the Borrow History view.
     */
    @FXML
    public void showBorrowHistory() {
        loadView("/LibraryManagement/FXML/BorrowHistory.fxml");
    }

    /**
     * Loads and displays the Order History view.
     */
    @FXML
    public void showOrderHistory() {
        loadView("/LibraryManagement/FXML/OrderHistory.fxml");
    }

    /**
     * Loads and displays the Member Management view.
     */
    @FXML
    public void showMember() {
        loadView("/LibraryManagement/FXML/Members.fxml");
    }

    /**
     * Loads and displays the Resources view.
     * Sets the Admin controller for the Resources view.
     */
    @FXML
    public void showResource() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Resources.fxml"));
            Parent resourcePane = loader.load();

            Resources resources = loader.getController();
            resources.setAdmin(this); // Pass this Admin instance to the Resources controller.

            updateMainContent(resourcePane);
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * Loads and displays the Google Book API view.
     * Sets the Admin controller for the Google Book API view.
     */
    @FXML
    public void addBookAPI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/GoogleBookAPI.fxml"));
            Parent bookAPIPane = loader.load();

            GoogleBookAPI googleBookAPI = loader.getController();
            googleBookAPI.setAdmin(this); // Pass this Admin instance to the GoogleBookAPI controller.

            updateMainContent(bookAPIPane);
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * A helper method to load a view and update the main content area.
     *
     * @param viewPath the path to the FXML file.
     */
    private void loadView(String viewPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Parent pane = loader.load();
            updateMainContent(pane);
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * A helper method to update the main content area with the provided pane.
     *
     * @param pane the pane to be displayed in the main content area.
     */
    private void updateMainContent(Parent pane) {
        main.getChildren().clear();
        main.getChildren().setAll(pane);
    }

    /**
     * Handles IOException by printing the stack trace.
     *
     * @param e the exception to be handled.
     */
    private void handleException(IOException e) {
        e.printStackTrace();
    }
}
