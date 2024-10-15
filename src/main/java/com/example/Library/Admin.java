package com.example.Library;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String name) {

        super(name);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new AddDocument(),
                new DeleteDocument(),
                new UpdateDocument(),
                new Search(),
                new DeleteAllData(),
                new ViewOrders(),
                new DisplayUserInfo(),
                new Exit()
        };
    }

    public Admin(String name, String email, String phonenumber, String password) {
        super(name, email, phonenumber, password);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new AddDocument(),
                new DeleteDocument(),
                new UpdateDocument(),
                new Search(),
                new DeleteAllData(),
                new ViewOrders(),
                new DisplayUserInfo(),
                new Exit()
        };
    }

    @Override
    public void menu(Database database, User user) {
        System.out.println("1. Display Documents");
        System.out.println("2. Add Document");
        System.out.println("3. Delete Document");
        System.out.println("4. Update Document");
        System.out.println("5. Search");
        System.out.println("6. Delete all data");
        System.out.println("7. View Orders");
        System.out.println("8. Display User Info");
        System.out.println("9. Exit");

        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        this.operations[n - 1].oper(database, user);
        s.close();
    }

    public String toString() {
        return name + "<N/>" + email + "<N/>" + phonenumber + "<N/>" + password + "<N/>" + "Admin";
    }
}
