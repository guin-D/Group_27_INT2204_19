package LibraryManagement.commandline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class User {
    protected String name;
    protected String phoneNumber;
    protected String password;
    protected String accessLevel;
    protected IOOperation[] operations;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String phoneNumber, String password, String accessLevel) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    abstract public void menu(User user);
}
