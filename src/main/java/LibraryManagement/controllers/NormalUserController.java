package LibraryManagement.controllers;

import LibraryManagement.commandline.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class NormalUserController extends UserController {
    public void showBorrow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/borrow.fxml"));
            Parent borrowPane = loader.load();

            borrow borrowController = loader.getController();
            borrowController.setUser(this);

            main.getChildren().clear();
            main.getChildren().setAll(borrowPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMember(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/members.fxml"));
            Parent memberPane = loader.load();

            members memberController = loader.getController();
            memberController.setUser(this);

            main.getChildren().clear();
            main.getChildren().setAll(memberPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showResource(Database database, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/resources.fxml"));
            Parent resourcePane = loader.load();

            resources resourcesController = loader.getController();
            resourcesController.setUser(this);

            main.getChildren().clear();
            main.getChildren().setAll(resourcePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
