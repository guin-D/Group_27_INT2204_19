package LibraryManagement.commandline;

import LibraryManagement.Database.OrderDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewOrders implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        System.out.println("\nEnter document title: ");
        Scanner s = new Scanner(System.in);
        String documentName = s.nextLine();

        ArrayList<Order> orders = OrderDatabase.getInstance().selectAll();

        System.out.printf("%-50s %-40s %-30s %-20s\n",
                "Title", "User", "Price", "Quantity");

        int i = 0;
        for (Order o : orders) {
            if(o.getDocumentTitle().matches(documentName)) {
                i = 1;
                System.out.printf("%-50s %-40s %-30s %-20s\n",
                        o.getDocumentTitle(), o.getUserName(), o.getPrice(), o.getQty());
            }
        }
        if(i == 0) {
            System.out.println("Nobody order this document");
        }

        System.out.println("\n");
//        int i = database.getDocument(documentName);
//        if (i > -1) {
//            System.out.println("Document\t\tUser\t\tPrice\t\tQty");
//            for (Order order : database.getAllOrder()) {
//                if (order.getBook().getTitle().matches(documentName)) {
//                    System.out.println(order.getBook().getTitle() + "\t\t" +
//                            order.getUser().getName() + "\t\t" +
//                            order.getPrice() + "\t\t" +
//                            order.getQty());
//                }
//            }
//            System.out.println();
//        } else {
//            System.out.println("Document doesn't exist!\n");
//        }
        user.menu(database, user);
    }
}
