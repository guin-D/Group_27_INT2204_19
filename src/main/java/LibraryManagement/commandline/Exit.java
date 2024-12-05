package LibraryManagement.commandline;

import LibraryManagement.LibraryCommandLine;

import java.util.Scanner;

public class Exit implements IOOperation {
    Scanner s;
    User user;

    @Override
    public void oper(User user) {
        this.user = user;
        System.out.println("\nAre you sure that you want to quit?\n"
                + "1. Yes\n2. Main Menu");
        s = new Scanner(System.in);
        int i = s.nextInt();
        if (i == 1) {
            System.out.println("0. Exit!\n1. Login\n2. New User");
            s = new Scanner(System.in);
            int num = s.nextInt();
            switch (num) {
                case 1:
                    LibraryCommandLine.login();
                    break;

                case 2:
                    LibraryCommandLine.newUser();
                    break;

                default:
                    System.out.println("Thank you!");
            }
        } else {
            user.menu(user);
        }
    }
}
