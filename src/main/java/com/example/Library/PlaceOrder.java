package com.example.Library;

import java.util.Scanner;

public class PlaceOrder implements IOOperation {

    @Override
    public void oper(Database database, User user) {

        Order order = new Order();
        System.out.println("\nEnter document name: ");
        Scanner s = new Scanner(System.in);
        String bookname = s.next();
        int i = database.getDocument(bookname);
        if (i <= -1) {
            System.out.println("Document doesn't exist!");
        } else {
            Document document = database.getDocument(i);
            order.setBook(database.getDocument(i));
            order.setUser(user);
            System.out.println("Enter qty: ");
            int qty = s.nextInt();
            order.setQty(qty);
            order.setPrice(document.getPrice() * qty);
            int bookindex = database.getDocument(document.getName());
            document.setQty(document.getQty() - 1);
            database.addOrder(order, document, bookindex);
            System.out.println("Order placed successfully!\n");
        }
        user.menu(database, user);
    }
}
