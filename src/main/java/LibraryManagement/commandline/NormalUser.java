package LibraryManagement.commandline;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a normal user in the library system, with a predefined set of operations
 * that can be performed.
 */
public class NormalUser extends User {

    /**
     * Constructs a new NormalUser with specified details and operations.
     *
     * @param name        The name of the user.
     * @param phoneNumber The phone number of the user.
     * @param password    The password of the user.
     * @param accessLevel The access level of the user.
     */
    public NormalUser(String name, String phoneNumber, String password, String accessLevel) {
        super(name, phoneNumber, password, accessLevel);
        this.operations = new IOOperation[]{
                new DisplayDocument(),
                new Search(),
                new PlaceOrder(),
                new BorrowDocuments(),
                new CalculateFine(),
                new ReturnDocument(),
                new DisplayUserInfo(),
                new Exit(),
        };
    }

    /**
     * Displays the menu for the normal user and processes the user's input.
     *
     * @param user The current user who is interacting with the system.
     */
    @Override
    public void menu(User user) {
        System.out.println();
        System.out.println("1. Display Documents");
        System.out.println("2. Search");
        System.out.println("3. Place Orders");
        System.out.println("4. Borrow Documents");
        System.out.println("5. Calculate Fine");
        System.out.println("6. Return Document");
        System.out.println("7. Display User Info");
        System.out.println("8. Exit");

        try (Scanner s = new Scanner(System.in)) {
            int n = s.nextInt();

            if (n < 1 || n > operations.length) {
                System.out.println("Action is not supported.\n");
                menu(user);
            } else {
                this.operations[n - 1].oper(user);
            }
        } catch (InputMismatchException e) {
            System.out.println("Action is not supported.\n");
            menu(user);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            menu(user);
        }
    }
}
