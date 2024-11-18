package LibraryManagement.commandline;

import java.util.Scanner;

public class BorrowDocuments implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the document title: ");
        String documentname = s.nextLine();

        int i = database.getDocument(documentname);
        if (i > -1) {
            Document document = database.getDocument(i);

            boolean n = true;
            for (Borrowing b : database.getBrws()) {
                if (b.getDocument().getTitle().matches(documentname) && b.getUser().getName().matches(user.getName())) {
                    n = false;
                    System.out.println("You have borrowed this document before!");
                }
            }

            if (n) {
                if (document.getBrwcopiers() > 1) {
                    Borrowing borrowing = new Borrowing(document, user);
                    document.setBrwcopiers(document.getBrwcopiers() - 1);
                    database.borrowDocument(borrowing, document, i);
                    System.out.println("You must return the document before 14 days from now\n"
                            + "Expiry date: " + borrowing.getFinish() + "\nEnjoy!");
                } else {
                    System.out.println("This document isn't available for borrowing!");
                }
            }
        } else {
            System.out.println("Document doesn't exist!\n");
        }
        user.menu(database, user);
    }

}
