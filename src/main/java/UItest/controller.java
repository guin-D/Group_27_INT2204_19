package UItest;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;

public class controller {
    @FXML
    private Button btnSignIn; // Nút đăng nhập
    @FXML
    private Button btnSignUp; // Nút đăng ký

    @FXML
    public void handleSignIn() {
        // Logic cho nút đăng nhập
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign In");
        alert.setContentText("Sign In Button Clicked!");
        alert.showAndWait();
    }

    @FXML
    public void handleSignUp() {
        // Logic cho nút đăng ký
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up");
        alert.setContentText("Sign Up Button Clicked!");
        alert.showAndWait();
    }
}
