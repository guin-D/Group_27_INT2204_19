package LibraryManagement.example.model;

import java.util.Scanner;

public class DeleteDocument implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter document name: ");
        String bookname = s.next();

        int i = database.getDocument(bookname);
        if (i != -1) {
            database.deleteDocument(i);
            System.out.println("Document deleted successfully!\n");
        } else {
            System.out.println("Document dosen't exist!\n");
        }

        user.menu(database, user);
    }
}
