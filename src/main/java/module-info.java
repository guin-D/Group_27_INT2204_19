module org.example.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires java.desktop;

    exports LibraryManagement.commandline;
    opens LibraryManagement.commandline to javafx.fxml;

    exports LibraryManagement;
    opens LibraryManagement to javafx.fxml;

    exports LibraryManagement.controllers;
    opens LibraryManagement.controllers to javafx.fxml;


}