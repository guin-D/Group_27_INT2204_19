package LibraryManagement.commandline;

import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for deleting a document from the document database.
 * It checks if the document exists, and if it does, it deletes it.
 */
public class DeleteDocument implements IOOperation {

    // Constructor
    public DeleteDocument() {
    }

    /**
     * This method prompts the user to enter the title of a document and deletes the document
     * if it exists in the database.
     *
     * @param user The user performing the delete operation.
     */
    @Override
    public void oper(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter document title: ");
        String documentTitle = scanner.nextLine();

        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();

        int index = -1;
        for (Document document : documents) {
            if (document.getTitle().matches(documentTitle)) {
                index = 0;
                break;
            }
        }

        Document find = new Document();
        find.setTitle(documentTitle);

        // If the document was found, delete it from the database
        if (index == 0) {
            DocumentDatabase.getInstance().remove(find);
            System.out.println("Document deleted successfully!\n");
        } else {
            System.out.println("Document doesn't exist!\n");
        }

        user.menu(user);
    }
}
