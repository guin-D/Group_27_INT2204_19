package LibraryManagement.commandline;

/**
 * This class is responsible for displaying information about the user.
 * It shows the user's name, phone number, and access level.
 */
public class DisplayUserInfo implements IOOperation {

    // Constructor
    public DisplayUserInfo() {
    }

    /**
     * This method displays the user's information, including the user's name, phone number, and access level.
     *
     * @param user The user whose information is to be displayed.
     */
    @Override
    public void oper(User user) {

        System.out.println("User name: " + user.getName()
                + "\nPhone number: " + user.getPhoneNumber()
                + "\nAccess level: " + user.accessLevel);
        System.out.println("\n");

        user.menu(user);
    }
}
