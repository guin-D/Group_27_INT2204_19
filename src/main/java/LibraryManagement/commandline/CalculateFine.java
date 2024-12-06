package LibraryManagement.commandline;

import LibraryManagement.DAO.BorrowingDatabase;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for calculating the fine for a user if they have returned a document late.
 * It checks if the document is overdue and calculates the fine based on the number of late days.
 */
public class CalculateFine implements IOOperation {

    // Constructor
    public CalculateFine() {
    }

    /**
     * This method checks whether the user has borrowed the specified document,
     * and calculates the fine if the document is overdue.
     *
     * @param user The user who is requesting to calculate the fine.
     */
    @Override
    public void oper(User user) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter document title: ");
        String documentName = s.nextLine();

        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        boolean found = false;

        // Iterate through all borrowings to find the matching document and user
        for (Borrowing b : borrowings) {
            // Check if the document title and user name match the input
            if (b.getDocumentTitle().matches(documentName) && b.getUserName().matches(user.getName())) {
                // If the document is overdue (daysLeft < 0), calculate the fine
                if (b.getDaysLeft() < 0) {
                    System.out.println("You are late!\n"
                            + "You have to pay " + Math.abs(b.getDaysLeft() * 10000) + "VND as fine\n");
                } else {
                    System.out.println("You don't have to pay fine\n");
                }
                found = true;
                break;
            }
        }

        // If the borrowing was not found, notify the user
        if (!found) {
            System.out.println("You didn't borrow this document!");
        }

        user.menu(user);
    }
}
