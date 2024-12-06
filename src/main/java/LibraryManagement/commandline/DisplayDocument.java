package LibraryManagement.commandline;

import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;

/**
 * This class is responsible for displaying the list of documents in the system.
 * It retrieves all documents from the database and prints their information in a tabular format.
 */
public class DisplayDocument implements IOOperation {

    // Constructor
    public DisplayDocument() {
    }

    /**
     * This method truncates a string to a specified maximum length, appending "..." if the string exceeds the limit.
     *
     * @param str The string to be truncated.
     * @param maxLength The maximum allowed length of the string.
     * @return The truncated string, or the original string if it's shorter than the maximum length.
     */
    public static String truncate(String str, int maxLength) {
        if (str.length() > maxLength) {
            // Truncate the string and append "..." if it exceeds the maximum length
            return str.substring(0, maxLength - 3) + "...";
        }
        return str;
    }

    /**
     * This method retrieves all documents from the database and displays them in a formatted table.
     * It shows information about each document, including its title, author, publisher, ISBN, quantity, price, and borrowing copies.
     *
     * @param user The user who is requesting to display the documents.
     */
    @Override
    public void oper(User user) {
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

        System.out.printf("%-40s %-30s %-40s %-20s %-10s %-10s %-20s\n",
                "Title", "Author", "Publisher", "ISBN", "Qty", "Price", "Brw cps");

        // Iterate through the documents and print their details
        for (Document b : documents) {
            System.out.printf("%-40s %-30s %-40s %-20s %-10d %-10.2f %-20d\n",
                    truncate(b.getTitle(), 40),
                    truncate(b.getAuthor(), 30),
                    truncate(b.getPublisher(), 40),
                    b.getIsbn(),
                    b.getQty(),
                    b.getPrice(),
                    b.getBrwcopiers()
            );
        }
        System.out.println("\n");

        user.menu(user);
    }
}
