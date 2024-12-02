package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class AddDocument implements IOOperation {
    @Override
    public void oper(User user) {
        Scanner s = new Scanner(System.in);
        System.out.println("\nEnter document title: ");
        String documentName = s.nextLine();
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        int i = -1;
        if (documents != null) {
            for (Document document : documents) {
                if (document.getTitle().matches(documentName)) {
                    i = documents.indexOf(document);
                    break;
                }
            }
        }
        if (i > -1) {
            System.out.println("There is document with this name!\n");
            user.menu(user);
        } else {
            Document document = new Document();
            document.setTitle(documentName);

            System.out.println("Enter document author: ");
            document.setAuthor(s.nextLine());

            System.out.println("Enter document publisher: ");
            document.setPublisher(s.nextLine());

            System.out.println("Enter ISBN: ");
            document.setIsbn(s.nextLine());

            System.out.println("Enter QTY: ");
            document.setQty(s.nextInt());

            System.out.println("Enter price: ");
            document.setPrice(s.nextDouble());

            System.out.println("Enter Borrowing copies: ");
            document.setBrwcopiers(s.nextInt());

            document.setImageLink("https://i.imgur.com/LprwO0E.png");
            DocumentDatabase.getInstance().insert(document);
            System.out.println("Document added successfully\n");
            user.menu(user);
            s.close();
        }
    }
}
