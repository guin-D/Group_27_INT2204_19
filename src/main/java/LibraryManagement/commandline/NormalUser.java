package LibraryManagement.commandline;

import java.util.Scanner;

public class NormalUser extends User {
    public NormalUser(String name) {
        super(name);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new Search(),
                new PlaceOrder(),
                new BorrowDocuments(),
                new CalculateFine(),
                new ReturnDocument(),
                new DisplayUserInfo(),
                new Exit(),
        };
    }

    public NormalUser(String name, String phoneNumber, String password, String accessLevel) {
        super(name, phoneNumber, password, accessLevel);
        this.operations = new IOOperation[]{
                new displayDocument(),
                new Search(),
                new PlaceOrder(),
                new BorrowDocuments(),
                new CalculateFine(),
                new ReturnDocument(),
                new DisplayUserInfo(),
                new Exit(),
        };
    }

    @Override
    public void menu(User user) {
        System.out.println("1. Display Documents");
        System.out.println("2. Search");
        System.out.println("3. Place Orders");
        System.out.println("4. Borrow Documents");
        System.out.println("5. Calculate Fine");
        System.out.println("6. Return Document");
        System.out.println("7. Display User Info");
        System.out.println("8. Exit");

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
