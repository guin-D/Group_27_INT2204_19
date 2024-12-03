module LibraryManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires java.desktop;
    requires jcommander;
    requires com.google.zxing;

    opens LibraryManagement.FXML to javafx.fxml;
    opens LibraryManagement to javafx.graphics;
    opens LibraryManagement.controllers to javafx.fxml;
    exports LibraryManagement.controllers to javafx.fxml;
}