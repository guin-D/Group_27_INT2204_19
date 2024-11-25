package LibraryManagement.commandline;

import LibraryManagement.Database.BorrowingDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class CalculateFine implements IOOperation {
    @Override
    public void oper(User user) {
        System.out.println("Enter document title: ");
        Scanner s = new Scanner(System.in);
        String documentName = s.nextLine();

        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        boolean g = true;

        for (Borrowing b : borrowings) {
            if (b.getDocument().getTitle().matches(documentName) && b.getUser().getName().matches(user.getName())) {
                if (b.getDaysLeft() > 0) {
                    System.out.println("You are late!\n"
                            + " You have to pay " + b.getDaysLeft() * 50 + " as fine");
                } else {
                    System.out.println("You don't have to pay fine\n");
                }
                g = false;
                break;
            }
        }
        if (g) {
            System.out.println("You didn't borrow this document!");
        }
        user.menu(user);
    }

}
