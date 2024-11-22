package LibraryManagement.commandline;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class BorrowDocuments implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the document title: ");
        String documentName = s.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        int i = -1;

        for (Document document : documents) {
            if (document.getTitle().matches(documentName)) {
                i = documents.indexOf(document);
            }
        }
        if(i > -1) {
            boolean j = true;
            for(Borrowing b: borrowings) {
                if(b.getDocumentTitle().matches(documentName)
                        && b.getUserName().matches(user.getName())){
                    j = false;
                    System.out.println("You have borrowed this document before!");
                    break;
                }
            }
            if(j) {
                Document document = documents.get(i);
                if(document.getBrwcopiers() > 1) {
                    Borrowing borrowing = new Borrowing(document, user);
                    document.setBrwcopiers(document.getBrwcopiers() - 1);
                    DocumentDatabase.getInstance().update(document);
                    borrowings.add(borrowing);
                    documents.set(i, document);
                    BorrowingDatabase.getInstance().insert(borrowing);
                    System.out.println("You must return the document before 14 days from now\n"
                            + "Expiry date: " + borrowing.getFinish() + "\nEnjoy!");
                } else {
                    System.out.println("This document isn't available for borrowing!");
                }
            }
        } else {
            System.out.println("We don't have this document");
        }

//        int i = database.getDocument(documentName);
//        if (i > -1) {
//            Document document = database.getDocument(i);
//
//            boolean n = true;
//            for (Borrowing b : database.getBrws()) {
//                if (b.getDocument().getTitle().matches(documentName) && b.getUser().getName().matches(user.getName())) {
//                    n = false;
//                    System.out.println("You have borrowed this document before!");
//                }
//            }
//
//            if (n) {
//                if (document.getBrwcopiers() > 1) {
//                    Borrowing borrowing = new Borrowing(document, user);
//                    document.setBrwcopiers(document.getBrwcopiers() - 1);
//                    database.borrowDocument(borrowing, document, i);
//                    System.out.println("You must return the document before 14 days from now\n"
//                            + "Expiry date: " + borrowing.getFinish() + "\nEnjoy!");
//                } else {
//                    System.out.println("This document isn't available for borrowing!");
//                }
//            }
//        } else {
//            System.out.println("Document doesn't exist!\n");
//        }
        user.menu(database, user);
    }

}
