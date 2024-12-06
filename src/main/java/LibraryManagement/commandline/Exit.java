package LibraryManagement.commandline;

import LibraryManagement.LibraryCommandLine;

import java.util.Scanner;

/**
 * The Exit class handles the operation of quitting the application or returning to the main menu.
 * It asks the user for confirmation and takes appropriate action based on the user's response.
 */
public class Exit implements IOOperation {

    // Constructor
    public Exit() {
    }

    private Scanner scanner;
    private User user;

    /**
     * This method is invoked to perform the exit operation.
     * It asks the user if they want to quit and handles the response accordingly.
     *
     * @param user The current user performing the operation.
     */
    @Override
    public void oper(User user) {
        this.user = user;

        System.out.println("\nAre you sure that you want to quit?\n" +
                "1. Yes\n2. Main Menu");
        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("0. Exit!\n1. Login\n2. New User");
            scanner = new Scanner(System.in);
            int num = scanner.nextInt();

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
