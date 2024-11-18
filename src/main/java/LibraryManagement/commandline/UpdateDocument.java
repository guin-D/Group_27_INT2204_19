package LibraryManagement.commandline;

import java.util.Scanner;

public class UpdateDocument implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the document title: ");
        s.nextLine();
        String documentname = s.nextLine();

        int i = database.getDocument(documentname);
        if (i > -1) {
            Document document = database.getDocument(i);
            System.out.println("\n" + database.getDocument(i).toString() + "\n");
            System.out.println("Author after update: ");
            document.setAuthor(s.nextLine());
            System.out.println("Publisher after update: ");
            document.setPublisher(s.nextLine());
            System.out.println("Genre after update: ");
            document.setISBN(s.nextLine());
            System.out.println("Qty after update: ");
            document.setQty(s.nextInt());
            System.out.println("Price after update: ");
            document.setPrice(s.nextDouble());
            System.out.println("Copies for borrowing after update: ");
            document.setBrwcopiers(s.nextInt());

            database.updateDocument(i, document);
        } else {
            System.out.println("Document doesn't exist!\n");
        }
        user.menu(database, user);
    }
}
