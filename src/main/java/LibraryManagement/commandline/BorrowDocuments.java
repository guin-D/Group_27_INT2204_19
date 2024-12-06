package LibraryManagement.commandline;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for handling the borrowing process of documents.
 */
public class BorrowDocuments implements IOOperation {

    /**
     * This method allows a user to borrow a document by providing the document title.
     * It checks if the document is available and if the user has borrowed it before.
     *
     * @param user The user attempting to borrow the document.
     */
    @Override
    public void oper(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the document title: ");
        String documentName = scanner.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        int documentIndex = -1;

        for (Document document : documents) {
            if (document.getTitle().matches(documentName)) {
                documentIndex = documents.indexOf(document);
            }
        }

        if (documentIndex > -1) {
            boolean isBorrowable = true;

            // Check if the user has borrowed this document before.
            for (Borrowing borrowing : borrowings) {
                if (borrowing.getDocumentTitle().matches(documentName)
                        && borrowing.getUserName().matches(user.getName())) {
                    isBorrowable = false;
                    System.out.println("You have borrowed this document before!");
                    break;
                }
            }

            // If the document is borrowable, check the availability and proceed with the borrowing.
            if (isBorrowable) {
                Document document = documents.get(documentIndex);

                // Check if the document has available copies to borrow.
                if (document.getBrwcopiers() > 1) {
                    Borrowing borrowing = new Borrowing(document, user);
                    document.setBrwcopiers(document.getBrwcopiers() - 1);

                    DocumentDatabase.getInstance().update(document);
                    borrowings.add(borrowing);
                    documents.set(documentIndex, document);
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
