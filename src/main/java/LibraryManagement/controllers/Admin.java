package LibraryManagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Admin extends UserController {


    @FXML
    public void showBorrowHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/BorrowHistory.fxml"));
            Parent borrowPane = loader.load();

            main.getChildren().clear();
            main.getChildren().setAll(borrowPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showMember() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Members.fxml"));
            Parent memberPane = loader.load();

            main.getChildren().clear();
            main.getChildren().setAll(memberPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showResource() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Resources.fxml"));
            Parent resourcePane = loader.load();

            Resources resources = loader.getController();
            resources.setAdmin(this);

            main.getChildren().clear();
            main.getChildren().setAll(resourcePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addBookAPI() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/GoogleBookAPI.fxml"));
            Parent bookAPIPane = loader.load();

            GoogleBookAPI googleBookAPI = loader.getController();
            googleBookAPI.setAdmin(this);

            main.getChildren().clear();
            main.getChildren().setAll(bookAPIPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
