package LibraryManagement.controllers;

import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.QR.QRCodeGenerator;
import LibraryManagement.commandline.Document;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class DocumentView {
    @FXML
    private ImageView documentImg;

    @FXML
    private TextField title;

    @FXML
    private TextField isbn;

    @FXML
    private TextField author;

    @FXML
    private TextField publisher;

    @FXML
    private TextField totalQuantity;

    @FXML
    private TextField totalOrder;

    @FXML
    private TextField price;


    @FXML
    private Button cancel;

    @FXML
    private Button createQr;

    private Document currentDocument;

    /**
     * Set the data to the fields for the document being updated.
     *
     * @param document The document object containing the data to be displayed.
     */
    public void setData(Document document) {
        this.currentDocument = document;
        if (this.currentDocument != null) {
            if (document.getImageLink() != null && !document.getImageLink().isEmpty()) {
                Image image = new Image(document.getImageLink(), true);
                documentImg.setImage(image);
            }
            title.setText(document.getTitle());
            isbn.setText(document.getIsbn());
            author.setText(document.getAuthor());
            publisher.setText(document.getPublisher());
            totalQuantity.setText(String.valueOf(document.getBrwcopiers()));
            totalOrder.setText(String.valueOf(document.getQty()));
            price.setText(String.valueOf(document.getPrice()));
        }
    }

    /**
     * Initialize the controller and set up event handlers for buttons.
     */
    @FXML
    private void initialize() {
        cancel.setOnAction(event -> handleCancel());

        createQr.setOnAction(event -> handleQR());


    }


    /**
     * Handle the cancel button click event. Close the window without saving.
     */
    private void handleCancel() {
        closeWindow();
    }

    /**
     * Show a success alert indicating the document has been successfully updated.
     */
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Successful");
        alert.setHeaderText("Document updated successfully!");
        alert.setContentText("The document information has been updated in the database.");
        alert.showAndWait();
    }

    /**
     * Close the current window.
     */
    private void closeWindow() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Create QR code for document.
     */
    private void handleQR() {
        String qrCodeImagePath = "src/main/resources/LibraryManagement/QRImage/" + currentDocument.getTitle() + ".png";

        File qrCodeImageFile = new File(qrCodeImagePath);
        if (qrCodeImageFile.exists()) {
            qrCodeImageFile.delete();
            qrCodeImageFile = new File(qrCodeImagePath);
        }

        currentDocument.setTitle(title.getText());
        currentDocument.setIsbn(isbn.getText());
        currentDocument.setAuthor(author.getText());
        currentDocument.setPublisher(publisher.getText());
        currentDocument.setQty(Integer.parseInt(totalOrder.getText()));
        currentDocument.setPrice(Double.parseDouble(price.getText()));
        currentDocument.setBrwcopiers(Integer.parseInt(totalQuantity.getText()));

        QRCodeGenerator.generateQRCode(currentDocument);

        String qrCodeUri = qrCodeImageFile.toURI().toString();
        Image qrCodeImage = new Image(qrCodeUri);
        ImageView imageView = new ImageView(qrCodeImage);

        Stage stage = new Stage();
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }
}
