package LibraryManagement.controllers;

import LibraryManagement.Database.UserDatabase;
import LibraryManagement.commandline.*;
import LibraryManagement.commandline.Admin;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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

    @FXML
    public void handleSignIn(ActionEvent event, String phoneNumber, String password) {
        Task<User> signInTask = new Task<>() {
            @Override
            protected User call() throws Exception {
                ArrayList<User> users = UserDatabase.getInstance().selectAll();

                for (User u : users) {
                    if (u.getPhonenumber().equals(phoneNumber) && u.getPassword().equals(password)) {
                        return u;
                    }
                }
                return null;
            }
        };

        signInTask.setOnSucceeded(e -> {
            User user = signInTask.getValue();
            if (user != null) {
                try {
                    FXMLLoader loader;
                    if ("Normal".equals(user.getAccessLevel())) {
                        loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Normal.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Admin.fxml"));
                    }

                    Scene newScene = new Scene(loader.load());

                    UserController controller = loader.getController();
                    controller.setUser(user);

                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(newScene);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Invalid credentials!");
            }
        });

        signInTask.setOnFailed(e -> {
            Throwable ex = signInTask.getException();
            ex.printStackTrace();
            System.out.println("Error occurred during sign-in process.");
        });

        Thread thread = new Thread(signInTask);
        thread.setDaemon(true);
        thread.start();
    }


    @FXML
    public void handleSignUp(ActionEvent event, String name, String phoneNumber, String password, String accessLevel) {
        Task<Void> signUpTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                if (database.userExists(name)) {
                    System.out.println("User already exists!");
                    return null;
                }

                Platform.runLater(() -> {
                    try {
                        FXMLLoader fxmlLoader;
                        Scene scene;
                        User user;
                        if (accessLevel.equals("Admin")) {
                            user = new Admin(name, phoneNumber, password, accessLevel);
                            fxmlLoader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Admin.fxml"));
                            scene = new Scene(fxmlLoader.load());
                        } else if (accessLevel.equals("User")) {
                            user = new NormalUser(name, phoneNumber, password, accessLevel);
                            fxmlLoader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Normal.fxml"));
                            scene = new Scene(fxmlLoader.load());
                        } else {
                            throw new IllegalArgumentException("Unknown access level: " + accessLevel);
                        }
                        UserDatabase.getInstance().insert(user);
                        UserController controller = fxmlLoader.getController();
                        controller.setUser(user);

                        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return null;
            }
        };

        Thread signUpThread = new Thread(signUpTask);
        signUpThread.setDaemon(true);
        signUpThread.start();
    }

}
