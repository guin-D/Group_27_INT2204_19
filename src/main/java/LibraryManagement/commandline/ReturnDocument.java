package LibraryManagement.commandline;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class ReturnDocument implements IOOperation {

    // Constructor
    public ReturnDocument() {

    }

    @Override
    public void oper(User user) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter document title: ");
        String documentName = s.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        Document document = null;
        Borrowing borrowing = null;

        for (Borrowing b : borrowings) {
            if (b.getDocumentTitle().equals(documentName) && b.getUserName().equals(user.getName())) {
                borrowing = b;
                break;
            }
        }

        if (borrowing != null) {
            for (Document d : documents) {
                if (d.getTitle().equals(documentName)) {
                    document = d;
                    break;
                }
            }
        }

        if (borrowing != null && document != null) {
            if (borrowing.getDaysLeft() < 0) {
                System.out.println("You are late!\n"
                        + "You have to pay " + Math.abs(borrowing.getDaysLeft() * 10000) + "VND as fine\n");
            }

            document.setBrwcopiers(document.getBrwcopiers() + 1);
            DocumentDatabase.getInstance().update(document);

            borrowings.remove(borrowing);
            BorrowingDatabase.getInstance().delete(borrowing);

            System.out.println("Document returned successfully. Thank you!");
        } else {
            System.out.println("You haven't borrowed this document, or the document does not exist!");
        }

        user.menu(user);
    }
}
