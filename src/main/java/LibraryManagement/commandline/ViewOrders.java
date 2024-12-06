package LibraryManagement.commandline;

import LibraryManagement.DAO.OrderDatabase;

import java.util.ArrayList;
import java.util.Scanner;

import static LibraryManagement.commandline.DisplayDocument.truncate;

/**
 * The ViewOrders class implements the IOOperation interface and handles viewing orders for a specific document.
 * It retrieves all orders from the database and displays the relevant details to the user.
 */
public class ViewOrders implements IOOperation {

    // Constructor
    public ViewOrders() {
    }
    /**
     * Handles the operation for viewing orders of a specific document.
     * The user is prompted to enter the document title, and all orders for that document are displayed.
     *
     * @param user The user who initiated the operation.
     */
    @Override
    public void oper(User user) {
        System.out.println("\nEnter document title: ");
        Scanner s = new Scanner(System.in);
        String documentName = s.nextLine();

        ArrayList<Ordering> orderings = OrderDatabase.getInstance().selectAll();

        int i = 0;

        for (Ordering o : orderings) {
            if (o.getDocumentTitle().matches(documentName)) {
                i = 1;
                break;
            }
        }

        if (i == 0) {
            System.out.println("Nobody ordered this document");
        } else {
            System.out.printf("%-50s %-40s %-30s %-20s\n", "Title", "User", "Price", "Quantity");

            for (Ordering o : orderings) {
                if (o.getDocumentTitle().matches(documentName)) {
                    System.out.printf("%-50s %-40s %-30s %-20s\n",
                            truncate(o.getDocumentTitle(), 50),
                            o.getUserName(),
                            o.getPrice(),
                            o.getQty());
                }
            }
        }

        System.out.println();
        user.menu(user);
    }
}
