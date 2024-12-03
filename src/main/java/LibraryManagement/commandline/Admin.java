package LibraryManagement.commandline;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String name) {

        super(name);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new AddDocument(),
                new DeleteDocument(),
                new UpdateDocument(),
                new Search(),
                new DeleteAllData(),
                new ViewOrders(),
                new DisplayUserInfo(),
                new Exit()
        };
    }

    public Admin(String name, String phoneNumber, String password, String accessLevel) {
        super(name, phoneNumber, password, accessLevel);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new AddDocument(),
                new DeleteDocument(),
                new UpdateDocument(),
                new Search(),
                new DeleteAllData(),
                new ViewOrders(),
                new DisplayUserInfo(),
                new Exit()
        };
    }

    @Override
    public void menu(User user) {
        System.out.println("1. Display Documents");
        System.out.println("2. Add Document");
        System.out.println("3. Delete Document");
        System.out.println("4. Update Document");
        System.out.println("5. Search");
        System.out.println("6. Delete all data");
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
