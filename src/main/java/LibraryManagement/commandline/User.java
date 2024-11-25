package LibraryManagement.commandline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class User {
    protected String name;
    protected String phonenumber;
    protected String password;
    protected String accessLevel;
    protected IOOperation[] operations;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String phonenumber, String password, String accessLevel) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }


    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    abstract public void menu(User user);

    abstract public String toString();

}
