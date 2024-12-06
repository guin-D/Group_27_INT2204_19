package LibraryManagement.commandline;

import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for adding a new document to the library.
 * It collects necessary details from the user and inserts the document into the database.
 */
public class AddDocument implements IOOperation {

    /**
     * Default constructor for AddDocument.
     */
    public AddDocument() {
    }

    /**
     * This method handles the process of adding a new document to the library.
     * It prompts the user to input various details for the new document and stores it in the database.
     *
     * @param user The user who is adding the document.
     */
    @Override
    public void oper(User user) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the document title.
        System.out.println("\nEnter document title: ");
        String documentName = scanner.nextLine();

        // Retrieve all documents from the database.
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
        int documentIndex = -1;

        // Check if the document already exists in the database.
        if (documents != null) {
            for (Document document : documents) {
                if (document.getTitle().matches(documentName)) {
                    documentIndex = documents.indexOf(document);
                    break;
                }
            }
        }

        // If the document already exists, inform the user and return to the menu.
        if (documentIndex > -1) {
            System.out.println("There is a document with this name!\n");
            user.menu(user);
        } else {
            Document document = new Document();
            document.setTitle(documentName);

            System.out.println("Enter document author: ");
            document.setAuthor(scanner.nextLine());

            System.out.println("Enter document publisher: ");
            document.setPublisher(scanner.nextLine());

            System.out.println("Enter ISBN: ");
            document.setIsbn(scanner.nextLine());

            System.out.println("Enter QTY: ");
            int qty;
            do {
                qty = scanner.nextInt();
                if (qty < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (qty < 0);
            document.setQty(qty);

            System.out.println("Enter price: ");
            double price;
            do {
                price = scanner.nextDouble();
                if (price < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (price < 0);
            document.setPrice(price);

            System.out.println("Enter Borrowing copies: ");
            int borrowingCopies;
            do {
                borrowingCopies = scanner.nextInt();
                if (borrowingCopies < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (borrowingCopies < 0);
            document.setBrwcopiers(borrowingCopies);

            document.setImageLink("https://i.imgur.com/LprwO0E.png");

            DocumentDatabase.getInstance().insert(document);
            System.out.println("Document added successfully\n");

            user.menu(user);
        }

        scanner.close();
    }
}
