package LibraryManagement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class NormalUserController extends UserController {
    public void showBorrow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Borrow.fxml"));
            Parent borrowPane = loader.load();


            main.getChildren().clear();
            main.getChildren().setAll(borrowPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMember(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Members.fxml"));
            Parent memberPane = loader.load();


            main.getChildren().clear();
            main.getChildren().setAll(memberPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
