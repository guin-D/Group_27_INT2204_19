package LibraryManagement.commandline;

import LibraryManagement.DAO.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

import static LibraryManagement.commandline.DisplayDocument.truncate;

public class Search implements IOOperation {

    // Constructor
    public Search() {
    }

    private Scanner s;

    @Override
    public void oper(User user) {
        s = new Scanner(System.in);
        System.out.println("1. Search by Title"
                + "\n2. Search by ISBN"
                + "\n3. Search by Author"
        );

        int num = s.nextInt();
        s.nextLine();

        switch (num) {
            case 1:
                searchDocuments("title");
                break;

            case 2:
                searchDocuments("isbn");
                break;

            case 3:
                searchDocuments("author");
                break;

            default:
                System.out.println("Action is not supported\n");
                break;
        }

        user.menu(user);
    }

    // General search method for all types
    private void searchDocuments(String searchType) {
        String keyword = "";
        if ("title".equals(searchType)) {
            System.out.println("Enter document's title keyword: ");
            keyword = s.nextLine();
        } else if ("isbn".equals(searchType)) {
            System.out.println("Enter document's ISBN: ");
            keyword = s.nextLine();
        } else if ("author".equals(searchType)) {
            System.out.println("Enter document's author: ");
            keyword = s.nextLine();
        }

        ArrayList<Document> documents = getDocuments(searchType, keyword);

        if (documents.isEmpty()) {
            System.out.println("Document not found");
        } else {
            displayDocuments(documents);
        }
    }

    // Fetch documents based on the search type
    private ArrayList<Document> getDocuments(String searchType, String keyword) {
        switch (searchType) {
            case "title":
                return DocumentDatabase.getInstance().searchByKeyword(keyword);
            case "isbn":
                return DocumentDatabase.getInstance().searchByIsbn(keyword);
            case "author":
                return DocumentDatabase.getInstance().searchByAuthor(keyword);
            default:
                return new ArrayList<>();
        }
    }

    // Method to display document results
    private void displayDocuments(ArrayList<Document> documents) {
        System.out.printf("%-40s %-30s %-40s %-20s %-10s %-10s %-20s\n",
                "Title", "Author", "Publisher", "ISBN", "Qty", "Price", "Brw cps");

        for (Document document : documents) {
            System.out.printf("%-40s %-30s %-40s %-20s %-10d %-10.2f %-20d\n",
                    truncate(document.getTitle(), 40),
                    truncate(document.getAuthor(), 30),
                    truncate(document.getPublisher(), 40),
                    document.getIsbn(),
                    document.getQty(),
                    document.getPrice(),
                    document.getBrwcopiers()
            );
        }
    }
}
