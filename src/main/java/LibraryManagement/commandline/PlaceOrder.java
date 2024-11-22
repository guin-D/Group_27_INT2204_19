package LibraryManagement.commandline;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;
import LibraryManagement.Database.OrderDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class PlaceOrder implements IOOperation {

    @Override
    public void oper(Database database, User user) {
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

//        int i = database.getDocument(bookName);
//        if (i <= -1) {
//            System.out.println("Document doesn't exist!");
//        } else {
//            Document document = database.getDocument(i);
//            order.setBook(database.getDocument(i));
//            order.setUser(user);
//            System.out.println("Enter qty: ");
//            int qty = s.nextInt();
//            order.setQty(qty);
//            order.setPrice(document.getPrice() * qty);
//            int bookindex = database.getDocument(document.getTitle());
//            document.setQty(document.getQty() - 1);
//            database.addOrder(order, document, bookindex);
//            System.out.println("Order placed successfully!\n");
//        }
        user.menu(database, user);
    }
}
