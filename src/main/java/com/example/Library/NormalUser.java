package com.example.Library;

import java.util.Scanner;

public class NormalUser extends User {
    public NormalUser(String name) {
        super(name);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new Search(),
                new PlaceOrder(),
                new BorrowDocuments(),
                new CalculateFine(),
                new ReturnDocument(),
                new displayDocument(),
                new Exit(),
        };
    }

    public NormalUser(String name, String email, String phonenumber, String password) {
        super(name, email, phonenumber, password);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new Search(),
                new PlaceOrder(),
                new BorrowDocuments(),
                new CalculateFine(),
                new ReturnDocument(),
                new DisplayUserInfo(),
                new Exit(),
        };
    }

    @Override
    public void menu(Database database, User user) {
        System.out.println("1. Display Documents");
        System.out.println("2. Search");
        System.out.println("3. Place Orders");
        System.out.println("4. Borrow Documents");
        System.out.println("5. Calculate Fine");
        System.out.println("6. Return Document");
        System.out.println("7. Display User Info");
        System.out.println("8. Exit");

        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        this.operations[n - 1].oper(database, user);
        s.close();
    }

    public String toString() {
        return name + "<N/>" + email + "<N/>" + phonenumber + "<N/>" + password + "<N/>" + "Normal";
    }
}
