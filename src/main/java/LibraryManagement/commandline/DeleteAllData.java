package LibraryManagement.commandline;

import java.util.Scanner;

public class DeleteAllData implements IOOperation {

    @Override
    public void oper(User user) {
        System.out.println("\n" +
                "Are you sure that you want to delete all data?\n"
                + "1. Continue\n2. Main Menu");
        Scanner s = new Scanner(System.in);
        int i = s.nextInt();
        if (i == 1) {
            //delete all data, sua sau
        } else {
            user.menu(user);
        }


    }
}
