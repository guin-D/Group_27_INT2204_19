package LibraryManagement.model;

import java.util.Scanner;

public class ViewOrders implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        System.out.println("\nEnter document name: ");
        Scanner s = new Scanner(System.in);
        String bookname = s.next();
        int i = database.getDocument(bookname);
        if (i > -1) {
            System.out.println("Document\t\tUser\t\tPrice\t\tQty");
            for (Order order : database.getAllOrder()) {
                if (order.getBook().getName().matches(bookname)) {
                    System.out.println(order.getBook().getName() + "\t\t" +
                            order.getUser().getName() + "\t\t" +
                            order.getPrice() + "\t\t" +
                            order.getQty());
                }
            }
            System.out.println();
        } else {
            System.out.println("Document doesn't exist!\n");
        }
        user.menu(database, user);
    }
}
