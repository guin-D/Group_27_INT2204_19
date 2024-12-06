package LibraryManagement.commandline;

import LibraryManagement.DAO.DocumentDatabase;
import LibraryManagement.DAO.OrderDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class PlaceOrder implements IOOperation {

    // Constructor
    public PlaceOrder() {
    }

    @Override
    public void oper(User user) {
        Scanner s = new Scanner(System.in);

        System.out.println("\nEnter document title: ");
        String documentName = s.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Ordering> orderings = OrderDatabase.getInstance().selectAll();

        // Find the document by title
        Document selectedDocument = null;
        for (Document document : documents) {
            if (document.getTitle().equals(documentName)) {
                selectedDocument = document;
                break;
            }
        }

        if (selectedDocument != null) {
            if (selectedDocument.getQty() > 0) {
                int qty = 0;
                boolean validQty = false;
                while (!validQty) {
                    System.out.println("Enter the quantity of the document you want to order:");
                    qty = s.nextInt();
                    if (qty <= 0) {
                        System.out.println("Quantity must be greater than 0.");
                    } else if ((selectedDocument.getQty() - qty) < 0) {
                        System.out.println("Not enough copies available! Available: " + selectedDocument.getQty());
                    } else {
                        validQty = true;
                    }
                }

                Ordering ordering = new Ordering(selectedDocument.getTitle(), user.getName(), selectedDocument.getPrice(), qty);
                selectedDocument.setQty(selectedDocument.getQty() - qty);

                DocumentDatabase.getInstance().update(selectedDocument);
                orderings.add(ordering);
                OrderDatabase.getInstance().insert(ordering);

                System.out.println("Order placed successfully!\n");
            } else {
                System.out.println("This document isn't available for order!\n");
            }
        } else {
            System.out.println("We don't have this document in our inventory.\n");
        }

        user.menu(user);
    }
}
