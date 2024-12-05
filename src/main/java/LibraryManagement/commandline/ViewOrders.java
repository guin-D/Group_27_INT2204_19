package LibraryManagement.commandline;

import LibraryManagement.Database.OrderDatabase;

import java.util.ArrayList;
import java.util.Scanner;

import static LibraryManagement.commandline.DisplayDocument.truncate;

public class ViewOrders implements IOOperation {

    @Override
    public void oper(User user) {
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
                        truncate(o.getDocumentTitle(), 50),
                        o.getUserName(),
                        o.getPrice(),
                        o.getQty());
            }
        }
        if(i == 0) {
            System.out.println("Nobody order this document");
        }

        System.out.println();

        user.menu(user);
    }
}
