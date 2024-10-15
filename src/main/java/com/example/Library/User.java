package com.example.Library;

public abstract class User {
    protected String name;
    protected String email;
    protected String phonenumber;
    protected String password;
    protected IOOperation[] operations;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, String phonenumber, String password) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPassword() {
        return password;
    }

    abstract public void menu(Database database, User user);

    abstract public String toString();
}
