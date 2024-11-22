package LibraryManagement.commandline;

import java.util.Scanner;

public class CalculateFine implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        System.out.println("Enter document title: ");
        Scanner s = new Scanner(System.in);
        String documentname = s.nextLine();

        boolean g = true;

        for (Borrowing b : database.getBrws()) {
            if (b.getDocument().getTitle().matches(documentname) && b.getUser().getName().matches(user.getName())) {
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
        user.menu(database, user);
    }

}
