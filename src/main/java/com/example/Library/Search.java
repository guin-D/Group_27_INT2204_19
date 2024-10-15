package com.example.Library;

import java.util.Scanner;

public class Search implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        System.out.println("\nEnter document name: ");
        Scanner s = new Scanner(System.in);
        String name = s.next();

        int i = database.getDocument(name);
        if (i > -1) {
            System.out.println("\n" + database.getDocument(i).toString() + "\n");
        } else {
            System.out.println("Document dosen't exist!\n");
        }

        user.menu(database, user);
    }

}
