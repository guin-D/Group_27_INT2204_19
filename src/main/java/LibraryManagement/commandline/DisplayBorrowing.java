package LibraryManagement.commandline;

import LibraryManagement.Database.BorrowingDatabase;

import java.util.ArrayList;

import static LibraryManagement.commandline.DisplayDocument.truncate;

public class DisplayBorrowing implements IOOperation {
    public void oper(User user) {
        ArrayList<Borrowing> borrowings = BorrowingDatabase.getInstance().selectAll();
        System.out.printf("%-30s %-30s %-10s %-40s %-10s\n",
                "Start Day", "Finish Day", "Day Left", "Document", "User");


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
