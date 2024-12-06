package LibraryManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The LibraryApp class extends the JavaFX Application class and serves as the main entry point
 * for the Library Management system. It sets up the initial UI scene.
 */
public class LibraryApp extends Application {

    /**
     * The start method is overridden from the Application class.
     * It initializes the primary stage and loads the FXML file for the main UI.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LibraryManagement/FXML/Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Library Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is the entry point to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
