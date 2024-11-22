package LibraryManagement.commandline;

import LibraryManagement.Database.BorrowingDatabase;
import LibraryManagement.Database.DocumentDatabase;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Scanner;

public class ReturnDocument implements IOOperation {
    @Override
    public void oper(Database database, User user) {
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
                        + "You have to pay " + Math.abs(borrowing.getDaysLeft() * 50) + "VND as fine\n");
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

//        if (!database.getBrws().isEmpty()) {
//            for (Borrowing b : database.getBrws()) {
//                if (b.getDocument().getTitle().matches(documentName) && b.getUser().getName().matches(user.getName())) {
//                    Document document = b.getDocument();
//                    int i = database.getAllDocuments().indexOf(document);
//                    if (b.getDaysleft() < 0) {
//                        System.out.println("You are late!\n"
//                                + "You have to pay " + Math.abs(b.getDaysleft() * 50) + " as fine\n");
//                    }
//                    document.setBrwcopiers(document.getBrwcopiers() + 1);
//                    database.returnDocument(b, document, i);
//                    System.out.println("Document returned\nThank you!");
//                } else {
//                    System.out.println("You didn't borrow this document!\n");
//                }
//            }
//        } else {
//            System.out.println("You didn't borrow this document!\n");
//        }
        user.menu(database, user);
    }

}
