package LibraryManagement.commandline;

import LibraryManagement.DAO.DocumentDatabase;
import java.util.Scanner;

/**
 * The UpdateDocument class implements the IOOperation interface to allow document updates in the library system.
 */
public class UpdateDocument implements IOOperation {

    // Constructor
    public UpdateDocument() {
    }

    /**
     * Prompts the user to input a document's title and then updates its information.
     *
     * @param user The user who performs the update operation.
     */
    @Override
    public void oper(User user) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the document title: ");
        String documentTitle = s.nextLine();

        Document find = new Document();
        find.setTitle(documentTitle);

        Document document = DocumentDatabase.getInstance().selectBy(find);

        try {
            System.out.println("Document found!\n"
                    + "Title: " + document.getTitle() + "\n"
                    + "Author: " + document.getAuthor() + "\n"
                    + "Publisher: " + document.getPublisher() + "\n"
                    + "ISBN: " + document.getIsbn() + "\n"
                    + "Quantity: " + document.getQty() + "\n"
                    + "Price: " + document.getPrice() + "\n"
                    + "Borrow copies: " + document.getBrwcopiers() + "\n");

            System.out.println("Author after update: ");
            document.setAuthor(s.nextLine());

            System.out.println("Publisher after update: ");
            document.setPublisher(s.nextLine());

            System.out.println("ISBN after update: ");
            document.setIsbn(s.nextLine());

            System.out.println("Qty after update: ");
            int qty;
            do {
                qty = s.nextInt();
                if (qty < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (qty < 0);
            document.setQty(qty);

            System.out.println("Price after update: ");
            double price;
            do {
                price = s.nextDouble();
                if (price < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (price < 0);
            document.setPrice(price);

            System.out.println("Copies for borrowing after update: ");
            int brwscopies;
            do {
                brwscopies = s.nextInt();
                if (brwscopies < 0) {
                    System.out.println("The value must be >= 0");
                }
            } while (brwscopies < 0);
            document.setBrwcopiers(brwscopies);

            System.out.println("Update completed!\n");

            DocumentDatabase.getInstance().update(document);
        } catch (Exception e) {
            System.out.println("Document doesn't exist!\n");
        }

        user.menu(user);
    }
}
