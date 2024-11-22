package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.Scanner;

public class AddDocument implements IOOperation {
    @Override
    public void oper(Database database, User user) {
        Scanner s = new Scanner(System.in);
        Document document = new Document();

        System.out.println("\nEnter document title: ");
        String title = s.nextLine();
        if (database.getDocument(title) > -1) {
            System.out.println("There is document with this name!\n");
            user.menu(database, user);
            return;
        } else {
            document.setTitle(title);

            System.out.println("Enter document author: ");
            document.setAuthor(s.nextLine());

            System.out.println("Enter document publisher: ");
            document.setPublisher(s.nextLine());

            System.out.println("Enter ISBN: ");
            document.setISBN(s.nextLine());

            System.out.println("Enter QTY: ");
            document.setQty(s.nextInt());

            System.out.println("Enter price: ");
            document.setPrice(s.nextDouble());

            System.out.println("Enter Borrowing copies: ");
            document.setBrwcopiers(s.nextInt());

            System.out.println("Enter description: ");
            document.setDescription(s.nextLine());
            DocumentDatabase.getInstance().insert(document);
//            database.AddDocument(document);
            System.out.println("Document added successfully\n");
            user.menu(database, user);
            s.close();
        }
    }
}
