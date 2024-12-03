package LibraryManagement.controllers;

import LibraryManagement.Database.UserDatabase;
import LibraryManagement.commandline.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Controller for managing the start screen of the application.
 * This includes handling user sign-in and sign-up functionalities.
 */
public class Start {
    @FXML
    private Pane main;

    /**
     * Initializes the start screen by showing the sign-in form.
     */
    public void initialize() {
        showSignIn();
    }

    /**
     * Loads and displays the sign-in screen.
     */
    @FXML
    public void showSignIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/SignIn.fxml"));
            Parent signInPane = loader.load();

            // Set the current 'Start' controller into the SignIn controller.
            SignIn signInController = loader.getController();
            signInController.setStart(this);

            // Replace the current pane with the sign-in pane.
            main.getChildren().clear();
            main.getChildren().setAll(signInPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the sign-up screen.
     */
    @FXML
    public void showSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/SignUp.fxml"));
            Parent signUpPane = loader.load();

            // Set the current 'Start' controller into the SignUp controller.
            SignUp signUpController = loader.getController();
            signUpController.setStart(this);

            // Replace the current pane with the sign-up pane.
            main.getChildren().clear();
            main.getChildren().setAll(signUpPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the sign-in process by verifying user credentials.
     * The process is executed asynchronously using a background thread.
     *
     * @param event       The action event triggered by the sign-in button.
     * @param phoneNumber The phone number entered by the user.
     * @param password    The password entered by the user.
     */
    @FXML
    public void handleSignIn(ActionEvent event, String phoneNumber, String password) {
        // Create a task to authenticate the user asynchronously.
        Task<User> signInTask = new Task<>() {
            @Override
            protected User call() throws Exception {
                // Retrieve all users from the database and check credentials.
                ArrayList<User> users = UserDatabase.getInstance().selectAll();
                for (User u : users) {
                    if (u.getPhonenumber().equals(phoneNumber) && u.getPassword().equals(password)) {
                        return u; // Return the user object if credentials match.
                    }
                }
                return null; // Return null if no matching user is found.
            }
        };

        signInTask.setOnSucceeded(e -> {
            // If sign-in is successful, navigate to the appropriate dashboard based on user type.
            User user = signInTask.getValue();
            if (user != null) {
                navigateToDashboard(event, user);
            } else {
                showErrorAlert("Invalid Credentials", "The phone number or password is incorrect.");
            }
        });

        signInTask.setOnFailed(e -> {
            // Handle any errors that occur during the sign-in process.
            Throwable ex = signInTask.getException();
            ex.printStackTrace();
            showErrorAlert("Sign-in Error", "An error occurred during the sign-in process.");
        });

        // Start the sign-in task on a separate thread.
        Thread signInThread = new Thread(signInTask);
        signInThread.setDaemon(true);
        signInThread.start();
    }

    /**
     * Show an error alert with a title and message.
     *
     * @param title   The title of the alert.
     * @param message The message to display in the alert.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Navigates to the user dashboard based on the user's access level.
     *
     * @param event The action event triggered by the sign-in button.
     * @param user  The authenticated user.
     */
    private void navigateToDashboard(ActionEvent event, User user) {
        try {
            // Determine the correct dashboard based on the user's access level.
            FXMLLoader loader;
            if ("Normal".equals(user.getAccessLevel())) {
                loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Normal.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Admin.fxml"));
            }

            // Load the dashboard and set the user object in the controller.
            Parent root = loader.load();
            UserController controller = loader.getController();
            controller.setUser(user);

            // Navigate to the new scene (dashboard).
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the sign-up process by creating a new user and inserting it into the database.
     * The process is executed asynchronously using a background thread.
     *
     * @param event       The action event triggered by the sign-up button.
     * @param name        The name entered by the user.
     * @param phoneNumber The phone number entered by the user.
     * @param password    The password entered by the user.
     * @param accessLevel The access level chosen by the user ("Admin" or "User").
     */
    @FXML
    public void handleSignUp(ActionEvent event, String name, String phoneNumber, String password, String accessLevel) {
        // Create a task to register the new user asynchronously.
        Task<Void> signUpTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Check if the user already exists in the database.
                ArrayList<User> users = UserDatabase.getInstance().selectAll();
                boolean userExists = users.stream()
                        .anyMatch(user -> user.getName().equalsIgnoreCase(name));

                if (userExists) {
                    showErrorAlert("User Exists", "A user with this name already exists.");
                    return null;
                }

                // If user doesn't exist, create a new user and insert it into the database.
                Platform.runLater(() -> {
                    try {
                        User user = createNewUser(name, phoneNumber, password, accessLevel);
                        UserDatabase.getInstance().insert(user);

                        // Load the appropriate dashboard based on the user's access level.
                        FXMLLoader fxmlLoader;
                        Scene scene;
                        fxmlLoader = new FXMLLoader(getClass().getResource(getDashboardFXML(accessLevel)));
                        scene = new Scene(fxmlLoader.load());

                        // Set the user in the controller and navigate to the dashboard.
                        UserController controller = fxmlLoader.getController();
                        controller.setUser(user);

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return null;
            }
        };

        // Start the sign-up task on a separate thread.
        Thread signUpThread = new Thread(signUpTask);
        signUpThread.setDaemon(true);
        signUpThread.start();
    }

    /**
     * Creates a new user based on the provided information and access level.
     *
     * @param name        The name entered by the user.
     * @param phoneNumber The phone number entered by the user.
     * @param password    The password entered by the user.
     * @param accessLevel The access level of the user ("Admin" or "User").
     * @return The created user object.
     */
    private User createNewUser(String name, String phoneNumber, String password, String accessLevel) {
        if ("Admin".equals(accessLevel)) {
            return new Admin(name, phoneNumber, password, accessLevel);
        } else {
            return new NormalUser(name, phoneNumber, password, accessLevel);
        }
    }

    /**
     * Returns the path to the appropriate dashboard FXML file based on the access level.
     *
     * @param accessLevel The access level of the user ("Admin" or "User").
     * @return The path to the FXML file for the corresponding dashboard.
     */
    private String getDashboardFXML(String accessLevel) {
        if ("Admin".equals(accessLevel)) {
            return "/LibraryManagement/FXML/Admin.fxml";
        } else {
            return "/LibraryManagement/FXML/Normal.fxml";
        }
    }
}
