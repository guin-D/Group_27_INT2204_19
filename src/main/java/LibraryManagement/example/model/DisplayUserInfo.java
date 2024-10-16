package LibraryManagement.example.model;

public class DisplayUserInfo implements IOOperation {
    public void oper(Database database, User user) {
        System.out.println("User name: " + user.getName() + "\nEmail: " + user.getEmail() + "\nPhone number: " + user.getPhonenumber());
        System.out.println("\n");
        
        user.menu(database, user);
    }
}
