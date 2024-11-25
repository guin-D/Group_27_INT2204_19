package LibraryManagement;

import LibraryManagement.Database.UserDatabase;
import LibraryManagement.commandline.*;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryCommandLine {
    static Scanner s;
    static Database database;

    public static void main(String[] args) {

        database = new Database();
        System.out.println("Welcome to Library Management System!");

        int num;
        System.out.println("0. Exit!\n1. Login\n2. New User");
        s = new Scanner(System.in);
        num = s.nextInt();
        switch (num) {
            case 1:
                login();
                break;

            case 2:
                newUser();
                break;

        }
    }


    public static void login() {
        System.out.println("Enter phone number: ");
        String phoneNumber = s.next();
        System.out.println("Enter password: ");
        String password = s.next();

        ArrayList<User> users = UserDatabase.getInstance().selectAll();
        int i = -1;
        for(User u: users) {
            if(u.getPhonenumber().matches(phoneNumber) && u.getPassword().matches(password)) {
                i = users.indexOf(u);
                break;
            }
        }
        if(i != -1) {
            User user = users.get(i);
            user.menu(user);
        } else {
            System.out.println("User doesn't exist!");
        }


//        int n = database.login(phoneNumber, password);
//        System.out.println(n);
//        if (n != -1) {
//            User user = database.getUser(n);
//            user.menu(database, user);
//        } else {
//            System.out.println("User doesn't exist!");
//        }
    }

    public static void newUser() {
        System.out.println("Enter name: ");
        s.nextLine();
        String name = s.nextLine();

        ArrayList<User> users = UserDatabase.getInstance().selectAll();

        boolean f = false;
        for (User user : users) {
            if (user.getName().toLowerCase().matches(name.toLowerCase())) {
                f = true;
                break;
            }
        }

        if (f) {
            System.out.println("User exist!");
            newUser();
        }
        System.out.println("Enter phone number: ");
        String phoneNumber = s.next();
        System.out.println("Enter password: ");
        String password = s.next();
        System.out.println("1. Admin\n2. Normal User");
        int n2 = s.nextInt();
        User user;
        if (n2 == 1) {
            String accessLevel = "Admin";
            user = new Admin(name, phoneNumber, password, accessLevel);
        } else {
            String accessLevel = "Normal";
            user = new NormalUser(name, phoneNumber, password, accessLevel);
        }
        UserDatabase.getInstance().insert(user);
        System.out.println("Create new user successful");
        user.menu(user);
    }
}
