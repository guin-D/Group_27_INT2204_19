package com.example.Library;

import java.util.Scanner;

public class AddDocument implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);
        Document document = new Document();

        System.out.println("\nEnter document name: ");
        String name = s.next();
        if (database.getDocument(name) > -1) {
            System.out.println("There is document with this name!\n");
            user.menu(database, user);
            return;
        } else {
            document.setName(name);

            System.out.println("Enter document author: ");
            document.setAuthor(s.next());

            System.out.println("Enter document publisher: ");
            document.setPublisher(s.next());

            System.out.println("Enter document genre: ");
            document.setGenre(s.next());

            System.out.println("Enter QTY: ");
            document.setQty(s.nextInt());

            System.out.println("Enter price: ");
            document.setPrice(s.nextDouble());

            System.out.println("Enter Borrowing copies: ");
            document.setBrwcopiers(s.nextInt());

            database.AddDocument(document);
            System.out.println("Document added successfully\n");
            user.menu(database, user);
            s.close();
        }
    }
}
