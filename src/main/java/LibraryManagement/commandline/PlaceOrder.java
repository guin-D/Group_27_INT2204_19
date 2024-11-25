package LibraryManagement.commandline;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.Database.OrderDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class PlaceOrder implements IOOperation {

    @Override
    public void oper(User user) {
        System.out.println("\nEnter document title: ");
        Scanner s = new Scanner(System.in);
        String documentName = s.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Order> orders = OrderDatabase.getInstance().selectAll();

        int i = -1;

        for (Document document : documents) {
            if (document.getTitle().matches(documentName)) {
                i = documents.indexOf(document);
            }
        }

        if(i > -1) {
            Document document = documents.get(i);
            if(document.getQty() > 1) {
                System.out.println("Enter the quantity of document you want to order:");
                int qty = s.nextInt();
                Order order = new Order(document.getTitle(), user.getName(), document.getPrice(), qty);
                document.setQty(document.getQty() - qty);
                DocumentDatabase.getInstance().update(document);
                orders.add(order);
                documents.set(i, document);
                OrderDatabase.getInstance().insert(order);
                System.out.println("Order placed successfully!\n");
            } else {
                System.out.println("This document isn't available for order!");
            }
        }

        user.menu(user);
    }
}
