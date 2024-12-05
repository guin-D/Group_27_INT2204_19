package LibraryManagement.commandline;

import LibraryManagement.Database.DocumentDatabase;

import java.util.ArrayList;

public class DisplayDocument implements IOOperation {

    public static String truncate(String str, int maxLength) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength - 3) + "...";
        }
        return str;
    }

    @Override
    public void oper(User user) {
        ArrayList<Document> documents = DocumentDatabase.getInstance().selectAll();
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
        System.out.println("\n");

        user.menu(user);
        System.out.println("End ... ");
    }
}
