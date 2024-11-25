package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.Scanner;

public class Search implements IOOperation {

    @Override
    public void oper(User user) {
        System.out.println("\nEnter document title: ");
        Scanner s = new Scanner(System.in);
        String documentTitle = s.nextLine();

        Document find = new Document();
        find.setTitle(documentTitle);
        Document document = DocumentDatabase.getInstance().selectBy(find);
        try {
            System.out.println("\n" + document.toString() + "\n");
        } catch(Exception e) {
            System.out.println("Document doesn't exist!\n");
        }

        user.menu(user);
    }

}
