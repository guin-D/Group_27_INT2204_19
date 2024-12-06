package LibraryManagement.commandline;

import java.util.Scanner;

/**
 * This class represents the admin user, inheriting from the User class.
 * It allows the admin to perform various operations such as displaying documents,
 * adding/deleting/updating documents, searching, and viewing borrowing details.
 */
public class Admin extends User {

    /**
     * Constructor to initialize the Admin object with specific attributes.
     * It also sets the operations available to the Admin user.
     *
     * @param name The name of the admin.
     * @param phoneNumber The phone number of the admin.
     * @param password The password of the admin.
     * @param accessLevel The access level of the admin.
     */
    public Admin(String name, String phoneNumber, String password, String accessLevel) {
        super(name, phoneNumber, password, accessLevel);
        this.operations = new IOOperation[]{
                new DisplayDocument(),
                new AddDocument(),
                new DeleteDocument(),
                new UpdateDocument(),
                new Search(),
                new DisplayBorrowing(),
                new ViewOrders(),
                new DisplayUserInfo(),
                new Exit()
        };
    }

    /**
     * This method displays the admin menu and handles the user's choice.
     * It calls the corresponding operation based on the user's input.
     *
     * @param user The admin user performing the operation.
     */
    @Override
    public void menu(User user) {
        System.out.println();
        System.out.println("1. Display Documents");
        System.out.println("2. Add Document");
        System.out.println("3. Delete Document");
        System.out.println("4. Update Document");
        System.out.println("5. Search");
        System.out.println("6. Display Borrowing");
        System.out.println("7. View Orders");
        System.out.println("8. Display User Info");
        System.out.println("9. Exit");

        try {
            Scanner s = new Scanner(System.in);
            int n = s.nextInt();

            this.operations[n - 1].oper(user);
            s.close();
        } catch (Exception e) {
            System.out.println("Action is not supported\n");
            menu(user);
        }
    }
}