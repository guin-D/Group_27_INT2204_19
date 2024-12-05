package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.ArrayList;
import java.util.Scanner;

import static LibraryManagement.commandline.DisplayDocument.truncate;

public class Search implements IOOperation {

    static Scanner s;
    @Override
    public void oper(User user) {

        System.out.println("1. Search by Title"
                + "\n2. Search by ISBN"
                + "\n3. Search by Author"
        );

        int num;
        s = new Scanner(System.in);
        num = s.nextInt();
        switch (num) {
            case 1:
                searchByTitle();
                break;

            case 2:
                searchByISBN();
                break;

            case 3:
                searchByAuthor();
                break;
        }

        user.menu(user);
    }

    public void searchByTitle() {
        System.out.println("Enter document's title keyword: ");
        s.nextLine();
        String keyword = s.nextLine();
        ArrayList<Document> documents = DocumentDatabase.getInstance().searchByKeyword(keyword);
        System.out.printf("%-40s %-30s %-40s %-20s %-10s %-10s %-20s\n",
                "Title", "Author", "Publisher", "ISBN", "Qty", "Price", "Brw cps");

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
    }

    public void searchByISBN() {
        System.out.println("Enter document's ISBN: ");
        s.nextLine();
        String isbn = s.nextLine();
        ArrayList<Document> documents = DocumentDatabase.getInstance().searchByIsbn(isbn);
        System.out.printf("%-40s %-30s %-40s %-20s %-10s %-10s %-20s\n",
                "Title", "Author", "Publisher", "ISBN", "Qty", "Price", "Brw cps");

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
    }

    public void searchByAuthor() {
        System.out.println("Enter document's author: ");
        s.nextLine();
        String author = s.nextLine();
        ArrayList<Document> documents = DocumentDatabase.getInstance().searchByAuthor(author);
        System.out.printf("%-40s %-30s %-40s %-20s %-10s %-10s %-20s\n",
                "Title", "Author", "Publisher", "ISBN", "Qty", "Price", "Brw cps");

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
    }

}
