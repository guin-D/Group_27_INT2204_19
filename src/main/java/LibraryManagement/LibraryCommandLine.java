package LibraryManagement;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.UserDatabase;
import LibraryManagement.commandline.*;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryCommandLine {
    static Scanner s;

    public static void main() {

        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        for(Borrowing b: borrowings) {
            b.setDaysLeft(b.getDaysLeft());
            BorrowingDatabase.getInstance().update(b);
        }

        System.out.println("Welcome to Library Management System!");

        int num;
        s = new Scanner(System.in);
        do {
            System.out.println("0. Exit!\n1. Login\n2. New User");
            num = s.nextInt();

            if (num < 0 || num > 2) {
                System.out.println("Action is not supported\n");
            }
        } while (num < 0 || num > 2);

        switch (num) {
            case 1:
                login();
                break;

            case 2:
                newUser();
                break;

            case 0:
                System.out.println("Thank you!");
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
        for (User u : users) {
            if (u.getPhoneNumber().matches(phoneNumber) && u.getPassword().matches(password)) {
                i = users.indexOf(u);
                break;
            }
        }
        if (i != -1) {
            User user = users.get(i);
            user.menu(user);
        } else {
            System.out.println("User doesn't exist!");
            main();
        }
    }

    public static void newUser() {
        System.out.println("Enter phone number: ");
        s.nextLine();
        String phoneNumber = s.nextLine();

        ArrayList<User> users = UserDatabase.getInstance().selectAll();

        boolean f = false;
        for (User user : users) {
            if (user.getPhoneNumber().toLowerCase().matches(phoneNumber.toLowerCase())) {
                f = true;
                break;
            }
        }

        if (f) {
            System.out.println("Phone number exist!");
            main();
        }
        System.out.println("Enter user name: ");
        String name = s.next();
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
