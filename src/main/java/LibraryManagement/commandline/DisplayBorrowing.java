package LibraryManagement.commandline;

import LibraryManagement.DAO.BorrowingDatabase;

import java.util.ArrayList;

import static LibraryManagement.commandline.DisplayDocument.truncate;

/**
 * This class is responsible for displaying the borrowing records of all users.
 * It prints the borrowing information, including the start date, finish date, days left,
 * document title, and user who borrowed the document.
 */
public class DisplayBorrowing implements IOOperation {

    // Constructor
    public DisplayBorrowing() {
    }

    /**
     * This method retrieves the borrowing records from the database and displays them
     * in a tabular format with relevant information about each borrowing.
     *
     * @param user The user who is requesting to display the borrowing records.
     */
    @Override
    public void oper(User user) {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();

        System.out.printf("%-30s %-30s %-10s %-40s %-10s\n",
                "Start Day", "Finish Day", "Day Left", "Document", "User");

        // Iterate through each borrowing and print its details
        for (Borrowing b : borrowings) {
            System.out.printf("%-30s %-30s %-10s %-40s %-20s\n",
                    b.getStart(),
                    b.getFinish(),
                    b.getDaysLeft(),
                    truncate(b.getDocumentTitle(), 40),
                    truncate(b.getUserName(), 20)
            );
        }
        System.out.println("\n");

        user.menu(user);
    }
}
