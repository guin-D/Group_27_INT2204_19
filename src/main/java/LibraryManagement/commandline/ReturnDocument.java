package LibraryManagement.commandline;

import LibraryManagement.DAO.BorrowingDatabase;
import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class ReturnDocument implements IOOperation {
    @Override
    public void oper(User user) {
        System.out.println("Enter document title: ");
        Scanner s = new Scanner(System.in);
        String documentName = s.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        int i = -1;
        int j = -1;
        for(Borrowing b: borrowings) {
            if(b.getDocumentTitle().matches(documentName) && b.getUserName().matches(user.getName())){
                for (Document document : documents) {
                    if (document.getTitle().matches(documentName)) {
                        i = documents.indexOf(document);
                        break;
                    }
                }
                j = borrowings.indexOf(b);
            }
        }
        try {
            Borrowing borrowing = borrowings.get(j);
            Document document = documents.get(i);
            if (borrowing.getDaysLeft() < 0) {
                System.out.println("You are late!\n"
                        + "You have to pay " + Math.abs(borrowing.getDaysLeft() * 10000) + "VND as fine\n");
            }
            document.setBrwcopiers(document.getBrwcopiers() + 1);
            DocumentDatabase.getInstance().update(document);
            borrowings.remove(borrowing);
            documents.set(i, document);
            BorrowingDatabase.getInstance().delete(borrowing);
            System.out.println("Document returned\nThank you!");
        } catch (Exception e) {
            System.out.println("You didn't borrow this document!\n");
        }

        user.menu(user);
    }

}
