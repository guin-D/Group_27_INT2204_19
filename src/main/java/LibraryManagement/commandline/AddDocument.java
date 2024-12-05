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
            int qty;
            do {
                qty = s.nextInt();
                if (qty < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (qty < 0);
            document.setQty(qty);

            System.out.println("Enter price: ");
            double price;
            do {
                price = s.nextInt();
                if (price < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (price < 0);
            document.setPrice(price);

            System.out.println("Enter Borrowing copies: ");
            int brwscopies;
            do {
                brwscopies = s.nextInt();
                if (brwscopies < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (brwscopies < 0);
            document.setBrwcopiers(brwscopies);

            document.setImageLink("https://i.imgur.com/LprwO0E.png");

            DocumentDatabase.getInstance().insert(document);
            System.out.println("Document added successfully\n");
            user.menu(user);
            s.close();
        }
    }
}