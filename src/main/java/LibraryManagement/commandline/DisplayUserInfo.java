package LibraryManagement.commandline;

public class DisplayUserInfo implements IOOperation {
    public void oper(User user) {
        System.out.println("User name: " + user.getName()
                + "\nPhone number: " + user.getPhonenumber() + "\nAccess level: "
                + user.accessLevel);
        System.out.println("\n");

        user.menu(user);
    }
}
