package LibraryManagement;

import LibraryManagement.commandline.*;


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
                newuser();
                break;

        }
    }


    private static void login() {
        System.out.println("Enter phone number: ");
        String phonenumber = s.next();
        System.out.println("Enter password: ");
        String password = s.next();
        int n = database.login(phonenumber, password);
        if (n != -1) {
            User user = database.getUser(n);
            user.menu(database, user);
        } else {
            System.out.println("User dosen't exist!");
        }
    }

    private static void newuser() {
        System.out.println("Enter name: ");
        s.nextLine();
        String name = s.nextLine();
        if (database.userExists(name)) {
            System.out.println("User exist!");
            newuser();
        }
        System.out.println("Enter phone number: ");
        String phonenumber = s.next();
        System.out.println("Enter password: ");
        String password = s.next();
        System.out.println("1. Admin\n2. Normal User");
        int n2 = s.nextInt();
        User user;
        if (n2 == 1) {
            String accessLevel = "Admin";
            user = new Admin(name, phonenumber, password, accessLevel);
        } else {
            String accessLevel = "Normal";
            user = new NormalUser(name, phonenumber, password, accessLevel);
        }
        database.AddUser(user);

        user.menu(database, user);
    }
}
