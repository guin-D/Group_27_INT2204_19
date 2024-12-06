package LibraryManagement.commandline;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class BorrowDocuments implements IOOperation {

    @Override
    public void oper(User user) {
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
                            + "Expiry date: " + borrowing.getFinish() + "\nEnjoy!\n");
                } else {
                    System.out.println("This document isn't available for borrowing!\n");
                }
            }
        } else {
            System.out.println("We don't have this document\n");
        }
        user.menu(user);
    }

}
