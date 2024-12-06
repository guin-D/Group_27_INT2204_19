package LibraryManagement.commandline;

import LibraryManagement.DAO.BorrowingDatabase;

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
            if (b.getDocumentTitle().matches(documentName) && b.getUserName().matches(user.getName())) {
                if (b.getDaysLeft() < 0) {
                    System.out.println("You are late!\n"
                            + "You have to pay " + Math.abs(b.getDaysLeft() * 10000) + "VND as fine\n");
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
