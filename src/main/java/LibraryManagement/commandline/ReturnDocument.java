package LibraryManagement.commandline;

import java.util.Scanner;

public class ReturnDocument implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        System.out.println("Enter document title: ");
        Scanner s = new Scanner(System.in);
        String bookname = s.nextLine();
        if (!database.getBrws().isEmpty()) {
            for (Borrowing b : database.getBrws()) {
                if (b.getDocument().getTitle().matches(bookname) && b.getUser().getName().matches(user.getName())) {
                    Document document = b.getDocument();
                    int i = database.getAllDocuments().indexOf(document);
                    if (b.getDaysleft() < 0) {
                        System.out.println("You are late!\n"
                                + "You have to pay " + Math.abs(b.getDaysleft() * 50) + " as fine\n");
                    }
                    document.setBrwcopiers(document.getBrwcopiers() + 1);
                    database.returnDocument(b, document, i);
                    System.out.println("Document returned\nThank you!");
                } else {
                    System.out.println("You didn't borrow this document!\n");
                }
            }
        } else {
            System.out.println("You didn't borrow this document!\n");
        }
        user.menu(database, user);
    }

}
