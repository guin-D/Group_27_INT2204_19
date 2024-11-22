package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.Scanner;

public class DeleteDocument implements IOOperation {

    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter document title: ");
        String documentTitle = s.nextLine();

//        int i = database.getDocument(documentTitle);
        Document find = new Document();
        find.setTitle(documentTitle);

        try {
//            database.deleteDocument(i);
            DocumentDatabase.getInstance().remove(find);
            System.out.println("Document deleted successfully!\n");
        } catch(Exception e) {
            System.out.println("Document doesn't exist!\n");
        }

        user.menu(database, user);
    }
}
